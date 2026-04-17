package com.example.booking_system.repository;

import com.example.booking_system.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    List<Booking> findByResource_Id(UUID resourceId);
    List<Booking> findByUser_Id(UUID userId);
}
