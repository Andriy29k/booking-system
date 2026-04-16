package com.example.booking_system.repository;

import com.example.booking_system.model.Resource;

import java.util.List;
import java.util.UUID;

public interface ResourceRepository {
    Resource save(Resource resource);
    Resource findById(UUID id);
    List<Resource> findAll();
    void deleteById(UUID id);
}