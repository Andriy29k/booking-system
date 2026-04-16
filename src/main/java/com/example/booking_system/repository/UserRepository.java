package com.example.booking_system.repository;

import com.example.booking_system.model.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    User findById(UUID id);
    List<User> findAll();
    void deleteById(UUID id);
}
