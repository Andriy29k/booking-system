package com.example.booking_system.model;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    private UUID id;
    private String title;
    private String description;
    private BigDecimal pricePerDay;
    private UUID ownerId;
    private LocalDateTime createdAt;
}
