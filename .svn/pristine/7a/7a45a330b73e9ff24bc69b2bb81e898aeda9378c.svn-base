/**
 * 
 */
package com.vsoftcorp.kls.service.thread;

import java.math.BigDecimal;

import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IAccountDAO;

/**
 * @author a9152
 * 
 */
public class PostTransactionThread implements Runnable {

	private CurrentTransaction currentTransaction;

	private BigDecimal totalCharges;

	/**
	 * @return the totalCharges
	 */
	public BigDecimal getTotalCharges() {
		return totalCharges;
	}

	/**
	 * @param totalCharges
	 *            the totalCharges to set
	 */
	public void setTotalCharges(BigDecimal totalCharges) {
		this.totalCharges = totalCharges;
	}

	/**
	 * @return the currentTransaction
	 */
	public CurrentTransaction getCurrentTransaction() {
		return currentTransaction;
	}

	/**
	 * @param currentTransaction
	 *            the currentTransaction to set
	 */
	public void setCurrentTransaction(CurrentTransaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}

	@Override
	public void run() {
		
		IAccountDAO dao = KLSDataAccessFactory.getAccountDAO();
		CurrentTransaction currTransaction = getCurrentTransaction();
		Account account = dao.getAccount(currTransaction.getAccount(), false);
		String accountNumber = account.getAccountNumber();
		String customerId = account.getAccountProperty().getCustomerId().toString();

	}
}
