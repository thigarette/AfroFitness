package com.example.thiga.afrofitness_android.models;

public class GymLocation {
    private int id;
    private String location_name;
    private String opening_time;
    private String closing_time;
    private double latitude;
    private double longitude;

    public GymLocation(int id, String location_name, String opening_time, String closing_time, double latitude, double longitude) {
        this.id = id;
        this.location_name = location_name;
        this.opening_time = opening_time;
        this.closing_time = closing_time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getLocationName() {
        return location_name;
    }

    public String getOpeningTime() {
        return opening_time;
    }

    public String getClosingTime() {
        return closing_time;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
