package com.zystems.plantdex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MenuActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private Menu navDrawerItems;
    private MenuItem navDrawerItemProfile, navDrawerItemLogin, navDrawerItemLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationDrawer);
        toolbar = findViewById(R.id.toolBar);

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
                }


                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        initAnonymousUser();
    }

    @Override
    public void onBackPressed() {
        ApplicationUtilities.setCloseApp(true);
        finish();
    }


    public void initAnonymousUser(){
        navDrawerItems = navigationView.getMenu();
        navDrawerItemProfile = navDrawerItems.findItem(R.id.nav_drawer_profile);
        navDrawerItemLogin = navDrawerItems.findItem(R.id.nav_drawer_login);
        navDrawerItemLogOut = navDrawerItems.findItem(R.id.nav_drawer_logout);

        navDrawerItemLogOut.setVisible(false);
        navDrawerItemProfile.setVisible(false);
    }
}
