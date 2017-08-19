/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;

import com.vsoftcorp.kls.business.entity.loan.TempLoanRepaymentSchedule;

/**
 * @author a9152
 * 
 */
public interface ITempLoanRepaymentScheduleDAO {

	/**
	 * 
	 * @param loanRepaymentScheduleList
	 */
	public void updateTempLoanRepaymentScheduleList(List<TempLoanRepaymentSchedule> loanRepaymentScheduleList);

	/**
	 * 
	 * @param loanLocId
	 * @return
	 */
	public List<TempLoanRepaymentSchedule> getTempLoanRepaymentScheduleByLocId(Long loanLocId);

	/**
	 * 
	 * @param loanRepaymentScheduleList
	 */
	public void saveTempLoanRepaymentScheduleList(List<TempLoanRepaymentSchedule> loanRepaymentScheduleList);

	/**
	 * 
	 * @param loanLocId
	 * @return
	 */
	public void deleteTempLoanRepaymentScheduleByLocId(Long loanLocId);

}
