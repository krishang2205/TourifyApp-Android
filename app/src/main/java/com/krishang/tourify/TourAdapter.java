package com.krishang.tourify;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.TourViewHolder> {

    private Context context;
    private List<Tour> tourList;
    private String startDate;
    private String endDate;

    public TourAdapter(Context context, List<Tour> tourList, String startDate, String endDate) {
        this.context = context;
        this.tourList = tourList;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tour_item, parent, false);
        return new TourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourViewHolder holder, int position) {
        Tour tour = tourList.get(position);

        // Populate the views with tour data
        holder.titleTextView.setText(tour.getTitle());
        holder.descriptionTextView.setText(tour.getDescription());
        holder.priceTextView.setText("Price: ₹" + tour.getPrice());
        holder.locationTextView.setText("Location: " + tour.getLocation());
        holder.durationTextView.setText("Duration: " + tour.getDays() + " days");
        holder.preferenceTextView.setText("Preference: " + tour.getPreference());

        // Set up the click listener for the "Book Tour" button
        holder.bookTourButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookTourActivity.class);
            intent.putExtra("TOUR_DETAILS", tour);
            intent.putExtra("START_DATE", startDate);
            intent.putExtra("END_DATE", endDate);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    // ViewHolder class for RecyclerView
    public static class TourViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView, priceTextView, locationTextView, durationTextView, preferenceTextView;
        Button bookTourButton;

        public TourViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            durationTextView = itemView.findViewById(R.id.durationTextView);
            preferenceTextView = itemView.findViewById(R.id.preferenceTextView);
            bookTourButton = itemView.findViewById(R.id.bookTourButton);
        }
    }
}
