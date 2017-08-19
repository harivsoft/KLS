package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.SeasonParameter;

/**
 * 
 * @author a1605
 *
 */
public interface ISeasonParameterDAO {

	//public SeasonParameter getSeasonParameter(Long seasonId,Integer cropId);

	public void saveSeasonParameter(SeasonParameter seasonParameter);

	public String updateSeasonParameter(SeasonParameter seasonParameter);

	public SeasonParameter getSeasonParameter(Long seasonId, Integer cropId, Integer pacsId);
	
	public List<SeasonParameter> getSeasonParameters(Long seasonId, Integer cropId);
	
}
