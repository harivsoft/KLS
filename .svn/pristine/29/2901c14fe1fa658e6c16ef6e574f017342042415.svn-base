package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cbs_uid", schema="kls_schema")
public class CBSUID implements Serializable {
	
	@Id
	@Column(name="id")
	private Integer id;
	
	@Column(name="pacs_id")
	private Integer pacsId;
	
	@Column(name="lastusedno")
	private BigInteger lastUsedNumber;

	public Integer getPacsId() {
		return pacsId;
	}

	public void setPacsId(Integer pacsId) {
		this.pacsId = pacsId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigInteger getLastUsedNumber() {
		return lastUsedNumber;
	}

	public void setLastUsedNumber(BigInteger lastUsedNumber) {
		this.lastUsedNumber = lastUsedNumber;
	}


}
