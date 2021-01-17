package com.zystems.plantdex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zystems.plantdex.adapters.CustomMapFragment;
import com.zystems.plantdex.models.ContributionsManagementResponse;
import com.zystems.plantdex.models.Plant;
import com.zystems.plantdex.models.PlantLocation;
import com.zystems.plantdex.viewmodels.ContributionsManagementResponseViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContributeFormActivity extends AppCompatActivity  implements OnMapReadyCallback{

    private ImageButton btnBack;
    private ImageButton btnExpandMap;

    private EditText txtScientificName;
    private EditText txtCommonName;
    private EditText txtRemarks;

    private RelativeLayout btnSubmit;

    private GoogleMap googleMap;
    private ScrollView scrollContainer;

    private RelativeLayout rootLayout, layoutLoading;
    private ContributionsManagementResponseViewModel viewModel;
    private Button btnSubmit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute_form);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnExpandMap = (ImageButton) findViewById(R.id.btnExpandMap);

        txtScientificName = (EditText) findViewById(R.id.txtScientificName);
        txtCommonName = (EditText) findViewById(R.id.txtCommonName);
        txtRemarks = (EditText) findViewById(R.id.txtRemarks);
        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);
        layoutLoading = (RelativeLayout) findViewById(R.id.layoutLoading);
        ApplicationUtilities.setHasChanged(false);
        ApplicationUtilities.setContributePlantLocations(null);
        viewModel = new ViewModelProvider(this).get(ContributionsManagementResponseViewModel.class);

        btnSubmit = (RelativeLayout) findViewById(R.id.btnSubmit);
        btnSubmit2 = (Button) findViewById(R.id.btnSubmit2);

        CustomMapFragment mapFragment = (CustomMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        scrollContainer = (ScrollView) findViewById(R.id.scrollContainer);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnExpandMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContributeFormActivity.this, ContributeMapActivity.class));
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContributionForm();
            }
        });

        btnSubmit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContributionForm();
            }
        });

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        LatLng sydney = new LatLng(-34, 151);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        ((CustomMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .setListener(new CustomMapFragment.OnTouchListener() {
                    @Override
                    public void onTouch()
                    {
                        scrollContainer.requestDisallowInterceptTouchEvent(true);
                    }
                });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                scrollContainer.requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_UP:
                scrollContainer.requestDisallowInterceptTouchEvent(false);
                break;
        }

        super.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ApplicationUtilities.isHasChanged()){
            if(googleMap != null) loadLocationsMarkers();
        }
    }


    private void loadLocationsMarkers(){
        if(googleMap != null){
            googleMap.clear();

            for(PlantLocation plantLocation : ApplicationUtilities.getContributePlantLocations()){
                LatLng _location = new LatLng(plantLocation.getLatitude(), plantLocation.getLongitude());
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(_location));

                googleMap.addMarker(new MarkerOptions()
                        .position(_location)
                        .title(plantLocation.getLocationName()));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(_location));
            }
        }
    }

    private void saveContributionForm(){

        rootLayout.setEnabled(false);
        layoutLoading.setVisibility(View.VISIBLE);

        String remarks = txtRemarks.getText().toString().trim();
        String scientificName = txtScientificName.getText().toString().trim();
        String commonName = txtCommonName.getText().toString().trim();

        if(remarks.isEmpty()){
            txtRemarks.setError("Field is required");
            txtRemarks.requestFocus();
            return;
        }

        if(scientificName.isEmpty()){
            txtScientificName.setError("Field is required");
            txtScientificName.requestFocus();
            return;
        }

        if(commonName.isEmpty()){
            txtCommonName.setError("Field is required");
            txtCommonName.requestFocus();
            return;
        }


        viewModel.getContributionsManagementResponseObserver().observe(ContributeFormActivity.this, new Observer<ContributionsManagementResponse>() {
            @Override
            public void onChanged(ContributionsManagementResponse contributionsManagementResponse) {
                if(contributionsManagementResponse != null){
                    if(contributionsManagementResponse.isSuccessful()){
                        startActivity(new Intent(ContributeFormActivity.this, ContributionSuccessActivity.class));
                    }
                    else Toast.makeText(ContributeFormActivity.this, contributionsManagementResponse.getMessage(), Toast.LENGTH_LONG).show();
                }else Toast.makeText(ContributeFormActivity.this, "Cannot connect to server", Toast.LENGTH_LONG).show();

                rootLayout.setEnabled(true);
                layoutLoading.setVisibility(View.INVISIBLE);
            }
        });

        viewModel.postContributionSubmission(scientificName, commonName, remarks, ApplicationUtilities.getContributePlantLocations());
    }
}
