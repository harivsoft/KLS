/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.account.BorrowingsLineOfCredit;
import com.vsoftcorp.kls.business.entity.account.Charges;
import com.vsoftcorp.kls.business.entity.account.LoanAccountProperty;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.LoanDisbursementSchedule;
import com.vsoftcorp.kls.business.entity.loan.LoanRepaymentSchedule;
import com.vsoftcorp.kls.business.entity.loan.PacsLoanApplication;
import com.vsoftcorp.kls.dataaccess.loan.ILoanAccountPropertyDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * @author a9152
 * 
 */
public class LoanAccountPropertyDAO implements ILoanAccountPropertyDAO {

	private static final Logger logger = Logger.getLogger(LoanAccountPropertyDAO.class);

	public LoanAccountProperty checkIfAccountExists(Long custId, boolean isCloseSession) {

		logger.info("Start: Fetching the Loan Account Property from the database in checkIfAccountExists() method.");
		LoanAccountProperty master = null;
		logger.info(" custId : " + custId);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (custId != null) {
				Query query = em.createQuery("SELECT a FROM LoanAccountProperty a where" + " a.customerId = :custId");
				query.setParameter("custId", custId);
				List<LoanAccountProperty> masterList = (List<LoanAccountProperty>) query.getResultList();
				if (masterList != null && !masterList.isEmpty()) {
					master = masterList.get(0);
				}
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the pacs account property data.");
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching the Loan Account Property from the database in checkIfAccountExists() method.");
		return master;
	}

	@Override
	public void updateLoanAccountProperty(LoanAccountProperty loanAccountProperty) {

		logger.info("Start: Updating the Loan Account Property to the database in updateLoanAccountProperty() method.");
		try {
			LoanAccountProperty master = null;
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long id = loanAccountProperty.getId();
			if (id != null) {
				master = em.find(LoanAccountProperty.class, id);
				em.getTransaction().begin();
				master.setAccountType(loanAccountProperty.getAccountType());
				master.setOperatorType(loanAccountProperty.getOperatorType());
				master.setOperatingInstructionsType(loanAccountProperty.getOperatingInstructionsType());
				master.setResolutionDate(loanAccountProperty.getResolutionDate());
				master.setResolutionNumber(loanAccountProperty.getResolutionNumber());
				master.setRemarks(loanAccountProperty.getRemarks());
				em.getTransaction().commit();
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the Loan Account Property"
					+ " to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the Loan Account Property data to the database.",
					excp.getCause());
		}
		logger.info("End: Updated the Loan Account Property to the database in updateLoanAccountProperty() method.");
	}

	@Override
	public LoanAccountProperty getLoanAccountProperty(Long loanAccountPropertyId) {

		logger.info("Start: Fetching the Loan Account Property from the database in getLoanAccountProperty() method.");
		LoanAccountProperty pacsAcctProperty = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (loanAccountPropertyId != null) {
				pacsAcctProperty = em.find(LoanAccountProperty.class, loanAccountPropertyId);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the account data from the "
					+ "database using Account Property Id.Exception thrown.");
			throw new DataAccessException("Could not fetch the Account data from the database.", excp.getCause());
		}
		logger.info("End: Fetching the Loan Account Property from the database in getLoanAccountProperty() method.");
		return pacsAcctProperty;
	}

	@Override
	public List<LoanAccountProperty> getAllLoanAccountProperties() {

		logger.info("Start: Fetching all the Loan Account Property records from the database in getAllLoanAccountProperties() method.");
		List<LoanAccountProperty> accountList = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			TypedQuery<LoanAccountProperty> query = em.createQuery("SELECT p FROM LoanAccountProperty p",
					LoanAccountProperty.class);
			accountList = query.getResultList();
			logger.info(" list size : " + accountList.size());
		} catch (Exception e) {
			logger.error("Error while retriving all loan account properties from the database");
			throw new DataAccessException("Error while retriving all loan account properties from the database",
					e.getCause());
		}
		logger.info("End: Fetching all the Loan Account Property records from the database in getAllLoanAccountProperties() method.");
		return accountList;
	}

	/**
	 * 
	 */
	public LoanAccountProperty saveLoanAccountProperty(LoanAccountProperty loanAccountProperty) {

		logger.info("Start: Saving the Loan account proprety to the database in saveLoanAccountProperty() method.");
		EntityManager em = EntityManagerUtil.getEntityManager();
		LoanAccountProperty loanAcctProperty = null;
		try {
			em.getTransaction().begin();
			loanAcctProperty = em.merge(loanAccountProperty);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the Loan Account Property"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the Loan Account Property data to the database.",
					excp.getCause());
		}
		logger.info("End: Saved the pacs account proprety to the database in saveLoanAccountProperty() method.");
		return loanAcctProperty;
	}

	@Override
	public LoanLineOfCredit saveLoanAccountProperty(LoanAccountProperty loanAccountProperty, LoanLineOfCredit loanLoc,
			List<Charges> chargesList, List<LoanRepaymentSchedule> scheduleList,
			LoanDisbursementSchedule disbursementSchedule, BorrowingsLineOfCredit bloc) {

		logger.info("Start: Saving the account related tables to the database in saveLoanAccountProperty() method.");
		EntityManager em = EntityManagerUtil.getEntityManager();
		LoanLineOfCredit loanLineOfCredit = null;
		try {
			em.getTransaction().begin();
			em.persist(loanAccountProperty);
			if (bloc != null) {
				em.persist(bloc);
				loanLoc.setBorrowingsLocId(bloc.getId());
			}
			loanLineOfCredit = em.merge(loanLoc);
			for (Charges charges : chargesList) {
				charges.setLineOfCredit(loanLineOfCredit);
				em.persist(charges);
			}
			/*for (LoanRepaymentSchedule schedule : scheduleList) {
				schedule.getLoanRepaymentScheduleId().setLineOfCreditId(loanLineOfCredit.getId());
				em.persist(schedule);
			}*/
			if (disbursementSchedule != null) {
				disbursementSchedule.getLoanDisbursmentCompositeId().setLineOfCreditId(loanLineOfCredit.getId());
				em.persist(disbursementSchedule);
			}
			PacsLoanApplication loanApplication = loanLoc.getPacsLoanApplication();
			em.persist(loanApplication);
			em.getTransaction().commit();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of saving the Loan Account Property"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the Loan Account Property data to the database.",
					excp.getCause());
		}
		logger.info("End: Saved the pacs account proprety to the database in saveLoanAccountProperty() method.");
		return loanLineOfCredit;
	}

	@Override
	public Long getCustomerIdByAccount(Long accountId) {
		Long customerId = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			List<Long> list = (List) em
					.createQuery(
							"select distinct l.customerId from LoanAccountProperty l where l.account.id=:accountId")
					.setParameter("accountId", accountId).getResultList();
			if (!list.isEmpty())
				customerId = list.get(0);
		} catch (Exception excp) {
			logger.error("cannot find loan account property by accountId:" + accountId);
			throw new DataAccessException("cannot find loan account property by accountId.", excp.getCause());
		}
		return customerId;
	}

	@Override
	public LoanAccountProperty getLoanAccountPropertyByAccountId(Long accountId) {
		logger.info("start: getLoanaccountProperty by accountid: " + accountId);
		LoanAccountProperty loanAccountProperty = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			loanAccountProperty = (LoanAccountProperty) em
					.createQuery("select l from LoanAccountProperty l where l.account.id=:accountId")
					.setParameter("accountId", accountId).getSingleResult();
		} catch (Exception excp) {
			logger.error("cannot find loan account property by accountId:" + accountId);
			throw new DataAccessException("cannot find loan account property by accountId.", excp.getCause());
		}
		logger.info("End: getLoanaccountProperty by accountid");
		return loanAccountProperty;
	}

	@Override
	public List<String> getSavingsAccountNumberByCustId(Long custId) {

		logger.info("Start: Fetching the LoanAccountProperty from the database in getSavingsAccountNumberByCustId() method.");
		List<String> savingsAccNumList = new ArrayList<String>();
		logger.info("custId : " + custId);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (custId != null) {
				Query query = em.createQuery("SELECT distinct a.savingsAccountNumber FROM LoanAccountProperty a where"
						+ " a.customerId = :custId");
				query.setParameter("custId", custId);
				savingsAccNumList = (List<String>) query.getResultList();
			}
		} catch (Exception e) {
			logger.error("Could not fetch the LoanAccountProperty from the "
					+ "database using customerId  Exception thrown.");
			throw new DataAccessException("Could not fetch the LoanAccountProperty from the database.", e.getCause());
		}
		logger.info("End: Fetching the LoanAccountProperty from the database in getSavingsAccountNumberByCustId() method.");
		return savingsAccNumList;
	}

	@Override
	public LoanLineOfCredit updateLoanAccountProperty(LoanAccountProperty loanAccountProperty, Charges charge,
			LoanLineOfCredit loanLoc) {

		logger.info("Start: Updating the account related tables to the database in updateLoanAccountProperty() method.");
		EntityManager em = EntityManagerUtil.getEntityManager();
		LoanLineOfCredit loanLineOfCredit = null;
		try {
			em.getTransaction().begin();
			LoanAccountProperty master = null;
			Long id = loanAccountProperty.getId();
			if (id != null) {
				master = em.find(LoanAccountProperty.class, id);
				master.setAccountType(loanAccountProperty.getAccountType());
				master.setOperatorType(loanAccountProperty.getOperatorType());
				master.setOperatingInstructionsType(loanAccountProperty.getOperatingInstructionsType());
				master.setResolutionDate(loanAccountProperty.getResolutionDate());
				master.setResolutionNumber(loanAccountProperty.getResolutionNumber());
				master.setRemarks(loanAccountProperty.getRemarks());
			}
			loanLineOfCredit = em.merge(loanLoc);
			em.merge(charge);
			PacsLoanApplication loanApplication = loanLoc.getPacsLoanApplication();
			em.merge(loanApplication);
			em.getTransaction().commit();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of updating the Loan Account Property"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the Loan Account Property data to the database.",
					excp.getCause());
		}
		logger.info("End: Updating the account related tables to the database in updateLoanAccountProperty() method.");
		return loanLineOfCredit;
	}
}
