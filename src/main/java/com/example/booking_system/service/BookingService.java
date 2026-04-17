package com.example.booking_system.service;

import com.example.booking_system.model.Booking;
import com.example.booking_system.model.Resource;
import com.example.booking_system.model.User;
import com.example.booking_system.model.enums.BookingStatus;
import com.example.booking_system.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ResourceService resourceService;

    public BookingService(BookingRepository bookingRepository,
                          UserService userService,
                          ResourceService resourceService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.resourceService = resourceService;
    }

    public Booking create(UUID userId,
                          UUID resourceId,
                          LocalDate start,
                          LocalDate end,
                          BigDecimal pricePerDay) {

        validateDates(start, end);

        User user = userService.getByIdOrThrow(userId);
        Resource resource = resourceService.getByIdOrThrow(resourceId);

        ensureAvailability(resourceId, start, end);

        Booking booking = buildBooking(user, resource, start, end, pricePerDay);

        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking getByIdOrThrow(UUID id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Transactional
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    @Transactional
    public List<Booking> getByUser(UUID userId) {
        return bookingRepository.findByUser_Id(userId);
    }

    @Transactional
    public List<Booking> getByResource(UUID resourceId) {
        return bookingRepository.findByResource_Id(resourceId);
    }

    public Booking updateDates(UUID bookingId,
                               LocalDate newStart,
                               LocalDate newEnd,
                               BigDecimal pricePerDay) {

        validateDates(newStart, newEnd);

        Booking booking = getByIdOrThrow(bookingId);

        ensureAvailability(
                booking.getResource().getId(),
                newStart,
                newEnd
        );

        booking.setStartDate(newStart);
        booking.setEndDate(newEnd);

        long days = ChronoUnit.DAYS.between(newStart, newEnd);
        booking.setTotalPrice(pricePerDay.multiply(BigDecimal.valueOf(days)));

        return bookingRepository.save(booking);
    }

    public void delete(UUID bookingId) {
        Booking booking = getByIdOrThrow(bookingId);
        bookingRepository.delete(booking);
    }

    private Booking buildBooking(User user,
                                 Resource resource,
                                 LocalDate start,
                                 LocalDate end,
                                 BigDecimal pricePerDay) {

        long days = ChronoUnit.DAYS.between(start, end);
        if (days <= 0) {
            throw new IllegalArgumentException("Invalid booking period");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setResource(resource);
        booking.setStartDate(start);
        booking.setEndDate(end);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setTotalPrice(pricePerDay.multiply(BigDecimal.valueOf(days)));

        return booking;
    }

    private void validateDates(LocalDate start, LocalDate end) {
        if (start.isAfter(end) || start.equals(end)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
    }

    private void ensureAvailability(UUID resourceId, LocalDate start, LocalDate end) {

        List<Booking> existing = bookingRepository.findByResource_Id(resourceId);

        boolean conflict = existing.stream().anyMatch(b ->
                start.isBefore(b.getEndDate()) &&
                        end.isAfter(b.getStartDate())
        );

        if (conflict) {
            throw new RuntimeException("Resource not available for selected dates");
        }
    }
}