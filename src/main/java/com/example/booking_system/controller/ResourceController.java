package com.example.booking_system.controller;

import com.example.booking_system.dto.CreateResourceRequest;
import com.example.booking_system.model.Resource;
import com.example.booking_system.service.ResourceService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping
    public Resource create(@RequestBody CreateResourceRequest request) {
        return resourceService.create(
                request.getTitle(),
                request.getDescription(),
                request.getPricePerDay(),
                request.getOwnerId()
        );
    }

    @GetMapping
    public List<Resource> getAll() {
        return resourceService.getAll();
    }

    @GetMapping("/{id}")
    public Resource getById(@PathVariable UUID id) {
        return resourceService.getByIdOrThrow(id);
    }

    @PutMapping("/{id}")
    public Resource update(@PathVariable UUID id,
                           @RequestParam String title,
                           @RequestParam String description,
                           @RequestParam BigDecimal pricePerDay) {

        return resourceService.update(id, title, description, pricePerDay);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        resourceService.delete(id);
    }
}