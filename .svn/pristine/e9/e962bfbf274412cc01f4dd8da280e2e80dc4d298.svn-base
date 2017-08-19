package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Village;
import com.vsoftcorp.kls.dataaccess.IVillageDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * 
 * @author a9152
 * 
 */
public class VillageDAO implements IVillageDAO {

	private static final Logger logger = Logger.getLogger(VillageDAO.class);

	/**
	 * Saves the village to the database.
	 */
	public void saveVillage(Village village) {

		logger.info("Start: Saving the village data to the database in saveVillage() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(village);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the village "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the village data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the village to the database in saveVillage() method.");
	}

	/**
	 * Updates the existing village in the database.
	 */
	public void updateVillage(Village village) {

		logger.info("Start: Updating the village data to the database in updateVillage() method.");
		Village master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer id = village.getId();
			if (id != null) {
				master = em.find(Village.class, id);
				if (master != null) {
					em.getTransaction().begin();
					master.setPin(village.getPin());
					master.setName(village.getName());
					master.setTaluka(village.getTaluka());
					em.getTransaction().commit();
				} else {
					logger.error("Cannot update the village record as it does not exist in the database.");
					throw new DataAccessException(
							"Cannot update the village record as it does not exist in the database.");
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the village "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the village data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the village record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the village record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the village data to the database in updateVillage() method.");
	}

	/**
	 * Checks for the village if it exists in the database and returns the
	 * village pojo.
	 */
	public Village getVillage(Village village) {

		logger.info("Start: Fetching the village data from the database in getVillage() method.");
		Village master = new Village();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer id = village.getId();
			if (id != null) {
				master = em.find(Village.class, id);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the village data from the "
					+ "database using village id.Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the village data from the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the village data from the database in getVillage() method.");
		return master;
	}

	/**
	 * Returns all the village master records to the client.
	 */
	public List<Village> getAllVillages() {

		logger.info("Start: Fetching all the village data from the database in getAllVillages() method.");
		List<Village> villageList = new ArrayList<Village>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			villageList = em.createQuery("SELECT v FROM Village v")
					.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all villages from the database");
			throw new DataAccessException(
					"Error while retriving all villages from the database",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the village data from the database in getAllVillages() method.");
		return villageList;
	}
	
	/**
	 * Returns all the village master records to the client.
	 */
	public List<Village> getAllVillagesByTaluka(Integer talukaId) {

		logger.info("Start: Fetching all the village data from the database in getAllVillagesByTaluka() method.");
		List<Village> villageList = new ArrayList<Village>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT v FROM Village v where v.taluka.id=:talukaId");
			query.setParameter("talukaId", talukaId);
					villageList=query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all villages based on taluka from the database");
			throw new DataAccessException(
					"Error while retriving all villages based on taluka from the database",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the village data from the database in getAllVillagesByTaluka() method.");
		return villageList;
	}
	
	public Village getVillageById(Integer id) {

		logger.info("Start: Fetching the village data from the database in getVillageById() method.");
		Village master = new Village();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (id != null) {
				master = em.find(Village.class, id);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the village data from the "
					+ "database using village id.Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the village data from the database.",
					excp.getCause());
		}
		logger.info("End: Successfully fetched the village data from the database in getVillageById() method.");
		return master;
	}
}
