package com.vsoftcorp.kls.business.entity.loan;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="pacs_loan_app_guarantor")
public class LoanApplicationForGuarantorDetails implements Serializable {
	
	@EmbeddedId
	private LoanApplicationGurantorId loanApplicationGurantorId;

	public LoanApplicationGurantorId getLoanApplicationGurantorId() {
		return loanApplicationGurantorId;
	}

	public void setLoanApplicationGurantorId(
			LoanApplicationGurantorId loanApplicationGurantorId) {
		this.loanApplicationGurantorId = loanApplicationGurantorId;
	}

}
