/**
 * 
 */
package com.vsoftcorp.kls.business.entity.loan;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author a9152
 * 
 */
@Embeddable
public class LoanRepaymentScheduleId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9080468038430827540L;

	@Column(name = "line_of_credit_id")
	private Long lineOfCreditId;

	@Column(name = "installment_number")
	private Integer installmentNumber;

	public Long getLineOfCreditId() {
		return lineOfCreditId;
	}

	public void setLineOfCreditId(Long lineOfCreditId) {
		this.lineOfCreditId = lineOfCreditId;
	}

	/**
	 * @return the installmentNumber
	 */
	public Integer getInstallmentNumber() {
		return installmentNumber;
	}

	/**
	 * @param installmentNumber
	 *            the installmentNumber to set
	 */
	public void setInstallmentNumber(Integer installmentNumber) {
		this.installmentNumber = installmentNumber;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}