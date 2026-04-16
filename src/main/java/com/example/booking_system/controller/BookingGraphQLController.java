package com.example.booking_system.controller;

import com.example.booking_system.dto.CreateBookingRequest;
import com.example.booking_system.model.Booking;
import com.example.booking_system.service.BookingService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookingGraphQLController {
    private final BookingService bookingService;

    public BookingGraphQLController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @QueryMapping
    public List<Booking> bookings() {
        return bookingService.getAll();
    }

    @MutationMapping
    public Booking createBooking(@Argument CreateBookingRequest input) {
        return bookingService.createBooking(
                input.getUserId(),
                input.getResourceId(),
                input.getStartDate(),
                input.getEndDate(),
                input.getPricePerDay()
        );
    }
}
