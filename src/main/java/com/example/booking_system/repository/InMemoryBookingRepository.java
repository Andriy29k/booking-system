package com.example.booking_system.repository;

import com.example.booking_system.model.Booking;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryBookingRepository implements BookingRepository {
    private final Map<UUID, Booking> storage = new HashMap<>();

    @Override
    public Booking save(Booking booking) {
        storage.put(booking.getId(), booking);
        return booking;
    }

    @Override
    public List<Booking> findByResourceId(UUID resourceId) {
        return storage.values().stream()
                .filter(b -> b.getResourceId().equals(resourceId))
                .toList();
    }

    @Override
    public List<Booking> findAll() {
        return new ArrayList<>(storage.values());
    }

}
