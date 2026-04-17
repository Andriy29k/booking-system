package com.example.booking_system.service;

import com.example.booking_system.model.User;
import com.example.booking_system.model.enums.Role;
import com.example.booking_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(String email,
                       String firstName,
                       String lastName,
                       Role role) {

        validate(email, firstName, lastName);

        ensureEmailUnique(email);

        User user = buildUser(email, firstName, lastName, role);

        return userRepository.save(user);
    }

    @Transactional
    public User getByIdOrThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User getByEmailOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User update(UUID id,
                       String email,
                       String firstName,
                       String lastName,
                       Role role) {

        User user = getByIdOrThrow(id);

        validate(email, firstName, lastName);

        if (!user.getEmail().equals(email)) {
            ensureEmailUnique(email);
            user.setEmail(email);
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(role);

        return userRepository.save(user);
    }

    public void delete(UUID id) {
        User user = getByIdOrThrow(id);
        userRepository.delete(user);
    }

    private User buildUser(String email,
                           String firstName,
                           String lastName,
                           Role role) {

        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(role != null ? role : Role.USER);
        user.setCreatedAt(LocalDateTime.now());

        return user;
    }

    private void validate(String email,
                          String firstName,
                          String lastName) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }

        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
    }

    private void ensureEmailUnique(String email) {
        userRepository.findByEmail(email).ifPresent(u -> {
            throw new RuntimeException("Email already in use");
        });
    }
}