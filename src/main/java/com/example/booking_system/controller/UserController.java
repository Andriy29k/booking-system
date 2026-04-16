package com.example.booking_system.controller;


import com.example.booking_system.dto.CreateUserRequest;
import com.example.booking_system.model.User;
import com.example.booking_system.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@RequestBody CreateUserRequest request) {
        return userService.create(
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getRole()
        );
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable UUID id) {
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userService.delete(id);
    }
}
