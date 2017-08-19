/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;

import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;

/**
 * @author a9152
 * 
 */
public interface ILoanLineOfCreditDAO {

	public List<LoanLineOfCredit> getLoanLocListByCustomerId(Long customerId);

	public LoanLineOfCredit getLoanLineOfCreditById(Long loanLocId);

	public List<Long> getLoanLocListByApplicationId(Long id);

	/**
	 * 
	 * @param loanLoc
	 */
	public void saveLoanLineOfCredit(LoanLineOfCredit loanLoc);

	/**
	 * 
	 * @param loanLoc
	 * @return
	 */
	public LoanLineOfCredit mergeLoanLineOfCredit(LoanLineOfCredit loanLoc);

	public List<LoanLineOfCredit> getDisbursedLocList(Long customerId);

	/**
	 * 
	 * @param loanLoc
	 */
	public void closeLoanLineOfCredit(LoanLineOfCredit loanLoc, List<CurrentTransaction> currentTransactionList);

	/**
	 * 
	 * @param loanAcctPropertyId
	 * @return
	 */
	public LoanLineOfCredit getLoanLineOfCreditByPropertyId(Long loanAcctPropertyId);

}
