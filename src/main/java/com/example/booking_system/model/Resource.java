package com.example.booking_system.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "resources")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerDay;

    private UUID ownerId;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "resource")
    private java.util.List<Booking> bookings;
}
