/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.transaction;

import java.util.List;

import com.vsoftcorp.kls.business.entity.transaction.TransactionHistory;
import com.vsoftcorp.kls.dataaccess.transaction.result.classes.TransactionRecordByProductAndCrdr;
import com.vsoftcorp.time.Date;
/**
 * @author a9152
 * 
 */
public interface ITransactionHistoryDAO {

	/**
	 * Saves the transaction history data to the database.
	 * 
	 * @param transactionHistory
	 */
	public void saveTransactionHistory(TransactionHistory transactionHistory);

	/**
	 * Updates the existing transaction history data to the database.
	 * 
	 * @param transactionHistory
	 */
	public void updateTransactionHistory(TransactionHistory transactionHistory);

	/**
	 * Checks if the transaction history id exists in the database.
	 * 
	 * @param transactionHistory
	 * @return TransactionHistory
	 */
	public TransactionHistory getTransactionHistory(
			TransactionHistory transactionHistory);

	/**
	 * 
	 * @param transactionHistoryId
	 * @return
	 */
	public TransactionHistory getTransactionHistoryById(
			Long transactionHistoryId);

	/**
	 * Returns all the transaction history records to the client.
	 * 
	 * @return
	 */
	public List<TransactionHistory> getAllTransactionHistoryRecords();
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public List<TransactionHistory> getTransactionHistoryRecordsByDate(String date);
	
	/**
	 * 
	 * @param flag
	 * @return
	 */
	public List<TransactionRecordByProductAndCrdr> getTransactionHistoryRecordsByProductAndCrdr();
	
	/**
	 * 
	 * @param transactionHistory,isCloseSession
	 * @return
	 */
	
	public void saveTransactionHistory(TransactionHistory transactionHistory,Boolean isCloseSession);
	
	public List getTransactionHistoryRecordsById(Long custId, String fromDate, String toDate);
	public List getTransactionHistoryRecordsById(Long custId, Long locid, String fromDate, String toDate);

	public List<TransactionHistory> getMiniStatement(String accountNum,Date transactionDate,Integer limit);

}
