package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.SeasonDefinition;
import com.vsoftcorp.kls.data.SeasonMasterData;
import com.vsoftcorp.kls.dataaccess.ISeasonMasterDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.ISeasonMasterService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.SeasonMasterHelper;

public class SeasonMasterService implements ISeasonMasterService {

	private static final Logger logger = Logger
			.getLogger(SeasonMasterService.class);

	@Override
	public void saveSeason(SeasonMasterData theData) {

		ISeasonMasterDAO dao = KLSDataAccessFactory.getSeasonDefinitionDAO();
		SeasonDefinition season = SeasonMasterHelper.getSeason(theData);
		SeasonDefinition masterSeason = null;
		try {
			masterSeason = dao.getSeason(season);
			if (masterSeason == null)
				dao.saveSeason(season);
		} catch (Exception e) {
			logger.error("Season Master cannot be saved");
			throw new KlsRuntimeException("Season master data cannot be saved",
					e);
		}
		if (masterSeason != null) {
			logger.error("Season master data already exists");
			throw new KlsRuntimeException("Season data already exists");
		}
	}

	@Override
	public void updateSeason(SeasonMasterData theData) {
		logger.info("Calling Season master dao in updateSeason() method.");
		ISeasonMasterDAO dao = KLSDataAccessFactory.getSeasonDefinitionDAO();
		SeasonDefinition master = SeasonMasterHelper.getSeason(theData);
		try {
			dao.updateSeason(master);
		} catch (Exception excp) {
			logger.error("Season data cannot be updated as Season code does not exist");
			throw new KlsRuntimeException(
					"Season data cannot be updated as Season code does not exist",
					excp.getCause());
		}
		logger.info("Season update done");

	}

	@Override
	public SeasonMasterData getSeason(SeasonMasterData theData) {
		ISeasonMasterDAO dao = KLSDataAccessFactory.getSeasonDefinitionDAO();
		SeasonDefinition master = SeasonMasterHelper.getSeason(theData);
		SeasonDefinition season = dao.getSeason(master);
		return SeasonMasterHelper.getSeasonData(season);
	}

	@Override
	public void deleteSeason(SeasonMasterData theData) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<SeasonMasterData> getAllSeasons() {
		List<SeasonDefinition> seasonlist = new ArrayList<SeasonDefinition>();
		List<SeasonMasterData> seasonMasterDataList = new ArrayList<SeasonMasterData>();
		ISeasonMasterDAO dao = KLSDataAccessFactory.getSeasonDefinitionDAO();
		seasonlist = dao.getAllSeasonDefinition();

		for (SeasonDefinition season : seasonlist) {
			seasonMasterDataList.add(SeasonMasterHelper
					.getSeasonData(season));
		}
		return seasonMasterDataList;
	}

}
