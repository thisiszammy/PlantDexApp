package com.zystems.plantdex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.zystems.plantdex.dialogs.AddPlantLocationDialog;
import com.zystems.plantdex.dialogs.RateAppDialog;
import com.zystems.plantdex.dialogs.ReportIssueDialog;
import com.zystems.plantdex.models.CustomerSupportManagementResponse;
import com.zystems.plantdex.viewmodels.CustomerSupportManagementResponseViewModel;

public class MenuActivity extends AppCompatActivity implements RateAppDialog.RateAppDialogCallbacks, ReportIssueDialog.ReportIssueDialogCallbacks {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private Menu navDrawerItems;
    private MenuItem navDrawerItemProfile, navDrawerItemLogin, navDrawerItemLogOut;
    private CustomerSupportManagementResponseViewModel viewModel;
    private RelativeLayout rootLayout, layoutLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationDrawer);
        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);
        layoutLoading = (RelativeLayout) findViewById(R.id.layoutLoading);
        toolbar = findViewById(R.id.toolBar);
        viewModel = new ViewModelProvider(this).get(CustomerSupportManagementResponseViewModel.class);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_close, R.string.nav_drawer_open);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_drawer_login:
                        startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                        break;
                    case R.id.nav_drawer_search:
                        startActivity(new Intent(MenuActivity.this, SearchActivity.class));
                        break;
                    case R.id.nav_drawer_classify:
                        startActivity(new Intent(MenuActivity.this, ClassifyActivity.class));
                        break;
                    case R.id.nav_drawer_contribute:
                        startActivity(new Intent(MenuActivity.this , ContributeActivity.class));
                        break;
                    case R.id.nav_drawer_logout:
                        initAnonymousUser();
                        Toast.makeText(MenuActivity.this, "Successfully Logged Out", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_drawer_rate:
                        if(ApplicationUtilities.getLoggedUser() != null) showRateAppDialog();
                        else Toast.makeText(MenuActivity.this, "You need to be logged in to rate", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_drawer_report:
                        if(ApplicationUtilities.getLoggedUser() != null) showIssueReportDialog();
                        else Toast.makeText(MenuActivity.this, "You need to be logged in to give feedback", Toast.LENGTH_SHORT).show();
                        break;
                }


                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        viewModel.getCustomerSupportManagementResponseObserver().observe(this, new Observer<CustomerSupportManagementResponse>() {
                    @Override
                    public void onChanged(CustomerSupportManagementResponse customerSupportManagementResponse) {
                        if(customerSupportManagementResponse != null){
                            if(customerSupportManagementResponse.isSuccessful()) Toast.makeText(MenuActivity.this, customerSupportManagementResponse.getMessage()+ "!", Toast.LENGTH_LONG).show();
                            else Toast.makeText(MenuActivity.this, customerSupportManagementResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }else Toast.makeText(MenuActivity.this, "Cannot connect to server", Toast.LENGTH_LONG).show();
                        rootLayout.setEnabled(true);
                        layoutLoading.setVisibility(View.INVISIBLE);
                    }
                });

        navDrawerItems = navigationView.getMenu();
        navDrawerItemLogin = navDrawerItems.findItem(R.id.nav_drawer_login);
        navDrawerItemLogOut = navDrawerItems.findItem(R.id.nav_drawer_logout);

        initAnonymousUser();

    }

    @Override
    public void onBackPressed() {
        ApplicationUtilities.setCloseApp(true);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ApplicationUtilities.getLoggedUser() != null) initLoggedUser();
        else initAnonymousUser();
    }

    private void initAnonymousUser(){
        ApplicationUtilities.setLoggedUser(null);

        navDrawerItemLogOut.setVisible(false);
        navDrawerItemProfile.setVisible(false);
        navDrawerItemLogin.setVisible(true);
    }

    private void initLoggedUser(){

        navDrawerItemLogin.setVisible(false);
        navDrawerItemLogOut.setVisible(true);
        navDrawerItemProfile.setVisible(true);
    }

    private void showRateAppDialog(){
        RateAppDialog rateAppDialog = new RateAppDialog();
        rateAppDialog.show(getSupportFragmentManager(), "Rate App");
    }

    private void showIssueReportDialog(){
        ReportIssueDialog dialog = new ReportIssueDialog();
        dialog.show(getSupportFragmentManager(), "Report Issue");
    }

    // Rate App Dialog Callback
    @Override
    public void onClick(int rating) {
        layoutLoading.setVisibility(View.VISIBLE);
        rootLayout.setEnabled(false);
        viewModel.postAppRating(ApplicationUtilities.getLoggedUser(),rating);
    }

    // Issue Report Dialog Callback

    @Override
    public void onClick(String remarks) {
        layoutLoading.setVisibility(View.VISIBLE);
        rootLayout.setEnabled(false);
        viewModel.postIssuedComplaint(ApplicationUtilities.getLoggedUser(),
                ApplicationUtilities.getCurrentAppVersionName(MenuActivity.this),
                android.os.Build.VERSION.RELEASE, remarks);
    }

}
