package com.zystems.plantdex;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class CaptureActivity extends AppCompatActivity {

    private ImageButton btnBack;

    private ImageButton btnOpenFolder;
    private ImageButton btnCapture;
    private ImageButton btnCancel;
    private ImageButton btnClassify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnOpenFolder = (ImageButton) findViewById(R.id.btnOpenFolder);
        btnCancel = (ImageButton) findViewById(R.id.btnCancel);
        btnClassify = (ImageButton) findViewById(R.id.btnClassify);
        btnCapture = (ImageButton) findViewById(R.id.btnCapture);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
