package com.musala.gateway.controller;

import java.util.ArrayList;
import java.util.List;

import com.musala.gateway.model.Device;
import com.musala.gateway.model.Gateway;
import com.musala.gateway.service.DeviceService;
import com.musala.gateway.service.GatewayService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.InetAddresses;

/*
 * API controller for the CRUD operations over the entity Reservation.
 */

@RestController
@RequestMapping("/api")
@Api(value = "Endpoints for the Gateway.")
public class GatewayController {

	@Autowired
	private GatewayService gatewayService;

	@Autowired
	private DeviceService deviceService;

	@GetMapping("/gateways")
	@ApiOperation(value = "Get all Gateways", notes = "This method returns a list of all Gateways with their corresponding Devices.")
	public ResponseEntity<?> getAllGateways() {
		List<Gateway> gateways = new ArrayList<Gateway>();
		try {
			gateways = gatewayService.getAllGateways();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok(gateways);
	}

	@GetMapping("/gateway/{id}")
	@ApiOperation(value = "Get a Gateway", notes = "This method returns the information of a Gateway with their corresponding Devices.")
	public ResponseEntity<?> getGateway(@ApiParam("Id of the Gateway. Cannot be empty.") @PathVariable("id") Long id) {
		Gateway gateway = new Gateway();
		try {
			gateway = gatewayService.getGateway(id);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok(gateway);
	}

	@PostMapping("/gateway")
	@ApiOperation(value = "Add a Gateway", notes = "This method add a new Gateway into the DB.")
	public ResponseEntity<?> addGateway(@RequestBody Gateway gateway) {
		if (!InetAddresses.isInetAddress(gateway.getIpv4()))
			return ResponseEntity.ok("IP address invalid.");

		Gateway newGateway = new Gateway();
		try {
			newGateway = gatewayService.addGateway(gateway);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok(String.format("Gateway created with id %s.", newGateway.getId()));
	}

	@PutMapping("/gateway/{id}/add")
	@ApiOperation(value = "Add a Device to a Gateway", notes = "This method add a new Device to an existing Gateway.")
	public ResponseEntity<?> addDevice(@ApiParam("Id of the Gateway. Cannot be empty.") @PathVariable("id") Long id,
			@ApiParam("Device to be added (can exist or not).") @RequestBody Device device) {
		Gateway gateway = gatewayService.getGateway(id);
		Device newDevice = new Device();

		if (gateway == null)
			return ResponseEntity.ok(String.format("Gateway with id %s not found", id));

		if (gateway.getDevices().size() == 10)
			return ResponseEntity.ok(String.format("Gateway with id %s already has 10 devices.", gateway.getId()));

		try {
			newDevice = device.getId() == null ? deviceService.addDevice(device)
					: deviceService.getDevice(device.getId());
			if (newDevice != null)
				newDevice.setGateway(gateway);
			else
				return ResponseEntity.ok(String.format("Device with id %s not found", device.getId()));

			deviceService.addDevice(newDevice);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.ok(String.format("Device with id %s added to Gateway with id %s.", device.getId(), id));
	}

	@DeleteMapping("/gateway/{id}/del")
	@ApiOperation(value = "Delete a Device from a Gateway", notes = "This method delete an existing Device from the selected Gateway.")
	public ResponseEntity<?> delDevice(@ApiParam("Id of the Gateway. Cannot be empty.") @PathVariable("id") Long id,
			@ApiParam("Device to be deleted.") @RequestBody Device device) {
		try {
			device.setGateway(null);
			deviceService.addDevice(device);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity
				.ok(String.format("Device with id %s deleted from Gateway with id %s.", device.getId(), id));
	}

	@DeleteMapping("/gateway/{id}")
	@ApiOperation(value = "Delete a Gateway", notes = "This method deletes an existing Gateway (and corresponding devices) from the DB.")
	public ResponseEntity<?> delGateway(@ApiParam("Id of the Gateway. Cannot be empty.") @PathVariable("id") Long id) {
		try {
			gatewayService.deleteGateway(id);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok(String.format("Gateway with id %s deleted.", id));
	}
}
