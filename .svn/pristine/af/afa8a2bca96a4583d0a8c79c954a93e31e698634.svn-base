package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.SeasonDefinition;
import com.vsoftcorp.kls.dataaccess.ISeasonMasterDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class SeasonMasterDAO implements ISeasonMasterDAO {

	private static final Logger logger = Logger
			.getLogger(SeasonMasterDAO.class);

	@Override
	public void saveSeason(SeasonDefinition theSeason) {
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(theSeason);
			em.getTransaction().commit();
		} catch (Exception e) {
			logger.error("Exception while saving to database ");
			throw new DataAccessException("Exception while saving to database",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("Saved Season.");

	}

	@Override
	public void updateSeason(SeasonDefinition theSeason) {
		logger.info("Updating the Season method.");
		SeasonDefinition master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer id = theSeason.getId();
			if (id != null) {
				master = em.find(SeasonDefinition.class, id);
				if (master != null) {
					em.getTransaction().begin();
					master.setName(theSeason.getName());
					master.setDrawalStartDay(theSeason.getDrawalStartDay());
					master.setDrawalStartMonth(theSeason.getDrawalStartMonth());
					master.setDrawalEndDuration(theSeason.getDrawalEndDuration());
					master.setLoanOverdueDuration(theSeason.getLoanOverdueDuration());
					master.setDueDateMethod(theSeason.getDueDateMethod());
					master.setDueDateInMonths(theSeason.getDueDateInMonths());
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Error while updating the season in SeasonMasterDAO");
			excp.printStackTrace();
			throw new DataAccessException("Could not update the Season",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the Season master record which does not exist in the database.");
			throw new DataAccessException("Season Does not Exist");
		}
		logger.info("Season update");

	}

	@Override
	public SeasonDefinition getSeason(SeasonDefinition theSeason) {
		SeasonDefinition master = null;
		logger.info("in season for getSeason");
		try {
			
			EntityManager em = EntityManagerUtil.getEntityManager();
			if(theSeason.getId()!=null){
			master = em.find(SeasonDefinition.class, theSeason.getId());
			}
		} catch (Exception excp) {
			throw new DataAccessException("No Season found", excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("fetching season done");
		return master;
	}

	@Override
	public void deleteSeason(SeasonDefinition theSeason) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SeasonDefinition> getAllSeasonDefinition() {
		/*
		 * List<Season> seasonList = new ArrayList<Season>(); EntityManager em =
		 * EntityManagerUtil.getEntityManager(); CriteriaBuilder builder =
		 * em.getCriteriaBuilder(); CriteriaQuery<Season> query =
		 * builder.createQuery(Season.class); seasonList = (List<Season>)
		 * query.from(Season.class); return seasonList;
		 */

		List<SeasonDefinition> seasonDefinitionList = new ArrayList<SeasonDefinition>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			seasonDefinitionList = em.createQuery(
					"SELECT s FROM SeasonDefinition s").getResultList();
		} catch (Exception e) {
			logger.error("unable to retrieve all seasons");
			throw new DataAccessException("Error while retrieving all seasons",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		return seasonDefinitionList;
	}

}
