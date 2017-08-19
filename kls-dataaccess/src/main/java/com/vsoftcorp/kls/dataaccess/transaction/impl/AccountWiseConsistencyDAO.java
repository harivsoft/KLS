package com.vsoftcorp.kls.dataaccess.transaction.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.transaction.AccountWiseConsistency;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.entity.transaction.TransactionHistory;
import com.vsoftcorp.kls.dataaccess.transaction.IAccountWiseConsistencyDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * 
 * @author a1565
 * 
 */
public class AccountWiseConsistencyDAO implements IAccountWiseConsistencyDAO {

	private static final Logger logger = Logger
			.getLogger(AccountWiseConsistencyDAO.class);

	public void saveInconsistency(AccountWiseConsistency accountWiseConsistency) {
		
		logger.info("Start: Saving the Inconsistence data to the database in saveInconsistency() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(accountWiseConsistency);
			em.getTransaction().commit();
			logger.info("successfully commited");
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error while saving  Inconsistency data.");
			throw new DataAccessException(
					"Error while saving  Inconsistency data.", excp.getCause());
		}
		logger.info("End: Successfully saved the Inconsistency to the database in saveInconsistency() method.");
	}
	
	@Override
	public List<Map> getAllLineOfCreditsAmount() {
		// TODO Auto-generated method stub
		logger.info("Start: Fetching all the LineOfCredit data from the database in getAllLineOfCreditsAmount() method.");
		List<Map> lOCBalance = new ArrayList<Map>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			lOCBalance = em
					.createQuery(
							"SELECT DISTINCT new Map(l.account.id as accountId,l.currentBalance as lBalance,l.id as lineOfCreditId) FROM LineOfCredit l where l.lineOfCreditStatus = 1 GROUP BY l.account.id,l.id")
					.getResultList();

		} catch (Exception e) {
			logger.error("Error while retriving all line of credit amount records from the database.");
			throw new DataAccessException(
					"Error while retriving all line of credit amount  records.",
					e.getCause());
		}
		return lOCBalance;
	}
	
	@Override
	public List<Object[]> getAllTotalTransactionsAmount() {
		// TODO Auto-generated method stub
		logger.info("Start: Fetching all the transaction data from the database in getAllTotalTransactionsAmount() method.");
		List<Object[]> transactionBalance = new ArrayList<Object[]>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.createNativeQuery("CREATE OR REPLACE VIEW temp_transaction AS ( SELECT c.account_id,c.line_of_credit_id,sum(c.crDr*c.trans_amt) from kls_schema.current_transaction c,kls_schema.line_of_credit l where l.id= c.line_of_credit_id and l.status = 1 GROUP BY c.line_of_credit_id,c.account_id union SELECT t.account_id,t.line_of_credit_id,sum(t.crDr*t.trans_amt) from kls_schema.transaction_history t,kls_schema.line_of_credit l where l.id= t.line_of_credit_id and l.status = 1 GROUP BY t.line_of_credit_id,t.account_id )").executeUpdate();;
			em.getTransaction().commit();
			transactionBalance = 
					em.createNativeQuery("select account_id as accountId,line_of_credit_id as lineOfCreditId, sum(sum) as transactionBalance  from temp_transaction group by line_of_credit_id,account_id").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving all  transaction amount records from the database.");
			throw new DataAccessException(
					"Error while retriving all  transaction amount records.",
					e.getCause());
		}
		logger.info("End: Fetching all the  transaction data from the database in getAllCurrentTransactions() method.---");
		logger.info(transactionBalance);
		logger.info("........."+transactionBalance.toString());
		return transactionBalance;
	}
	
	@Override
	public List<CurrentTransaction> getCurrentTransactionBasedOnLid(Long masterLid) {
		List<CurrentTransaction> list = new ArrayList<CurrentTransaction>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long lId = masterLid;
			if (lId != null) {
				Query query = em
						.createQuery("SELECT c FROM CurrentTransaction c where c.lineOfCredit.id=:lineOfCreditId and c.lineOfCredit.lineOfCreditStatus = 1");
				query.setParameter("lineOfCreditId", lId);

				list = query.getResultList();
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the CurrentTransaction data from the "
					+ "database using loc id.Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the CurrentTransaction data from the database.",
					excp.getCause());
		} /*finally {
			EntityManagerUtil.closeSession();
		}*/
		logger.info("End: Successfully fetched the CurrentTransaction data from the database in getTransactionBasedOnLid() method.");

		return list;
	}
	
	@Override
	public List<TransactionHistory> getTransactionBasedOnLid(Long masterLid) {
		List<TransactionHistory> list = new ArrayList<TransactionHistory>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long lId = masterLid;
			if (lId != null) {
				Query query = em
						.createQuery("SELECT c FROM TransactionHistory c where c.lineOfCredit.id=:lineOfCreditId and c.lineOfCredit.lineOfCreditStatus = 1");
				query.setParameter("lineOfCreditId", lId);
				list = query.getResultList();
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the TransactionHistory data from the "
					+ "database using loc id.Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the TransactionHistory data from the database.",
					excp.getCause());
		} /*finally {
			EntityManagerUtil.closeSession();
		}*/
		logger.info("End: Successfully fetched the TransactionHistory data from the database in getTransactionBasedOnLid() method.");

		return list;
	}
	
	@Override
	public List<AccountWiseConsistency> getAllInConsistency() {
		logger.info("Start: Fetching all the InConsistency data from the database in getAllInConsistency() method.");
		List<AccountWiseConsistency> inconsistencyList = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();

			Query query = em.createQuery("SELECT i FROM AccountWiseConsistency i where i.consistencyStatus=:consistencyStatus");
			query.setParameter("consistencyStatus", "N");
			inconsistencyList = query.getResultList();

			logger.info("getting the list after...." + inconsistencyList.size());
		} catch (Exception e) {
			logger.error("Error while retriving all InConsistency from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all InConsistency from the database", e.getCause());
		}
		/*finally{
			EntityManagerUtil.closeSession();
		}*/
		logger.info("End: Fetching all the InConsistency data from the database in getAllInConsistency() method.");
		return inconsistencyList;
	}

	
	@Override
	public void updateInconsistencyAsConsistency(AccountWiseConsistency acMaster) {
		// TODO Auto-generated method stub
		logger.info("Start: Updating the Inconsistency as consistent master data to the database in updateCrop() method.");
		AccountWiseConsistency master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long inconsistencyId = acMaster.getId();
			if (inconsistencyId != null) {
				master = em.find(AccountWiseConsistency.class, inconsistencyId);
				if (master != null) {
					em.getTransaction().begin();
					master.setId(acMaster.getId());
					master.setConsistencyStatus("Y");
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the Inconsistency as consistent  "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the Inconsistency as consistent data to the database.",
					excp.getCause());
		}
		/*finally{
			EntityManagerUtil.closeSession();
		}*/
		if (master == null) {
			logger.error("Trying to update the Inconsistency as consistent record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the Inconsistency as consistent record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the Inconsistency as consistent  to the database in updateInconsistencyAsConsistency() method.");
	}
		
	
	
	
	/*
	@Override
	public List<Map> getAllCurrentTransactionsAmount() {
		// TODO Auto-generated method stub
		logger.info("Start: Fetching all the current transaction data from the database in getAllCurrentTransactions() method.");
		List<Map> transactionBalance = new ArrayList<Map>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			transactionBalance = em
					.createQuery(
							"SELECT DISTINCT new Map(c.account.id as accountId,sum(c.openingBalance +(c.crDr*c.transactionAmount)) as transactionBalance,c.lineOfCredit.id as lineOfCreditId)"
									+ " FROM CurrentTransaction c GROUP BY c.account.id,c.lineOfCredit.id")
					.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all current transaction amount records from the database.");
			throw new DataAccessException(
					"Error while retriving all current transaction amount records.",
					e.getCause());
		}
		logger.info("End: Fetching all the current transaction data from the database in getAllCurrentTransactions() method.---"+transactionBalance);
		return transactionBalance;
	}
	@Override
	public List<Map> getAllTransactionHistoryAmount() {
		// TODO Auto-generated method stub
		logger.info("Start: Fetching all the current transaction data from the database in getAllCurrentTransactions() method.");
		List<Map> transactionHistoryBalance = new ArrayList<Map>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			transactionHistoryBalance = em
					.createQuery(
							"SELECT DISTINCT new Map(c.account.id as accountId,sum(c.openingBalance+(c.crDr*c.transactionAmount)) as transactionBalance,c.lineOfCredit.id as lineOfCreditId)"
									+ " FROM TransactionHistory c GROUP BY c.account.id,c.lineOfCredit.id")
					.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all current transaction amount records from the database.");
			throw new DataAccessException(
					"Error while retriving all current transaction amount records.",
					e.getCause());
		}
		logger.info("End: Fetching all the current transaction data from the database in getAllCurrentTransactions() method.---"+transactionHistoryBalance);
		return transactionHistoryBalance;
	}
	

	@Override
	public AccountWiseConsistency getInConsistency(
			AccountWiseConsistency inConsistency) {

		logger.info("Start: Fetching the AccountWiseConsistency master data from the database in getInConsistency() method.");
		AccountWiseConsistency master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long inConsistencyId = inConsistency.getId();
			if (inConsistencyId != null) {
				master = em.find(AccountWiseConsistency.class, inConsistencyId);
			}

		} catch (Exception excp) {
			logger.error("Could not fetch the AccountWiseConsistency master data from the "
					+ "database using AccountWiseConsistency Id.Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the AccountWiseConsistency master data from the database.",
					excp.getCause());
		}
		logger.info("End: Successfully fetched the AccountWiseConsistency master data from the database in getInConsistency() method.");
		return master;
	}
	
	@Override
	public void deleteDateWiseInconsistency(AccountWiseConsistency dateMaster) {

		logger.info("Start: Checking the InConsistency  data based on bussiness Date from the database in deleteDateWiseInconsistency() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Date businessDate = dateMaster.getBusinessDate();
			logger.info("In deleteDateWiseInconsistency() Business Date is .."+businessDate);
			if (businessDate != null) {
				Query query = em
						.createQuery("delete from AccountWiseConsistency a where a.businessDate=:businessDate");
				query.setParameter("businessDate", businessDate);
				em.getTransaction().begin();
				query.executeUpdate();
				em.getTransaction().commit();
				logger.info("Duplicate Records are deleted Succesfully By comparing Current BusinessDate");
			}

		} catch (Exception excp) {
			logger.error("Could not fetch the AccountWiseConsistency master data from the "
					+ "database using AccountWiseConsistency bussinessDate.Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the AccountWiseConsistency master data from the database.",
					excp.getCause());
		}
		logger.info("End: Successfully fetched & deleted duplicate records of AccountWiseConsistency master  from the database in deleteDateWiseInconsistency() method.");
	}


	@Override
	public AccountWiseConsistency getAccountWiseInconsitencyRecords(AccountWiseConsistency inConsistency) {
		// TODO Auto-generated method stub
			logger.info("Start: Fetching the AccountWiseConsistency master data from the database in getInConsistency() method.");
			AccountWiseConsistency master = null;
			try {
				EntityManager em = EntityManagerUtil.getEntityManager();
				Long locId = inConsistency.getLineOfCredit().getId();
				if (locId != null) {
					master = em.find(AccountWiseConsistency.class, locId);
				}

			} catch (Exception excp) {
				logger.error("Could not fetch the AccountWiseConsistency master data from the "
						+ "database using AccountWiseConsistency Id.Exception thrown.");
				throw new DataAccessException(
						"Could not fetch the AccountWiseConsistency master data from the database.",
						excp.getCause());
			}
			logger.info("End: Successfully fetched the AccountWiseConsistency master data from the database in getInConsistency() method.");
			return master;
	}
	
*/
}
