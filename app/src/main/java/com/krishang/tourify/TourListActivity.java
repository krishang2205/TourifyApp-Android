package com.krishang.tourify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TourListActivity extends AppCompatActivity {

    private RecyclerView recommendedRecyclerView, otherToursRecyclerView;
    private TextView recommendedHeaderTextView, otherToursHeaderTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_list);

        // Initialize views
        recommendedHeaderTextView = findViewById(R.id.tv_best_recommended);
        otherToursHeaderTextView = findViewById(R.id.tv_other_recommended);
        recommendedRecyclerView = findViewById(R.id.recommendedToursRecyclerView);
        otherToursRecyclerView = findViewById(R.id.otherToursRecyclerView);

        // Set layout managers for the RecyclerViews
        recommendedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        otherToursRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();

        // Retrieve the tours passed from MainActivity
        List<Tour> recommendedTours = (List<Tour>) getIntent().getSerializableExtra("RECOMMENDED_TOUR_LIST");
        List<Tour> otherTours = (List<Tour>) getIntent().getSerializableExtra("OTHER_TOUR_LIST");
        String startDate = intent.getStringExtra("START_DATE");
        String endDate = intent.getStringExtra("END_DATE");

        // Check if there are recommended tours
        if (recommendedTours != null && !recommendedTours.isEmpty()) {
            TourAdapter recommendedAdapter = new TourAdapter(this,recommendedTours, startDate, endDate);
            recommendedRecyclerView.setAdapter(recommendedAdapter);
            recommendedHeaderTextView.setText("Best Recommended Tours");
        } else {
            recommendedHeaderTextView.setText("No Best Recommended Tours Found");
            Toast.makeText(this, "No recommended tours available", Toast.LENGTH_SHORT).show();
        }

        // Check if there are other tours in the same location
        if (otherTours != null && !otherTours.isEmpty()) {
            TourAdapter otherToursAdapter = new TourAdapter(this,otherTours, startDate, endDate);
            otherToursRecyclerView.setAdapter(otherToursAdapter);
            otherToursHeaderTextView.setText("Other Tours in the Same Destination");
        } else {
            otherToursHeaderTextView.setText("No Other Tours Found");
            Toast.makeText(this, "No other tours available", Toast.LENGTH_SHORT).show();
        }
    }
}
