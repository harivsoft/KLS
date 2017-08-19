package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.SlabwiseInterestRate;
import com.vsoftcorp.kls.dataaccess.ISlabwiseInterestRateDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;

public class SlabwiseInterestRateDAO implements ISlabwiseInterestRateDAO {

	private static final Logger logger = Logger.getLogger(SlabwiseInterestRateDAO.class);

	/**
	 * Saves the slab wise interest rate data to the database.
	 */
	public void saveSlabwiseInterestRate(SlabwiseInterestRate slabwiseInterestRate) {

		logger.info("Start: Saving the slab wise interest rate data to the database in saveSlabwiseInterest() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(slabwiseInterestRate);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the slab wise interest rate "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the slab wise interest rate data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the slab wise interest rate to the database in saveSlabwiseInterest() method.");
	}

	/**
	 * Updates the slab wise interest rate in the database.
	 */
	public void updateSlabwiseInterestRate(SlabwiseInterestRate slabwiseInterestRate) {

		logger.info("Start: Updating the slab wise interest rate data to the database in updateSlabwiseInterest() method.");
		SlabwiseInterestRate master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long id = slabwiseInterestRate.getSlabwiseInterestRateId();
			if (id != null) {
				master = em.find(SlabwiseInterestRate.class, id);
				if (master != null) {
					em.getTransaction().begin();
					master.setInterestCategory(slabwiseInterestRate.getInterestCategory());
					master.setFromPeriod(slabwiseInterestRate.getFromPeriod());
					master.setFromSlab(slabwiseInterestRate.getFromSlab());
					master.setPenalRoi(slabwiseInterestRate.getPenalRoi());
					master.setRoi(slabwiseInterestRate.getRoi());
					master.setToPeriod(slabwiseInterestRate.getToPeriod());
					master.setToSlab(slabwiseInterestRate.getToSlab());
					em.getTransaction().commit();
				} else {
					logger.error("Cannot update the slab wise interest rate record as it does not exist in the database.");
					throw new DataAccessException(
							"Cannot update the slab wise interest rate record as it does not exist in the database.");
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the slab wise interest rate "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the slab wise interest rate data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully updated the slab wise interest rate data to the database in updateSlabwiseInterest() method.");
	}

	/**
	 * Checks for the slab wise interest rate id if it exists in the database
	 * and returns the slab wise interest rate pojo.
	 */
	public SlabwiseInterestRate getSlabwiseInterestRate(SlabwiseInterestRate slabwiseInterestRate) {

		logger.info("Start: Fetching the slab wise interest rate data from the database in getSlabwiseInterestRecord() method.");
		SlabwiseInterestRate master = new SlabwiseInterestRate();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long id = slabwiseInterestRate.getSlabwiseInterestRateId();
			if (id != null) {
				master = em.find(SlabwiseInterestRate.class, id);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the slab wise interest rate data from the "
					+ "database using slab wise interest id.Exception thrown.");
			throw new DataAccessException("Could not fetch the slab wise interest rate data from the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the slab wise interest rate data from the database in getSlabwiseInterestRecord() method.");
		return master;
	}

	/**
	 * Returns all the slab wise interest rate records to the client.
	 */
	public List<SlabwiseInterestRate> getAllSlabwiseInterestRateRecords() {

		logger.info("Start: Fetching all the slab wise interest rate data from the database in getAllSlabwiseInterestRecords() method.");
		List<SlabwiseInterestRate> slabwiseInterestRateList = new ArrayList<SlabwiseInterestRate>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			slabwiseInterestRateList = em.createQuery("SELECT s FROM SlabwiseInterestRate s").getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all slab wise interest records from the database");
			throw new DataAccessException("Error while retriving all slab wise interest records from the database",
					e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the slab wise interest rate data from the database in getAllSlabwiseInterestRecords() method.");
		return slabwiseInterestRateList;
	}

	/**
	 * Saves the list of slab wise interest rate data to the database.
	 */
	public void saveSlabwiseInterestRate(List<SlabwiseInterestRate> slabwiseInterestRateList) {

		logger.info("Start: Saving the list of slab wise interest rate data to the database in saveSlabwiseInterest() method.");
		try {
			for (SlabwiseInterestRate master : slabwiseInterestRateList) {
				if (master.getSlabwiseInterestRateId() == null) {
					saveSlabwiseInterestRate(master);
				} else {
					updateSlabwiseInterestRate(master);
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the list of slab wise interest rate "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the list of slab wise interest rate data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the list of slab wise interest rate to the database in saveSlabwiseInterest() method.");
	}

	/**
	 * Deletes the slab wise interest rate data from the database.
	 */
	public void deleteSlabwiseInterestRateRecord(Long slabwiseInterestRateId) {

		logger.info("Start: Deleting the slab wise interest rate data from the database in deleteSlabwiseInterest() method.");
		SlabwiseInterestRate master = null;
		try
		{
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (slabwiseInterestRateId != null) {
				master = em.find(SlabwiseInterestRate.class, slabwiseInterestRateId);
				if (master != null) {
					em.getTransaction().begin();
					em.remove(master);
					em.getTransaction().commit();
				}
		}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of deleting the slab wise interest rate "
					+ "data from the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of deleting the slab wise interest rate data from the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully deleted the slab wise interest rate from the database in deleteSlabwiseInterest() method.");
	}

	@Override
	public List<SlabwiseInterestRate> getSlabwiseInterestRateRecords(Integer interestCategoryId, Date effectivedate) {

		logger.info("Start: Fetching all the slab wise interest rate from the database in getSlabwiseInterestRateRecords() method.");
		List<SlabwiseInterestRate> slabwiseInterestRateList = new ArrayList<SlabwiseInterestRate>();
		try {
			logger.info("interestCategoryId : " + interestCategoryId);
			logger.info("effectivedate : " + effectivedate);
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT s FROM SlabwiseInterestRate s where s.interestCategory.id = :interestCategoryId"
							+ " and '" + effectivedate + "'"  + " >= s.effectiveDate");
			query.setParameter("interestCategoryId", interestCategoryId);
			slabwiseInterestRateList = (List<SlabwiseInterestRate>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving all slab wise interest records from the database");
			throw new DataAccessException(
					"Error while retriving all slab wise interest records for interest category id", e.getCause());
		}
		logger.info("End: Fetching all the slab wise interest rate data from the database in getSlabwiseInterestRateRecords() method.");
		return slabwiseInterestRateList;
	}
}
