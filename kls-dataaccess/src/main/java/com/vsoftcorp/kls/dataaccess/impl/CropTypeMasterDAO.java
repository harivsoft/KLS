package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.CropTypeMaster;
import com.vsoftcorp.kls.dataaccess.ICropTypeMasterDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * @author sponnam
 * 
 */
public class CropTypeMasterDAO implements ICropTypeMasterDAO {

	private static final Logger logger = Logger
			.getLogger(CropTypeMasterDAO.class);

	/**
	 * 
	 */
	public void saveCropType(CropTypeMaster theCropTypeMaster) {

		logger.info("Start: Saving the crop type data to the database in saveCropType() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(theCropTypeMaster);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the crop type "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the crop type data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
	}

	/**
	 * 
	 */
	public void updateCropType(CropTypeMaster theCropTypeMaster) {

		logger.info("Start: Updating the crop type data to the database in updateCropType() method.");
		CropTypeMaster master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String cropTypeCode = theCropTypeMaster.getCropTypeCode();
			if (cropTypeCode != null && !cropTypeCode.equalsIgnoreCase("")) {
				master = em.find(CropTypeMaster.class, cropTypeCode);
				if (master != null) {
					em.getTransaction().begin();
					master.setCropTypeCode(theCropTypeMaster.getCropTypeCode());
					master.setCropTypeName(theCropTypeMaster.getCropTypeName());
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the crop type master "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the crop type master data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the crop type master record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the crop type master record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the crop type data to the database in updateCropType() method.");
	}

	@Override
	public CropTypeMaster getCropType(CropTypeMaster theCropTypeMaster) {

		logger.info("Start: Fetching the crop type master data from the database in getCropType() method.");
		CropTypeMaster master = new CropTypeMaster();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String cropTypeCode = theCropTypeMaster.getCropTypeCode();
			if (cropTypeCode != null && !cropTypeCode.equalsIgnoreCase("")) {
				master = em.find(CropTypeMaster.class, cropTypeCode);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the crop type master data from the "
					+ "database using croptype code.Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the crop type master data from the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the crop type master data from the database in getCropType() method.");
		return master;
	}
	
	/**
	 * Returns all the Crop type master records 
	 */
	public List<CropTypeMaster> getAllCropTypes() {

		logger.info("Start: Fetching all the crop types master data from the database in getAllCropTypes() method.");
		List<CropTypeMaster> cropTypeMasterList = new ArrayList<CropTypeMaster>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			cropTypeMasterList = em.createQuery("SELECT v FROM CropTypeMaster v")
					.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all crop Types from the database");
			e.printStackTrace();
			throw new DataAccessException(
					"Error while retriving all crop Types from the database",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the crop Type master data from the database in getAllCropTypes() method.");
		return cropTypeMasterList;
	}
}
