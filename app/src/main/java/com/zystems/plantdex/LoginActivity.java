package com.zystems.plantdex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zystems.plantdex.models.ApplicationUser;
import com.zystems.plantdex.models.UsersManagementResponse;
import com.zystems.plantdex.viewmodels.UsersManagementResponseViewModel;

public class LoginActivity extends AppCompatActivity {

    private TextView txtSignUp;
    private EditText txtUsername, txtPassword;
    private RelativeLayout rootLayout, layoutLoading;
    private RelativeLayout btnLogin;
    private Button btnLogin2;
    private UsersManagementResponseViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtSignUp = (TextView) findViewById(R.id.txtSignUp);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);
        layoutLoading = (RelativeLayout) findViewById(R.id.layoutLoading);
        btnLogin = (RelativeLayout) findViewById(R.id.btnLogin);
        btnLogin2 = (Button) findViewById(R.id.btnLogin2);
        viewModel = new ViewModelProvider(this).get(UsersManagementResponseViewModel.class);

        SpannableString spannableString = new SpannableString(txtSignUp.getText().toString());
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        txtSignUp.setText(spannableString);

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        viewModel.getUserManagementResponseObserver().observe(this, new Observer<UsersManagementResponse>() {
            @Override
            public void onChanged(UsersManagementResponse usersManagementResponse) {

                if(usersManagementResponse != null){
                    if(usersManagementResponse.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
                        ApplicationUtilities.setLoggedUser(usersManagementResponse.get_Id());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 2000);
                    }
                    else{
                        String error = usersManagementResponse.getMessage();
                        if(usersManagementResponse.getErrors() != null){
                            for(String _error : usersManagementResponse.getErrors()){
                                error += " - " + _error;
                            }
                        }
                        Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
                    }
                }else Toast.makeText(LoginActivity.this, "Cannot connect to server", Toast.LENGTH_LONG).show();

                rootLayout.setEnabled(true);
                layoutLoading.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void login(){
        String username = txtUsername.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if(username.isEmpty()){
            txtUsername.setError("Field requires input");
            txtUsername.requestFocus();
            return;
        }

        if(password.isEmpty()){
            txtPassword.setError("Field requires input");
            txtPassword.requestFocus();
        }

        rootLayout.setEnabled(false);
        layoutLoading.setVisibility(View.VISIBLE);
        viewModel.postLoginform(username, password);
    }
}
