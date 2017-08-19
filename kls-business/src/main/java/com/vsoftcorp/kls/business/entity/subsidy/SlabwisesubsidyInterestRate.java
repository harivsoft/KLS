package com.vsoftcorp.kls.business.entity.subsidy;

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
 * @author a1623
 *
 */

@TypeDefs({
	@TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = {
			@Parameter(name = "defaultISOCurrencyCode", value = "INR"),
			@Parameter(name = "isDebitNegative", value = "true") }) })

@Entity
@Table(name = "slabwise_subsidy_interestrate")
public class SlabwisesubsidyInterestRate {
	
	@Id
	@GeneratedValue(generator = "slabwiseSubsidyRoiIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "slabwiseSubsidyRoiIdSequence", sequenceName = "slabwise_subsidy_roi_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;
	
	@Basic
	@Type(type = "money")
	@Column(name = "from_amount", precision = 22, scale = 6)
	private Money fromAmount;

	@Basic
	@Type(type = "money")
	@Column(name = "to_amount", precision = 22, scale = 6)
	private Money toAmount;

	@Basic
	@Column(name = "subsidy_roi_per_annum")
	private BigDecimal subsidyRoiPerAnnum;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "interest_subsidy_id", referencedColumnName = "id")
	private InterestSubsidy interestSubsidy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Money getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(Money fromAmount) {
		this.fromAmount = fromAmount;
	}

	public Money getToAmount() {
		return toAmount;
	}

	public void setToAmount(Money toAmount) {
		this.toAmount = toAmount;
	}

	public BigDecimal getSubsidyRoiPerAnnum() {
		return subsidyRoiPerAnnum;
	}

	public void setSubsidyRoiPerAnnum(BigDecimal subsidyRoiPerAnnum) {
		this.subsidyRoiPerAnnum = subsidyRoiPerAnnum;
	}

	public InterestSubsidy getInterestSubsidy() {
		return interestSubsidy;
	}

	public void setInterestSubsidy(InterestSubsidy interestSubsidy) {
		this.interestSubsidy = interestSubsidy;
	}
	
	
	
}
