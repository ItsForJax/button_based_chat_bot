package com.epds.dev.buttonbasedchatbot;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.epds.dev.buttonbasedchatbot.chatadapter.ChatAdapter;
import com.epds.dev.buttonbasedchatbot.dataclass.ChatItem;
import com.epds.dev.buttonbasedchatbot.viewholder.FormViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ChatAdapter.OnItemClickListener {
    // Hi
    String lastChosen;
    int currentIndex = 0;
    ArrayList<ChatItem> chatItems;
    RecyclerView recyclerView;
    ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        chatItems = new ArrayList<>();

        chatItems.add(new ChatItem.Bot(getString(R.string.allowance_type)));
        chatItems.add(new ChatItem.Options(Arrays.asList("1", "2", "3", "4", "5", "6", "7")));
        adapter = new ChatAdapter(this, chatItems, recyclerView, this, this, this); // Create your adapter
        showDialog();
    }

    @Override
    public void onCancelClicked(FormViewHolder formView) {
        chatItems.remove(chatItems.size() - 1);
        adapter.notifyItemRemoved(chatItems.size());
        chatItems.add(new ChatItem.Bot("You have cancelled the form, Goodbye!"));
        chatItems.add(new ChatItem.Bot("Thank you for using our service!"));
        chatItems.add(new ChatItem.Bot("Anything else you would like to ask? \n1. Yes\n2. No"));
        chatItems.add(new ChatItem.Options(Arrays.asList("1", "2")));
        adapter.notifyItemInserted(chatItems.size() - 1);
    }


    @Override
    public void onSubmitClicked(FormViewHolder formView) {
        List<String> checkedItems = getStrings(formView);

        String concatenatedString = String.join("", checkedItems);

        chatItems.remove(chatItems.size() - 1);
        adapter.notifyItemRemoved(chatItems.size());
        chatItems.add(new ChatItem.Bot("You have already submitted the following forms: " + concatenatedString));
        chatItems.add(new ChatItem.Bot("Anything else you would like to ask? \n1. Yes\n2. No"));
        chatItems.add(new ChatItem.Options(Arrays.asList("1", "2")));
        adapter.notifyItemInserted(chatItems.size() - 1);
        recyclerView.smoothScrollToPosition(chatItems.size() - 1);
    }

    @NonNull
    private static List<String> getStrings(FormViewHolder formView) {
        List<String> checkedItems = new ArrayList<>();

        // Check each checkbox and add the name to the list if it's checked
        if (formView.scholarShipForm.isChecked()) checkedItems.add("\n\t•Scholarship Form");
        if (formView.LOR.isChecked()) checkedItems.add("\n\t•Letter of Recommendation");
        if (formView.TOR.isChecked()) checkedItems.add("\n\t•Transcript of Records");
        if (formView.PSA_NSO.isChecked()) checkedItems.add("\n\t•PSA/NSO");
        if (formView.StudentForm.isChecked()) checkedItems.add("\n\t•Student Form");
        return checkedItems;
    }

    @Override
    public void onItemClick(String position) {
        Log.i("TESTING", "onItemClick: 1" );
        chatItems.remove(chatItems.size() - 1);
        if (adapter != null) { // Check if adapter is initialized
            adapter.notifyItemRemoved(chatItems.size());
            Log.i("TESTING", "onItemClick: 2" );
        }
        boolean newInter = false;
        chatItems.add(new ChatItem.User("Selected: " + position));
        if (currentIndex == 0) {
            if (position.equals("1") && lastChosen == null) {
                chatItems.add(new ChatItem.Bot("Have you received your STIPEND allowance?\n1. Yes\n2. No"));
                chatItems.add(new ChatItem.Options(Arrays.asList("1", "2")));
            } else if (position.equals("2") && lastChosen == null) {
                chatItems.add(new ChatItem.Bot("Have you received your BOOK allowance?\n1. Yes\n2. No"));
                chatItems.add(new ChatItem.Options(Arrays.asList("1", "2")));
            } else if (position.equals("3") && lastChosen == null) {
                chatItems.add(new ChatItem.Bot("Have you received your LAPTOP allowance?\n1. Yes\n2. No"));
                chatItems.add(new ChatItem.Options(Arrays.asList("1", "2")));
            } else if (position.equals("4") && lastChosen == null) {
                chatItems.add(new ChatItem.Bot("Have you received your THESIS allowance?\n1. Yes\n2. No"));
                chatItems.add(new ChatItem.Options(Arrays.asList("1", "2")));
            } else if (position.equals("5") && lastChosen == null) {
                chatItems.add(new ChatItem.Bot("Have you received your OJT allowance?\n1. Yes\n2. No"));
                chatItems.add(new ChatItem.Options(Arrays.asList("1", "2")));
            } else if (position.equals("6") && lastChosen == null) {
                chatItems.add(new ChatItem.Bot("Have you received your ONE-TIME ATTENDANCE allowance?\n1. Yes\n2. No"));
                chatItems.add(new ChatItem.Options(Arrays.asList("1", "2")));
            } else if (position.equals("7") && lastChosen == null) {
                chatItems.add(new ChatItem.Bot("Have you received your ONE-TIME FINANCIAL allowance?\n1. Yes\n2. No"));
                chatItems.add(new ChatItem.Options(Arrays.asList("1", "2")));
            }
        }

        else if (currentIndex == 1) {
            if (lastChosen.equals("1")) {
                if (position.equals("1")) {
                    chatItems.add(new ChatItem.Bot("Congratulations! You have received your STIPEND allowance!"));
                    chatItems.add(new ChatItem.Form());
                } else {
                    chatItems.add(new ChatItem.Bot("Sorry to here that you have not received your STIPEND allowance!"));
                    chatItems.add(new ChatItem.Form());
                }
            }
            if (lastChosen.equals("2")) {
                if (position.equals("1")) {
                    chatItems.add(new ChatItem.Bot("Congratulations! You have received your BOOK allowance!"));
                    chatItems.add(new ChatItem.Form());
                } else {
                    chatItems.add(new ChatItem.Bot("Sorry to here that you have not received your BOOK allowance!"));
                    chatItems.add(new ChatItem.Form());
                }
            }
            if (lastChosen.equals("3")) {
                if (position.equals("1")) {
                    chatItems.add(new ChatItem.Bot("Congratulations! You have received your LAPTOP allowance!"));
                    chatItems.add(new ChatItem.Form());
                } else {
                    chatItems.add(new ChatItem.Bot("Sorry to here that you have not received your LAPTOP allowance!"));
                    chatItems.add(new ChatItem.Form());
                }
            }
            if (lastChosen.equals("4")) {
                if (position.equals("1")) {
                    chatItems.add(new ChatItem.Bot("Congratulations! You have received your THESIS allowance!"));
                    chatItems.add(new ChatItem.Form());
                } else {
                    chatItems.add(new ChatItem.Bot("Sorry to here that you have not received your THESIS allowance!"));
                    chatItems.add(new ChatItem.Form());
                }
            }
            if (lastChosen.equals("5")) {
                if (position.equals("1")) {
                    chatItems.add(new ChatItem.Bot("Congratulations! You have received your OJT allowance!"));
                    chatItems.add(new ChatItem.Form());
                } else {
                    chatItems.add(new ChatItem.Bot("Sorry to here that you have not received your OJT allowance!"));
                    chatItems.add(new ChatItem.Form());
                }
            }
            if (lastChosen.equals("6")) {
                if (position.equals("1")) {
                    chatItems.add(new ChatItem.Bot("Congratulations! You have received your ONE-TIME ATTENDANCE allowance!"));
                    chatItems.add(new ChatItem.Form());
                } else {
                    chatItems.add(new ChatItem.Bot("Sorry to here that you have not received your ONE-TIME ATTENDANCE allowance!"));
                    chatItems.add(new ChatItem.Form());
                }
            }
            if (lastChosen.equals("7")) {
                if (position.equals("1")) {
                    chatItems.add(new ChatItem.Bot("Congratulations! You have received your ONE-TIME FINANCIAL allowance!"));
                    chatItems.add(new ChatItem.Form());
                } else {
                    chatItems.add(new ChatItem.Bot("Sorry to here that you have not received your ONE-TIME FINANCIAL allowance!"));
                    chatItems.add(new ChatItem.Form());
                }
            }
        }

        else if (currentIndex == 2) {
            if (position.equals("1")) {
                newInter = true;
                lastChosen = null;
                currentIndex = 0;
                chatItems.add(new ChatItem.Bot(getString(R.string.allowance_type)));
                chatItems.add(new ChatItem.Options(Arrays.asList("1", "2", "3", "4", "5", "6", "7")));
            } else if (position.equals("2")) {
                chatItems.add(new ChatItem.Bot("Thank you for your time!"));
            }
        }



        if (!newInter){
            currentIndex++;
            lastChosen = position;
        }



        adapter.notifyItemInserted(chatItems.size() - 1);
        recyclerView.smoothScrollToPosition(chatItems.size() - 1);

    }

    private void showDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_chat);

        // Get the device's screen size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;

        // Calculate the desired height and width for the dialog
        int dialogHeight = (int) (screenHeight * 0.7); // 70% of the device's height
        int dialogWidth = (int) (screenWidth * 0.9);   // 90% of the device's width

        // Set fixed width and height for the dialog
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = dialogWidth;
        layoutParams.height = dialogHeight;
        dialog.getWindow().setAttributes(layoutParams);

        recyclerView = dialog.findViewById(R.id.dialogRecyclerView);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);
        Button submitButton = dialog.findViewById(R.id.submitButton);

        // Setup your RecyclerView with a layout manager and adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Set click listeners for the buttons
        cancelButton.setOnClickListener(v -> {
            // Handle cancel button click
            dialog.dismiss();
        });

        submitButton.setOnClickListener(v -> {
            // Handle submit button click
            // Perform any actions needed
            dialog.dismiss();
        });

        // Show the dialog
        dialog.show();
    }


}
