package com.vsoftcorp.kls.business.entity.loan;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LoanApplicationGurantorId implements Serializable {
	
	@Column(name="application_id")
	private Long applicationId;
	
	@Column(name="guarantor_id")
	private Long guarantorId;

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getGuarantorId() {
		return guarantorId;
	}

	public void setGuarantorId(Long guarantorId) {
		this.guarantorId = guarantorId;
	}

}
