package com.zystems.plantdex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zystems.plantdex.models.RemoteConfigResponse;
import com.zystems.plantdex.viewmodels.RemoteConfigResponseViewModel;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout brandContainer;
    private ProgressBar progressBar;
    private RemoteConfigResponseViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.component_fade_in);
        brandContainer = (RelativeLayout) findViewById(R.id.brandContainer);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        brandContainer.setAnimation(animation);
        ApplicationUtilities.setCloseApp(false);
        viewModel = new ViewModelProvider(MainActivity.this).get(RemoteConfigResponseViewModel.class);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                getRemoteConfig();
            }
        },2000);


    }

    private void getRemoteConfig(){
        viewModel.getRemoteConfigObserver().observe(MainActivity.this, new Observer<RemoteConfigResponse>() {
            @Override
            public void onChanged(RemoteConfigResponse remoteConfigResponse) {
                if(remoteConfigResponse != null) {
                    if(!remoteConfigResponse.isSuccessful){
                        showApiErrorResponse(remoteConfigResponse);
                        return;
                    }

                    if(remoteConfigResponse.version.equals(ApplicationUtilities.getCurrentAppVersionName(MainActivity.this))) redirectToMenu();

                    forceUpdateDialog();
                }
                showApiErrorResponse(null);
            }
        });
        viewModel.getRemoteConfig();
    }

    private void forceUpdateDialog(){
        Toast.makeText(MainActivity.this, "Please Update the App!", Toast.LENGTH_LONG).show();
    }

    private void showApiErrorResponse(RemoteConfigResponse response){
        Toast.makeText(MainActivity.this, (response == null) ? "Cannot connect to the server!" : response.message, Toast.LENGTH_LONG).show();
    }

    private void redirectToMenu(){
        /*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectToMenu();
            }
        }, 4000);
         */

        startActivity(new Intent(MainActivity.this, MenuActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ApplicationUtilities.isCloseApp()) finish();
    }
}
