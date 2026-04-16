package com.example.booking_system.service;

import com.example.booking_system.model.Booking;
import com.example.booking_system.model.enums.BookingStatus;
import com.example.booking_system.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    private final Map<UUID, Booking> storage = new HashMap<>();

    public Booking createBooking(UUID userId,
                                 UUID resourceId,
                                 LocalDate start,
                                 LocalDate end,
                                 BigDecimal pricePerDay) {
        validateDates(start, end);
        checkAvailability(resourceId, start, end);

        Booking booking = new Booking();
        booking.setId(UUID.randomUUID());
        booking.setUserId(userId);
        booking.setResourceId(resourceId);
        booking.setStartDate(start);
        booking.setEndDate(end);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setCreatedAt(LocalDateTime.now());

        long days = ChronoUnit.DAYS.between(start, end);
        booking.setTotalPrice(pricePerDay.multiply(BigDecimal.valueOf(days)));

        return bookingRepository.save(booking);
    }

    private void validateDates(LocalDate start, LocalDate end) {
        if(start.isAfter(end) || start.equals(end)) {
            throw new IllegalArgumentException("Start date must be after end date");
        }
    }

    private void checkAvailability(UUID resourceId, LocalDate start, LocalDate end) {
        List<Booking> existing = bookingRepository.findByResourceId(resourceId);

        boolean conflict = existing.stream().anyMatch(b -> start.isBefore(b.getEndDate()) && end.isAfter(b.getStartDate()));

        if(conflict) {
            throw new RuntimeException("Resource not available");
        }
    }

    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

}
