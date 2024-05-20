package com.epds.dev.buttonbasedchatbot.chatadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epds.dev.buttonbasedchatbot.R;
import com.epds.dev.buttonbasedchatbot.dataclass.ChatItem;
import com.epds.dev.buttonbasedchatbot.viewholder.BotViewHolder;
import com.epds.dev.buttonbasedchatbot.viewholder.FormViewHolder;
import com.epds.dev.buttonbasedchatbot.viewholder.MainViewHolder;
import com.epds.dev.buttonbasedchatbot.viewholder.OptionsViewHolder;
import com.epds.dev.buttonbasedchatbot.viewholder.UserViewHolder;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private RecyclerView recyclerView;
    private static final int TYPE_USER = 0;
    private static final int TYPE_BOT = 1;
    private static final int TYPE_MULTI_QUESTION_BOT = 2;

    private static final int TYPE_FORM = 3;

    Button button;

    private List<ChatItem> items;
    private Context context;
    private OnItemClickListener listener, cancelListener, confirmListener;

    public interface OnItemClickListener {
        void onItemClick(String position);
        void onCancelClicked(FormViewHolder formView);
        void onSubmitClicked(FormViewHolder formView);
    }

    public ChatAdapter(Context context, List<ChatItem> items, RecyclerView recyclerView,
                       OnItemClickListener listener, OnItemClickListener cancelListener, OnItemClickListener confirmListener) {
        this.context = context;
        this.items = items;
        this.recyclerView = recyclerView;
        this.listener = listener;
        this.confirmListener = confirmListener;
        this.cancelListener = cancelListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof ChatItem.User) {
            return TYPE_USER;
        } else if (items.get(position) instanceof ChatItem.Bot) {
            return TYPE_BOT;
        } else if (items.get(position) instanceof ChatItem.Options) {
            return TYPE_MULTI_QUESTION_BOT;
        } else if (items.get(position) instanceof ChatItem.Form) {
            return TYPE_FORM;
        }
        throw new IllegalArgumentException("Invalid view type");
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_USER) {
            View view = inflater.inflate(R.layout.user_message, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == TYPE_BOT) {
            View view = inflater.inflate(R.layout.bot_message, parent, false);
            return new BotViewHolder(view);
        } else if (viewType == TYPE_MULTI_QUESTION_BOT) {
            View view = inflater.inflate(R.layout.options_layout, parent, false);
            return new OptionsViewHolder(view);
        } else if (viewType == TYPE_FORM) {
            View view = inflater.inflate(R.layout.checkbox_requirements, parent, false);
            return new FormViewHolder(view);
        }
        throw new IllegalArgumentException("Invalid view type");
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            ChatItem.User userItem = (ChatItem.User) items.get(position);
            ((UserViewHolder) holder).userMessage.setText(userItem.message);
        } else if (holder instanceof BotViewHolder) {
            ChatItem.Bot botItem = (ChatItem.Bot) items.get(position);
            ((BotViewHolder) holder).botTextView.setText(botItem.message);
        } else if (holder instanceof FormViewHolder) {
            FormViewHolder formView = (FormViewHolder) holder;

            formView.cancel.setOnClickListener(v -> {
                cancelListener.onCancelClicked(formView);
            });

            formView.submit.setOnClickListener(v -> {
                confirmListener.onSubmitClicked(formView);
            });
        }  else if (holder instanceof OptionsViewHolder) {
            ChatItem.Options options = (ChatItem.Options) items.get(position);
            OptionsViewHolder optionsViewHolder = (OptionsViewHolder) holder;
            optionsViewHolder.optionsLayout.removeAllViews();

            // Flag to track if a button has been clicked
            boolean[] buttonClicked = {false};

            for (int i = 0; i < options.questions.size(); i++) {
                String question = options.questions.get(i);
                View buttonView = LayoutInflater.from(context).inflate(R.layout.button_option, optionsViewHolder.optionsLayout, false);
                Button button = buttonView.findViewById(R.id.option_button);
                button.setText(question);

                // Set OnClickListener
                button.setOnClickListener(v -> {
                    if (!buttonClicked[0]) {
                        // Disable all buttons
                        for (int j = 0; j < optionsViewHolder.optionsLayout.getChildCount(); j++) {
                            View child = optionsViewHolder.optionsLayout.getChildAt(j);
                            if (child instanceof ViewGroup) {
                                ViewGroup childGroup = (ViewGroup) child;
                                for (int k = 0; k < childGroup.getChildCount(); k++) {
                                    View childButtonView = childGroup.getChildAt(k);
                                    if (childButtonView instanceof Button) {
                                        childButtonView.setEnabled(false);
                                    }
                                }
                            } else if (child instanceof Button) {
                                child.setEnabled(false);
                            }
                        }

                        // Set the flag to true to prevent further clicks
                        buttonClicked[0] = true;

                        // Perform the click action for the pressed button
                        listener.onItemClick(question);
                    }
                });

                // Apply animation
                Animation popInOutAnimation = AnimationUtils.loadAnimation(context, R.anim.pop_in_out);
                popInOutAnimation.setStartOffset((i + 1) * 100L); // 200ms delay between each button
                buttonView.startAnimation(popInOutAnimation);

                // Add the button view to the layout
                optionsViewHolder.optionsLayout.addView(buttonView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
