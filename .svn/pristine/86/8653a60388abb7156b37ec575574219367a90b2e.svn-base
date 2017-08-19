package com.vsoftcorp.kls.business.entities;
/**
 *  @author a1565
 */

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "purpose")
public class Purpose implements Serializable{
	private static final long serialVersionUID = -3756942664131123897L;
	public Purpose() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@GeneratedValue(generator = "purposeIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "purposeIdSequence", sequenceName = "purpose_id_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "id")
	private Integer id;
	
	
	@Column(name = "purpose", length = 45)
	private String name;
	

	@Column(name = "remarks", length = 150)
	private String remarks;
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	@Column(name = "activity_req", length = 1)
	private String activityRequired;

	public String getName() {
		return name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActivityRequired() {
		return activityRequired;
	}

	public void setActivityRequired(String activityRequired) {
		this.activityRequired = activityRequired;
	}

	public String getMasterAppReq() {
		return masterAppReq;
	}

	public void setMasterAppReq(String masterAppReq) {
		this.masterAppReq = masterAppReq;
	}

	public String getSubPurposeReq() {
		return subPurposeReq;
	}

	public void setSubPurposeReq(String subPurposeReq) {
		this.subPurposeReq = subPurposeReq;
	}


	@Column(name = "master_app_req", length = 1)
	private String masterAppReq;

	@Column(name = "sub_purpose_req", length = 1)
	private String subPurposeReq;

}
