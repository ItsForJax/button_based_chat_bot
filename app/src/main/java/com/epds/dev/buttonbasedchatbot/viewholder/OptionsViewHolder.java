package com.epds.dev.buttonbasedchatbot.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.epds.dev.buttonbasedchatbot.R;

public class OptionsViewHolder extends MainViewHolder {
    public LinearLayout optionsLayout;
    public OptionsViewHolder(@NonNull View itemView) {
        super(itemView);
        this.optionsLayout = itemView.findViewById(R.id.options_layout);
    }
}
