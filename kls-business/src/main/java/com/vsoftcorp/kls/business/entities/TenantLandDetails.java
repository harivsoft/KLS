package com.vsoftcorp.kls.business.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tenant_land_details")
public class TenantLandDetails {

	@Id
	@SequenceGenerator(name = "tenantlandDetailIdSeq", sequenceName = "tenant_land_detail_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tenantlandDetailIdSeq")
	@Column(name = "id")
	private Long id;

	@Basic
	@Column(name = "customer_id")
	private Long customerId;

	@Basic
	@Column(name = "guarantor_id")
	private Long guarantorId;

	@Basic
	@Column(name = "area_cultivated")
	private Double areaCultivated;

	@Basic
	@Column(name = "land_type_id")
	private Integer landType;

	@Basic
	@Column(name = "land_ref_id")
	private Long landRefId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getGuarantorId() {
		return guarantorId;
	}

	public void setGuarantorId(Long guarantorId) {
		this.guarantorId = guarantorId;
	}

	public Double getAreaCultivated() {
		return areaCultivated;
	}

	public void setAreaCultivated(Double areaCultivated) {
		this.areaCultivated = areaCultivated;
	}

	public Integer getLandType() {
		return landType;
	}

	public void setLandType(Integer landType) {
		this.landType = landType;
	}

	public Long getLandRefId() {
		return landRefId;
	}

	public void setLandRefId(Long landRefId) {
		this.landRefId = landRefId;
	}

}