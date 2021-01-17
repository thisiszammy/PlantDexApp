package com.zystems.plantdex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ContributionSuccessActivity extends AppCompatActivity {

    private RelativeLayout btnHome;
    private Button btnHome2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribution_success);

        btnHome = (RelativeLayout) findViewById(R.id.btnHome);
        btnHome2 = (Button) findViewById(R.id.btnHome2);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToMenu();
            }
        });

        btnHome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToMenu();
            }
        });
    }

    private void redirectToMenu(){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
