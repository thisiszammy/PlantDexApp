package com.zystems.plantdex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zystems.plantdex.adapters.SearchPlantAdapter;
import com.zystems.plantdex.models.Plant;
import com.zystems.plantdex.models.PlantsManagementResponse;
import com.zystems.plantdex.viewmodels.PlantsManagementResponseViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchPlantAdapter.SearchPlantAdapterCallbacks {

    private ImageButton btnBack;
    private EditText txtPlantName;
    private RecyclerView rvResults;
    private SearchPlantAdapter adapter;
    private PlantsManagementResponseViewModel viewModel;
    private RelativeLayout rootLayout, layoutLoading;
    private TextView txtResultsCount;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        txtPlantName = (EditText) findViewById(R.id.txtPlantName);
        rvResults = (RecyclerView) findViewById(R.id.rvResults);
        adapter = new SearchPlantAdapter(this);
        viewModel = new ViewModelProvider(SearchActivity.this).get(PlantsManagementResponseViewModel.class);
        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);
        layoutLoading = (RelativeLayout) findViewById(R.id.layoutLoading);
        txtResultsCount = (TextView) findViewById(R.id.txtResultsCount);

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

                        if(txtPlantName.getText().toString().trim().isEmpty()){
                            txtPlantName.setError("Please input plant name");
                            txtPlantName.requestFocus();
                            return true;
                        }

                        loadPlants(txtPlantName.getText().toString().trim());

                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void loadPlants(String queryName){

        viewModel.getPlantsManagementResponseObserver().observe(SearchActivity.this, new Observer<PlantsManagementResponse>() {
            @Override
            public void onChanged(PlantsManagementResponse plantsManagementResponse) {
                if(plantsManagementResponse != null){
                    if(plantsManagementResponse.isSuccessful()){
                        List<Plant> searchPlantResults = (plantsManagementResponse.getPlants() == null) ? new ArrayList<>() : plantsManagementResponse.getPlants();
                        adapter.setPlants(searchPlantResults);
                        ApplicationUtilities.setSearchPlantsByNameResults(searchPlantResults);

                        String resultsText = getResources().getString(R.string.label_search_prompt) + " (" + searchPlantResults.size() + " results found)";
                        txtResultsCount.setText(resultsText);
                    }
                    else Toast.makeText(SearchActivity.this, plantsManagementResponse.getMessage(), Toast.LENGTH_LONG).show();
                }else Toast.makeText(SearchActivity.this, "Cannot connect to server", Toast.LENGTH_LONG).show();

                layoutLoading.setVisibility(View.INVISIBLE);
                rootLayout.setEnabled(true);
            }
        });

        new SearchPlantObserverTask().execute(queryName);
    }

    @Override
    public void onClick(Plant plant) {
        Intent intent = new Intent(SearchActivity.this, PlantDetailsActivity.class);
        intent.putExtra(ApplicationUtilities.SEARCH_SELECTED_PLANT, plant.getId());
        startActivity(intent);
    }


    private class SearchPlantObserverTask extends AsyncTask<String, Void, Void>{


        @Override
        protected void onPreExecute() {
            layoutLoading.setVisibility(View.VISIBLE);
            rootLayout.setEnabled(false);
        }


        @Override
        protected Void doInBackground(String... strings) {
            viewModel.getPlantByQueryName(strings[0]);
            return null;
        }

    }
}
