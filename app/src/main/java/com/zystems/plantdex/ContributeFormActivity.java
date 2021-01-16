package com.zystems.plantdex;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class ContributeFormActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private ImageButton btnExpandMap;

    private EditText txtScientificName;
    private EditText txtCommonName;
    private EditText txtRemarks;

    private RelativeLayout btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute_form);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnExpandMap = (ImageButton) findViewById(R.id.btnExpandMap);

        txtScientificName = (EditText) findViewById(R.id.txtScientificName);
        txtCommonName = (EditText) findViewById(R.id.txtCommonName);
        txtRemarks = (EditText) findViewById(R.id.txtRemarks);

        btnSubmit = (RelativeLayout) findViewById(R.id.btnSubmit);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
