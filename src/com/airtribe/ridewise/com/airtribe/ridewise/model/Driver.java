package com.airtribe.ridewise.model;

public class Driver {
    private final String id;
    private final String name;
    private double currentLocation;
    private boolean available;
    private int completedRidesCount;

    public Driver(String id, String name, double currentLocation) {
        this.id = id;
        this.name = name;
        this.currentLocation = currentLocation;
        this.available = true;
        this.completedRidesCount = 0;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(double currentLocation) { this.currentLocation = currentLocation; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public int getCompletedRidesCount() { return completedRidesCount; }
    public void incrementCompletedRides() { this.completedRidesCount++; }

    @Override
    public String toString() {
        return "Driver{id='" + id + "', name='" + name + "', currentLocation=" + currentLocation +
                ", available=" + available + ", completedRides=" + completedRidesCount + "}";
    }
}