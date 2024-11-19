package com.krishang.tourify; // Change this to your actual package name

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Ensure this is the correct XML layout

        // Initialize views
        Button signUpButton = findViewById(R.id.button); // Sign up/in button
        // App logo ImageView

        // Set up button click listeners
        signUpButton.setOnClickListener(v -> {
            // Navigate to sign-up/sign-in page
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class); // Make sure this activity exists
            startActivity(intent);
        });

    }
}
