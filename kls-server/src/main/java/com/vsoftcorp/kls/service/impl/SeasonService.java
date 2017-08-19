package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.business.entities.SeasonDefinition;
import com.vsoftcorp.kls.data.SeasonData;
import com.vsoftcorp.kls.dataaccess.ISeasonDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.ISeasonService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.SeasonHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

/**
 * 
 * @author a9153
 * 
 */
public class SeasonService implements ISeasonService {

	private static final Logger logger = Logger.getLogger(SeasonService.class);

	/**
	 * This methods save the Season only if not exists.
	 */
	public void saveSeason(SeasonData theSeasonMasterData) {

		logger.info("Start : Calling Season master dao in saveSeason() method.");

		ISeasonDAO dao = KLSDataAccessFactory.getSeasonDAO();
		Season master = SeasonHelper.getSeason(theSeasonMasterData);
		Season season = KLSDataAccessFactory.getLoanApplicationRenewalDAO().getSeasonByName(master.getName());
		try {
			if (season == null)
				dao.saveSeason(master);
			 if(season!=null)
	                dao.updateSeason(master);
		} catch (Exception excp) {
			logger.error("Season master data cannot be saved");
			throw new KlsRuntimeException("Season master data cannot be saved", excp);
		}
		/*if (season != null)
			throw new KlsRuntimeException("Season already exists");*/

		logger.info("End : Calling Season master dao in saveSeason() method.");
	}

	/**
	 * Updates a Season if already exists.
	 * 
	 */
	public void updateSeason(SeasonData theSeasonMasterData) {

		logger.info("Start: Inside method updateSeason()");
		try {
			ISeasonDAO dao = KLSDataAccessFactory.getSeasonDAO();
			Season master = SeasonHelper.getSeason(theSeasonMasterData);

			dao.updateSeason(master);
		} catch (Exception e) {
			logger.error("Exception while updating Season: " + e.getMessage());
			throw new KlsRuntimeException("Exception while saving Season: ", e.getCause());
		}
		logger.info("End: Inside method updateSeason()");
	}

	/**
	 * Retrives all Seasons from Database
	 * 
	 */
	public List<SeasonData> getAllSeasons() {
		logger.info("Start: Inside method getAllSeasons ()");
		ISeasonDAO dao = KLSDataAccessFactory.getSeasonDAO();
		List<SeasonData> seasons = new ArrayList<SeasonData>();
		try {

			List<Season> seasonMasterList = dao.getAllSeasons();
			for (Season masterData : seasonMasterList) {
				seasons.add(SeasonHelper.getSeasonData(masterData));
			}
		} catch (Exception e) {
			logger.error("Error while retriving all Seasons  from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all Seasons ", e.getCause());
		}
		logger.info("End : Inside method getAllSeasons ()");
		return seasons;
	}

	@Override
	public List<SeasonData> getAllSeasonsBySeasonYr(String seasonYear) {
		logger.info("Start: Inside method getAllSeasons ()");
		ISeasonDAO dao = KLSDataAccessFactory.getSeasonDAO();
		List<SeasonData> seasons = new ArrayList<SeasonData>();
		try {

			List<Season> seasonMasterList = dao.getAllSeasonsBySeasonYr(seasonYear);
			for (Season masterData : seasonMasterList) {
				seasons.add(SeasonHelper.getSeasonData(masterData));
			}
		} catch (Exception e) {
			logger.error("Error while retriving all Seasons  from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all Seasons ", e.getCause());
		}
		logger.info("End : Inside method getAllSeasons ()");
		return seasons;
	}

	@Override
	public List<SeasonData> getCurrentFutureSeasons(String businessDate) {
		logger.info("Start: Inside method getCurrentFutureSeasons ()");
		ISeasonDAO dao = KLSDataAccessFactory.getSeasonDAO();
		List<SeasonData> seasons = new ArrayList<SeasonData>();
		try {

			List<Season> seasonMasterList = dao.getCurrentAndFutureSeasons(businessDate);
			for (Season masterData : seasonMasterList) {
				seasons.add(SeasonHelper.getSeasonData(masterData));
			}
		} catch (Exception e) {
			logger.error("Error while retriving all Seasons  from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all Seasons ", e.getCause());
		}
		logger.info("End : Inside method getCurrentFutureSeasons ()");
		return seasons;
	}
	
	@Override
	public List<SeasonData> getActiveSeasons(String businessDate) {
		logger.info("Start: Inside method getCurrentFutureSeasons ()");
		ISeasonDAO dao = KLSDataAccessFactory.getSeasonDAO();
		List<SeasonData> seasons = new ArrayList<SeasonData>();
		try {

			List<Season> seasonMasterList = dao.getActiveSeasons(businessDate);
			for (Season masterData : seasonMasterList) {
				seasons.add(SeasonHelper.getSeasonData(masterData));
			}
		} catch (Exception e) {
			logger.error("Error while retriving all Seasons  from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all Seasons ", e.getCause());
		}
		logger.info("End : Inside method getCurrentFutureSeasons ()");
		return seasons;
	}
	
	public SeasonData getSeasonDataBySeasonDef(SeasonDefinition seasonDefinition, String seasonYear) {
		SeasonDefinition sDefinition = new SeasonDefinition();
		SeasonData seasonData = SeasonHelper.getSeasonDataByDef(seasonDefinition, seasonYear);
		return seasonData;
	}
	
	@Override
	public SeasonData getSeason(Long seasonId) {
		logger.info("Start: Inside method getSeason()");
		ISeasonDAO dao = KLSDataAccessFactory.getSeasonDAO();
		SeasonData	season = null;
		try {
		season = SeasonHelper.getSeasonData( dao.getSeason(seasonId));
		} catch (Exception e) {
			logger.error("Error while retriving Season  from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving  Season ", e.getCause());
		}
		logger.info("End : Inside method getAllSeasons ()");
		return season;
	}

}
