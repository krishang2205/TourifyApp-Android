package com.krishang.tourify;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tour.db"; // Name of your database in assets
    @SuppressLint("SdCardPath")
    private static final String DATABASE_PATH = "/data/data/com.krishang.tourify/databases/"; // Path where it will be copied
    private static final int DATABASE_VERSION = 1;

    // Table name and column names should match your existing database
    private static final String TABLE_TOURS = "tours1";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DESTINATION = "location"; // Assuming 'location' matches 'destination'
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DURATION = "days"; // Assuming 'days' is for duration calculation
    private static final String COLUMN_PREFERENCE = "category"; // Assuming 'category' is your 'preference'

    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

        // Check if the database exists, if not, copy from assets
        if (!checkDatabase()) {
            copyDatabase();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // This method will not run if the database is imported
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implement database upgrade logic if needed
    }

    // Check if the database already exists
    private boolean checkDatabase() {
        File dbFile = new File(DATABASE_PATH + DATABASE_NAME);
        return dbFile.exists();
    }

    // Copy the database from assets to internal storage
    private void copyDatabase() {
        try {
            InputStream inputStream = context.getAssets().open(DATABASE_NAME);
            String outputPath = DATABASE_PATH + DATABASE_NAME;
            File outputFile = new File(DATABASE_PATH);
            if (!outputFile.exists()) {
                outputFile.mkdirs(); // Create directory if it doesn't exist
            }

            OutputStream outputStream = new FileOutputStream(outputPath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
            Log.d("DatabaseHelper", "Database copied successfully to " + outputPath);
        } catch (IOException e) {
            Log.e("DatabaseHelper", "Error copying database", e);
        }
    }

    // Get the database to query
    private SQLiteDatabase getDatabase() {
        File dbFile = new File(DATABASE_PATH + DATABASE_NAME);
        if (!dbFile.exists()) {
            Log.e("DatabaseHelper", "Database does not exist at the expected location: " + dbFile.getPath());
        }
        return SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
    }

    // Method to get best recommended tours based on destination, days, budget, and preference
    public List<Tour> getBestRecommendedTours(String location, int days, double budget, String preference) {
        List<Tour> tours = new ArrayList<>();
        SQLiteDatabase db = getDatabase(); // Open the imported database

        // Adjust days to allow for a range of ±2 days
        int minDays = days - 2;
        int maxDays = days + 2;

        // Query for recommended tours based on filters
        String query = "SELECT _id, title, description, location, price, days, category " +
                "FROM " + TABLE_TOURS +
                " WHERE " + COLUMN_DESTINATION + " = ? AND " +
                COLUMN_DURATION + " BETWEEN ? AND ? AND " +  // Modified condition to include days ±2
                COLUMN_PRICE + " <= ? AND " +               // Price must be less than or equal to budget
                COLUMN_PREFERENCE + " = ?";

        String[] selectionArgs = {location, String.valueOf(minDays), String.valueOf(maxDays), String.valueOf(budget), preference};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                @SuppressLint("Range") String tourDestination = cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
                @SuppressLint("Range") int tourDuration = cursor.getInt(cursor.getColumnIndex(COLUMN_DURATION));
                @SuppressLint("Range") String tourPreference = cursor.getString(cursor.getColumnIndex(COLUMN_PREFERENCE));

                Tour tour = new Tour(id, title, description, tourDestination, price, tourDuration, tourPreference);
                tours.add(tour);
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            Log.e("DatabaseHelper", "No tours found for the specified filters");
        }

        db.close(); // Close the database when done
        return tours;
    }

    // Method to get other tours in the same location but not the best ones
    public List<Tour> getOtherTours(String location) {
        List<Tour> tours = new ArrayList<>();
        SQLiteDatabase db = getDatabase();

        // Query for all tours in the same location
        String query = "SELECT _id, title, description, location, price, days, category " +
                "FROM " + TABLE_TOURS +
                " WHERE " + COLUMN_DESTINATION + " = ?";

        String[] selectionArgs = {location};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                @SuppressLint("Range") String tourDestination = cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
                @SuppressLint("Range") int tourDuration = cursor.getInt(cursor.getColumnIndex(COLUMN_DURATION));
                @SuppressLint("Range") String tourPreference = cursor.getString(cursor.getColumnIndex(COLUMN_PREFERENCE));

                Tour tour = new Tour(id, title, description, tourDestination, price, tourDuration, tourPreference);
                tours.add(tour);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return tours;
    }
}
