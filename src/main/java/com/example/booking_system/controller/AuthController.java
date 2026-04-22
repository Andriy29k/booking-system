package com.example.booking_system.controller;

import com.example.booking_system.dto.LoginRequest;
import com.example.booking_system.dto.RegisterRequest;
import com.example.booking_system.dto.ResetPasswordRequest;
import com.example.booking_system.model.User;
import com.example.booking_system.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return authService.register(
                request.getEmail(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName()
        );
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) {
        return authService.login(
                request.getEmail(),
                request.getPassword()
        );
    }

 @PostMapping("/reset-code")
    public String generateResetCode(@RequestParam String email) {
        return authService.generateResetCode(email);
    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody ResetPasswordRequest request) {
        authService.resetPassword(
                request.getEmail(),
                request.getCode(),
                request.getNewPassword()
        );
    }
}