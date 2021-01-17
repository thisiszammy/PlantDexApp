package com.zystems.plantdex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zystems.plantdex.adapters.CustomMapFragment;
import com.zystems.plantdex.adapters.PlantLocationsAdapter;
import com.zystems.plantdex.dialogs.AddPlantLocationDialog;
import com.zystems.plantdex.models.PlantLocation;

import java.util.ArrayList;
import java.util.List;

public class ContributeMapActivity extends AppCompatActivity implements OnMapReadyCallback, AddPlantLocationDialog.AddLocationDialogCallback, PlantLocationsAdapter.PlantLocationsAdapterCallbacks {

    private ImageButton btnBack, btnAddLocation;
    private Button btnSaveMap;

    private GoogleMap googleMap;

    private PlantLocationsAdapter adapter;
    private List<PlantLocation> plantLocations;
    private RecyclerView rvResults;
    private SupportMapFragment mapFragment;
    private CardView locationsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute_map);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnSaveMap = (Button) findViewById(R.id.btnSaveMap);
        btnAddLocation = (ImageButton) findViewById(R.id.btnAddLocation);
        rvResults = (RecyclerView) findViewById(R.id.rvResults);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        locationsContainer = (CardView) findViewById(R.id.locationsContainer);

        adapter = new PlantLocationsAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvResults.setLayoutManager(layoutManager);

        rvResults.setAdapter(adapter);

        if(ApplicationUtilities.getContributePlantLocations() == null) plantLocations = new ArrayList<>();
        else plantLocations = new ArrayList<>(ApplicationUtilities.getContributePlantLocations());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddPlantLocationDialog();
            }
        });

        btnSaveMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationUtilities.setContributePlantLocations(plantLocations);
                ApplicationUtilities.setDumpData(false);
            }
        });

        loadLocationsView();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        loadLocationsMarkers();
    }


    @Override
    public void addLocation(PlantLocation location) {
        plantLocations.add(location);
        loadLocationsView();
        loadLocationsMarkers();
    }

    public void openAddPlantLocationDialog(){
        AddPlantLocationDialog addPlantLocationDialog = new AddPlantLocationDialog();
        addPlantLocationDialog.show(getSupportFragmentManager(), "Add Location");
    }

    @Override
    public void removeLocation(PlantLocation location) {
        plantLocations.remove(location);
        loadLocationsView();
        loadLocationsMarkers();
    }

    @Override
    public void onClickLocation(PlantLocation location) {
        if(googleMap != null) googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
    }

    private void loadLocationsView(){
        adapter.setLocations(plantLocations);
    }

    private void loadLocationsMarkers(){
        if(googleMap != null){
            googleMap.clear();

            for(PlantLocation plantLocation : plantLocations){
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
