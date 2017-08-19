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

@Entity
@Table(name = "PRODUCT_TYPE")
public class ProductType implements Serializable {

	private static final long serialVersionUID = 6067921645190702550L;

	public ProductType() {
	}

	@Id
	@SequenceGenerator(name = "productTypeIdSeq", sequenceName = "product_type_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productTypeIdSeq")
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "DESCRIPTION", length = 45)
	private String description;

	@Basic
	@Column(name = "ATM_APPLICABLE", length = 1)
	private String atmApplicable;

	@ManyToOne(optional = false)
	@JoinColumn(name = "interest_category_id", referencedColumnName = "id")
	private InterestCategory interestCategory;

	@Basic
	@Column(name = "loan_type", length = 2)
	private String loanType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAtmApplicable() {
		return atmApplicable;
	}

	public void setAtmApplicable(String atmApplicable) {
		this.atmApplicable = atmApplicable;
	}

	public InterestCategory getInterestCategory() {
		return interestCategory;
	}

	public void setInterestCategory(InterestCategory interestCategory) {
		this.interestCategory = interestCategory;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((atmApplicable == null) ? 0 : atmApplicable.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((interestCategory == null) ? 0 : interestCategory.hashCode());
		result = prime * result
				+ ((loanType == null) ? 0 : loanType.hashCode());
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
		ProductType other = (ProductType) obj;
		if (atmApplicable == null) {
			if (other.atmApplicable != null)
				return false;
		} else if (!atmApplicable.equals(other.atmApplicable))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (interestCategory == null) {
			if (other.interestCategory != null)
				return false;
		} else if (!interestCategory.equals(other.interestCategory))
			return false;
		if (loanType == null) {
			if (other.loanType != null)
				return false;
		} else if (!loanType.equals(other.loanType))
			return false;
		return true;
	}

}
