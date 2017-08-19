package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.dataaccess.IInterestCategoryDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class InterestCategoryDAO implements IInterestCategoryDAO {

	private static final Logger logger = Logger.getLogger(InterestCategoryDAO.class);

	/**
	 * Saves the Interest category to the database.
	 */
	public void saveInterestCategory(InterestCategory interestCategory) {

		logger.info("Start: Saving the Interest category data to the database in saveInterestCategory() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(interestCategory);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the Interest category "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the Interest category data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the Interest category to the database in saveInterestCategory() method.");
	}

	/**
	 * Updates the existing Interest category in the database.
	 */
	public void updateInterestCategory(InterestCategory interestCategoryMaster) {

		logger.info("Start: Updating the Interest category data to the database in updateInterestCategory() method.");
		InterestCategory master = new InterestCategory();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			master = em.find(InterestCategory.class, interestCategoryMaster.getId());
			master.setIntrCategoryDesc(interestCategoryMaster.getIntrCategoryDesc());
			em.merge(master);
			em.getTransaction().commit();

		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the Interest category "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the Interest category data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully updated the Interest category data to the database in updateInterestCategory() method.");
	}

	/**
	 * Checks for the Interest category id if it exists in the database and
	 * returns the Interest category.
	 */
	public InterestCategory getInterestCategory(InterestCategory interestCategoryMaster, boolean isCloseSession) {

		logger.info("Start: Fetching the Interest category data from the database in getInterestCategory() method.");
		InterestCategory master = null;// new InterestCategory();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer interestCategoryId = interestCategoryMaster.getId();
			if (interestCategoryId != null) {
				master = em.find(InterestCategory.class, interestCategoryId);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the Interest category data from the "
					+ "database using Interest category id.Exception thrown.");
			throw new DataAccessException("Could not fetch the Interest category data from the database.",
					excp.getCause());
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the Interest category data from the database in getInterestCategory() method.");
		return master;
	}

	/**
	 * Returns all the Interest category master records to the client.
	 */
	public List<InterestCategory> getAllInterestCategories(boolean isCloseSession) {

		logger.info("Start: Fetching all the Interest category data from the database in getAllInterestCategorys() method.");
		List<InterestCategory> interestCategoryList = new ArrayList<InterestCategory>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			interestCategoryList = em.createQuery("SELECT d FROM InterestCategory d").getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all Interest categories from the database");
			throw new DataAccessException("Error while retriving all Interest categories from the database",
					e.getCause());
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the Interest category data from the database in getAllInterestCategorys() method.");
		return interestCategoryList;
	}

	public InterestCategory getInterestCategoryById(Integer interestCategoryId) {

		logger.info("Start: Fetching the Interest category data from the database in getInterestCategoryById() method.");
		InterestCategory master = null;
		logger.info(" interestCategoryId : " + interestCategoryId);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (interestCategoryId != null) {
				master = em.find(InterestCategory.class, interestCategoryId);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the Interest category data from the "
					+ "database using Interest category id.Exception thrown.");
			throw new DataAccessException("Could not fetch the Interest category data from the database.",
					excp.getCause());
		}
		logger.info("End: Successfully fetched the Interest category data from the database in getInterestCategoryById() method.");
		return master;
	}
}
