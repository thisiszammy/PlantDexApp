package com.zystems.plantdex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zystems.plantdex.models.ClassifyPlantResponse;
import com.zystems.plantdex.models.Plant;
import com.zystems.plantdex.models.PlantClassificationResult;
import com.zystems.plantdex.models.PlantsManagementResponse;
import com.zystems.plantdex.viewmodels.ClassifyPlantResponseViewModel;
import com.zystems.plantdex.viewmodels.PlantsManagementResponseViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassifyActivity extends AppCompatActivity{

    private ImageButton btnBack;
    private ImageButton btnClassify;
    private ImageButton btnCancel;
    private ImageButton btnOpenFolder;
    private ImageButton btnCapture;
    private ImageView imgView;

    private RelativeLayout layoutLoading;
    private RelativeLayout rootLayout;
    private File selectedImageFile;

    private int REQ_CAPTURE_IMAGE = 1;
    private int REQ_OPEN_GALLERY = 2;

    private ClassifyPlantResponseViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnClassify = (ImageButton) findViewById(R.id.btnClassify);
        btnCancel = (ImageButton) findViewById(R.id.btnCancel);
        btnOpenFolder = (ImageButton) findViewById(R.id.btnOpenFolder);
        btnCapture = (ImageButton) findViewById(R.id.btnCapture);
        imgView = (ImageView) findViewById(R.id.imgView);
        layoutLoading = (RelativeLayout) findViewById(R.id.layoutLoading);
        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);

        ApplicationUtilities.setClassifyPlantsResults(null);

        viewModel = new ViewModelProvider(this).get(ClassifyPlantResponseViewModel.class);

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassifyActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQ_CAPTURE_IMAGE);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnOpenFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classifyImage();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CAPTURE_IMAGE) {
            if(resultCode == RESULT_OK){
                String returnedImagePath = data.getData().toString();
                File file = new File(returnedImagePath);
                loadImageFromFile(file);
                return;
            }
        }else if(requestCode == REQ_OPEN_GALLERY){
            if(resultCode == RESULT_OK){
                loadImageFromFile(data.getData());
                return;
            }
        }

        btnClassify.setVisibility(View.INVISIBLE);
    }

    private void loadImageFromFile(File file){
        Uri uri = Uri.fromFile(file);
        imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgView.setImageURI(uri);
        imgView.setRotation(90f);
        this.selectedImageFile = file;
        btnClassify.setVisibility(View.VISIBLE);
    }

    private void loadImageFromFile(Uri uri){
        this.selectedImageFile = new File(uri.toString());
        imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgView.setImageURI(uri);
        btnClassify.setVisibility(View.VISIBLE);
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, REQ_OPEN_GALLERY);
    }

    private void classifyImage(){

        if(selectedImageFile == null){
            Toast.makeText(ClassifyActivity.this, "Please Set An Image to Classify!", Toast.LENGTH_LONG).show();
            return;
        }

        rootLayout.setEnabled(false);
        layoutLoading.setVisibility(View.VISIBLE);

        viewModel.getClassifyPlantResponseObserver().observe(this, new Observer<ClassifyPlantResponse>() {
            @Override
            public void onChanged(ClassifyPlantResponse classifyPlantResponse) {
                if(classifyPlantResponse != null){
                    if(classifyPlantResponse.isSuccessful()){
                        List<PlantClassificationResult> searchPlantResults = (classifyPlantResponse.getPlantClassificationResults() == null) ? new ArrayList<>() : classifyPlantResponse.getPlantClassificationResults();
                        /*
                        if(classifyPlantResponse.getPlantClassificationResults() != null){
                            if(classifyPlantResponse.getPlantClassificationResults().trim().length() > 2){
                                searchPlantResults = new Gson().fromJson(classifyPlantResponse.getPlantClassificationResults(), new TypeToken<List<PlantClassificationResult>>(){}.getType());
                            }
                        }*/

                        ApplicationUtilities.setClassifyPlantsResults(searchPlantResults);
                        startActivity(new Intent(ClassifyActivity.this, ClassifyResultsActivity.class));
                    }
                    else Toast.makeText(ClassifyActivity.this, classifyPlantResponse.getMessage(), Toast.LENGTH_LONG).show();
                }else Toast.makeText(ClassifyActivity.this, "Cannot connect to server", Toast.LENGTH_LONG).show();

                layoutLoading.setVisibility(View.INVISIBLE);
                rootLayout.setEnabled(true);
            }
        });
        viewModel.postPlantClassificationRequest(selectedImageFile.getPath());
    }
}
