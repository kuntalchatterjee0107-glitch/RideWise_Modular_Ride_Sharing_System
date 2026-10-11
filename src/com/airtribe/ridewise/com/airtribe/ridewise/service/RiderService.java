package com.airtribe.ridewise.service;

import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.util.IdGenerator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RiderService {
    private final Map<String, Rider> riders = new ConcurrentHashMap<>();

    public Rider registerRider(String name, double location) {
        String id = IdGenerator.nextRiderId();
        Rider rider = new Rider(id, name, location);
        riders.put(id, rider);
        return rider;
    }

    public Rider getRiderById(String id) {
        return riders.get(id);
    }
}