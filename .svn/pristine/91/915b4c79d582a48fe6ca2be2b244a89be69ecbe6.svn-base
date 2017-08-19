/**
 * 
 */
package com.vsoftcorp.kls.service.transaction.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.transaction.TransactionHistory;
import com.vsoftcorp.kls.data.TransactionHistoryData;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.transaction.ITransactionHistoryDAO;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.TransactionHistoryHelper;
import com.vsoftcorp.kls.service.transaction.ITransactionHistoryService;

/**
 * @author a9152
 * 
 */
public class TransactionHistoryService implements ITransactionHistoryService {

	private static final Logger logger = Logger
			.getLogger(TransactionHistoryService.class);

	@Override
	public void saveTransactionHistory(
			TransactionHistoryData transactionHistoryData) {

		logger.info("Start : Calling transaction history dao in saveTransactionHistory() method.");
		// get the transaction history dao.
		ITransactionHistoryDAO dao = KLSDataAccessFactory
				.getTransactionHistoryDAO();
		TransactionHistory master = TransactionHistoryHelper
				.getTransactionHistory(transactionHistoryData);
		try {
			if (master.getId() == null) {
				dao.saveTransactionHistory(master);
			} else {
				logger.error("Transaction history id already exists.");
				throw new KlsRuntimeException(
						"Transaction history id already exists");
			}
		} catch (Exception excp) {
			logger.error("Transaction history data cannot be saved.");
			throw new KlsRuntimeException(
					"Transaction history data cannot be saved", excp.getCause());
		}
		logger.info("End : Calling transaction history dao in saveTransactionHistory() method.");
	}

	@Override
	public void updateTransactionHistory(
			TransactionHistoryData transactionHistoryData) {

		logger.info("Start : Calling transaction history dao in updateTransactionHistory() method.");
		// get the transaction history dao.
		ITransactionHistoryDAO dao = KLSDataAccessFactory
				.getTransactionHistoryDAO();
		TransactionHistory master = TransactionHistoryHelper
				.getTransactionHistory(transactionHistoryData);
		// update the transaction history to the db.
		try {
			dao.updateTransactionHistory(master);
		} catch (Exception excp) {
			logger.error("Transaction history data cannot be updated as transaction history id does not exist");
			throw new KlsRuntimeException(
					"Transaction history data cannot be updated as transaction history id does not exist",
					excp.getCause());
		}
		logger.info("End : Calling transaction history dao in updateTransactionHistory() method.");
	}

	@Override
	public List<TransactionHistoryData> getAllTransactionHistoryRecords() {

		logger.info("Start : Calling transaction history dao in getAllTransactionHistoryRecords() method.");
		// get the transaction history dao.
		ITransactionHistoryDAO dao = KLSDataAccessFactory
				.getTransactionHistoryDAO();
		List<TransactionHistoryData> transactionHistoryDataList = new ArrayList<TransactionHistoryData>();
		try {
			List<TransactionHistory> transactionHistoryList = dao
					.getAllTransactionHistoryRecords();
			for (TransactionHistory data : transactionHistoryList) {
				transactionHistoryDataList.add(TransactionHistoryHelper
						.getTransactionHistoryData(data));
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the transaction history records");
			throw new KlsRuntimeException(
					"Error in retrieving all the transaction history records",
					excp.getCause());
		}
		logger.info("End : Calling transaction history dao in getAllTransactionHistoryRecords() method.");
		return transactionHistoryDataList;
	}
	@Override
	public List<TransactionHistoryData> getAllTransactionHistoryRecordsById(Long custId, Long locId, String fromDate, String toDate) {
		logger.info("Start : Calling transaction history dao in getAllTransactionHistoryRecords() method.");
		// get the transaction history dao.
		ITransactionHistoryDAO dao = KLSDataAccessFactory
				.getTransactionHistoryDAO();
		List<TransactionHistoryData> transactionHistoryDataList = new ArrayList<TransactionHistoryData>();
		try {
			
			List transactionHistoryList = null; 
					if(locId != null) {
						transactionHistoryList = dao.getTransactionHistoryRecordsById(custId, locId, fromDate, toDate);						
					}
					else {
						transactionHistoryList = dao.getTransactionHistoryRecordsById(custId, fromDate, toDate);
						}
			for (Object data : transactionHistoryList) {
				transactionHistoryDataList.add(TransactionHistoryHelper.getTransactionHistoryData(data));
				}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the transaction history records"+excp.toString());
			throw new KlsRuntimeException(
					"Error in retrieving all the transaction history records",
					excp.getCause());
		}
		logger.info("End : Calling transaction history dao in getAllTransactionHistoryRecords() method.");
		return transactionHistoryDataList;
	}
}
