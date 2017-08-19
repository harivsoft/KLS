package com.vsoftcorp.kls.business.entity.subsidy;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "subsidy_interest_amounts")
public class SubsidyInterestAmounts {
	
	@EmbeddedId
    private SubsidyInterestAmountsId id;
	
	@Basic
	@Column(name = "subsidy_accrued")
	private BigDecimal subsidyAccrued;

	@Basic
	@Column(name = "subsidy_receivable")
	private BigDecimal subsidyReceivable;
	
	@Basic
	@Column(name = "subsidy_paid")
	private BigDecimal subsidyPaid;

	public BigDecimal getSubsidyAccrued() {
		return subsidyAccrued;
	}

	public void setSubsidyAccrued(BigDecimal subsidyAccrued) {
		this.subsidyAccrued = subsidyAccrued;
	}

	public BigDecimal getSubsidyReceivable() {
		return subsidyReceivable;
	}

	public void setSubsidyReceivable(BigDecimal subsidyReceivable) {
		this.subsidyReceivable = subsidyReceivable;
	}

	public SubsidyInterestAmountsId getId() {
		return id;
	}

	public void setId(SubsidyInterestAmountsId id) {
		this.id = id;
	}

	public BigDecimal getSubsidyPaid() {
		return subsidyPaid;
	}

	public void setSubsidyPaid(BigDecimal subsidyPaid) {
		this.subsidyPaid = subsidyPaid;
	}
	
	

}
