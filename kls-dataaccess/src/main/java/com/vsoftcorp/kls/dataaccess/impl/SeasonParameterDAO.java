package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.SeasonParameter;
import com.vsoftcorp.kls.dataaccess.ISeasonParameterDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class SeasonParameterDAO implements ISeasonParameterDAO {

	private static final Logger logger = Logger.getLogger(SeasonParameterDAO.class);

	@Override
	public void saveSeasonParameter(SeasonParameter seasonParameter) {

		logger.info("Start: Saving the SeasonParameter master data to the database in saveSeasonParameter() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(seasonParameter);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the SeasonParameter master " + "data to the database.Exception thrown.");
			throw new DataAccessException("Could not commit the transaction of saving the SeasonParameter master data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the SeasonParameter master to the database in saveSeasonParameter() method.");
	}

	@Override
	public String updateSeasonParameter(SeasonParameter seasonParameter) {

		logger.info("Start: Updating the SeasonParameter master data to the database in updateSeasonParameter() method.");
		SeasonParameter master = null;
		String returnMsg="";
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer seasonParameterId = seasonParameter.getId();
			if (seasonParameterId != null) {
				master = em.find(SeasonParameter.class, seasonParameterId);
				if (master != null) {
					em.getTransaction().begin();
					master.setId(seasonParameter.getId());
					master.setCrop(seasonParameter.getCrop());
					master.setInsuranceByFarmer(seasonParameter.getInsuranceByFarmer());
					if(master.getInsuranceGL()==null)
						returnMsg="Crop Insurance Parameter Saved Successfully!";
					else
						returnMsg="Crop Insurance Parameter Updated Successfully!";
					master.setInsuranceGL(seasonParameter.getInsuranceGL());
					master.setSeason(seasonParameter.getSeason());
					master.setInsuranceSubsidy(seasonParameter.getInsuranceSubsidy());
					master.setPacs(seasonParameter.getPacs());
					master.setInsurancePeriod(seasonParameter.getInsurancePeriod());
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the SeasonParameter master " + "data to the database.Exception thrown.");
			throw new DataAccessException("Could not commit the transaction of updating the SeasonParameter master data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the SeasonParameter master record which does not exist in the database.");
			throw new DataAccessException("Trying to update the SeasonParameter master record which does not exist in the database.");
		}

		logger.info("End: Successfully updated the SeasonParameter master data to the database in updateSeasonParameter() method.");
		return returnMsg;
	}

	@Override
	public SeasonParameter getSeasonParameter(Long seasonId, Integer cropId, Integer pacsId) {
		logger.info("Start: getting seasonParameter by passing seasonI :" + seasonId + ", cropId:" + cropId+ " pacsId: "+pacsId);
		SeasonParameter seasonParameter = null;

		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select sp from SeasonParameter sp where sp.pacs.id=:pacsId and sp.season.id=:seasonId and sp.crop.id=:cropId");
			query.setParameter("seasonId", seasonId);
			query.setParameter("cropId", cropId);
			query.setParameter("pacsId", pacsId);

			seasonParameter = (SeasonParameter) query.getSingleResult();
		} catch (NoResultException noExcp) {
			logger.error("no record found for season parameter");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error: while getting season parameter.");
			throw new DataAccessException("Could not get SeasonParameter.", e.getCause());
		}
		logger.info("End: Successfully got SeasonParameter.");
		return seasonParameter;
	}
	
	@Override
	public List<SeasonParameter> getSeasonParameters(Long seasonId, Integer cropId) {
		logger.info("Start: getting seasonParameter by passing seasonI :" + seasonId + ", cropId:" + cropId);
		SeasonParameter seasonParameter = null;
       List<SeasonParameter> seasonParamList=new ArrayList<SeasonParameter>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select sp from SeasonParameter sp where  sp.season.id=:seasonId and sp.crop.id=:cropId");
			query.setParameter("seasonId", seasonId);
			query.setParameter("cropId", cropId);
			//query.setParameter("pacsId", pacsId);

			seasonParamList =  query.getResultList();
			
		} catch (NoResultException noExcp) {
			logger.error("no record found for season parameter");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error: while getting season parameter.");
			throw new DataAccessException("Could not get SeasonParameter.", e.getCause());
		}
		logger.info("End: Successfully got SeasonParameter.");
		return seasonParamList;
	}
}
