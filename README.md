# RideWise — Modular Ride-Sharing System

RideWise is a simplified, console-based Ride-Sharing (Uber/Ola style) System demonstrating Low-Level Design (LLD), Object-Oriented Programming (OOP), and SOLID design principles in Java.

---

## Key Learning & Design Principles

* **Single Responsibility Principle (SRP):** Single-purpose classes (`RiderService`, `DriverService`, `RideService`, strategies).
* **Open/Closed Principle (OCP):** Adding new ride selection or pricing strategies without modifying core service logic.
* **Liskov Substitution Principle (LSP):** Strategy implementations remain completely interchangeable at runtime.
* **Interface Segregation Principle (ISP):** Focused, lightweight interfaces (`RideMatchingStrategy`, `FareStrategy`).
* **Dependency Inversion Principle (DIP):** `RideService` depends upon strategy interfaces, not concrete implementations.
* **Composition over Inheritance:** Strategies and receipts are composed directly within collaborating entities/services.
* **Law of Demeter:** Direct service-to-service communication avoiding deep method chains.

---

## Object Relationships

| Relationship | Type |
|---|---|
| Rider $\rightarrow$ Ride | Association |
| Driver $\rightarrow$ Ride | Association |
| Ride $\rightarrow$ FareReceipt | Composition |
| RideService $\rightarrow$ Strategies | Composition |

---

## Project Structure

```text
src/
└── com/
    └── airtribe/
        └── ridewise/
            ├── Main.java
            ├── model/
            │   ├── Rider.java
            │   ├── Driver.java
            │   ├── Ride.java
            │   ├── FareReceipt.java
            │   └── RideStatus.java
            ├── strategy/
            │   ├── RideMatchingStrategy.java
            │   ├── NearestDriverStrategy.java
            │   ├── LeastActiveDriverStrategy.java
            │   ├── FareStrategy.java
            │   ├── DefaultFareStrategy.java
            │   └── PeakHourFareStrategy.java
            ├── service/
            │   ├── RiderService.java
            │   ├── DriverService.java
            │   └── RideService.java
            ├── exception/
            │   └── NoDriverAvailableException.java
            └── util/
                └── IdGenerator.java
docs/
├── Requirements.md
├── Class_Model.md
├── SOLID_Reflection.md
└── Object_Relationships.md


Console Features
The application runs via Main.java with a menu interface:

Add Rider (ID, name, coordinate location)

Add Driver (ID, name, current coordinate location, availability)

View Available Drivers

Request Ride (allocates a driver via RideMatchingStrategy)

Complete Ride (calculates fare via FareStrategy, issues FareReceipt)

View Rides (lifecycle: REQUESTED, ASSIGNED, COMPLETED, CANCELLED)

Exit

Build & Run

Compile
javac -d bin $(find src -name "*.java")

Run
java -cp bin com.airtribe.ridewise.Main