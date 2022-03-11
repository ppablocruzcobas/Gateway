package com.musala.gateway.service;

import com.musala.gateway.model.Device;
import com.musala.gateway.repository.DeviceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Service to perform CRUD operations with the Reservation model

@Service
public class DeviceService {

	@Autowired
	DeviceRepository deviceRepository;
	
	public Device getDevice(Long id) {
		return deviceRepository.findById(id).get();
	}

	@Transactional
	public Device addDevice(Device device) {
		return deviceRepository.save(device);
	}

	@Transactional
	public void delDevice(Long id) {
		deviceRepository.deleteById(id);
	}

}
