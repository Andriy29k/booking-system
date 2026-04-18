package com.example.booking_system.repository;

import com.example.booking_system.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByResource_Id(Long resourceId);
    List<Booking> findByUser_Id(Long userId);
}
