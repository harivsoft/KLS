package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "district")
public class District implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8077315832639353814L;

	public District() {
	}

	@Id
	@GeneratedValue(generator = "districtIdsequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "districtIdsequence", sequenceName = "district_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "name", nullable=false, length = 45)
	private String name;

	@Basic
	@Column(name = "dcc_bank_code", nullable=false, length = 5)
	private String dccBankCode;

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

	public String getDccBankCode() {
		return dccBankCode;
	}

	public void setDccBankCode(String dccBankCode) {
		this.dccBankCode = dccBankCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dccBankCode == null) ? 0 : dccBankCode.hashCode());
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
		District other = (District) obj;
		if (dccBankCode == null) {
			if (other.dccBankCode != null)
				return false;
		} else if (!dccBankCode.equals(other.dccBankCode))
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

}