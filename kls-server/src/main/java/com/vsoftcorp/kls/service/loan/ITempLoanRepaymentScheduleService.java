/**
 * 
 */
package com.vsoftcorp.kls.service.loan;

import com.vsoftcorp.kls.data.LoanRepaymentScheduleData;
import com.vsoftcorp.kls.data.LoanRepaymentScheduleDataList;

/**
 * @author a9152
 * 
 */
public interface ITempLoanRepaymentScheduleService {

	/**
	 * 
	 * @param loanRepaymentScheduleData
	 */
	public void saveTempRepaymentSchedule(LoanRepaymentScheduleData loanRepaymentScheduleData);

	/**
	 * 
	 * @param lineOfCreditId
	 * @return
	 */
	public LoanRepaymentScheduleDataList getModifiedRepaymentScheduleDataList(Long lineOfCreditId,
			Integer installmentNum, String principalAmt,Integer editType);

	/**
	 * 
	 * @param scheduleDataList
	 * @return
	 */
	public void updateTempRepaymentScheduleDataList(LoanRepaymentScheduleDataList scheduleDataList);
}
