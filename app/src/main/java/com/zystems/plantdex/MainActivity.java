package com.zystems.plantdex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout brandContainer;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.component_fade_in);
        brandContainer = (RelativeLayout) findViewById(R.id.brandContainer);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        brandContainer.setAnimation(animation);

        /*
            TODO 1) : Get remote config
            TODO 2) : Show Update dialog if version mismatch, other wise proceed to next page
         */

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        },2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectToMenu();
            }
        }, 4000);

    }

    private void checkVersion(){

    }

    private void forceUpdateDialog(){

    }

    private void redirectToMenu(){
        startActivity(new Intent(MainActivity.this, MenuActivity.class));
    }
}
