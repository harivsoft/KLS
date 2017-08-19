package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

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
 * @author a9152
 * 
 */

@TypeDefs({
	@TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = {
			@Parameter(name = "defaultISOCurrencyCode", value = "INR"),
			@Parameter(name = "isDebitNegative", value = "true") }) })
@Entity
@Table(name = "SLABWISE_INTEREST_RATE")
public class SlabwiseInterestRate implements Serializable {

	private static final long serialVersionUID = -5288660432318897602L;

	public SlabwiseInterestRate() {
	}

	@Id
	@SequenceGenerator(name = "slabwiseInterestIdSeq", sequenceName = "slabwiseinterest_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "slabwiseInterestIdSeq")
	@Column(name = "ID")
	private Long slabwiseInterestRateId;

	@ManyToOne(optional=false)
	@JoinColumn(name="INTEREST_CATEGORY_ID",referencedColumnName="ID")
	private InterestCategory interestCategory;

	@Basic
	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;

	@Basic
	@Type(type = "money")
	@Column(name = "FROM_SLAB", precision = 22, scale = 6)
	private Money fromSlab;

	@Basic
	@Type(type = "money")
	@Column(name = "TO_SLAB", precision = 22, scale = 6)
	private Money toSlab;

	@Basic
	@Column(name = "ROI", precision = 6, scale = 2)
	private BigDecimal roi;

	@Basic
	@Column(name = "PENAL_ROI", precision = 6, scale = 2)
	private BigDecimal penalRoi;

	@Basic
	@Column(name = "FROM_PERIOD", nullable = false)
	private Integer fromPeriod;

	@Basic
	@Column(name = "TO_PERIOD", nullable = false)
	private Integer toPeriod;

	/**
	 * @return the slabwiseInterestRateId
	 */
	public Long getSlabwiseInterestRateId() {
		return slabwiseInterestRateId;
	}

	/**
	 * @param slabwiseInterestRateId
	 *            the slabwiseInterestRateId to set
	 */
	public void setSlabwiseInterestRateId(Long slabwiseInterestRateId) {
		this.slabwiseInterestRateId = slabwiseInterestRateId;
	}

	/**
	 * @return the fromDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate
	 *            the fromDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}



	/**
	 * @return the roi
	 */
	public BigDecimal getRoi() {
		return roi;
	}

	/**
	 * @param roi
	 *            the roi to set
	 */
	public void setRoi(BigDecimal roi) {
		this.roi = roi;
	}

	/**
	 * @return the penalRoi
	 */
	public BigDecimal getPenalRoi() {
		return penalRoi;
	}

	/**
	 * @param penalRoi
	 *            the penalRoi to set
	 */
	public void setPenalRoi(BigDecimal penalRoi) {
		this.penalRoi = penalRoi;
	}

	/**
	 * @return the fromPeriod
	 */
	public Integer getFromPeriod() {
		return fromPeriod;
	}

	/**
	 * @param fromPeriod
	 *            the fromPeriod to set
	 */
	public void setFromPeriod(Integer fromPeriod) {
		this.fromPeriod = fromPeriod;
	}

	/**
	 * @return the toPeriod
	 */
	public Integer getToPeriod() {
		return toPeriod;
	}

	/**
	 * @param toPeriod
	 *            the toPeriod to set
	 */
	public void setToPeriod(Integer toPeriod) {
		this.toPeriod = toPeriod;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public InterestCategory getInterestCategory() {
		return interestCategory;
	}

	public Money getFromSlab() {
		return fromSlab;
	}

	public Money getToSlab() {
		return toSlab;
	}

	public void setInterestCategory(InterestCategory interestCategory) {
		this.interestCategory = interestCategory;
	}

	public void setFromSlab(Money fromSlab) {
		this.fromSlab = fromSlab;
	}

	public void setToSlab(Money toSlab) {
		this.toSlab = toSlab;
	}
}
