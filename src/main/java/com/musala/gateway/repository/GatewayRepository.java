package com.musala.gateway.repository;

import com.musala.gateway.model.Gateway;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// Default CRUD repository which already has some methods implemented.
// It's better to extend it, otherwise all operations need to be implemented.

@Repository
public interface GatewayRepository extends CrudRepository<Gateway, Long> {
}
