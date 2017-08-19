/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.dataaccess.loan.ILoanLineOfCreditDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.LineOfCreditStatus;

/**
 * @author a9152
 * 
 */
public class LoanLineOfCreditDAO implements ILoanLineOfCreditDAO {

	private static final Logger logger = Logger.getLogger(LoanLineOfCreditDAO.class);

	// DAO To Get LineOfCredit Id List By On Customer Id
	@Override
	public List<LoanLineOfCredit> getLoanLocListByCustomerId(Long customerId) {

		logger.info("Start: Fetching the Loan loc list from the database in getLoanLocListByCustomerId() method.");
		List<LoanLineOfCredit> list = new ArrayList<LoanLineOfCredit>();
		logger.info(" customerId : " + customerId);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (customerId != null) {
				Query query = em.createQuery("SELECT l FROM LoanLineOfCredit l where l.customerId = :customerId "
						+ "and l.lineOfCreditStatus = :status");
				query.setParameter("customerId", customerId);
				query.setParameter("status", LineOfCreditStatus.Active);
				list = query.getResultList();
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the loan loc id list from the "
					+ "database using customerId  Exception thrown.");
			throw new DataAccessException("Could not fetch the loan loc list from the database.", excp.getCause());
		}
		logger.info("End: Fetching the Loan loc id list from the database in getLoanLocListByCustomerId() method.");
		return list;
	}

	@Override
	public LoanLineOfCredit getLoanLineOfCreditById(Long loanLocId) {

		logger.info("Start: Fetching the Loan line of credit id from the database in getLoanLineOfCreditById() method-DAO---."
				+ loanLocId);
		LoanLineOfCredit master = null;
		logger.info(" loanLocId : " + loanLocId);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (loanLocId != null) {
				master = em.find(LoanLineOfCredit.class, loanLocId);
				logger.info("PAC ID: " + master.getAccount().getAccountProperty().getPacs());
				logger.info("Barnch ID: " + master.getAccount().getAccountProperty().getBranch().getId());
				logger.info("account number : " + master.getAccount().getAccountNumber());

			}
		} catch (Exception excp) {
			logger.error("Could not fetch the loan loc data from the " + "database using loc id  Exception thrown.");
			throw new DataAccessException("Could not fetch the loan loc data from the database.", excp.getCause());
		}
		logger.info("End: Fetching the Loan line of credit data from the database in getLoanLineOfCreditById() method.");
		return master;
	}

	@Override
	public List<Long> getLoanLocListByApplicationId(Long applicationId) {

		logger.info("Start: Fetching the Loan loc list from the database in getLoanLocListByApplicationId() method.");
		List<Long> locIdsList = new ArrayList<Long>();
		logger.info(" applicationId : " + applicationId);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (applicationId != null) {
				Query query = em
						.createQuery("SELECT l.id FROM LoanLineOfCredit l where l.pacsLoanApplication.id = :applicationId "
								+ "and l.lineOfCreditStatus = :status");
				query.setParameter("applicationId", applicationId);
				query.setParameter("status", LineOfCreditStatus.Active);
				locIdsList = query.getResultList();
				logger.info("line of Credit data list" + locIdsList);

			}
		} catch (Exception excp) {
			logger.error("Could not fetch the loan loc id list from the "
					+ "database using applicationId  Exception thrown.");
			throw new DataAccessException("Could not fetch the loan loc list from the database.", excp.getCause());
		}
		logger.info("End: Fetching the Loan loc id list from the database in getLoanLocListByApplicationId() method.");
		return locIdsList;
	}

	@Override
	public void saveLoanLineOfCredit(LoanLineOfCredit loanLoc) {

		logger.info("Start: Saving the loan line of credit data to the database in saveLoanLineOfCredit() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(loanLoc);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the loan line of credit"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the loan line of credit data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the loan line of credit to the database in saveLoanLineOfCredit() method.");
	}

	@Override
	public LoanLineOfCredit mergeLoanLineOfCredit(LoanLineOfCredit loanLoc) {

		logger.info("Start: Merging the loan line of credit data to the database in saveLoanLineOfCredit() method.");
		LoanLineOfCredit returnLoanLoc = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			returnLoanLoc = em.merge(loanLoc);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the loan line of credit"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the loan line of credit data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully merged the loan line of credit to the database in saveLoanLineOfCredit() method.");
		return returnLoanLoc;
	}

	@Override
	public List<LoanLineOfCredit> getDisbursedLocList(Long customerId) {

		logger.info("Start: Fetching the Loan loc list from the database in getLoanLocListByCustomerId() method.");
		List<LoanLineOfCredit> list = new ArrayList<LoanLineOfCredit>();
		logger.info(" customerId : " + customerId);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (customerId != null) {
				Query query = em.createQuery("SELECT l FROM LoanLineOfCredit l where l.currentBalance != 0 and "
						+ "l.customerId = :customerId and l.lineOfCreditStatus = :status");
				query.setParameter("customerId", customerId);
				query.setParameter("status", LineOfCreditStatus.Active);
				list = query.getResultList();
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the loan loc id list from the "
					+ "database using customerId  Exception thrown.");
			throw new DataAccessException("Could not fetch the loan loc list from the database.", excp.getCause());
		}
		logger.info("End: Fetching the Loan loc id list from the database in getLoanLocListByCustomerId() method.");
		return list;
	}

	@Override
	public void closeLoanLineOfCredit(LoanLineOfCredit loanLoc, List<CurrentTransaction> currentTransactionList) {

		logger.info("Start: Closing the loan line of credit data to the database in closeLoanLineOfCredit() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
		//	em.getTransaction().begin();
			em.merge(loanLoc);
			for (CurrentTransaction currTransaction : currentTransactionList) {
				em.persist(currTransaction);
			}
		//	em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of closing the loan line of credit"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of closing the loan line of credit data to the database.",
					excp.getCause());
		}
		logger.info("End: Successfully closed the loan line of credit to the database in closeLoanLineOfCredit() method.");
	}

	public LoanLineOfCredit getLoanLineOfCreditByPropertyId(Long loanAcctPropertyId) {

		logger.info("Start: Fetching the Loan loc from the database in getLoanLineOfCreditByPropertyId() method.");
		List<LoanLineOfCredit> loanLocList = null;
		LoanLineOfCredit loanLoc = null;
		logger.info(" loanAcctPropertyId : " + loanAcctPropertyId);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (loanAcctPropertyId != null) {
				Query query = em
						.createQuery("SELECT l FROM LoanLineOfCredit l where l.loanAccountProperty.id = :loanAcctPropertyId "
								+ "and l.lineOfCreditStatus = :status");
				query.setParameter("loanAcctPropertyId", loanAcctPropertyId);
				query.setParameter("status", LineOfCreditStatus.Active);
				loanLocList = (List<LoanLineOfCredit>) query.getResultList();
				if (loanLocList != null && !loanLocList.isEmpty()) {
					loanLoc = loanLocList.get(0);
				}

			}
		} catch (Exception excp) {
			logger.error("Could not fetch the loan loc from the "
					+ "database using loan account property id. Exception thrown.");
			throw new DataAccessException("Could not fetch the loan loc list from the database.", excp.getCause());
		}
		logger.info("End: Fetching the Loan loc from the database in getLoanLineOfCreditByPropertyId() method.");
		return loanLoc;
	}
}
