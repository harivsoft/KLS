package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.LandType;
import com.vsoftcorp.kls.dataaccess.ILandTypeDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * 
 * @author a9152
 * 
 */
public class LandTypeDAO implements ILandTypeDAO {

	private static final Logger logger = Logger.getLogger(LandTypeDAO.class);

	@Override
	public void saveLandType(LandType landType) {

		logger.info("Start: Saving the land type data to the database in saveLandTypeMaster() method.???="
				+ landType.getName());
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(landType);
			em.getTransaction().commit();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of saving the land type"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the land typedata to the database.", excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the land type to the database in saveLandTypeMaster() method.");
	}

	@Override
	public void updateLandType(LandType landType) {
		logger.info("Start: Updating the Land Type  data to the database in updateLandType() method.");
		LandType master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer LandTypeId = landType.getId();
			if (LandTypeId != null) {
				master = em.find(LandType.class, LandTypeId);
				if (master != null) {
					em.getTransaction().begin();
					master.setName(landType.getName());
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the Land Type  "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the Land Type  data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the Land Type  record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the Land Type  record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the Land Typedata to the database in updateLandType() method.");

	}

	@Override
	public LandType getLandType(LandType landType) {

		logger.info("Start: Fetching the land type data from the database in getLandTypeMaster() method.");
		LandType master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer landTypeCode = landType.getId();
			if (landTypeCode != null) {
				master = em.find(LandType.class, landTypeCode);
			}
		} catch (Exception excp) {
			// excp.printStackTrace();
			logger.error("Could not fetch the land type data from the "
					+ "database using land type code id.Exception thrown.");
			throw new DataAccessException("Could not fetch the land type data from the database.", excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the land type data from the database in getLandTypeMaster() method.");
		return master;
	}

	@Override
	public List<LandType> getAllLandTypes() {

		logger.info("Start: Fetching all the land type data from the database in getAllLandTypes() method.");
		List<LandType> landTypeMasterList = new ArrayList<LandType>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			landTypeMasterList = em.createQuery("SELECT l FROM LandType l").getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all land types from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all land types from the database", e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the land types data from the database in getAllLandTypes() method.");
		return landTypeMasterList;

	}

	@Override
	public LandType getLandType(Integer landTypeId) {

		logger.info("Start: Fetching the land type data from the database in getLandType() method.");
		LandType master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (landTypeId != null) {
				master = em.find(LandType.class, landTypeId);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the land type data from the "
					+ "database using land type code id.Exception thrown.");
			throw new DataAccessException("Could not fetch the land type data from the database.", excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the land type data from the database in getLandType() method.");
		return master;
	}
}
