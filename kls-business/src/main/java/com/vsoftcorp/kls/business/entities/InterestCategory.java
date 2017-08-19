package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 
 * @author a9153
 * This is the entity class with JPA mappings for interest category
 */

@Entity
@Table(name = "INTEREST_CATEGORY")
public class InterestCategory implements Serializable {

	private static final long serialVersionUID = -7561752101720603519L;

	public InterestCategory() {
	}
	
	@Id
	@GeneratedValue(generator = "interestCategoryIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "interestCategoryIdSequence", sequenceName = "interest_category_id_seq", allocationSize = 1)
	
	@Column(name = "id")
	private Integer id;
	
	@Basic
	@Column(name = "DESCRIPTION", length = 45)
	private String intrCategoryDesc;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIntrCategoryDesc() {
		return intrCategoryDesc;
	}

	public void setIntrCategoryDesc(String intrCategoryDesc) {
		this.intrCategoryDesc = intrCategoryDesc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((intrCategoryDesc == null) ? 0 : intrCategoryDesc.hashCode());
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
		InterestCategory other = (InterestCategory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (intrCategoryDesc == null) {
			if (other.intrCategoryDesc != null)
				return false;
		} else if (!intrCategoryDesc.equals(other.intrCategoryDesc))
			return false;
		return true;
	}

}