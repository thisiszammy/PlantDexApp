package com.zystems.plantdex;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.zystems.plantdex.adapters.CustomMapFragment;

public class ContributeMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ImageButton btnBack, btnAddLocation;
    private Button btnSaveMap;

    private GoogleMap googleMap;
    private ScrollView scrollContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute_map);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnSaveMap = (Button) findViewById(R.id.btnSaveMap);
        btnAddLocation = (ImageButton) findViewById(R.id.btnAddLocation);

        CustomMapFragment mapFragment = (CustomMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        scrollContainer = (ScrollView) findViewById(R.id.scrollContainer);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
}
