package com.zystems.plantdex;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.zystems.plantdex.adapters.CustomMapFragment;
import com.zystems.plantdex.models.Plant;
import com.zystems.plantdex.models.PlantClassificationResult;
import com.zystems.plantdex.models.PlantLocation;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class PlantDetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    private ImageButton btnBack;
    private GoogleMap googleMap;
    private ScrollView scrollContainer;
    private Plant selectedPlant;
    private ImageView imgPlant;

    private TextView txtScientificName, txtCommonName, txtShortDescription,
        txtLongDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        CustomMapFragment mapFragment = (CustomMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        scrollContainer = (ScrollView) findViewById(R.id.scrollContainer);

        txtScientificName = (TextView) findViewById(R.id.txtScientificName);
        txtCommonName = (TextView) findViewById(R.id.txtCommonName);
        txtShortDescription = (TextView) findViewById(R.id.txtShortDescription);
        txtLongDescription = (TextView) findViewById(R.id.txtLongDescription);
        imgPlant = (ImageView) findViewById(R.id.imgPlant);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        int selectedPlantId = getIntent().getIntExtra(ApplicationUtilities.SEARCH_SELECTED_PLANT, 0);
        if(selectedPlantId == 0) finish();
        loadPlantData(selectedPlantId);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        Gson gson = new Gson();
        if(selectedPlant.getLocations() != null) {
            JsonArray jsonArray = (new JsonParser()).parse(selectedPlant.getLocations()).getAsJsonArray();
            List<PlantLocation> plants = new ArrayList<>();

            for (JsonElement element : jsonArray) {
                plants.add(gson.fromJson(element, PlantLocation.class));
            }


            if (plants.size() > 0) {
                LatLng latestLatLng;
                for (PlantLocation plantLocation : plants) {
                    LatLng location = new LatLng(plantLocation.getLatitude(), plantLocation.getLongitude());
                    googleMap.addMarker(new MarkerOptions()
                            .position(location)
                            .title(plantLocation.getLocationName()));

                    if (plantLocation == plants.get(plants.size() - 1))
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));

                }
            }

        }

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

    private void loadPlantData(int plantId){
        this.selectedPlant = null;

        if(ApplicationUtilities.getSearchPlantsByNameResults() != null){
            for(Plant plant : ApplicationUtilities.getSearchPlantsByNameResults()){
                if(plant.getId() == plantId){
                    this.selectedPlant = plant;
                    break;
                }
            }
        }


        if(ApplicationUtilities.getClassifyPlantsResults() != null && selectedPlant == null){
            for(PlantClassificationResult plant : ApplicationUtilities.getClassifyPlantsResults()){
                if(plant.getPlant().getId() == plantId){
                    this.selectedPlant = plant.getPlant();
                    break;
                }
            }
        }


        Log.d(PlantDetailsActivity.class.getSimpleName(), plantId + "");

        txtLongDescription = (TextView) findViewById(R.id.txtLongDescription);
        txtShortDescription = (TextView) findViewById(R.id.txtShortDescription);
        txtCommonName = (TextView) findViewById(R.id.txtCommonName);
        txtScientificName = (TextView) findViewById(R.id.txtScientificName);
        txtCommonName.setText(selectedPlant.getCommonName());

        txtShortDescription.setText(selectedPlant.getShortDescription());
        txtLongDescription.setText(selectedPlant.getDescription());

        SpannableString spannableString = new SpannableString(selectedPlant.getScientificName());
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        txtScientificName.setText(spannableString);

        if(selectedPlant.getPlantImage() != null){
            byte[] plantImage = Base64.decode(selectedPlant.getPlantImage(), Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(plantImage, 0, plantImage.length);
            imgPlant.setImageBitmap(bmp);
        }

    }

}
