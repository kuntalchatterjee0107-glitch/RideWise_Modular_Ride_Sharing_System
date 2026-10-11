package com.airtribe.ridewise.service;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.util.IdGenerator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DriverService {
    private final Map<String, Driver> drivers = new ConcurrentHashMap<>();

    public Driver registerDriver(String name, double currentLocation) {
        String id = IdGenerator.nextDriverId();
        Driver driver = new Driver(id, name, currentLocation);
        drivers.put(id, driver);
        return driver;
    }

    public void updateAvailability(String driverId, boolean available) {
        Driver driver = drivers.get(driverId);
        if (driver != null) {
            driver.setAvailable(available);
        }
    }

    public List<Driver> listAvailableDrivers() {
        return drivers.values().stream()
                .filter(Driver::isAvailable)
                .collect(Collectors.toList());
    }

    public Driver getDriverById(String id) {
        return drivers.get(id);
    }
}