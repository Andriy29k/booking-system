package com.example.booking_system.model;

import lombok.*;
import java.util.UUID;
import com.example.booking_system.model.enums.Role;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private LocalDateTime createdAt;
}