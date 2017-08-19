package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.SeasonDefinition;

/**
 * @author sponnam
 * 
 */
public interface ISeasonMasterDAO {

	public void saveSeason(SeasonDefinition theSeasonDefinition);

	public void updateSeason(SeasonDefinition theSeasonDefinition);

	public SeasonDefinition getSeason(SeasonDefinition theSeasonDefinition);

	public void deleteSeason(SeasonDefinition theSeasonDefinition);

	public List<SeasonDefinition> getAllSeasonDefinition();

}
