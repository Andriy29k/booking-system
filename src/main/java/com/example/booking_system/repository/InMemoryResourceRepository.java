package com.example.booking_system.repository;

import com.example.booking_system.model.Resource;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryResourceRepository implements ResourceRepository {

    private final Map<UUID, Resource> storage = new HashMap<>();

    @Override
    public Resource save(Resource resource) {
        storage.put(resource.getId(), resource);
        return resource;
    }

    @Override
    public Resource findById(UUID id) {
        return storage.get(id);
    }

    @Override
    public List<Resource> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(UUID id) {
        storage.remove(id);
    }
}