/**
 * 
 */
package com.vsoftcorp.kls.service.transaction;

import java.util.List;
import java.util.Map;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.data.CurrentTransactionData;
import com.vsoftcorp.kls.data.MiniStatementData;
import com.vsoftcorp.kls.data.VoucherData;
import com.vsoftcorp.kls.data.gateway.datahelpers.KLSRequest;
import com.vsoftcorp.kls.data.gateway.datahelpers.KLSResponse;
import com.vsoftcorp.kls.data.gateway.datahelpers.STLoanRecoveryData;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;

/**
 * @author a9152
 * 
 */
public interface ICurrentTransactionService {

	/**
	 * Saves the current transaction data to the DB.
	 * 
	 * @param currentTransactionData
	 */
	public KLSResponse handleDebitTransaction(CurrentTransactionData currentTransactionData, String suvikasBalance);

	/**
	 * Saves the credit current transaction data to the DB.
	 * 
	 * @param currentTransactionData
	 */
	public KLSResponse handleCreditTransaction(CurrentTransactionData currentTransactionData);

	/**
	 * Updates the current transaction data to the DB.
	 * 
	 * @param currentTransactionData
	 */
	public void updateCurrentTransaction(CurrentTransactionData currentTransactionData);

	/**
	 * Returns all the current transaction data to the client.
	 * 
	 * @return
	 */
	public List<CurrentTransactionData> getAllCurrentTransactions();

	/**
	 * Saves the kind current transaction data to the DB.
	 * 
	 * @param currentTransactionData
	 */
	public KLSResponse handleKindTransaction(CurrentTransactionData currentTransactionData);

	/**
	 * Computes and returns available loan balance
	 * 
	 * @param currentTransactionData
	 */
	public KLSResponse handleBalanceEnquiry(CurrentTransactionData currentTransactionData);

	/**
	 * 
	 * @param loanLoc
	 * @param currentTransactionList
	 * @param transactionCode
	 * @param remarks
	 * @param crdr
	 * @param transactionAmount
	 * @param transactionType
	 * @param voucherNumber
	 */
	public void populateCurrentTransactionRecord(LineOfCredit loanLoc, List<CurrentTransaction> currentTransactionList,
			TransactionCode transactionCode, String remarks, Integer crdr, AccountingMoney transactionAmount, TransactionType transactionType,
			String voucherNumber);
	public void checkTransactionDateValidity(CurrentTransaction master, List lineOfCreditAccountsList);

	
	public KLSResponse handleCreditTransaction(CurrentTransactionData currentTransactionData, STLoanRecoveryData stLoanRecoveryData);
	
	public List<VoucherData> getTransactionsByVoucherNumber(
			String voucherNumber);
	
	public String checkTransactionDateValid(CurrentTransaction master, List lineOfCreditAccountsList);
	
	public Map<String,String> checkLoanLimitsService(KLSRequest klsRequest);
	
	public KLSResponse reversalTransaction(Map<String,String> isoDataElements);
	
	public List<MiniStatementData> getMiniStatement(String accountNum);
}
