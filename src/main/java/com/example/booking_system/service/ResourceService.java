package com.example.booking_system.service;

import com.example.booking_system.model.Resource;
import com.example.booking_system.model.User;
import com.example.booking_system.repository.ResourceRepository;
import com.example.booking_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;

    public ResourceService(ResourceRepository resourceRepository,
                           UserRepository userRepository) {
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
    }

    public Resource create(String title,
                           String description,
                           BigDecimal pricePerDay,
                           Long ownerId) {

        validate(title, pricePerDay, ownerId);

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Resource resource = buildResource(title, description, pricePerDay, owner);

        return resourceRepository.save(resource);
    }

    public Resource getByIdOrThrow(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));
    }

    public List<Resource> getAll() {
        return resourceRepository.findAll();
    }

    public Resource update(Long id,
                           String title,
                           String description,
                           BigDecimal pricePerDay) {

        Resource resource = getByIdOrThrow(id);

        validate(title, pricePerDay, resource.getOwner().getId());

        resource.setTitle(title);
        resource.setDescription(description);
        resource.setPricePerDay(pricePerDay);

        return resourceRepository.save(resource);
    }

    public void delete(Long id) {
        Resource resource = getByIdOrThrow(id);
        resourceRepository.delete(resource);
    }

    private Resource buildResource(String title,
                                   String description,
                                   BigDecimal pricePerDay,
                                   User owner) {

        Resource resource = new Resource();
        resource.setTitle(title);
        resource.setDescription(description);
        resource.setPricePerDay(pricePerDay);
        resource.setOwner(owner);
        resource.setCreatedAt(LocalDateTime.now());

        return resource;
    }

    private void validate(String title,
                          BigDecimal pricePerDay,
                          Long ownerId) {

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        if (pricePerDay == null || pricePerDay.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }

        if (ownerId == null) {
            throw new IllegalArgumentException("OwnerId is required");
        }
    }
}