package com.airtribe.ridewise;

import com.airtribe.ridewise.exception.NoDriverAvailableException;
import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.FareReceipt;
import com.airtribe.ridewise.model.Ride;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.service.DriverService;
import com.airtribe.ridewise.service.RideService;
import com.airtribe.ridewise.service.RiderService;
import com.airtribe.ridewise.strategy.DefaultFareStrategy;
import com.airtribe.ridewise.strategy.NearestDriverStrategy;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RiderService riderService = new RiderService();
        DriverService driverService = new DriverService();
        RideService rideService = new RideService(
                driverService,
                new NearestDriverStrategy(),
                new DefaultFareStrategy()
        );

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== RideWise System CLI ===");

        while (running) {
            System.out.println("\n1. Add Rider");
            System.out.println("2. Add Driver");
            System.out.println("3. View Available Drivers");
            System.out.println("4. Request Ride");
            System.out.println("5. Complete Ride");
            System.out.println("6. View Rides");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1": {
                    System.out.print("Enter rider name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter rider location (numeric km/coordinate): ");
                    try {
                        double location = Double.parseDouble(scanner.nextLine().trim());
                        Rider rider = riderService.registerRider(name, location);
                        System.out.println("Registered: " + rider);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid numeric input for location.");
                    }
                    break;
                }
                case "2": {
                    System.out.print("Enter driver name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter driver current location (numeric km/coordinate): ");
                    try {
                        double location = Double.parseDouble(scanner.nextLine().trim());
                        Driver driver = driverService.registerDriver(name, location);
                        System.out.println("Registered: " + driver);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid numeric input for location.");
                    }
                    break;
                }
                case "3": {
                    List<Driver> available = driverService.listAvailableDrivers();
                    if (available.isEmpty()) {
                        System.out.println("No drivers currently available.");
                    } else {
                        System.out.println("Available Drivers:");
                        available.forEach(d -> System.out.println(" - " + d));
                    }
                    break;
                }
                case "4": {
                    System.out.print("Enter Rider ID: ");
                    String riderId = scanner.nextLine().trim();
                    Rider rider = riderService.getRiderById(riderId);
                    if (rider == null) {
                        System.out.println("Rider not found.");
                        break;
                    }

                    System.out.print("Enter ride distance (km): ");
                    try {
                        double distance = Double.parseDouble(scanner.nextLine().trim());
                        Ride ride = rideService.requestRide(rider, distance);
                        System.out.println("Ride assigned successfully: " + ride);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid numeric input for distance.");
                    } catch (NoDriverAvailableException e) {
                        System.out.println("Matching failed: " + e.getMessage());
                    }
                    break;
                }
                case "5": {
                    System.out.print("Enter Ride ID to complete: ");
                    String rideId = scanner.nextLine().trim();
                    try {
                        FareReceipt receipt = rideService.completeRide(rideId);
                        System.out.println("Ride completed successfully!");
                        System.out.println(receipt);
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case "6": {
                    List<Ride> rides = rideService.listRides();
                    if (rides.isEmpty()) {
                        System.out.println("No rides have been recorded.");
                    } else {
                        System.out.println("Recorded Rides:");
                        rides.forEach(r -> System.out.println(" - " + r));
                    }
                    break;
                }
                case "7":
                    running = false;
                    System.out.println("Exiting RideWise. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose between 1 and 7.");
            }
        }
        scanner.close();
    }
}