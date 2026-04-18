package com.example.booking_system.controller;


import com.example.booking_system.dto.CreateUserRequest;
import com.example.booking_system.dto.UpdateUserRequest;
import com.example.booking_system.model.User;
import com.example.booking_system.model.enums.Role;
import com.example.booking_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
    public User getById(@PathVariable Long id) {
        return userService.getByIdOrThrow(id);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id,
                       @RequestBody UpdateUserRequest request) {

        return userService.update(
                id,
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getRole()
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PostMapping("/{id}/avatar")
    public User uploadAvatar(@PathVariable Long id,
                             @RequestParam MultipartFile file) throws IOException {

        return userService.updateAvatar(
                id,
                file.getBytes(),
                file.getContentType()
        );
    }

    @GetMapping("/{id}/avatar")
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long id) {

        User user = userService.getByIdOrThrow(id);

        return ResponseEntity.ok()
                .header("Content-Type",
                        user.getAvatarContentType() != null
                                ? user.getAvatarContentType()
                                : "image/jpeg")
                .body(user.getAvatar());
    }

    @DeleteMapping("/{id}/avatar")
    public User deleteAvatar(@PathVariable Long id) {
        return userService.removeAvatar(id);
    }
}