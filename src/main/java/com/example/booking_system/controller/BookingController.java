package com.example.booking_system.controller;

import com.example.booking_system.dto.CreateBookingRequest;
import com.example.booking_system.model.Booking;
import com.example.booking_system.service.BookingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking create(@RequestBody CreateBookingRequest request) {
        return bookingService.createBooking(
                request.getUserId(),
                request.getResourceId(),
                request.getStartDate(),
                request.getEndDate(),
                request.getPricePerDay()
        );
    }

    public List<Booking> getAll() {
        return bookingService.getAll();
    }

}
