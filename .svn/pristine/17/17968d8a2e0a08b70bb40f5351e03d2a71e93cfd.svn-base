package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sbnxtnos")
public class SBNextNumbers implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4298525341634302453L;

	@Id
	@Column(name="id")
	private Long id;
	
	@Column(name="pacs_id")
	private Integer pacsId;
	
	@Column(name="nxtnotype")
	private String nextNumberType;
	
	@Column(name="transtype")
	private String transType;
	
	@Column(name="lastusedno")
	private Integer lastUsedNumber;

	public Integer getPacsId() {
		return pacsId;
	}

	public void setPacsId(Integer pacsId) {
		this.pacsId = pacsId;
	}

	public String getNextNumberType() {
		return nextNumberType;
	}

	public void setNextNumberType(String nextNumberType) {
		this.nextNumberType = nextNumberType;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Integer getLastUsedNumber() {
		return lastUsedNumber;
	}

	public void setLastUsedNumber(Integer lastUsedNumber) {
		this.lastUsedNumber = lastUsedNumber;
	}

}
