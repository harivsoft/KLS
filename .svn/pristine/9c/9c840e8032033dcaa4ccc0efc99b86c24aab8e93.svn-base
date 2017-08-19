/**
 * 
 */
package com.vsoftcorp.kls.service.loan;

import java.util.List;

import com.vsoftcorp.kls.data.LoanRepaymentScheduleData;
import com.vsoftcorp.kls.data.LoanRepaymentScheduleDataList;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.time.Date;

/**
 * @author a9152
 * 
 */
public interface ILoanRepaymentScheduleService {

	/**
	 * 
	 * @param loanRepaymentScheduleData
	 */
	public void saveRepaymentSchedule(LoanRepaymentScheduleData loanRepaymentScheduleData);

	/**
	 * 
	 * @param lineOfCreditId
	 * @return
	 */
	public LoanRepaymentScheduleDataList getRepaymentScheduleDataList(Long lineOfCreditId, Integer editType);

	/**
	 * 
	 * @param scheduleDataList
	 * @return
	 */
	public void updateRepaymentScheduleDataList(LoanRepaymentScheduleDataList scheduleDataList);
	
	
	public LoanRepaymentScheduleData getRepaymentScheduleData(Long lineOfCreditId, Date businessDate);
	
	public List<LoanRepaymentScheduleData> getLoanRepaymentScheduleByLocIdAndBusinessDate(Long lineOfCreditId, Date businessDate);
	
	public String getNextInstallmentAmount(Long lineOfCreditId, Date businessDate);

}
