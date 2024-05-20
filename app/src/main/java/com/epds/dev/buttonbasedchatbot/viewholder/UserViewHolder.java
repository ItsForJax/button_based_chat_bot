package com.epds.dev.buttonbasedchatbot.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epds.dev.buttonbasedchatbot.R;

public class UserViewHolder extends MainViewHolder {
    public TextView userMessage;
    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        this.userMessage = itemView.findViewById(R.id.user_message);
    }
}
