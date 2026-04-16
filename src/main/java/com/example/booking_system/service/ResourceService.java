package com.example.booking_system.service;

import com.example.booking_system.model.Resource;
import com.example.booking_system.repository.ResourceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public Resource create(String title,
                           String description,
                           BigDecimal pricePerDay,
                           UUID ownerId) {
        Resource resource = new Resource();
        resource.setId(UUID.randomUUID());
        resource.setTitle(title);
        resource.setDescription(description);
        resource.setPricePerDay(pricePerDay);
        resource.setOwnerId(ownerId);
        resource.setCreatedAt(LocalDateTime.now());

        return resourceRepository.save(resource);
    }

    public List<Resource> getAll() {
        return resourceRepository.findAll();
    }

    public Resource getById(UUID id) {
        return resourceRepository.findById(id);
    }

    public void delete(UUID id) {
        resourceRepository.deleteById(id);
    }

}
