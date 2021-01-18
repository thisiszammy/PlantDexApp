package com.zystems.plantdex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zystems.plantdex.models.ApplicationUser;
import com.zystems.plantdex.models.UsersManagementResponse;
import com.zystems.plantdex.viewmodels.UsersManagementResponseViewModel;

import okhttp3.internal.connection.RealConnection;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtFirstName, txtMiddleName, txtLastName, txtUsername, txtPassword, txtConfirmPassword;

    private RelativeLayout btnSubmit, layoutLoading, rootLayout;
    private Button btnSubmit2;
    private UsersManagementResponseViewModel viewModel;

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
        viewModel = new ViewModelProvider(this).get(UsersManagementResponseViewModel.class);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRegistration();
            }
        });

        btnSubmit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRegistration();
            }
        });

        viewModel.getUserManagementResponseObserver().observe(this, new Observer<UsersManagementResponse>() {
            @Override
            public void onChanged(UsersManagementResponse usersManagementResponse) {
                if(usersManagementResponse != null){
                    if(usersManagementResponse.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_LONG).show();
                    }
                }else Toast.makeText(RegisterActivity.this, "Cannot connect to server", Toast.LENGTH_LONG).show();

                rootLayout.setEnabled(true);
                layoutLoading.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void postRegistration(){

        String firstName = txtFirstName.getText().toString().trim();
        String middleName = txtMiddleName.getText().toString().trim();
        String lastName = txtLastName.getText().toString().trim();
        String username = txtUsername.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String confirmPassword = txtConfirmPassword.getText().toString().trim();

        if(firstName.isEmpty()){
            txtFirstName.setError("Field requires input");
            txtFirstName.requestFocus();
            return;
        }

        if(middleName.isEmpty()){
            txtMiddleName.setError("Field requires input");
            txtMiddleName.requestFocus();
            return;
        }

        if(lastName.isEmpty()){
            txtLastName.setError("Field requires input");
            txtLastName.requestFocus();
            return;
        }

        if(username.isEmpty()){
            txtUsername.setError("Field requires input");
            txtUsername.requestFocus();
            return;
        }

        if(password.isEmpty()){
            txtPassword.setError("Field requires input");
            txtPassword.requestFocus();
            return;
        }

        if(confirmPassword.isEmpty()){
            txtConfirmPassword.setError("Field requires input");
            txtConfirmPassword.requestFocus();
            return;
        }

        if(!password.equals(confirmPassword)){
            txtConfirmPassword.setError("Passwords not matching");
            txtConfirmPassword.requestFocus();
            return;
        }

        layoutLoading.setVisibility(View.VISIBLE);
        rootLayout.setEnabled(false);
        viewModel.postRegistrationForm(firstName, middleName, lastName, "sample@xmail.com", "09999999999", "Person", password, password, username);
    }
}
