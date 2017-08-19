package com.vsoftcorp.kls.business.entities;
/****
 * @author a1565
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
@Table(name = "unit")
public class Unit implements Serializable{
	
	private static final long serialVersionUID = -2749777550102796561L;

	public Unit() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@GeneratedValue(generator = "unitIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "unitIdSequence", sequenceName = "unit_id_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "unit", length = 45)
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
