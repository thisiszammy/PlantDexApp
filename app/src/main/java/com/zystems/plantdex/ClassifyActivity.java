package com.zystems.plantdex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class ClassifyActivity extends AppCompatActivity{

    private ImageButton btnBack;
    private ImageButton btnClassify;
    private ImageButton btnCancel;
    private ImageButton btnOpenFolder;
    private ImageButton btnCapture;
    private ImageView imgView;

    private int REQ_CAPTURE_IMAGE = 1;
    private int REQ_OPEN_GALLERY = 2;

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
                startActivity(new Intent(ClassifyActivity.this, ClassifyResultsActivity.class));
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
            if(resultCode == RESULT_OK) loadImageFromFile(data.getData());
        }

        btnClassify.setVisibility(View.GONE);
    }

    private void loadImageFromFile(File file){
        Uri uri = Uri.fromFile(file);
        imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgView.setImageURI(uri);
        imgView.setRotation(90f);
        btnClassify.setVisibility(View.VISIBLE);
    }

    private void loadImageFromFile(Uri uri){
        imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgView.setImageURI(uri);
        btnClassify.setVisibility(View.VISIBLE);
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, REQ_OPEN_GALLERY);
    }
}
