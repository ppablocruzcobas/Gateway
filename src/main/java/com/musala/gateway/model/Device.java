package com.musala.gateway.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

// The Entity Device
// Validations were made using annotations.

@Entity
@Table(name = "device")
@EntityListeners(AuditingEntityListener.class)
@ApiModel
public class Device {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "uid")
	@ApiModelProperty(value = "Device UID.", name = "uid", dataType = "Long", example = "893425738", required = true)
	private Long uid;

	@Column(name = "vendor")
	@ApiModelProperty(value = "Device Vendor.", name = "vendor", dataType = "String", example = "Musala", required = true)
	private String vendor;

	@CreatedDate
	@ApiModelProperty(value = "Creation date (not required, automatically generated).", required = false)
	@Column(name = "created_date", nullable = false, updatable = false)
	private Long createdDate;

	@Column(name = "status")
	@Pattern(regexp = "^(online|offline)$", message = "Invalid status.")
	@ApiModelProperty(value = "Device status.", name = "status", dataType = "String", example = "online", required = true)
	private String status;

	// Here the ForeignKey of the Gateway who owns the Device.
	@ManyToOne()
	@JoinColumn(name = "gateway_id", referencedColumnName = "id")
	// @NotNull(message = "Gateway cannot be null or empty")
	@ApiModelProperty(value = "Gateway who owns this Device.", required = false)
	private Gateway gateway;

	public Long getId() {
		return id;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getUid() {
		return uid;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getVendor() {
		return vendor;
	}

	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}

	public Date getCreatedDate() {
		return new Date(createdDate);
	}

	
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	@ApiModelProperty(hidden = true)
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}

}
