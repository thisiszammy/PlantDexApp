package com.zystems.plantdex.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.zystems.plantdex.R;

public class RateAppDialog extends AppCompatDialogFragment {

    private EditText txtRating;
    private AlertDialog alertDialog;
    private RateAppDialogCallbacks callbacks;

    public interface RateAppDialogCallbacks{
        void onClick(int rating);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v =  inflater.inflate(R.layout.dialog_rate_app, null);

        txtRating = (EditText) v.findViewById(R.id.txtRating);
        builder.setView(v)
                .setTitle( "Rate App ")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(txtRating.getText().toString().trim().isEmpty()){
                            txtRating.setError("Please Input Rating");
                            txtRating.requestFocus();
                            return;
                        }
                        callbacks.onClick(Integer.parseInt(txtRating.getText().toString().trim()));
                        dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setIcon(getResources().getDrawable(R.drawable.ic_alert));

        alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btnOk = ((AlertDialog)alertDialog).getButton(DialogInterface.BUTTON_POSITIVE);
                btnOk.setTextColor(getResources().getColor(R.color.colorPrimaryDim));
            }
        });

        return alertDialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            callbacks = (RateAppDialogCallbacks) context;
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}