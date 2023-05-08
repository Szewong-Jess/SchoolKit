package com.example.group6_schoolkit.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.group6_schoolkit.R;

public class RegisterActivity extends AppCompatActivity {
    Button buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonLogin=findViewById(R.id.buttonLogin);
        startActivity(new Intent(RegisterActivity.this, activity_login.class));
    }
}