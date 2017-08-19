package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.Season;

/**
 * 
 * @author a9153
 *
 */

public interface ISeasonDAO {

	public void saveSeason(Season theSeason);

	public void updateSeason(Season theSeason);
	
	public Season getSeason(Season theSeason);
	
	public Season getSeason(Long theSeasonId);
	
	public List<Season> getAllSeasons();

	public List<Season> getAllSeasonsBySeasonYr(String seasonYear);
	
	public List<Season> getCurrentAndFutureSeasons(String businessDate);

	public List<Season> getActiveSeasons(String businessDate);
}
