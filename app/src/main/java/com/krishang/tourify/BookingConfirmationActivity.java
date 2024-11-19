package com.krishang.tourify;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;


public class BookingConfirmationActivity extends AppCompatActivity {

    private ImageView logoImageView;
    private Button backToMainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour_booking_confirm); // Reference to the XML layout
        backToMainButton = findViewById(R.id.back_to_main_button);

        // Initialize the ImageView to display the logo
        logoImageView = findViewById(R.id.logo_image);

        // Set the logo image resource (you can replace it with your actual logo drawable)
        logoImageView.setImageResource(R.drawable.ic_logo2); // Replace "ic_logo2" with your actual logo

        // Set up the click listener for the "Back to Main" button
        backToMainButton.setOnClickListener(view -> {
            // Intent to go back to MainActivity
            Intent intent = new Intent(BookingConfirmationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }

}

