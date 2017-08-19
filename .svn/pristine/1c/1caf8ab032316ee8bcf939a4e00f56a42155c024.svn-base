/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;

import com.vsoftcorp.kls.business.entity.loan.LoanRepaymentSchedule;
import com.vsoftcorp.time.Date;

/**
 * @author a9152
 * 
 */
public interface ILoanRepaymentScheduleDAO {

	/**
	 * 
	 * @param loanRepaymentScheduleList
	 */
	public void updateLoanRepaymentScheduleList(List<LoanRepaymentSchedule> loanRepaymentScheduleList);

	/**
	 * 
	 * @param loanLocId
	 * @return
	 */
	public List<LoanRepaymentSchedule> getLoanRepaymentScheduleByLocId(Long loanLocId);

	/**
	 * 
	 * @return
	 */
	public List<LoanRepaymentSchedule> getAllLoanRepaymentSchedules();

	/**
	 * 
	 * @param loanRepaymentScheduleList
	 */
	public void saveLoanRepaymentScheduleList(List<LoanRepaymentSchedule> loanRepaymentScheduleList);

	public List<LoanRepaymentSchedule> getLoanRepaymentScheduleByLocIdAndBusinessDate(Long locId, Date businessDate);


	/**
	 * 
	 * @param loanLocId
	 */
	public void deleteLoanRepaymentScheduleByLocId(Long loanLocId);
	
	public LoanRepaymentSchedule getLoanRepaymentSchedule(Long locId, Date businessDate);
	
	public List<LoanRepaymentSchedule> getLoanRepaymentScheduleByLocIdAndBusinessDateForDisburshment(Long locId, Date businessDate);
	
	public List<LoanRepaymentSchedule> getNextInstallmentAmount(Long locId, Date businessDate);
	
}
