package com.krishang.tourify;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText regUsername, regPassword, regConfirmPassword;
    private TextInputLayout usernameLayout, passwordLayout, confirmPasswordLayout;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration);

        // Initialize components from the layout
        regUsername = findViewById(R.id.reg_username);
        regPassword = findViewById(R.id.reg_password);
        regConfirmPassword = findViewById(R.id.reg_confirm_password);

        // Directly reference the TextInputLayouts
        usernameLayout = findViewById(R.id.username_layout);
        passwordLayout = findViewById(R.id.password_layout);
        confirmPasswordLayout = findViewById(R.id.confirm_password_layout);

        Button registerButton = findViewById(R.id.register_button);
        TextView loginLink = findViewById(R.id.login_link);

        // Register button click listener
        registerButton.setOnClickListener(v -> handleRegistration());

        // Login link action
        loginLink.setOnClickListener(v -> startActivity(new Intent(RegistrationActivity.this, LoginActivity.class)));
    }

    private void handleRegistration() {
        String username = Objects.requireNonNull(regUsername.getText()).toString().trim();
        String password = Objects.requireNonNull(regPassword.getText()).toString().trim();
        String confirmPassword = Objects.requireNonNull(regConfirmPassword.getText()).toString().trim();

        // Clear previous errors
        usernameLayout.setError(null);
        passwordLayout.setError(null);
        confirmPasswordLayout.setError(null);

        // Basic validation
        if (username.isEmpty()) {
            usernameLayout.setError("Username is required");
            return;
        }

        if (password.isEmpty()) {
            passwordLayout.setError("Password is required");
            return;
        }

        if (confirmPassword.isEmpty()) {
            confirmPasswordLayout.setError("Please confirm your password");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordLayout.setError("Passwords do not match");
            return;
        }

        // Perform registration logic here, e.g., save to a database or authenticate with a server
        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();

        // Navigate to login screen
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        finish();
    }
}

