package com.zystems.plantdex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.zystems.plantdex.adapters.SearchPlantAdapter;
import com.zystems.plantdex.models.Plant;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private EditText txtPlantName;
    private RecyclerView rvResults;
    private SearchPlantAdapter adapter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        txtPlantName = (EditText) findViewById(R.id.txtPlantName);
        rvResults = (RecyclerView) findViewById(R.id.rvResults);
        adapter = new SearchPlantAdapter();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvResults.setLayoutManager(linearLayoutManager);
        rvResults.setAdapter(adapter);

        txtPlantName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (txtPlantName.getRight() - txtPlantName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        loadPlants();

                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void loadPlants(){

        List<Plant> plants = new ArrayList<>();

        Plant plant = new Plant(1, "Onion", "Allium cepa", "This is a short description for the plant Allium cepa, also commonly known as the 'Onion'",
                "The onion also know as allium cepa is a very common plant that is found in many gardes, it is mainly used for cooking corned beef, it is " +
                        "particulatly delicous when sauted first", "", "", "");

        plants.add(plant);
        plants.add(plant);

        adapter.setPlants(plants);

    }
}
