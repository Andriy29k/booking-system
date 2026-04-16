package com.example.booking_system.dto;

import com.example.booking_system.model.enums.Role;
import lombok.Data;

@Data
public class CreateUserRequest {
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
}