package com.example.booking_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingRequest {
    private UUID userId;
    private UUID resourceId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal pricePerDay;
}
