package com.zystems.plantdex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zystems.plantdex.adapters.ClassifyPlantAdapter;
import com.zystems.plantdex.models.Plant;
import com.zystems.plantdex.models.PlantClassificationResult;

import java.util.ArrayList;
import java.util.List;

public class ClassifyResultsActivity extends AppCompatActivity implements ClassifyPlantAdapter.ClassifyPlantAdapterCallbacks {

    private ImageButton btnBack;
    private TextView txtResultsCount;
    private RecyclerView rvResults;

    private ClassifyPlantAdapter classifyPlantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify_results);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        txtResultsCount = (TextView) findViewById(R.id.txtResultsCount);
        rvResults = (RecyclerView) findViewById(R.id.rvResults);
        classifyPlantAdapter = new ClassifyPlantAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvResults.setLayoutManager(linearLayoutManager);
        rvResults.setAdapter(classifyPlantAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadPlants();
    }

    private void loadPlants(){

        List<PlantClassificationResult> classificationResults = ApplicationUtilities.getClassifyPlantsResults();
        String resultsCount = "Found " + classificationResults.size() + " Results";
        txtResultsCount.setText(resultsCount);

        classifyPlantAdapter.setPlantClassificationResultList(classificationResults);
    }

    @Override
    public void onClick(PlantClassificationResult plantClassificationResult) {
        Intent intent = new Intent(new Intent(ClassifyResultsActivity.this, PlantDetailsActivity.class));
        intent.putExtra(ApplicationUtilities.SEARCH_SELECTED_PLANT, plantClassificationResult.getPlant().getId());
        startActivity(intent);
    }
}
