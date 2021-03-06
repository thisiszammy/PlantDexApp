package com.zystems.plantdex.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.zystems.plantdex.ApplicationUtilities;
import com.zystems.plantdex.R;
import com.zystems.plantdex.adapters.PlantLocationsAdapter;
import com.zystems.plantdex.models.PlantLocation;

import java.util.ArrayList;
import java.util.List;

public class AddPlantLocationDialog extends AppCompatDialogFragment {

    private EditText txtLocationName, txtLatitude, txtLongitude;
    private AlertDialog alertDialog;

    private AddLocationDialogCallback addLocationDialogCallback;


    public interface AddLocationDialogCallback{
        void addLocation(PlantLocation location);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v =  inflater.inflate(R.layout.dialog_add_location, null);

        txtLocationName = (EditText) v.findViewById(R.id.txtLocationName);
        txtLatitude = (EditText) v.findViewById(R.id.txtLatitude);
        txtLongitude = (EditText) v.findViewById(R.id.txtLongitude);

        builder.setView(v)
                .setTitle( "Add Hotspot")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", null)
                .setIcon(getResources().getDrawable(R.drawable.ic_add_location));

        alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btnAdd = ((AlertDialog)alertDialog).getButton(DialogInterface.BUTTON_POSITIVE);
                Button btnCancel = ((AlertDialog)alertDialog).getButton(DialogInterface.BUTTON_NEGATIVE);

                btnAdd.setTextColor(getResources().getColor(R.color.colorPrimaryDim));
                btnCancel.setTextColor(getResources().getColor(R.color.colorPrimaryDim));


                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String locationName = txtLocationName.getText().toString().trim();
                        String latitude = txtLatitude.getText().toString().trim();
                        String longitude = txtLongitude.getText().toString().trim();

                        if(locationName.isEmpty()){
                            txtLocationName.setError("Location Name is required");
                            txtLocationName.requestFocus();
                            return;
                        }

                        if(latitude.isEmpty()){
                            txtLatitude.setError("Latitude Coordinate is required");
                            txtLatitude.requestFocus();
                            return;
                        }

                        if(longitude.isEmpty()){
                            txtLongitude.setError("Longitude Coordinate is required");
                            txtLongitude.requestFocus();
                            return;
                        }

                        addLocationDialogCallback.addLocation(new PlantLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), locationName));
                        dismiss();
                    }
                });
            }
        });

        return alertDialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            addLocationDialogCallback = (AddLocationDialogCallback) context;
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
