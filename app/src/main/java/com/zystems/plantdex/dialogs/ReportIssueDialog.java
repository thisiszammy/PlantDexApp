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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.zystems.plantdex.R;

public class ReportIssueDialog extends AppCompatDialogFragment {

    private EditText txtRating;
    private AlertDialog alertDialog;
    private ReportIssueDialogCallbacks callbacks;

    public interface ReportIssueDialogCallbacks{
        void onClick(String remarks);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v =  inflater.inflate(R.layout.dialog_issue_report, null);

        txtRating = (EditText) v.findViewById(R.id.txtRemarks);
        builder.setView(v)
                .setTitle( "Issue Report ")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(txtRating.getText().toString().trim().isEmpty()){
                            txtRating.setError("Please Input Remarks");
                            txtRating.requestFocus();
                            return;
                        }
                        callbacks.onClick(txtRating.getText().toString().trim());
                        dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setIcon(getResources().getDrawable(R.drawable.ic_report));

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
            callbacks = (ReportIssueDialogCallbacks) context;
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}