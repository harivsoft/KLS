package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Taluka;
import com.vsoftcorp.kls.dataaccess.ITalukaDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * 
 * @author a9153 This class has DAO implementations for Taluka
 * 
 */

public class TalukaDAO implements ITalukaDAO {

	private static final Logger logger = Logger
			.getLogger(TalukaDAO.class);

	/**
	 * Saves taluka to the database.
	 */
	public void saveTaluka(Taluka theTaluka) {

		logger.info("Start: Saving the taluka  data to the database in saveTaluka() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info("Got connection" + em);
			em.getTransaction().begin();
			em.persist(theTaluka);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the taluka  "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the taluka  data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the taluka  to the database in saveTaluka() method.");
	}

	/**
	 * Updates an existing taluka in the database.
	 */
	public void updateTaluka(Taluka theTaluka) {
		
		logger.info("Start: Updating the taluka  data to the database in updateTaluka() method.");
		Taluka taluka = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer talukaCode = theTaluka.getId();
			if (talukaCode != null ) {
				taluka = em.find(Taluka.class, talukaCode);
				if (taluka != null) {
					em.getTransaction().begin();
					taluka.setName(theTaluka.getName());
					taluka.setDistrict(theTaluka.getDistrict());
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the taluka"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the taluka  data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		if (taluka == null) {
			logger.error("Trying to update the taluka  record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the taluka  record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the taluka  data to the database in updateTaluka() method.");
	}

	/**
	 * Retrives and returns the taluka record from database if already exists
	 * 
	 */
	public Taluka getTaluka(Taluka theTaluka) {
		logger.info("Start: Inside method getTaluka()");
		Taluka taluka = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer talukaCode = theTaluka.getId();
			
			if (talukaCode != null) {
				taluka = em.find(Taluka.class, talukaCode);
			} 
		} catch (Exception e) {
			logger.error("Error while retriving taluka from the database");
			throw new DataAccessException(
					"Error while retriving taluka from the database.",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Inside method getTaluka()");
		return taluka;
	}
	
	
	/**
	 * Returns all the taluka  records to the client.
	 */
	public List<Taluka> getAllTalukas() {

		logger.info("Start: Fetching all the taluka  data from the database in getAllTalukas() method.");
		List<Taluka> talukaList = new ArrayList<Taluka>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			talukaList = em.createQuery("SELECT v FROM Taluka v")
					.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all talukas from the database");
			e.printStackTrace();
			throw new DataAccessException(
					"Error while retriving all talukas from the database",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the taluka  data from the database in getAllTalukas() method.");
		return talukaList;
	}
	
}
