/**
 * 
 */
package com.vsoftcorp.kls.data;

import java.util.List;

/**
 * @author a9152
 * 
 */
public class LoanRepaymentScheduleDataList {

	private List<LoanRepaymentScheduleData> loanRepaymentScheduleData;

	private LoanRepaymentEditTypeData repaymentEditTypeData;

	private String errorMessage;

	/**
	 * @return the loanRepaymentScheduleData
	 */
	public List<LoanRepaymentScheduleData> getLoanRepaymentScheduleData() {
		return loanRepaymentScheduleData;
	}

	/**
	 * @param loanRepaymentScheduleData
	 *            the loanRepaymentScheduleData to set
	 */
	public void setLoanRepaymentScheduleData(List<LoanRepaymentScheduleData> loanRepaymentScheduleData) {
		this.loanRepaymentScheduleData = loanRepaymentScheduleData;
	}

	/**
	 * @return the repaymentEditTypeData
	 */
	public LoanRepaymentEditTypeData getRepaymentEditTypeData() {
		return repaymentEditTypeData;
	}

	/**
	 * @param repaymentEditTypeData
	 *            the repaymentEditTypeData to set
	 */
	public void setRepaymentEditTypeData(LoanRepaymentEditTypeData repaymentEditTypeData) {
		this.repaymentEditTypeData = repaymentEditTypeData;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
