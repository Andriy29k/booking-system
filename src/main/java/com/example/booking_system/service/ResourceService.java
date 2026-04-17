package com.example.booking_system.service;

import com.example.booking_system.model.Resource;
import com.example.booking_system.repository.ResourceRepository;
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

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public Resource create(String title,
                           String description,
                           BigDecimal pricePerDay,
                           UUID ownerId) {

        validate(title, pricePerDay, ownerId);

        Resource resource = buildResource(title, description, pricePerDay, ownerId);

        return resourceRepository.save(resource);
    }

    @Transactional
    public Resource getByIdOrThrow(UUID id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));
    }

    @Transactional
    public List<Resource> getAll() {
        return resourceRepository.findAll();
    }

    public Resource update(UUID id,
                           String title,
                           String description,
                           BigDecimal pricePerDay) {

        Resource resource = getByIdOrThrow(id);

        validate(title, pricePerDay, resource.getOwnerId());

        resource.setTitle(title);
        resource.setDescription(description);
        resource.setPricePerDay(pricePerDay);

        return resourceRepository.save(resource);
    }

    public void delete(UUID id) {
        Resource resource = getByIdOrThrow(id);
        resourceRepository.delete(resource);
    }

    private Resource buildResource(String title,
                                   String description,
                                   BigDecimal pricePerDay,
                                   UUID ownerId) {

        Resource resource = new Resource();
        resource.setTitle(title);
        resource.setDescription(description);
        resource.setPricePerDay(pricePerDay);
        resource.setOwnerId(ownerId);
        resource.setCreatedAt(LocalDateTime.now());

        return resource;
    }

    private void validate(String title,
                          BigDecimal pricePerDay,
                          UUID ownerId) {

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
