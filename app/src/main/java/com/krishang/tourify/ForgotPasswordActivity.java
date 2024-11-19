package com.krishang.tourify;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextInputEditText usernameInput, emailInput, newPasswordInput, confirmPasswordInput;
    private TextInputLayout usernameLayout, emailLayout, newPasswordLayout, confirmPasswordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_activity);

        // Initialize components
        usernameInput = findViewById(R.id.username_input);
        emailInput = findViewById(R.id.email_input);
        newPasswordInput = findViewById(R.id.new_password_input);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);

        usernameLayout = findViewById(R.id.username_layout);
        emailLayout = findViewById(R.id.email_layout);
        newPasswordLayout = findViewById(R.id.new_password_layout);
        confirmPasswordLayout = findViewById(R.id.confirm_password_layout);

        Button submitButton = findViewById(R.id.submit_button);
        TextView backToLogin = findViewById(R.id.back_to_login);

        // Submit button click listener
        submitButton.setOnClickListener(v -> handlePasswordReset());

        // Link to go back to Login screen
        backToLogin.setOnClickListener(v -> startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class)));
    }

    private void handlePasswordReset() {
        String username = usernameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String newPassword = newPasswordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        // Clear previous errors
        usernameLayout.setError(null);
        emailLayout.setError(null);
        newPasswordLayout.setError(null);
        confirmPasswordLayout.setError(null);

        // Basic validation
        if (TextUtils.isEmpty(username)) {
            usernameLayout.setError("Username is required");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            emailLayout.setError("Email is required");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError("Invalid email address");
            return;
        }

        if (TextUtils.isEmpty(newPassword)) {
            newPasswordLayout.setError("New password is required");
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordLayout.setError("Confirm password is required");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            confirmPasswordLayout.setError("Passwords do not match");
            return;
        }

        // Call your logic for password reset here
        // For example, you can update the user data in the database or send a reset confirmation

        Toast.makeText(this, "Password reset successful!", Toast.LENGTH_SHORT).show();

        // Navigate back to Login activity after submission
        startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
        finish();
    }
}
