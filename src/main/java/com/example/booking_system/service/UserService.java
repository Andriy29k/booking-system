package com.example.booking_system.service;

import com.example.booking_system.model.User;
import com.example.booking_system.model.enums.Role;
import com.example.booking_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(String email,
                       String firstName,
                       String lastName,
                       Role role) {

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(role);
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(UUID id) {
        return userRepository.findById(id);
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
