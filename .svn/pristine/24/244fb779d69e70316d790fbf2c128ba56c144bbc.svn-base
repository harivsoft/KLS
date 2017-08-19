package com.vsoftcorp.kls.service;


import java.util.List;

import com.vsoftcorp.kls.business.entities.SeasonDefinition;
import com.vsoftcorp.kls.data.SeasonData;

public interface ISeasonService {

	public void saveSeason(SeasonData theSeasonData);

	public void updateSeason(SeasonData theSeasonData);
	
	public List<SeasonData> getAllSeasons();

	public List<SeasonData> getAllSeasonsBySeasonYr(String seasonYear);
	
	public List<SeasonData> getCurrentFutureSeasons(String businessDate);

	public SeasonData getSeasonDataBySeasonDef(
			SeasonDefinition seasonDefinition, String seasonYear);
	
	public List<SeasonData> getActiveSeasons(String businessDate);
	public SeasonData getSeason(Long seasonId);
}
