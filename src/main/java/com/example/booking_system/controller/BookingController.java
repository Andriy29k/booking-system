package com.example.booking_system.controller;

import com.example.booking_system.dto.CreateBookingRequest;
import com.example.booking_system.model.Booking;
import com.example.booking_system.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking create(@RequestBody CreateBookingRequest request) {
        return bookingService.create(
                request.getUserId(),
                request.getResourceId(),
                request.getStartDate(),
                request.getEndDate(),
                request.getPricePerDay()
        );
    }

    @GetMapping("/{id}")
    public Booking getById(@PathVariable Long id) {
        return bookingService.getByIdOrThrow(id);
    }

    @GetMapping
    public List<Booking> getAll() {
        return bookingService.getAll();
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getByUser(@PathVariable Long userId) {
        return bookingService.getByUser(userId);
    }

    @GetMapping("/resource/{resourceId}")
    public List<Booking> getByResource(@PathVariable Long resourceId) {
        return bookingService.getByResource(resourceId);
    }

    @PutMapping("/{id}")
    public Booking update(@PathVariable Long id,
                          @RequestParam LocalDate startDate,
                          @RequestParam LocalDate endDate,
                          @RequestParam BigDecimal pricePerDay) {

        return bookingService.updateDates(id, startDate, endDate, pricePerDay);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookingService.delete(id);
    }
}