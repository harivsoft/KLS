/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.transaction.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.transaction.TransactionHistory;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.dataaccess.transaction.ITransactionHistoryDAO;
import com.vsoftcorp.kls.dataaccess.transaction.result.classes.TransactionRecordByProductAndCrdr;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.time.Date;

/**
 * @author a9152
 * 
 */
public class TransactionHistoryDAO implements ITransactionHistoryDAO {

	private static final Logger logger = Logger.getLogger(TransactionHistoryDAO.class);

	/**
	 * 
	 * Saves the transaction history data to the database.
	 * 
	 * @param transactionHistory
	 */
	public void saveTransactionHistory(TransactionHistory transactionHistory) {

		logger.info("Start:Saving the transaction history data to the database in saveTransactionHistory() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			//em.getTransaction().begin();
			em.persist(transactionHistory);
			//em.getTransaction().commit();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of saving the transaction history data.");
			throw new DataAccessException("Could not commit the transaction of saving the transaction history data.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the transaction history data to the database in saveTransactionHistory() method.");
	}

	/**
	 * 
	 * Saves the transaction history data to the database.
	 * 
	 * @param transactionHistory
	 *            ,isCloseSession
	 */
	public void saveTransactionHistory(TransactionHistory transactionHistory, Boolean isCloseSession) {

		logger.info("Start:Saving the transaction history data to the database in saveTransactionHistory() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			//em.getTransaction().begin();
			em.persist(transactionHistory);
			//em.getTransaction().commit();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of saving the transaction history data.");
			throw new DataAccessException("Could not commit the transaction of saving the transaction history data.",
					excp.getCause());
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the transaction history data to the database in saveTransactionHistory() method.");
	}

	/**
	 * Updates the existing transaction history data to the database.
	 * 
	 * @param transactionHistory
	 */
	public void updateTransactionHistory(TransactionHistory transactionHistory) {

		logger.info("Start: Updating the transaction history data to the database in updateTransactionHistory() method.");
		TransactionHistory master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long id = transactionHistory.getId();
			if (id != null) {
				master = em.find(TransactionHistory.class, id);
				if (master != null) {
					em.getTransaction().begin();
					master.setAccount(transactionHistory.getAccount());
					master.setAccountNumber(transactionHistory.getAccountNumber());
					master.setBusinessDate(transactionHistory.getBusinessDate());
					master.setChannelType(transactionHistory.getChannelType());
					master.setCrDr(transactionHistory.getCrDr());
					master.setLineOfCredit(transactionHistory.getLineOfCredit());
					master.setOpeningBalance(transactionHistory.getOpeningBalance());
					master.setPacs(transactionHistory.getPacs());
					master.setTransactionAmount(transactionHistory.getTransactionAmount());
					master.setTransactionCode(TransactionCode.PRINCIPAL_BAL);
					master.setTransactionType(transactionHistory.getTransactionType());
					master.setVoucherNumber(transactionHistory.getVoucherNumber());
					master.setTerminalId(transactionHistory.getAccountNumber());
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the transaction history data.");
			throw new DataAccessException("Could not commit the transaction of updating the transaction history data.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the transaction history record which does not exist.");
			throw new DataAccessException("Trying to update the transaction history record which does not exist.");
		}
		logger.info("End: Successfully updated the transaction history data to the database in updateTransactionHistory() method.");

	}

	/**
	 * Checks if the transaction history id exists in the database.
	 * 
	 * @param transactionHistory
	 * @return TransactionHistory
	 */
	public TransactionHistory getTransactionHistory(TransactionHistory transactionHistory) {

		logger.info("Start: Fetching the transaction history data from the database in getTransactionHistory() method.");
		TransactionHistory master = new TransactionHistory();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long id = transactionHistory.getId();
			if (id != null) {
				master = em.find(TransactionHistory.class, id);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the transaction history data.");
			throw new DataAccessException("Could not fetch the transaction history data.", excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the transaction history data from the database in getTransactionHistory() method.");
		return master;
	}

	@Override
	public TransactionHistory getTransactionHistoryById(Long transactionHistoryId) {

		logger.info("Start: Fetching the transaction history data from the database in getTransactionHistoryById() method.");
		TransactionHistory master = new TransactionHistory();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (transactionHistoryId != null) {
				master = em.find(TransactionHistory.class, transactionHistoryId);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the transaction history data.");
			throw new DataAccessException("Could not fetch the transaction history data.", excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the transaction history data from the database in getTransactionHistoryById() method.");
		return master;
	}

	/**
	 * Returns all the transaction history records to the client.
	 * 
	 * @return
	 */
	public List<TransactionHistory> getAllTransactionHistoryRecords() {

		logger.info("Start: Fetching all the transaction data from the database in getAllTransactionHistoryRecords() method.");
		List<TransactionHistory> transactionHistoryList = new ArrayList<TransactionHistory>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			transactionHistoryList = em.createQuery("SELECT t FROM TransactionHistory t").getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all transaction history records from the database.");
			throw new DataAccessException("Error while retriving all transaction history records.", e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the transaction history data from the database in getAllTransactionHistoryRecords() method.");
		return transactionHistoryList;
	}

	@Override
	public List<TransactionHistory> getTransactionHistoryRecordsByDate(String date) {

		logger.info("Start: Fetching the transaction history data from the database in getTransactionHistoryRecordsByDate() method.");
		List<TransactionHistory> transactionHistoryList = new ArrayList<TransactionHistory>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT t FROM TransactionHistory t where to_char(t.businessDate,'YYYY-MM-DD') = :date");
			query.setParameter("date", date);
			transactionHistoryList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving transaction history records from the database for the effective date.");
			throw new DataAccessException("Error while retriving transaction history records for the effective date.",
					e.getCause());
		}
		logger.info("End: Fetching the transaction history data from the database in getTransactionHistoryRecordsByDate() method.");
		return transactionHistoryList;
	}

	/**
	 * 
	 */
	public List<TransactionRecordByProductAndCrdr> getTransactionHistoryRecordsByProductAndCrdr() {

		logger.info("Start: Fetching the transaction history data from the database in getTransactionHistoryRecordsByProductAndCrdr() method.");
		List<TransactionRecordByProductAndCrdr> transactionHistoryList = new ArrayList<TransactionRecordByProductAndCrdr>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String queryStr = "select NEW com.vsoftcorp.kls.dataaccess.transaction.result.classes."
					+ "TransactionRecordByProductAndCrdr(sum(th.transactionAmount),s.product.id,th.crDr,loc.id,loc.account.id)"
					+ " from TransactionHistory th,LineOfCredit loc ,"
					+ "Product p ,Scheme s where th.lineOfCredit.id"
					+ " = loc.id and loc.scheme.id = s.id and s.product.id = p.id "
					+ "group by s.product.id,th.crDr,loc.id,loc.account.id";
			TypedQuery<TransactionRecordByProductAndCrdr> query = em.createQuery(queryStr,
					TransactionRecordByProductAndCrdr.class);
			transactionHistoryList = query.getResultList();
			logger.info("results size : " + transactionHistoryList.size());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving transaction history records from the database by product and crdr.");
			throw new DataAccessException("Error while retriving transaction history records by product and crdr.",
					e.getCause());
		}
		logger.info("End: Fetching the transaction history data from the database in getTransactionHistoryRecordsByProductAndCrdr() method.");
		return transactionHistoryList;
	}
	

	@Override
	public List getTransactionHistoryRecordsById(Long custId, String fromDate, String toDate) {
		
		logger.info("Start: Getting traction history based on id: ");
		EntityManager em = EntityManagerUtil.getEntityManager();
		List transactionHistoryList = null;
		try {
			
			String query = "select  business_date as transactionDate,channel_type as channelType,crdr as crdr,String(null) as TransType, String(null) as TransCode, sum(trans_amt) as transactionAmount"
			+" from (select * from kls_schema.transaction_history union select * from kls_schema.current_transaction) trans, kls_schema.Line_Of_Credit locId "
					+ "where trans.line_of_credit_id = locId.id and locId.status = 1 and locId.loan_type = 'C'"
			+" and locId.customer_Id = "+custId +" and trans.business_Date between '"+fromDate+ "' and '"
					+toDate+"' and trans.tran_Code in (1,2,3,4,5,6,7,9)  " +
			" group by trans.business_date, trans.channel_type, trans.crdr order by business_date desc" ;
			logger.info("query from current transaction"+query);
/**
			String query1 = "select t from TransactionHistory t, LineOfCredit loc "
					+ "where t.lineOfCredit.id = loc.id and loc.lineOfCreditStatus = 1 and loc.loanType = 'C' and loc.customerId = "+custId +" and t.businessDate between '"+DateUtil.getVSoftDateByString(fromDate)+ "' and '"
					+DateUtil.getVSoftDateByString(toDate)+"'";
	**/		
			Query tQuery = em.createNativeQuery(query);
			
			transactionHistoryList = tQuery.getResultList();
			
			System.out.println("Transaction History Size: "+transactionHistoryList);
			
		} catch (Exception e) {
			logger.error("Error : while getting traction history based on custumer id"+e.toString());
			throw new DataAccessException("Count not get securityList data.", e.getCause());
		}
		
		logger.info("End: Successfully got Traction History based on id ");
		return transactionHistoryList;
	}

	@Override
	public List getTransactionHistoryRecordsById(Long custId,Long locId, String fromDate, String toDate) {
	
		logger.info("Start: Getting traction history based on id: "+locId);
		EntityManager em = EntityManagerUtil.getEntityManager();
		List transactionHistoryList = null;
		try {
			
			String query = "select  business_date as transactionDate,channel_type as channelType,crdr as crdr,trans_type as transType,tran_code as transCode,trans_amt as transactionAmount"
			+" from (select * from kls_schema.transaction_history union select * from kls_schema.current_transaction) trans, kls_schema.Line_Of_Credit locId "
					+ "where trans.line_of_credit_id = locId.id and locId.status = 1 and locId.loan_type = 'L'"
			+" and locId.id = "+locId+" and locId.customer_Id = "+custId +" and trans.business_Date between '"+fromDate+ "' and '"
					+toDate+"' and trans.tran_Code<>13 and trans.tran_Code<>16  order by business_date desc,trans.id desc ";
			logger.info("query from current transaction"+query);
			//TypedQuery<TransactionHistory> tQuery = em.createQuery(query, TransactionHistory.class);
			Query qry = em.createNativeQuery(query);
			transactionHistoryList = qry.getResultList();
			
			System.out.println("Transaction History Size: "+transactionHistoryList);
			
		} catch (Exception e) {
			logger.error("Error : while getting traction history based on custumer id");
			throw new DataAccessException("Count not get tractionHistory data.", e.getCause());
		}
		
		logger.info("End: Successfully got Traction History based on id ");
		return transactionHistoryList;
	}
@Override
public List<TransactionHistory> getMiniStatement(String accountNum,Date transactionDate,Integer limit){
	logger.info("Start: Getting traction history :");
	List<TransactionHistory> transactionHistoryList=new ArrayList<TransactionHistory>();
	try{
	EntityManager em=EntityManagerUtil.getEntityManager();
	String qryStr="select th from TransactionHistory th where th.accountNumber=:accountNum order by th.businessDate desc";
	Query qry=em.createQuery(qryStr);
	qry.setParameter("accountNum", accountNum);
	transactionHistoryList=qry.setMaxResults(limit).getResultList();
	}catch(NoResultException nre){
		logger.info("No Records Found for account"+accountNum);
		return null;
	}catch (Exception e) {
		e.printStackTrace();
	}
	logger.info("End: Successfully got Traction History ");
	return transactionHistoryList;
}
	}


