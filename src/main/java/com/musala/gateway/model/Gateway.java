package com.musala.gateway.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

// The Entity Gateway
// Validations were made using annotations.

@Entity
@Table(name = "gateway")
@ApiModel
public class Gateway {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "serial_number", unique = true, nullable = false)
	@ApiModelProperty(value = "Serial Number (unique constraint).", name = "serialNumber", dataType = "String", example = "SN 179-WS", required = true)
	private String serialNumber;

	@Column(name = "name")
	// @NotEmpty(message = "Name cannot be null or empty")
	@ApiModelProperty(value = "Name of Gateway.", name = "name", dataType = "String", example = "Gateway 1", required = true)
	private String name;

	@Column(name = "ipv4")
	@ApiModelProperty(value = "Ipv4 address (validated field).", name = "ipv4", dataType = "String", example = "192.168.1.10", required = true)
	private String ipv4;

	// This 'cascade' allows me to delete Gateways without manual delete of his
	// devices,
	// since it performs CRUD operations in cascade.
	// Placed ForeignKey in Device, pointing to the Gateway who owns it.
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "gateway")
	@ApiModelProperty(value = "list of Devices associated with this Gateway (only used to return the list of Devices).", required = false)
	private List<Device> devices = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setIpv4(String ipv4) {
		this.ipv4 = ipv4;
	}

	public String getIpv4() {
		return ipv4;
	}
	
	public List<Device> getDevices() {
		return devices;
	}

}
