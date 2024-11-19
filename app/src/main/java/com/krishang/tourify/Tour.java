package com.krishang.tourify;

import java.io.Serializable;

public class Tour implements Serializable {

    private int _id;
    private String title;
    private String description;
    private String destination;
    private double price;
    private int Days;
    private String preference;

    public Tour(int _id, String title, String description, String destination, double price, int Days, String preference) {
        this._id = _id;
        this.title = title;
        this.description = description;
        this.destination = destination;
        this.price = price;
        this.Days = Days;
        this.preference = preference;
    }

    public int getId() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return destination;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDays() {
        return Days;
    }

    public String getPreference() {
        return preference;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "Description: " + description + "\n" +
                "Price: ₹" + price + "\n" +
                "Location: " + destination + "\n" +
                "Duration: " + Days + " days\n" +
                "Preference: " + preference;
    }
}
