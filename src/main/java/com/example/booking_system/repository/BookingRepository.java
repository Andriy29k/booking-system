package com.example.booking_system.repository;

import com.example.booking_system.model.Booking;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository {
    Booking save(Booking booking);
    List<Booking> findByResourceId(UUID resourceId);
    List<Booking> findAll();
}
