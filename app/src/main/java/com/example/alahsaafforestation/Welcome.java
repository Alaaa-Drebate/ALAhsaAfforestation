package com.example.alahsaafforestation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.alahsaafforestation.utils.SharedPrefManager;

public class Welcome extends AppCompatActivity {


    Button mNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mNext = findViewById(R.id.next);

        mNext.setOnClickListener(v -> goToLogInActivity());


    }

    private void goToLogInActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}