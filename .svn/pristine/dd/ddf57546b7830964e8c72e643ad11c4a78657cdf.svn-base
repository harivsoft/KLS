package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

import javax.persistence.*;

import com.vsoftcorp.kls.business.entities.District;

/**
 * 
 * @author a9153
 * This is the entity class with JPA mappings for Taluka
 */

@Entity
@Table(name = "taluka")
public class Taluka implements Serializable {

	private static final long serialVersionUID = -7561752101720603519L;

	public Taluka() {
	}

	@Id
	@GeneratedValue(generator = "talukaIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "talukaIdSequence", sequenceName = "taluka_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Integer id;
	@Basic
	@Column(name = "name",length=45)
	private String name;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "dist_id", referencedColumnName = "id")
	private  District district;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((district == null) ? 0 : district.hashCode());
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((name == null) ? 0 : name.hashCode());
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
		Taluka other = (Taluka) obj;
		if (district == null) {
			if (other.district != null)
				return false;
		} else if (!district.equals(other.district))
			return false;
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

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}
	
}