/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;

import com.vsoftcorp.kls.business.entity.account.BorrowingsLineOfCredit;
import com.vsoftcorp.kls.business.entity.account.Charges;
import com.vsoftcorp.kls.business.entity.account.LoanAccountProperty;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.LoanDisbursementSchedule;
import com.vsoftcorp.kls.business.entity.loan.LoanRepaymentSchedule;

/**
 * @author a9152
 * 
 */
public interface ILoanAccountPropertyDAO {

	/**
	 * 
	 * @param custId
	 * @param isCloseSession
	 * @return
	 */
	public LoanAccountProperty checkIfAccountExists(Long custId, boolean isCloseSession);

	/**
	 * 
	 * @param account
	 */
	public void updateLoanAccountProperty(LoanAccountProperty loanAccountProperty);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public LoanAccountProperty getLoanAccountProperty(Long loanAccountPropertyId);

	/**
	 * 
	 * @return
	 */
	public List<LoanAccountProperty> getAllLoanAccountProperties();

	/**
	 * 
	 * @param loanAccountProperty
	 * @return
	 */
	public LoanAccountProperty saveLoanAccountProperty(LoanAccountProperty loanAccountProperty);

	/**
	 * 
	 * @param loanAccountProperty
	 * @param loanLoc
	 * @param chargesList
	 * @param scheduleList
	 * @param disbursementSchedule
	 */
	public LoanLineOfCredit saveLoanAccountProperty(LoanAccountProperty loanAccountProperty, LoanLineOfCredit loanLoc,
			List<Charges> chargesList, List<LoanRepaymentSchedule> scheduleList,
			LoanDisbursementSchedule disbursementSchedule, BorrowingsLineOfCredit bloc);

	/**
	 * 
	 * @param accountId
	 * @return
	 */

	public Long getCustomerIdByAccount(Long accountId);

	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public LoanAccountProperty getLoanAccountPropertyByAccountId(Long accountId);

	/**
	 * 
	 * @param custId
	 * @return
	 */
	public List<String> getSavingsAccountNumberByCustId(Long custId);

	/**
	 * 
	 * @param loanAccountProperty
	 * @param charge
	 * @param loanLoc
	 */
	public LoanLineOfCredit updateLoanAccountProperty(LoanAccountProperty loanAccountProperty, Charges charge,
			LoanLineOfCredit loanLoc);

}
