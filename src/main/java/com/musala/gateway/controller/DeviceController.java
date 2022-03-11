package com.musala.gateway.controller;

import com.musala.gateway.model.Device;
import com.musala.gateway.service.DeviceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * API controller for the CRUD operations over the entity Device.
 */

@RestController
@RequestMapping("/api")
@Api(value = "Endpoints for the Device.")
public class DeviceController {

	@Autowired
	private DeviceService deviceService;

	@PostMapping("/device")
	@ApiOperation(value = "Add a Device", notes = "This method adds a new Device into the DB.")
	public ResponseEntity<?> addDevice(
			@ApiParam(value = "Device to be added.") @RequestBody Device device) {
		Device newDevice = new Device();
		try {
			newDevice = deviceService.addDevice(device);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok(String.format("Device created with id %s.", newDevice.getId()));
	}

	@DeleteMapping("/device/{id}")
	@ApiOperation(value = "Delete a Device", notes = "This method deletes a device from the DB.")
	public ResponseEntity<?> delDevice(
			@ApiParam(value = "Id of the Device. Cannot be empty.") @PathVariable("id") Long id) {
		try {
			deviceService.delDevice(id);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok(String.format("Device with id %s deleted.", id));
	}

}
