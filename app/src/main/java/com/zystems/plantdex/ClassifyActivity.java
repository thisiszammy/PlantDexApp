package com.zystems.plantdex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CAPTURE_IMAGE) {
            if(resultCode == RESULT_OK){
                String returnedImagePath = data.getData().toString();
                File file = new File(returnedImagePath);
                loadImageFromFile(file);
                Log.d(ClassifyActivity.class.getSimpleName(), "Capture Image");
                return;
            }
        }else if(requestCode == REQ_OPEN_GALLERY){
            if(resultCode == RESULT_OK){
                loadImageFromFile(data.getData());
                Log.d(ClassifyActivity.class.getSimpleName(), "Open Image");
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
        this.selectedImageFile = new File(getImageRealPath(getContentResolver(), uri, null));
        imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgView.setImageURI(uri);
        btnClassify.setVisibility(View.VISIBLE);
    }

    private String getImageRealPath(ContentResolver contentResolver, Uri uri, String whereClause)
    {
        String ret = "";
        // Query the uri with condition.
        Cursor cursor = contentResolver.query(uri, null, whereClause, null, null);
        if(cursor!=null)
        {
            boolean moveToFirst = cursor.moveToFirst();
            if(moveToFirst)
            {
                // Get columns name by uri type.
                String columnName = MediaStore.Images.Media.DATA;
                if( uri==MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
                {
                    columnName = MediaStore.Images.Media.DATA;
                }else if( uri==MediaStore.Audio.Media.EXTERNAL_CONTENT_URI )
                {
                    columnName = MediaStore.Audio.Media.DATA;
                }else if( uri==MediaStore.Video.Media.EXTERNAL_CONTENT_URI )
                {
                    columnName = MediaStore.Video.Media.DATA;
                }
                // Get column index.
                int imageColumnIndex = cursor.getColumnIndex(columnName);
                // Get column value which is the uri related file local path.
                ret = cursor.getString(imageColumnIndex);
            }
        }
        return ret;
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

        new ClassifierObserverTask().execute();
    }

    private class ClassifierObserverTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            rootLayout.setEnabled(false);
            layoutLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            viewModel.postPlantClassificationRequest(selectedImageFile.getPath());
            return null;
        }
    }
}
