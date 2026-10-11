package com.airtribe.ridewise.service;

import com.airtribe.ridewise.exception.NoDriverAvailableException;
import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.FareReceipt;
import com.airtribe.ridewise.model.Ride;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.model.RideStatus;
import com.airtribe.ridewise.strategy.FareStrategy;
import com.airtribe.ridewise.strategy.RideMatchingStrategy;
import com.airtribe.ridewise.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RideService {
    private final DriverService driverService;
    private RideMatchingStrategy matchingStrategy;
    private FareStrategy fareStrategy;
    private final Map<String, Ride> rides = new ConcurrentHashMap<>();

    public RideService(DriverService driverService, RideMatchingStrategy matchingStrategy, FareStrategy fareStrategy) {
        this.driverService = driverService;
        this.matchingStrategy = matchingStrategy;
        this.fareStrategy = fareStrategy;
    }

    public void setMatchingStrategy(RideMatchingStrategy matchingStrategy) {
        this.matchingStrategy = matchingStrategy;
    }

    public void setFareStrategy(FareStrategy fareStrategy) {
        this.fareStrategy = fareStrategy;
    }

    public Ride requestRide(Rider rider, double distance) {
        Ride ride = new Ride(IdGenerator.nextRideId(), rider, distance);
        rides.put(ride.getId(), ride);

        List<Driver> availableDrivers = driverService.listAvailableDrivers();
        Driver matchedDriver = matchingStrategy.findDriver(rider, availableDrivers);

        if (matchedDriver == null) {
            ride.setStatus(RideStatus.CANCELLED);
            throw new NoDriverAvailableException("No drivers available to fulfill ride request.");
        }

        ride.setDriver(matchedDriver);
        ride.setStatus(RideStatus.ASSIGNED);
        driverService.updateAvailability(matchedDriver.getId(), false);

        return ride;
    }

    public FareReceipt completeRide(String rideId) {
        Ride ride = rides.get(rideId);
        if (ride == null) {
            throw new IllegalArgumentException("Ride with ID " + rideId + " does not exist.");
        }
        if (ride.getStatus() != RideStatus.ASSIGNED) {
            throw new IllegalStateException("Ride cannot be completed in status: " + ride.getStatus());
        }

        double fare = fareStrategy.calculateFare(ride);
        FareReceipt receipt = new FareReceipt(ride.getId(), fare);
        ride.setReceipt(receipt);
        ride.setStatus(RideStatus.COMPLETED);

        Driver driver = ride.getDriver();
        if (driver != null) {
            driver.incrementCompletedRides();
            driverService.updateAvailability(driver.getId(), true);
        }

        return receipt;
    }

    public List<Ride> listRides() {
        return new ArrayList<>(rides.values());
    }

    public Ride getRideById(String rideId) {
        return rides.get(rideId);
    }
}