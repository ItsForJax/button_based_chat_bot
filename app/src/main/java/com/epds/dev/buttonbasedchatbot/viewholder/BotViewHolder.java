package com.epds.dev.buttonbasedchatbot.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epds.dev.buttonbasedchatbot.R;

public class BotViewHolder extends MainViewHolder {
    public TextView botTextView;
    public BotViewHolder(@NonNull View itemView) {
        super(itemView);
        this.botTextView = itemView.findViewById(R.id.bot_message);
    }
}

