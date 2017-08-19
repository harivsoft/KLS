package com.vsoftcorp.kls.dataaccess.transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.entity.transaction.TransactionHistory;
import com.vsoftcorp.kls.dataaccess.transaction.result.classes.TransactionRecordByProductAndCrdr;
import com.vsoftcorp.time.Date;

/**
 * @author a9152
 * 
 */
public interface ICurrentTransactionDAO {

	/**
	 * Saves the current transaction data to the database.
	 * 
	 * @param currentTransaction
	 */
	public void saveCurrentTransaction(CurrentTransaction currentTransaction);

	/**
	 * Updates the existing current transaction data to the database.
	 * 
	 * @param currentTransaction
	 */
	public void updateCurrentTransaction(CurrentTransaction currentTransaction);

	/**
	 * Checks if the current transaction id exists in the database.
	 * 
	 * @param currentTransaction
	 * @return CurrentTransaction
	 */
	public CurrentTransaction getCurrentTransaction(CurrentTransaction currentTransaction);

	/**
	 * 
	 * @param currentTransactionId
	 * @return
	 */
	public CurrentTransaction getCurrentTransactionById(Long currentTransactionId);

	/**
	 * Returns all the current transaction records to the client.
	 * 
	 * @return
	 */
	public List<CurrentTransaction> getAllCurrentTransactions();

	/**
	 * Saves the current transaction data and update the debit amount to the
	 * line of credit accounts.
	 * 
	 * @param <T>
	 * 
	 * @param currentTransaction
	 */
	public <T> void saveCurrentTransaction(List<CurrentTransaction> currentTransactionList,
			Map<String, List<T>> debitAccountMap);

	/**
	 * 
	 * @param currentTransactionList
	 * @param creditAccountMap
	 */
	public void saveCreditCurrentTransaction(List<CurrentTransaction> currentTransactionList,
			Map<String, Object> creditAccountMap);

	public Integer moveCurrentTransactionRecordsToTransactionHistory(List<Integer> pacsIdsList);

	public List<CurrentTransaction> getCurrentTransactionByStatus(List<Integer> pacsIdsList);
	
	public List<CurrentTransaction> getCurrentTransactionByStatus(String loanType,List<Integer> pacsIdsList);
	
	public List<CurrentTransaction> getCurrentTransactionsByDate(String date);
	
	/**
	 * 
	 * @return
	 */
	public List<TransactionRecordByProductAndCrdr> getCurrentTransactionsByProductAndCrdr();
	
	/**
	 * Saves the current transaction data and update the kind amount to the
	 * line of credit accounts.
	 * 
	 * @param <T>
	 * 
	 * @param currentTransaction
	 */
	public <T> void saveKindCurrentTransaction(List<CurrentTransaction> currentTransactionList,
			Map<String, List<T>> kindAccountMap);
	
	public List<CurrentTransaction> saveCurrentTransactionsList(List<CurrentTransaction> currentTransactionsList);
	
	public List<Map> getTransactionsGroupedByDateProductPacs(Integer crDr , String loanType,List<Integer> pacsIdsList);
	
	public List<Map> getTransactionsGroupedByDateProductPacsTransCode(Integer crDr, String loanType,List<Integer> pacsIdsList);
	
	public List<Map> getTransactionsGroupedByDateProductPacsLoc(Integer crDr, String loanType,List<Integer> pacsIdsList);

	public Date getFirstDrawalLocDates(Long id);

	public List<CurrentTransaction> getSubsidyAllSubsidyTransactions(
			LineOfCredit loc);

	public List<TransactionHistory> getSubsidyAllSubsidyHisTransactions(
			LineOfCredit loc);
	
	public List<CurrentTransaction> getTransactionsByVoucherNumber(String voucherNumber);

	public AccountingMoney getSubsidyTransactionsByLocId(Long locId,Integer prdId,Date businessDate);

	public CurrentTransaction getCurrentTransactionByAccountNum(String accountNum,Date transactionDate);
	
	public String saveReversalTransaction(CurrentTransaction master);
	
	public List<CurrentTransaction> getMiniStatement(String accountNum,Date transactionDate);
	
	public List<CurrentTransaction> getTransactionsByPacs(String loanType,Integer pacsID);
}
