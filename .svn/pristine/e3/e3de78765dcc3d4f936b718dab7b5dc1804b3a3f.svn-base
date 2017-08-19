package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author a9152
 * 
 */
@Entity
@Table(name = "VILLAGE")
public class Village implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7012632568039349509L;

	public Village() {
	}

	@Id
	@GeneratedValue(generator = "villageIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "villageIdSequence", sequenceName = "village_id_seq", allocationSize = 1)
	@Column(name = "ID")
	private Integer id;

	@Basic
	@Column(name = "NAME", length = 45)
	private String name;

	@Basic
	@Column(name = "PIN", length = 6)
	private Integer pin;

	@ManyToOne(optional = false)
	@JoinColumn(name = "taluka_id", referencedColumnName = "id")
	private Taluka taluka;

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

	public Integer getPin() {
		return pin;
	}

	public void setPin(Integer pin) {
		this.pin = pin;
	}

	public Taluka getTaluka() {
		return taluka;
	}

	public void setTaluka(Taluka taluka) {
		this.taluka = taluka;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pin == null) ? 0 : pin.hashCode());
		result = prime * result + ((taluka == null) ? 0 : taluka.hashCode());
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
		Village other = (Village) obj;
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
		if (pin == null) {
			if (other.pin != null)
				return false;
		} else if (!pin.equals(other.pin))
			return false;
		if (taluka == null) {
			if (other.taluka != null)
				return false;
		} else if (!taluka.equals(other.taluka))
			return false;
		return true;
	}

}
