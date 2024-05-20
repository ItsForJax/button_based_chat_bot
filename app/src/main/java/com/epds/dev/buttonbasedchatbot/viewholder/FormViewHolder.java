package com.epds.dev.buttonbasedchatbot.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;

import com.epds.dev.buttonbasedchatbot.R;

public class FormViewHolder extends MainViewHolder {

    public CheckBox PSA_NSO, StudentForm, TOR, scholarShipForm, LOR;
    public Button submit, cancel;

    public FormViewHolder(@NonNull View itemView) {
        super(itemView);
        this.PSA_NSO = itemView.findViewById(R.id.PSA_NSO);
        this.StudentForm = itemView.findViewById(R.id.StudentForm);
        this.TOR = itemView.findViewById(R.id.TOR);
        this.scholarShipForm = itemView.findViewById(R.id.scholarShipForm);
        this.LOR = itemView.findViewById(R.id.LOR);
        this.submit = itemView.findViewById(R.id.submitFormConfirm);
        this.cancel = itemView.findViewById(R.id.submitFormCancel);
    }
}
