package com.krishang.tourify;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText startDateInput, endDateInput, budgetInput;
    private Spinner destinationSpinner, preferencesSpinner;
    private Button getRecommendationsButton;
    private ListView packagesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupSpinners();
        configureDatePickers();
        setupGetRecommendationsButton();
    }

    private void initializeViews() {
        startDateInput = findViewById(R.id.start_date_input);
        endDateInput = findViewById(R.id.end_date_input);
        budgetInput = findViewById(R.id.budget_input);
        destinationSpinner = findViewById(R.id.destination_spinner);
        preferencesSpinner = findViewById(R.id.preferences_spinner);
        getRecommendationsButton = findViewById(R.id.get_recommendations_button);
        packagesListView = findViewById(R.id.packagesListView);

        // Disable text input for date fields (enables date picker only)
        startDateInput.setInputType(0);
        endDateInput.setInputType(0);
        startDateInput.setFocusable(false);
        endDateInput.setFocusable(false);
    }

    private void configureDatePickers() {
        startDateInput.setOnClickListener(v -> showDatePicker(startDateInput));
        endDateInput.setOnClickListener(v -> showDatePicker(endDateInput));
    }

    private void showDatePicker(final EditText dateInput) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    String formattedDate = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year);
                    dateInput.setText(formattedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void setupSpinners() {
        // List of destinations
        String[] destinations = {
                "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh",
                "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka",
                "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram",
                "Nagaland", "Odisha", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu",
                "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal",
                "Andaman and Nicobar Islands", "Chandigarh", "Dadra and Nagar Haveli and Daman and Diu",
                "Delhi", "Jammu and Kashmir", "Ladakh", "Lakshadweep", "Puducherry"
        };

        // List of preferences
        String[] preferences = {
                "Historical Tours", "Food Tours", "Outdoor Adventures", "Cultural Tours",
                "Religious Tours", "Beach Getaways", "Wildlife Safaris", "Mountain Treks",
                "Luxury Stays", "Eco-Tours", "Adventure Sports", "City Sightseeing",
                "Water Sports", "Wellness Retreats"
        };

        // Adapters for the spinners
        ArrayAdapter<String> destinationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, destinations);
        destinationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destinationSpinner.setAdapter(destinationAdapter);

        ArrayAdapter<String> preferencesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, preferences);
        preferencesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        preferencesSpinner.setAdapter(preferencesAdapter);
    }

    private void setupGetRecommendationsButton() {
        getRecommendationsButton.setOnClickListener(v -> fetchTourRecommendations());
    }

    private void fetchTourRecommendations() {
        String startDate = startDateInput.getText().toString().trim();
        String endDate = endDateInput.getText().toString().trim();
        String destination = destinationSpinner.getSelectedItem().toString();
        String budget = budgetInput.getText().toString().trim();
        String preference = preferencesSpinner.getSelectedItem().toString();

        if (!validateInputs(startDate, endDate, budget)) {
            return;
        }

        // Check if end date is before start date
        if (isEndDateBeforeStartDate(startDate, endDate)) {
            Toast.makeText(this, "Please enter valid dates. End date cannot be before start date.", Toast.LENGTH_SHORT).show();
            return;
        }

        double userBudget = Double.parseDouble(budget);
        int days = calculateDaysDifference(startDate, endDate);

        // Fetch tours from DatabaseHelper
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Tour> recommendedTours = dbHelper.getBestRecommendedTours(destination, days, userBudget, preference);
        List<Tour> otherTours = dbHelper.getOtherTours(destination);

        if (recommendedTours.isEmpty() && otherTours.isEmpty()) {
            Toast.makeText(this, "No tours found based on your criteria", Toast.LENGTH_SHORT).show();
        } else {
            // Launch the new activity and pass the tours list
            Intent intent = new Intent(MainActivity.this, TourListActivity.class);
            intent.putExtra("RECOMMENDED_TOUR_LIST", new ArrayList<>(recommendedTours));
            intent.putExtra("OTHER_TOUR_LIST", new ArrayList<>(otherTours));
            intent.putExtra("START_DATE", startDate);
            intent.putExtra("END_DATE", endDate);
            startActivity(intent);
        }
    }

    private boolean validateInputs(String startDate, String endDate, String budget) {
        if (startDate.isEmpty() || endDate.isEmpty() || budget.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            Double.parseDouble(budget);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid budget entered", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private int calculateDaysDifference(String startDate, String endDate) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date start = format.parse(startDate);
            Date end = format.parse(endDate);
            assert end != null;
            long difference = end.getTime() - start.getTime();
            return (int) (difference / (1000 * 60 * 60 * 24));
        } catch (Exception e) {
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    private boolean isEndDateBeforeStartDate(String startDate, String endDate) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date start = format.parse(startDate);
            Date end = format.parse(endDate);

            if (start != null && end != null) {
                return end.before(start);
            }
        } catch (Exception e) {
            // If the date format is incorrect
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
