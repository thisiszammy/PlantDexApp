package com.zystems.plantdex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zystems.plantdex.dialogs.AlertCustomDialog;
import com.zystems.plantdex.models.RemoteConfigResponse;
import com.zystems.plantdex.viewmodels.RemoteConfigResponseViewModel;

public class MainActivity extends AppCompatActivity implements AlertCustomDialog.ShowAlertDialogCallbacks {

    private RelativeLayout brandContainer;
    private RelativeLayout containerRefresh;
    private ProgressBar progressBar;
    private RemoteConfigResponseViewModel viewModel;
    private ImageButton btnRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.component_fade_in);
        brandContainer = (RelativeLayout) findViewById(R.id.brandContainer);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        containerRefresh = (RelativeLayout) findViewById(R.id.containerRefresh);
        btnRefresh = (ImageButton) findViewById(R.id.btnRefresh);

        brandContainer.setAnimation(animation);
        ApplicationUtilities.setCloseApp(false);
        viewModel = new ViewModelProvider(MainActivity.this).get(RemoteConfigResponseViewModel.class);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRemoteConfig();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getRemoteConfig();
            }
        },2000);

    }

    private void getRemoteConfig(){
        progressBar.setVisibility(View.VISIBLE);
        containerRefresh.setVisibility(View.INVISIBLE);
        viewModel.getRemoteConfigResponseObserver().observe(MainActivity.this, new Observer<RemoteConfigResponse>() {
            @Override
            public void onChanged(RemoteConfigResponse remoteConfigResponse) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(remoteConfigResponse != null) {
                            if(!remoteConfigResponse.isSuccessful()){
                                showApiErrorResponse(remoteConfigResponse);
                                return;
                            }
                            if(remoteConfigResponse.getVersion().equals(ApplicationUtilities.getCurrentAppVersionName(MainActivity.this))){
                                redirectToMenu();
                                return;
                            }

                            forceUpdateDialog();
                            return;
                        }
                        showApiErrorResponse(null);
                    }
                }, 3000);

            }
        });
        viewModel.getRemoteConfig();
    }

    private void forceUpdateDialog(){
        progressBar.setVisibility(View.INVISIBLE);
        AlertCustomDialog alertCustomDialog = new AlertCustomDialog();
        alertCustomDialog.show(getSupportFragmentManager(), "Update Alert");
    }

    private void showApiErrorResponse(RemoteConfigResponse response){
        containerRefresh.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(MainActivity.this, (response == null) ? "Network Error" : response.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void redirectToMenu(){
        startActivity(new Intent(MainActivity.this, MenuActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ApplicationUtilities.isCloseApp()) finish();
    }

    @Override
    public void onClick() {
        finish();
    }
}
