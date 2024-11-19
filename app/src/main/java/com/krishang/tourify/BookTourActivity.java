package com.krishang.tourify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookTourActivity extends AppCompatActivity {

    private TextView tourDetailsTextView;
    private TextView startDateTextView;
    private TextView endDateTextView;
    private TextView totalPriceTextView;
    private TextView gstTextView;
    private TextView totalAmountTextView;
    private Button bookNowButton; // Add a Button for booking

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_tour);

        // Initialize the TextViews
        tourDetailsTextView = findViewById(R.id.tourDetailsTextView);
        startDateTextView = findViewById(R.id.startDateTextView);
        endDateTextView = findViewById(R.id.endDateTextView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        gstTextView = findViewById(R.id.gstTextView);
        totalAmountTextView = findViewById(R.id.totalAmountTextView);

        // Initialize the Book Now button
        bookNowButton = findViewById(R.id.bookNowButton);

        // Retrieve the tour details from the intent
        Tour tour = (Tour) getIntent().getSerializableExtra("TOUR_DETAILS");
        String startDate = getIntent().getStringExtra("START_DATE");
        String endDate = getIntent().getStringExtra("END_DATE");

        if (tour != null) {
            // Display tour details
            tourDetailsTextView.setText(tour.toString()); // Customize this based on your Tour object
        }

        // Set the start and end dates
        startDateTextView.setText("Start Date: " + startDate);
        endDateTextView.setText("End Date: " + endDate);

        // Retrieve the price of the tour
        double price = tour.getPrice(); // Assuming you have a getPrice() method in your Tour class

        // Calculate GST (5%)
        double gst = price * 0.05; // 5% GST

        // Calculate total amount including GST
        double totalAmount = price + gst;

        // Display price, GST, and total amount
        totalPriceTextView.setText("Total Price: ₹" + String.format("%.2f", price));
        gstTextView.setText("GST (5%): ₹" + String.format("%.2f", gst));
        totalAmountTextView.setText("Total Amount (Including GST): ₹" + String.format("%.2f", totalAmount));

        // Set the click listener for the Book Now button
        bookNowButton.setOnClickListener(v -> {
            // Start the new activity (TourBookingActivity)
            Intent intent = new Intent(BookTourActivity.this, BookingConfirmationActivity.class);
            // Pass any relevant data to the new activity
            intent.putExtra("TOUR_DETAILS", tour);
            intent.putExtra("START_DATE", startDate);
            intent.putExtra("END_DATE", endDate);

            // Start the activity with transition
            startActivity(intent);
            // Apply transition animation (fade-in, fade-out for example)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }
}
