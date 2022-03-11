package com.musala.gateway.repository;

import com.musala.gateway.model.Device;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// Default CRUD repository which already has some methods implemented.
// It's better to extend it, otherwise all operations need to be implemented.

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {
}
