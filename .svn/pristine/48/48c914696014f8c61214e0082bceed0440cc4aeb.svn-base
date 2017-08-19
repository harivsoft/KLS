package com.vsoftcorp.kls.business.entities;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table; 

@Entity
@Table(name = "sub_purpose")
public class SubPurpose implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SubPurpose() {
		// TODO Auto-generated constructor stub
	}
	

	@Id
	@GeneratedValue(generator = "subpurposeIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "subpurposeIdSequence", sequenceName = "sub_purpose_id_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "sub_purpose_name", length = 45)
	private String name;
	

	@Column(name = "remarks", length = 150)
	private String remarks;
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
}
