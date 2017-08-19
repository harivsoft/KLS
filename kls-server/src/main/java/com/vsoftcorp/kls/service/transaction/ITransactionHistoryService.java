/**
 * 
 */
package com.vsoftcorp.kls.service.transaction;

import java.util.List;

import com.vsoftcorp.kls.data.TransactionHistoryData;

/**
 * @author a9152
 *
 */
public interface ITransactionHistoryService {
	
	/**
	 * Saves the transaction history data to the DB.
	 * 
	 * @param transactionHistoryData
	 */
	public void saveTransactionHistory(TransactionHistoryData transactionHistoryData);

	/**
	 * Updates the transaction history data to the DB.
	 * 
	 * @param transactionHistoryData
	 */
	public void updateTransactionHistory(TransactionHistoryData transactionHistoryData);

	/**
	 * Returns all the transaction history data to the client.
	 * 
	 * @return
	 */
	public List<TransactionHistoryData> getAllTransactionHistoryRecords();
	
	//for omni transaction history
	public List<TransactionHistoryData> getAllTransactionHistoryRecordsById(Long custId, Long locId, String fromDate, String toDate);

}
