package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.PacsGl;
import com.vsoftcorp.kls.dataaccess.IPacsGlDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class PacsGlDAO implements IPacsGlDAO {

	private static final Logger logger = Logger.getLogger(PacsGlDAO.class);

	@Override
	public void savePacsGl(PacsGl pacsGl) {

		logger.info("Start: Saving the gl master data in database in saveGLMaster() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(pacsGl);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the gl master "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the gl master data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the gl master data to the database in saveGLMaster() method.");
	}

	
	/**
	 * Updates the existing GL master in the database.
	 */
	public void updatePacsGl(PacsGl pacsGl) {
		logger.info("Start: Updating the GL master data to the database in updateGL() method.");
		PacsGl master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			
			Long glCode = pacsGl.getId();
			if (glCode != null) {
				master = em.find(PacsGl.class, glCode);
				if (master != null) {
					em.getTransaction().begin();
					master.setName(pacsGl.getName());
					master.setType(pacsGl.getType());
					master.setEnteredBy(pacsGl.getEnteredBy());
					master.setEnteredDate(pacsGl.getEnteredDate());
					master.setPacs(pacsGl.getPacs());
					master.setGlCode(pacsGl.getGlCode());
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the GL master "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the GL master data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the GL master record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the GL master record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the GL master data to the database in updateGL() method.");
	}
	
	
	@Override
	public PacsGl getPacsGl(PacsGl pacsGl, boolean isCloseSession) {
		logger.info("Start: Fetching the gl master data from the database in getGLMaster() method.");
		PacsGl master = null; //new PacsGl();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long glCode = pacsGl.getId();
			if (glCode != null) {
				master = em.find(PacsGl.class, glCode);}
		} catch (Exception excp) {
			logger.error("Could not fetch the gl master data from the "
					+ "database using gl master code.Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the gl master data from the database.",
					excp.getCause());
		}
		finally{
			if (isCloseSession)
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the gl master data from the database in getGLMaster() method.");
		return master;
	}

	
	
	/**
	 * Returns all the GL master records to the client.
	 */
	public List<PacsGl> getAllPacsGls(boolean isCloseSession) {

		logger.info("Start: Fetching all the"
				+ " data from the database in getAllGLs() method.");
		List<PacsGl> pacsGlList = new ArrayList<PacsGl>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			pacsGlList = em.createQuery("SELECT d FROM PacsGl d")
					.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all GLs from the database");
			throw new DataAccessException("Error while retriving all "
					+ " from the database", e.getCause());
		}
		finally{
			if (isCloseSession)
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the GL master data from the database in getAllGLs() method.");
		return pacsGlList;
	}
}
