/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.loan.LoanRepaymentSchedule;
import com.vsoftcorp.kls.dataaccess.loan.ILoanRepaymentScheduleDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;

/**
 * @author a9152
 * 
 */
public class LoanRepaymentScheduleDAO implements ILoanRepaymentScheduleDAO {

	private static final Logger logger = Logger.getLogger(LoanRepaymentScheduleDAO.class);

	/**
	 * 
	 */
	public void updateLoanRepaymentScheduleList(List<LoanRepaymentSchedule> loanRepaymentScheduleList) {

		logger.info("Start: Updating the Loan Repayment Schedule to the database in updateLoanRepaymentScheduleList() method.");
		try {
			if (!loanRepaymentScheduleList.isEmpty()) {
				EntityManager em = EntityManagerUtil.getEntityManager();
//				em.getTransaction().begin();
				for (LoanRepaymentSchedule loanRepaymentSchedule : loanRepaymentScheduleList) {
					em.merge(loanRepaymentSchedule);
				}
//				em.getTransaction().commit();
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the Loan Repayment Schedule"
					+ " to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the Loan Repayment Schedule data to the database.",
					excp.getCause());
		}
		logger.info("End: Updated the Loan Repayment Schedule to the database in updateLoanRepaymentScheduleList() method.");
	}

	/**
	 * 
	 */
	public List<LoanRepaymentSchedule> getLoanRepaymentScheduleByLocId(Long loanLocId) {

		logger.info("Start: Fetching the list of loan repayment schedule list by line of credit id in getLoanRepaymentScheduleByLocId() method.");
		List<LoanRepaymentSchedule> repaymentScheduleList = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info(" loanLocId : " + loanLocId);
			if (loanLocId != null) {
				Query query = em
						.createQuery("SELECT l FROM LoanRepaymentSchedule l where "
								+ "l.loanRepaymentScheduleId.lineOfCreditId = :loanLocId  order by l.loanRepaymentScheduleId.installmentNumber");
				query.setParameter("loanLocId", loanLocId);
				repaymentScheduleList = query.getResultList();
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the loan repayment schedule list from the "
					+ "database using line of credit id.Exception thrown.");

			throw new DataAccessException("Could not fetch the loan repayment schedule list from the database.",
					excp.getCause());
		}
		logger.info("End: Fetching the list of loan repayment schedule list by line of credit id in getLoanRepaymentScheduleByLocId() method.");
		return repaymentScheduleList;
	}

	/**
	 * 
	 */
	public List<LoanRepaymentSchedule> getAllLoanRepaymentSchedules() {

		logger.info("Start: Fetching all the Loan Repayment Schedule records from the database in getAllLoanRepaymentSchedules() method.");
		List<LoanRepaymentSchedule> repaymentScheduleList = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			TypedQuery<LoanRepaymentSchedule> query = em.createQuery("SELECT p FROM LoanRepaymentSchedule p",
					LoanRepaymentSchedule.class);
			repaymentScheduleList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all loan repayment schedule records from the database");
			throw new DataAccessException(
					"Error while retriving all loan repayment schedule records from the database", e.getCause());
		}
		logger.info("End: Fetching all the Loan Repayment Schedule records from the database in getAllLoanRepaymentSchedules() method.");
		return repaymentScheduleList;
	}

	/**
	 * 
	 */
	public void saveLoanRepaymentScheduleList(List<LoanRepaymentSchedule> loanRepaymentScheduleList) {

		logger.info("Start: Saving the loan repayment schedule list to the database in saveLoanRepaymentScheduleList() method.");
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
			if (!loanRepaymentScheduleList.isEmpty()) {
//				em.getTransaction().begin();
				for (LoanRepaymentSchedule loanRepaymentSchedule : loanRepaymentScheduleList) {
					em.persist(loanRepaymentSchedule);
				}
//				em.getTransaction().commit();
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of saving the loan repayment schedule list"
					+ "to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the loan repayment schedule list to the database.",
					excp.getCause());
		}
		logger.info("End: Saving the Loan repayment schedule list to the database in saveLoanRepaymentScheduleList() method.");
	}

	@Override
	public List<LoanRepaymentSchedule> getLoanRepaymentScheduleByLocIdAndBusinessDate(Long locId, Date businessDate) {
		logger.info("locId : " + locId);
		logger.info("businessDate : " + businessDate);
		logger.info("Start: Fetching the list of loan repayment schedule list by line of credit id in getLoanRepaymentScheduleByLocIdAndBusinessDate() method.");
		List<LoanRepaymentSchedule> repaymentScheduleList = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info(" loanLocId : " + locId);
			if (locId != null) {
				Query query = em
						.createQuery("SELECT l FROM LoanRepaymentSchedule l where "
								+ "l.loanRepaymentScheduleId.lineOfCreditId = :loanLocId and l.installmentDate <= :businessDate order by l.loanRepaymentScheduleId.installmentNumber");
				query.setParameter("loanLocId", locId);
				query.setParameter("businessDate", businessDate);
				repaymentScheduleList = query.getResultList();
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the loan repayment schedule list from the "
					+ "database using line of credit id.Exception thrown.");

			throw new DataAccessException("Could not fetch the loan repayment schedule list from the database.",
					excp.getCause());
		}
		logger.info("End: Fetching the list of loan repayment schedule list by line of credit id in getLoanRepaymentScheduleByLocIdAndBusinessDate() method.");
		return repaymentScheduleList;
	}

	/**
	 * 
	 */
	public void deleteLoanRepaymentScheduleByLocId(Long loanLocId) {

		logger.info("Start: Deleting the list of loan repayment schedule by line of credit id in deleteLoanRepaymentScheduleByLocId() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info(" loanLocId : " + loanLocId);
			em.getTransaction().begin();
			if (loanLocId != null) {
				Query query = em
						.createQuery("DELETE FROM LoanRepaymentSchedule t where t.loanRepaymentScheduleId.lineOfCreditId = :loanLocId");
				query.setParameter("loanLocId", loanLocId);
				int deletedCount = query.executeUpdate();
				logger.info(" deletedCount : " + deletedCount);
				em.getTransaction().commit();
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not delete the loan repayment schedule list from the "
					+ "database using line of credit id.Exception thrown.");
			throw new DataAccessException("Could not delete the loan repayment schedule list from the database.",
					excp.getCause());
		}
		logger.info("End: Deleting the list of loan repayment schedule by line of credit id in deleteLoanRepaymentScheduleByLocId() method.");
	}
	
	@Override
	public LoanRepaymentSchedule getLoanRepaymentSchedule(Long locId, Date businessDate) {
		logger.info("Start: Fetching the list of loan repayment schedule list by line of credit id in getLoanRepaymentScheduleByLocIdAndBusinessDate() method.");
		LoanRepaymentSchedule repaymentSchedule = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info(" loanLocId : " + locId);
			if (locId != null) {
				Query query = em.createQuery("SELECT l FROM LoanRepaymentSchedule l where l.loanRepaymentScheduleId.lineOfCreditId = "+locId+" and l.installmentDate = (select min(ll.installmentDate) FROM LoanRepaymentSchedule ll where ll.loanRepaymentScheduleId.lineOfCreditId = "+locId+" and ll.installmentDate  >= '"+businessDate+"')");
				repaymentSchedule = (LoanRepaymentSchedule) query.getSingleResult();
			}
		} catch (NoResultException ne) {
			logger.error("Could not fetch the loan repayment schedule from the "
					+ "database using line of credit id.Exception thrown.");
			repaymentSchedule = null;

		}catch (Exception excp) {
			logger.error("Could not fetch the loan repayment schedule from the "
					+ "database using line of credit id.Exception thrown.");

			throw new DataAccessException("Could not fetch the loan repayment schedule from the database.",
					excp.getCause());
		}
		logger.info("End: Fetching the list of loan repayment schedule list by line of credit id in getLoanRepaymentScheduleByLocIdAndBusinessDate() method.");

		return repaymentSchedule;
	}
	
	@Override
	public List<LoanRepaymentSchedule> getLoanRepaymentScheduleByLocIdAndBusinessDateForDisburshment(Long locId, Date businessDate) {
		logger.info("locId : " + locId);
		logger.info("businessDate : " + businessDate);
		logger.info("Start: Fetching the list of loan repayment schedule list by line of credit id in getLoanRepaymentScheduleByLocIdAndBusinessDateForDisburshment() method.");
		List<LoanRepaymentSchedule> repaymentScheduleList = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info(" loanLocId : " + locId);
			if (locId != null) {
				Query query = em
						.createQuery("SELECT l FROM LoanRepaymentSchedule l where "
								+ "l.loanRepaymentScheduleId.lineOfCreditId = :loanLocId and l.installmentDate > :businessDate order by l.loanRepaymentScheduleId.installmentNumber");
				query.setParameter("loanLocId", locId);
				query.setParameter("businessDate", businessDate);
				repaymentScheduleList = query.getResultList();
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the loan repayment schedule list from the "
					+ "database using line of credit id.Exception thrown.");

			throw new DataAccessException("Could not fetch the loan repayment schedule list from the database.",
					excp.getCause());
		}
		logger.info("End: Fetching the list of loan repayment schedule list by line of credit id in getLoanRepaymentScheduleByLocIdAndBusinessDateForDisburshment() method.");
		return repaymentScheduleList;
	}
	
	@Override
	public List<LoanRepaymentSchedule> getNextInstallmentAmount(Long locId, Date businessDate) {
		logger.info("locId : " + locId);
		logger.info("businessDate : " + businessDate);
		logger.info("Start: Fetching the list of loan repayment schedule list by line of credit id in getNextInstallmentAmount() method.");
		List<LoanRepaymentSchedule> repaymentScheduleList = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info(" loanLocId : " + locId);
			if (locId != null) {
				Query query = em
						.createQuery("SELECT l FROM LoanRepaymentSchedule l where "
								+ "l.loanRepaymentScheduleId.lineOfCreditId = :loanLocId and l.installmentDate >= :businessDate order by l.loanRepaymentScheduleId.installmentNumber");
				query.setParameter("loanLocId", locId);
				query.setParameter("businessDate", businessDate);
				repaymentScheduleList = query.getResultList();
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the loan repayment schedule list from the "
					+ "database using line of credit id.Exception thrown.");

			throw new DataAccessException("Could not fetch the loan repayment schedule list from the database.",
					excp.getCause());
		}
		logger.info("End: Fetching the list of loan repayment schedule list by line of credit id in getNextInstallmentAmount() method.");
		return repaymentScheduleList;
	}
	
}
