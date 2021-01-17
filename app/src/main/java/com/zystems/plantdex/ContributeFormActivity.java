package com.zystems.plantdex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zystems.plantdex.adapters.CustomMapFragment;
import com.zystems.plantdex.models.PlantLocation;

public class ContributeFormActivity extends AppCompatActivity  implements OnMapReadyCallback{

    private ImageButton btnBack;
    private ImageButton btnExpandMap;

    private EditText txtScientificName;
    private EditText txtCommonName;
    private EditText txtRemarks;

    private RelativeLayout btnSubmit;

    private GoogleMap googleMap;
    private ScrollView scrollContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute_form);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnExpandMap = (ImageButton) findViewById(R.id.btnExpandMap);

        txtScientificName = (EditText) findViewById(R.id.txtScientificName);
        txtCommonName = (EditText) findViewById(R.id.txtCommonName);
        txtRemarks = (EditText) findViewById(R.id.txtRemarks);
        ApplicationUtilities.setHasChanged(false);
        ApplicationUtilities.setContributePlantLocations(null);

        btnSubmit = (RelativeLayout) findViewById(R.id.btnSubmit);

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
}
