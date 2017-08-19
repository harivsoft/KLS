package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;

import com.vsoftcorp.kls.business.entity.loan.LoanDisbursementSchedule;

/**
 * 
 * @author a1565
 *
 */
public interface ILoanDisbursementScheduleDAO {

	public void saveLoanDisbursementSchedule(LoanDisbursementSchedule master);
	
	public void deleteNonDisbursedSchedule(Long lineOfCreditId);

	public List<LoanDisbursementSchedule> getDisbursementSchedule(Long lineOfCreditId);

	

	
}
