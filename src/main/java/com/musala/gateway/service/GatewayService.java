package com.musala.gateway.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.musala.gateway.model.Gateway;
import com.musala.gateway.repository.GatewayRepository;

// Service to perform CRUD operations with the Gateway model.

@Service
public class GatewayService {

	@Autowired
	GatewayRepository gatewayRepository;

	public List<Gateway> getAllGateways() {
		Iterable<Gateway> it = gatewayRepository.findAll();
		List<Gateway> gateways = new ArrayList<Gateway>();

		it.forEach(e -> gateways.add(e));

		return gateways;
	}

	public Gateway getGateway(Long id) {
		return gatewayRepository.findById(id).get();
	}

	@Transactional
	public Gateway addGateway(Gateway gateway) {
		return gatewayRepository.save(gateway);
	}

	@Transactional
	public void deleteGateway(Long id) {
		gatewayRepository.deleteById(id);
	}

}
