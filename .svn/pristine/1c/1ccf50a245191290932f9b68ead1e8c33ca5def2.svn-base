package com.vsoftcorp.kls.GepRep.business;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sbgenreport_parameter")
public class ReportParameters implements Serializable{
	
	@Id
	@Column(name = "report_id")
	private Integer id;
	
	@Id
	@Column(name = "serialnumber")
	private Integer serialNumber;

	@Basic
	@Column(name = "user_caption")
	private String userCaption;

	@Basic
	@Column(name = "user_data_type")
	private String userDataType;

	@Basic
	@Column(name = "user_databasefield")
	private String userDatabaseField;

	@Basic
	@Column(name = "user_list_of_values")
	private String userListofValues;

	@Basic
	@Column(name = "user_default_values")
	private String userDefaultValues;
	
	@Column(name="rest_url")
	private String restUrl;
	
	@Column(name="context")
	private String context;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getUserCaption() {
		return userCaption;
	}

	public void setUserCaption(String userCaption) {
		this.userCaption = userCaption;
	}

	public String getUserDataType() {
		return userDataType;
	}

	public void setUserDataType(String userDataType) {
		this.userDataType = userDataType;
	}

	public String getUserDatabaseField() {
		return userDatabaseField;
	}

	public void setUserDatabaseField(String userDatabaseField) {
		this.userDatabaseField = userDatabaseField;
	}

	public String getUserListofValues() {
		return userListofValues;
	}

	public void setUserListofValues(String userListofValues) {
		this.userListofValues = userListofValues;
	}

	public String getUserDefaultValues() {
		return userDefaultValues;
	}

	public void setUserDefaultValues(String userDefaultValues) {
		this.userDefaultValues = userDefaultValues;
	}

	public String getRestUrl() {
		return restUrl;
	}

	public void setRestUrl(String restUrl) {
		this.restUrl = restUrl;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	
}
