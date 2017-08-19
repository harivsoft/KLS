package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author a9152
 * 
 */
@Entity
@Table(name = "scheme")
public class Scheme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3756942664131123897L;

	@Id
	@GeneratedValue(generator = "schemeIdSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "schemeIdSeq", sequenceName = "scheme_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "name", length = 45)
	private String name;

/*	@OneToOne(optional = false)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;*/
/*
	@Basic
	@Column(name = "insurance_percentage", precision = 5, scale = 2)
	private BigDecimal insurancePercentage;*/

	public Scheme() {
	}

	/*public BigDecimal getInsurancePercentage() {
		return insurancePercentage;
	}

	public void setInsurancePercentage(BigDecimal insurancePercentage) {
		this.insurancePercentage = insurancePercentage;
	}*/

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Scheme other = (Scheme) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
