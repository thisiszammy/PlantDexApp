package com.zystems.plantdex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private TextView txtSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtSignUp = (TextView) findViewById(R.id.txtSignUp);

        SpannableString spannableString = new SpannableString(txtSignUp.getText().toString());
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        txtSignUp.setText(spannableString);
    }
}
