package com.example.booking_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateResourceRequest {
    private String title;
    private String description;
    private BigDecimal pricePerDay;
    private UUID ownerId;
}
