package com.example.booking_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "resources")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "resource")
    @JsonIgnore
    private List<Booking> bookings;

    @ElementCollection
    @CollectionTable(
            name = "resource_images",
            joinColumns = @JoinColumn(name = "resource_id")
    )
    @Column(name = "image_url")
    private List<String> images;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}