package com.zystems.plantdex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;

public class ContributeActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private ImageButton btnContribute;
    private ImageButton btnCancel;
    private ImageButton btnOpenFolder;
    private ImageButton btnCapture;
    private ImageView imgView;

    private int REQ_CAPTURE_IMAGE = 1;
    private int REQ_OPEN_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnContribute = (ImageButton) findViewById(R.id.btnContribute);
        btnCancel = (ImageButton) findViewById(R.id.btnCancel);
        btnOpenFolder = (ImageButton) findViewById(R.id.btnOpenFolder);
        btnCapture = (ImageButton) findViewById(R.id.btnCapture);
        imgView = (ImageView) findViewById(R.id.imgView);


        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContributeActivity.this, CaptureActivity.class);
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

        btnContribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContributeActivity.this, ContributeFormActivity.class));
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

        btnContribute.setVisibility(View.INVISIBLE);
    }

    private void loadImageFromFile(File file){
        Uri uri = Uri.fromFile(file);
        imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgView.setImageURI(uri);
        imgView.setRotation(90f);
        ApplicationUtilities.setContributedPlantImageFile(file);
        btnContribute.setVisibility(View.VISIBLE);
    }

    private void loadImageFromFile(Uri uri){
        ApplicationUtilities.setContributedPlantImageFile(new File(getImageRealPath(getContentResolver(),uri,null)));
        imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgView.setImageURI(uri);
        btnContribute.setVisibility(View.VISIBLE);
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, REQ_OPEN_GALLERY);
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

}
