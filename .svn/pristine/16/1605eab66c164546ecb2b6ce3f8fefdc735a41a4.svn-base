package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Branch;
import com.vsoftcorp.kls.dataaccess.IBranchDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * 
 * @author a9152
 * 
 */
public class BranchDAO implements IBranchDAO {

	private static final Logger logger = Logger.getLogger(BranchDAO.class);

	/**
	 * Saves the branch to the database.
	 */
	public void saveBranch(Branch branch) {

		logger.info("Start: Saving the branch data to the database in savebranch() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(branch);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the branch "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the branch data to the database.", excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the branch to the database in savebranch() method.");
	}

	/**
	 * Updates the existing branch in the database.
	 */
	public void updateBranch(Branch branch) {
		logger.info("Start: Updating the branch data to the database in updatebranch() method.");
		Branch master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();

			if (branch.getId() != null) {
				master = em.find(Branch.class, branch.getId());
				if (master != null) {
					em.getTransaction().begin();
					master.setName(branch.getName());
					master.setLocation(branch.getLocation());
					master.setCode(branch.getCode());
					em.getTransaction().commit();
				} else {
					logger.error("Cannot update the branch record as it does not exist in the database.");
					throw new DataAccessException(
							"Cannot update the branch record as it does not exist in the database.");
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the branch "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the branch data to the database.", excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the branch record which does not exist in the database.");
			throw new DataAccessException("Trying to update the branch record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the branch data to the database in updatebranch() method.");
	}

	/**
	 * Checks for the branch code if it exists in the database and returns the
	 * branch master pojo.
	 */
	public Branch getBranch(Branch branch) {

		logger.info("Start: Fetching the branch data from the database in getbranch() method.");
		Branch master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (branch.getId() != null) {
				master = em.find(Branch.class, branch.getId());
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the branch data from the " + "database using branch id.Exception thrown.");
			throw new DataAccessException("Could not fetch the branch data from the database.", excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the branch data from the database in getbranch() method.");
		return master;
	}

	/**
	 * Returns all the branch records to the client.
	 */
	public List<Branch> getAllBranches() {

		logger.info("Start: Fetching all the branch data from the database in getAllBranches() method.");
		List<Branch> branchMasterList = new ArrayList<Branch>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			branchMasterList = em.createQuery("SELECT b FROM Branch b").getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all branchs from the database");
			throw new DataAccessException("Error while retriving all branchs from the database", e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the branch data from the database in getAllBranches() method.");
		return branchMasterList;
	}
}
