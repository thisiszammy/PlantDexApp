package com.zystems.plantdex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import okhttp3.internal.connection.RealConnection;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtFirstName, txtMiddleName, txtLastName, txtUsername, txtPassword, txtConfirmPassword;

    private RelativeLayout btnSubmit, layoutLoading, rootLayout;
    private Button btnSubmit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtFirstName = (EditText) findViewById(R.id.txtFirstName);
        txtMiddleName =(EditText) findViewById(R.id.txtMiddleName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);

        btnSubmit = (RelativeLayout) findViewById(R.id.btnSubmit);
        btnSubmit2 = (Button) findViewById(R.id.btnSubmit2);

        layoutLoading = (RelativeLayout) findViewById(R.id.layoutLoading);
        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);

        postRegistration();
    }

    private void postRegistration(){
        layoutLoading.setVisibility(View.VISIBLE);
        rootLayout.setEnabled(false);
    }
}
