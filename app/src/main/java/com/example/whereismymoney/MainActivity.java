package com.example.whereismymoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toSignUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void toMainMenu(View view) {
        EditText email, password;
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);

        DatabaseHelper db = new DatabaseHelper(MainActivity.this);
        Cursor cursor = db.loginUser(email.getText().toString().trim(), password.getText().toString().trim());

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            // get data from cursor
            @SuppressLint("Range") int result_id = cursor.getInt(cursor.getColumnIndex("_id"));
            @SuppressLint("Range") String result_email = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range") String result_username = cursor.getString(cursor.getColumnIndex("username"));

            // save data to shared preferences
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
            prefEditor.putString("email", result_email);
            prefEditor.putInt("id", result_id);
            prefEditor.putString("username", result_username);
            prefEditor.apply();

            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }
}