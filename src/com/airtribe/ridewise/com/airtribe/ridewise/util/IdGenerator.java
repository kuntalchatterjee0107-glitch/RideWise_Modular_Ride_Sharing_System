package com.airtribe.ridewise.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final AtomicInteger riderCounter = new AtomicInteger(100);
    private static final AtomicInteger driverCounter = new AtomicInteger(200);
    private static final AtomicInteger rideCounter = new AtomicInteger(1000);

    public static String nextRiderId() {
        return "RDR-" + riderCounter.incrementAndGet();
    }

    public static String nextDriverId() {
        return "DRV-" + driverCounter.incrementAndGet();
    }

    public static String nextRideId() {
        return "RIDE-" + rideCounter.incrementAndGet();
    }
}