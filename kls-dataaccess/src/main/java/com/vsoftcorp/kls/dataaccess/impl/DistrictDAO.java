package com.vsoftcorp.kls.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.District;
import com.vsoftcorp.kls.dataaccess.IDistrictDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class DistrictDAO implements IDistrictDAO {

	private static final Logger logger = Logger.getLogger(DistrictDAO.class);

	/**
	 * Saves the districtto the database.
	 */
	public void saveDistrict(District theDistrict) {

		logger.info("Start: Saving the districtdata to the database in saveDistrict() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(theDistrict);
			em.getTransaction().commit();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of saving the district"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the districtdata to the database.", excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the districtto the database in saveDistrict() method.");
	}

	/**
	 * Updates the existing districtin the database.
	 */
	public void updateDistrict(District district) {

		logger.info("Start: Updating the districtdata to the database in updateDistrict() method.");
		District master = new District();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();

			em.getTransaction().begin();
			master = em.find(District.class, district.getId());
			master.setName(district.getName());
			em.merge(master);
			em.getTransaction().commit();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of updating the district"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the districtdata to the database.", excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}

		logger.info("End: Successfully updated the districtdata to the database in updateDistrict() method.");
	}

	/**
	 * Checks for the district code if it exists in the database and returns the
	 * districtpojo.
	 */
	public District getDistrict(District district) {

		logger.info("Start: Fetching the districtdata from the database in getDistrict() method.");
		District master = null; // new District();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer districtCode = district.getId();
			if (districtCode != null) {
				master = em.find(District.class, districtCode);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the districtdata from the "
					+ "database using district code.Exception thrown.");
			throw new DataAccessException("Could not fetch the districtdata from the database.", excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the districtdata from the database in getDistrict() method.");
		return master;
	}

	/**
	 * Returns all the districtrecords to the client.
	 */
	public List<District> getAllDistricts() {

		logger.info("Start: Fetching all the villagedata from the database in getAllDistricts() method.");
		// List<DistrictMaster> districtMasterList = new
		// ArrayList<DistrictMaster>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();

			CriteriaBuilder cb = em.getCriteriaBuilder();

			CriteriaQuery<District> cq = cb.createQuery(District.class);

			Root<District> districtMaster = cq.from(District.class);

			CriteriaQuery<District> allDistricts = cq.select(districtMaster);

			TypedQuery<District> q = em.createQuery(allDistricts);

			List<District> districtMasterList = q.getResultList();
			if (districtMasterList.size() == 0) {

			}
			return districtMasterList;

		} catch (Exception e) {
			logger.error("Error while retriving all districts from the database");
			throw new DataAccessException("Error while retriving all districts from the database", e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}

	}
	
	public District getDistrictById(Integer districtCode) {

		logger.info("Start: Fetching the districtdata from the database in getDistrictById() method.");
		District master = null; // new District();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (districtCode != null) {
				master = em.find(District.class, districtCode);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the districtdata from the "
					+ "database using district code.Exception thrown.");
			throw new DataAccessException("Could not fetch the districtdata from the database.", excp.getCause());
		}
		logger.info("End: Successfully fetched the districtdata from the database in getDistrictById() method.");
		return master;
	}
}
