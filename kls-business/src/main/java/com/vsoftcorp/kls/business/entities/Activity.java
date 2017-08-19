package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;
import java.math.BigDecimal;

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

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.finance.Money;

/**
 * 
 * @author a1565
 * 
 */
@TypeDefs({ @TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = {
		@Parameter(name = "defaultISOCurrencyCode", value = "INR"),
		@Parameter(name = "isDebitNegative", value = "true") }) })
@Entity
@Table(name = "activity")
public class Activity implements Serializable {

	private static final long serialVersionUID = -7561752101720603519L;

	public Activity() {
	}

	@Id
	@GeneratedValue(generator = "activityIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "activityIdSequence", sequenceName = "activity_id_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "activity_name", length = 45)
	private String name;

	@Basic
	@Column(name = "unit_cost", precision = 22, scale = 6)
	private BigDecimal unitCost;

	@Basic
	@Column(name = "maximum_limit", precision = 22, scale = 6)
	@Type(type = "money")
	private Money maximumLimit;

	@ManyToOne(optional = false)
	@JoinColumn(name = "unit_id",referencedColumnName="id")
	private Unit unit;
	
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
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

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public Money getMaximumLimit() {
		return maximumLimit;
	}

	public void setMaximumLimit(Money maximumLimit) {
		this.maximumLimit = maximumLimit;
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
		Activity other = (Activity) obj;
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
