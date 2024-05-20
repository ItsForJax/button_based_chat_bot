package com.epds.dev.buttonbasedchatbot;

import android.os.Bundle;
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
import java.util.Objects;

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

        //Bot starter
        chatItems.add(new ChatItem.Bot("Hello there! Is it hot today?\n1. Yes\n2. No"));
        chatItems.add(new ChatItem.Options(Arrays.asList("1", "2")));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ChatAdapter(this, chatItems, recyclerView, this, this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCancelClicked(FormViewHolder formView) {
        chatItems.remove(chatItems.size() - 1);
        adapter.notifyItemRemoved(chatItems.size());
        chatItems.add(new ChatItem.Bot("You have cancelled the form, Goodbye!"));
        adapter.notifyItemInserted(chatItems.size() - 1);
    }


    @Override
    public void onSubmitClicked(FormViewHolder formView) {
        List<String> checkedItems = getStrings(formView);

        String concatenatedString = String.join("", checkedItems);

        chatItems.remove(chatItems.size() - 1);
        adapter.notifyItemRemoved(chatItems.size());
        chatItems.add(new ChatItem.Bot("You have submitted the following forms: " + concatenatedString));
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
        chatItems.remove(chatItems.size()-1);
        adapter.notifyItemRemoved(chatItems.size());

        chatItems.add(new ChatItem.User("Seleted: " + position));

        if (currentIndex == 0 && position.equals("1") && lastChosen == null) {
            chatItems.add(new ChatItem.Bot("Have you drunk anything today?\n1. Yes\n2. No"));
            chatItems.add(new ChatItem.Options(Arrays.asList("1", "2")));
        } else if (currentIndex == 0 && position.equals("2") && lastChosen == null) {
            chatItems.add(new ChatItem.Bot("Is it raining?\n1. Yes\n2. No"));
            chatItems.add(new ChatItem.Options(Arrays.asList("1", "2")));
        } else if (currentIndex == 1 && Objects.equals(lastChosen, "1") && position.equals("1")) {
            chatItems.add(new ChatItem.Bot("What kind of drink did you have?\n1. Water\n2. Juice\n3. Coffee"));
            chatItems.add(new ChatItem.Options(Arrays.asList("1", "2", "3")));
        } else if (currentIndex == 1 && Objects.equals(lastChosen, "2") && position.equals("1")) {
            chatItems.add(new ChatItem.Bot("It's raining, so you should take an umbrella.\nThanks for chatting with me!"));
        } else if (currentIndex == 1 && Objects.equals(lastChosen, "1") && position.equals("2")) {
            chatItems.add(new ChatItem.Bot("You should drink something today!"));
        } else if (currentIndex == 1 && Objects.equals(lastChosen, "2") && position.equals("2")) {
            chatItems.add(new ChatItem.Bot("It's a nice day, so you can go out without an umbrella."));
            chatItems.add(new ChatItem.Form());
        }

        currentIndex++;
        lastChosen = position;

        adapter.notifyItemInserted(chatItems.size() - 1);
        recyclerView.smoothScrollToPosition(chatItems.size() - 1);

    }

    private void notifyRecycler(String lastChosenSet) {
        currentIndex++;
        lastChosen = lastChosenSet;

        adapter.notifyItemInserted(chatItems.size() - 1);
        recyclerView.smoothScrollToPosition(chatItems.size() - 1);
    }

}

