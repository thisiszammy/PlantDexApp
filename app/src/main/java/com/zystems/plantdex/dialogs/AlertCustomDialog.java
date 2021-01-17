package com.zystems.plantdex.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.zystems.plantdex.R;

public class AlertCustomDialog extends AppCompatDialogFragment {

    private TextView txtAlertMessage;
    private AlertDialog alertDialog;
    private ShowAlertDialogCallbacks callbacks;

    public interface ShowAlertDialogCallbacks{
        void onClick();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v =  inflater.inflate(R.layout.dialog_custom_alert, null);

        txtAlertMessage = (TextView) v.findViewById(R.id.txtAlertMessage);
        builder.setView(v)
                .setTitle( "Update Alert")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callbacks.onClick();
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
            callbacks = (ShowAlertDialogCallbacks) context;
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
