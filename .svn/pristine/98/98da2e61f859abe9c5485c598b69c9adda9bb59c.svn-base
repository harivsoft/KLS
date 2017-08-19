/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.loan.LoanRepaymentSchedule;
import com.vsoftcorp.kls.business.entity.loan.TempLoanRepaymentSchedule;
import com.vsoftcorp.kls.dataaccess.loan.ITempLoanRepaymentScheduleDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * @author a9152
 * 
 */
public class TempLoanRepaymentScheduleDAO implements ITempLoanRepaymentScheduleDAO {

	private static final Logger logger = Logger.getLogger(TempLoanRepaymentScheduleDAO.class);

	/**
	 * 
	 */
	public void updateTempLoanRepaymentScheduleList(List<TempLoanRepaymentSchedule> loanRepaymentScheduleList) {

		logger.info("Start: Updating the Loan Repayment Schedule to the database in updateLoanRepaymentScheduleList() method.");
		try {
			if (!loanRepaymentScheduleList.isEmpty()) {
				EntityManager em = EntityManagerUtil.getEntityManager();
				em.getTransaction().begin();
				for (TempLoanRepaymentSchedule loanRepaymentSchedule : loanRepaymentScheduleList) {
					em.merge(loanRepaymentSchedule);
				}
				em.getTransaction().commit();
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
	public List<TempLoanRepaymentSchedule> getTempLoanRepaymentScheduleByLocId(Long loanLocId) {

		logger.info("Start: Fetching the list of loan repayment schedule list by line of credit id in getTempLoanRepaymentScheduleByLocId() method.");
		List<TempLoanRepaymentSchedule> repaymentScheduleList = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info(" loanLocId : " + loanLocId);
			if (loanLocId != null) {
				Query query = em
						.createQuery("SELECT t FROM TempLoanRepaymentSchedule t "
								+ "where t.loanRepaymentScheduleId.lineOfCreditId = :loanLocId order by t.loanRepaymentScheduleId.installmentNumber");
				query.setParameter("loanLocId", loanLocId);
				repaymentScheduleList = query.getResultList();
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the temp loan repayment schedule list from the "
					+ "database using line of credit id.Exception thrown.");
			throw new DataAccessException("Could not fetch the temp loan repayment schedule list from the database.",
					excp.getCause());
		}
		logger.info("End: Fetching the list of loan repayment schedule list by line of credit id in getTempLoanRepaymentScheduleByLocId() method.");
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
	public void saveTempLoanRepaymentScheduleList(List<TempLoanRepaymentSchedule> loanRepaymentScheduleList) {

		logger.info("Start: Saving the temp loan repayment schedule list to the database in saveTempLoanRepaymentScheduleList() method.");
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
			if (!loanRepaymentScheduleList.isEmpty()) {
				em.getTransaction().begin();
				for (TempLoanRepaymentSchedule loanRepaymentSchedule : loanRepaymentScheduleList) {
					em.merge(loanRepaymentSchedule);
				}
				em.getTransaction().commit();
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the temp loan repayment schedule list"
					+ "to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the temp loan repayment schedule list to the database.",
					excp.getCause());
		} finally {
			// do not remove this code.
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Saving the temp loan repayment schedule list to the database in saveTempLoanRepaymentScheduleList() method.");
	}

	@Override
	public void deleteTempLoanRepaymentScheduleByLocId(Long loanLocId) {

		logger.info("Start: Deleting the list of temp loan repayment schedule by line of credit id in deleteTempLoanRepaymentScheduleByLocId() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info(" loanLocId : " + loanLocId);
			em.getTransaction().begin();
			if (loanLocId != null) {
				Query query = em
						.createQuery("DELETE FROM TempLoanRepaymentSchedule t where t.loanRepaymentScheduleId.lineOfCreditId = :loanLocId");
				query.setParameter("loanLocId", loanLocId);
				int deletedCount = query.executeUpdate();
				logger.info(" deletedCount : " + deletedCount);
				em.getTransaction().commit();
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not delete the temp loan repayment schedule list from the "
					+ "database using line of credit id.Exception thrown.");
			throw new DataAccessException("Could not delete the temp loan repayment schedule list from the database.",
					excp.getCause());
		}
		logger.info("End: Deleting the list of loan repayment schedule by line of credit id in deleteTempLoanRepaymentScheduleByLocId() method.");
	}

}
