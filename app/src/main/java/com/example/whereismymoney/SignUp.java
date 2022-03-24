package com.example.whereismymoney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//        Button signUpButton = findViewById(R.id.signupButton);
//        signUpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditText email, password, username;
//                email = findViewById(R.id.email_login);
//                password = findViewById(R.id.password);
//                username = findViewById(R.id.username);
//
//                DatabaseHelper db = new DatabaseHelper(SignUp.this);
//                db.createUser(email.getText().toString().trim(), password.getText().toString().trim(), username.getText().toString().trim());
//            }
//        });
    }

    public void toLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onSignUpClick(View view) {
        EditText email, password, username;
        email = findViewById(R.id.email_signup);
        password = findViewById(R.id.password);
        username = findViewById(R.id.username);

        CharSequence cs = email.getText().toString().trim();

        if (TextUtils.isEmpty(cs) || TextUtils.isEmpty(password.getText().toString().trim()) || TextUtils.isEmpty(username.getText().toString().trim()))
        {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
        else {
            boolean result = !(cs == null) && android.util.Patterns.EMAIL_ADDRESS.matcher(cs).matches();
            if (!result) {
                Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            } else {

                DatabaseHelper db = new DatabaseHelper(SignUp.this);
                db.createUser(email.getText().toString().trim(), password.getText().toString().trim(), username.getText().toString().trim());
            }
        }
    }
}