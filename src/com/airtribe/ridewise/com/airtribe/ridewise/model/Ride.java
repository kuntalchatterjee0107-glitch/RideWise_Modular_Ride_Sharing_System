package com.airtribe.ridewise.model;

public class Ride {
    private final String id;
    private final Rider rider;
    private Driver driver;
    private final double distance;
    private RideStatus status;
    private FareReceipt receipt;

    public Ride(String id, Rider rider, double distance) {
        this.id = id;
        this.rider = rider;
        this.distance = distance;
        this.status = RideStatus.REQUESTED;
    }

    public String getId() { return id; }
    public Rider getRider() { return rider; }
    public Driver getDriver() { return driver; }
    public void setDriver(Driver driver) { this.driver = driver; }
    public double getDistance() { return distance; }
    public RideStatus getStatus() { return status; }
    public void setStatus(RideStatus status) { this.status = status; }
    public FareReceipt getReceipt() { return receipt; }
    public void setReceipt(FareReceipt receipt) { this.receipt = receipt; }

    @Override
    public String toString() {
        String driverName = (driver != null) ? driver.getName() : "None";
        String receiptStr = (receipt != null) ? String.format(", Fare=%.2f", receipt.getAmount()) : "";
        return "Ride{id='" + id + "', rider=" + rider.getName() + ", driver=" + driverName +
                ", distance=" + distance + ", status=" + status + receiptStr + "}";
    }
}