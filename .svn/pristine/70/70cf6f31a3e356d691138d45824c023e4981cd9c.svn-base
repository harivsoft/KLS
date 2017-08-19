package com.vsoftcorp.kls.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Crop;
import com.vsoftcorp.kls.dataaccess.ICropDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * @author sponnam
 * 
 */
public class CropDAO implements ICropDAO {

	private static final Logger logger = Logger.getLogger(CropDAO.class);

	@Override
	public void saveCrop(Crop theCropMaster) {

		logger.info("Start: Saving the crop master data to the database in saveCrop() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(theCropMaster);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the crop master "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the crop master data to the database.", excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the crop master to the database in saveCrop() method.");
	}

	@Override
	public void updateCrop(Crop theCropMaster) {

		logger.info("Start: Updating the crop master data to the database in updateCrop() method.");
		Crop master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer cropId = theCropMaster.getId();
			if (cropId != null) {
				master = em.find(Crop.class, cropId);
				if (master != null) {
					em.getTransaction().begin();
					master.setId(theCropMaster.getId());
					master.setName(theCropMaster.getName());
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the crop master "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the crop master data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the crop master record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the crop master record which does not exist in the database.");
		}
		
		logger.info("End: Successfully updated the crop master data to the database in updateCrop() method.");
	}

	@Override
	public Crop getCrop(Crop theCropMaster) {

		logger.info("Start: Fetching the crop master data from the database in getCrop() method.");
		Crop master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer cropId = theCropMaster.getId();
			if (cropId != null) {
				master = em.find(Crop.class, cropId);
			}

		} catch (Exception excp) {
			logger.error("Could not fetch the crop master data from the "
					+ "database using crop code.Exception thrown.");
			throw new DataAccessException("Could not fetch the crop master data from the database.", excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the crop master data from the database in getCrop() method.");
		return master;
	}

	@Override
	public void deleteCrop(Crop theCropMaster) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		em.remove(theCropMaster);
		em.getTransaction().commit();
	}

	/**
	 * Returns all the crop master records to the client.
	 */
	@SuppressWarnings("unchecked")
	public List<Crop> getAllCrops() {

		logger.info("Start: Fetching all the crop master data from the database in getAllCrops() method.");
		List<Crop> cropMasterList = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();

			TypedQuery<Crop> query = em.createQuery("SELECT v FROM Crop v", Crop.class);
			query.setHint("org.hibernate.cacheable", false);
			cropMasterList = query.getResultList();

			logger.info("getting the list after" + cropMasterList.size());
		} catch (Exception e) {
			logger.error("Error while retriving all crops from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all crops from the database", e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the crop master data from the database in getAllCrops() method.");
		return cropMasterList;
	}

	@Override
	public Crop getCrop(Integer cropId) {

		logger.info("Start: Fetching the crop data from the database in getCrop() method.");
		Crop master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (cropId != null) {
				master = em.find(Crop.class, cropId);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the crop data from the " + "database using crop id.Exception thrown.");
			throw new DataAccessException("Could not fetch the crop data from the database.", excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the crop data from the database in getCrop() method.");
		return master;
	}

}
