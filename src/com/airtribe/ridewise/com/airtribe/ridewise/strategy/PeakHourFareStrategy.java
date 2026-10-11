package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Ride;

public class PeakHourFareStrategy implements FareStrategy {
    private static final double BASE_FARE = 75.0;
    private static final double PER_KM_RATE = 18.0;
    private static final double SURCHARGE_MULTIPLIER = 1.3;

    @Override
    public double calculateFare(Ride ride) {
        return (BASE_FARE + (ride.getDistance() * PER_KM_RATE)) * SURCHARGE_MULTIPLIER;
    }
}