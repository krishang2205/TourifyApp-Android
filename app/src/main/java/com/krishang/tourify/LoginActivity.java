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

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText username, password;  // These should be for TextInputEditText
    private TextInputLayout usernameLayout, passwordLayout;  // These should be for TextInputLayout

    @SuppressLint({"MissingInflatedId", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2); // Ensure the layout name is correct

        // Initialize TextInputEditText and TextInputLayout from the XML layout
        username = findViewById(R.id.username);  // Change to TextInputEditText ID
        password = findViewById(R.id.password);  // Change to TextInputEditText ID

        usernameLayout = findViewById(R.id.username_layout);  // Change to TextInputLayout ID
        passwordLayout = findViewById(R.id.password_layout);  // Change to TextInputLayout ID

        // Initialize buttons and text views
        Button loginButton = findViewById(R.id.login_button);
        TextView registerLink = findViewById(R.id.register_link);
        TextView forgotPassword = findViewById(R.id.forgot_password);

        // Set onClickListener for the login button
        loginButton.setOnClickListener(v -> handleLogin());

        // Set onClickListener for the register link
        registerLink.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegistrationActivity.class)));

        // Set onClickListener for the forgot password link
        forgotPassword.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class)));
    }

    private void handleLogin() {
        // Retrieve username and password values
        String user = username.getText() != null ? username.getText().toString().trim() : "";
        String pass = password.getText() != null ? password.getText().toString().trim() : "";

        // Basic validation for username and password
        if (user.isEmpty()) {
            usernameLayout.setError("Please enter username");
            return;
        } else {
            usernameLayout.setError(null);
        }

        if (pass.isEmpty()) {
            passwordLayout.setError("Please enter password");
            return;
        } else {
            passwordLayout.setError(null);
        }

        // Perform login logic (e.g., authenticate with server)
        if (user.equals("admin") && pass.equals("1234")) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
        }
    }
}
