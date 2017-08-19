package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.dataaccess.ISeasonDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * 
 * @author a9153 This class has DAO implementations for Season
 * 
 */

public class SeasonDAO implements ISeasonDAO {

	private static final Logger logger = Logger.getLogger(SeasonDAO.class);

	/**
	 * Saves season to the database.
	 */
	public void saveSeason(Season theSeasonMaster) {

		logger.info("Start: Saving the season master data to the database in saveSeason() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info("Got connection" + em);
			em.getTransaction().begin();
			em.persist(theSeasonMaster);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the season master "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the season master data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the season master to the database in saveSeason() method.");
	}

	/**
	 * Updates an existing season in the database.
	 */
	public void updateSeason(Season theSeasonMaster) {

		logger.info("Start: Updating the season master data to the database in updateSeason() method.");
		Season master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long seasonCode = theSeasonMaster.getId();
			if (seasonCode != null) {
				master = em.find(Season.class, seasonCode);
				if (master != null) {
					em.getTransaction().begin();
					master.setName(theSeasonMaster.getName());
					master.setDrawalEndDate(theSeasonMaster.getDrawalEndDate());
					master.setDrawalStartDate(theSeasonMaster
							.getDrawalStartDate());
					master.setOverdueDate(theSeasonMaster.getOverdueDate());
					master.setProcessStatus(theSeasonMaster.getProcessStatus());
					master.setRecoveryPeriod(theSeasonMaster.getRecoveryPeriod());
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the season master "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the season master data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the season master record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the season master record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the season master data to the database in updateSeason() method.");
	}

	/**
	 * Retrives and returns the season record from database if already exists
	 * 
	 */
	public Season getSeason(Season theSeasonMaster) {
		logger.info("Start: Inside method getSeason()");
		Season seasonMaster = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long seasonCode = theSeasonMaster.getId();
			if (seasonCode != null) {
				seasonMaster = em.find(Season.class, seasonCode);
			} 
		} catch (Exception e) {
			logger.error("Error while retriving season from the database");
			throw new DataAccessException(
					"Error while retriving season from the database.",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Inside method getSeason()");
		return seasonMaster;
	}

	/**
	 * Returns all the season master records to the client.
	 */
	public List<Season> getAllSeasons() {

		logger.info("Start: Fetching all the season master data from the database in getAllSeasons() method.");
		List<Season> seasonMasterList = new ArrayList<Season>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			seasonMasterList = em.createQuery("SELECT v FROM Season v")
					.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all seasons from the database");
			e.printStackTrace();
			throw new DataAccessException(
					"Error while retriving all seasons from the database",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the season master data from the database in getAllSeasons() method.");
		return seasonMasterList;
	}

	@Override
	public Season getSeason(Long theSeasonId) {

		logger.info("Start: Inside method getSeason()");
		Season seasonMaster = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (theSeasonId != null) {
				seasonMaster = em.find(Season.class, theSeasonId);
			} else {
				logger.info("Season id not set for retrival of season record");
				throw new DataAccessException(
						"Season id not set for retrival of season record");
			}
		} catch (Exception e) {
			logger.error("Error while retriving season from the database");
			throw new DataAccessException(
					"Error while retriving season from the database.",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Inside method getSeason()");
		return seasonMaster;
	}

	@Override
	public List<Season> getAllSeasonsBySeasonYr(String seasonYear) {

		logger.info("Start: Fetching all the season master data from the database in getAllSeasonsBySeasonYr() method.");
		List<Season> seasonMasterList = new ArrayList<Season>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query= em.createQuery("SELECT v FROM Season v where v.drawalStartDate >= :financialYrStartDate and v.drawalEndDate <= :financialYrEndDate ");
			query.setParameter("financialYrStartDate", DateUtil.getVSoftDateByString("01/04/"+seasonYear.substring(0,4)));
			query.setParameter("financialYrEndDate",  DateUtil.getVSoftDateByString("31/03/"+seasonYear.substring(5,9)));
			seasonMasterList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all seasons from the database");
			e.printStackTrace();
			throw new DataAccessException(
					"Error while retriving all seasons from the database",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the season master data from the database in getAllSeasonsBySeasonYr() method.");
		return seasonMasterList;
	}
	
	@Override
	public List<Season> getActiveSeasons(String businessDate){
		logger.info("Start: Fetching all the season master data from the database in getCurrentAndFutureSeasons() method.");
		List<Season> seasonMasterList = new ArrayList<Season>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query= em.createQuery("SELECT v FROM Season v where v.drawalEndDate > :businessDate");
			query.setParameter("businessDate", DateUtil.getVSoftDateByString(businessDate));
			seasonMasterList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all seasons from the database");
			e.printStackTrace();
			throw new DataAccessException(
					"Error while retriving all seasons from the database",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the season master data from the database in getCurrentAndFutureSeasons() method.");
		return seasonMasterList;
	}
	
	@Override
	public List<Season> getCurrentAndFutureSeasons(String businessDate){
		logger.info("Start: Fetching all the season master data from the database in getCurrentAndFutureSeasons() method.");
		List<Season> seasonMasterList = new ArrayList<Season>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query= em.createQuery("SELECT v FROM Season v where v.drawalStartDate >= :businessDate");
			query.setParameter("businessDate", DateUtil.getVSoftDateByString(businessDate));
			seasonMasterList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all seasons from the database");
			e.printStackTrace();
			throw new DataAccessException(
					"Error while retriving all seasons from the database",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the season master data from the database in getCurrentAndFutureSeasons() method.");
		return seasonMasterList;
	}
}
