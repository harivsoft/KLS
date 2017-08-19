package com.vsoftcorp.kls.business.entity.loan;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LoanDisbursementCompositeId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9080468038430827540L;

	@Column(name = "line_of_credit_id")
	private Long lineOfCreditId;

	@Column(name = "no_of_disbursement")
	private Integer noOfDisbursement;

	public Long getLineOfCreditId() {
		return lineOfCreditId;
	}

	public void setLineOfCreditId(Long lineOfCreditId) {
		this.lineOfCreditId = lineOfCreditId;
	}

	public Integer getNoOfDisbursement() {
		return noOfDisbursement;
	}

	public void setNoOfDisbursement(Integer noOfDisbursement) {
		this.noOfDisbursement = noOfDisbursement;
	}

}
