package com.vsoftcorp.kls.GepRep.business;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "npa_parameters")
public class NPAParameters  implements Serializable{
	
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Basic
	@Column(name = "status")
	private String status;
	
	@Basic
	@Column(name = "no_of_seasons")
	private Integer noOfSeasons;

	@Basic
	@Column(name = "period")
	private Integer period;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNoOfSeasons() {
		return noOfSeasons;
	}

	public void setNoOfSeasons(Integer noOfSeasons) {
		this.noOfSeasons = noOfSeasons;
	}
	
	
	
}
