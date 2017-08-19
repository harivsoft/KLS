package com.vsoftcorp.kls.service;

import java.util.List;

import com.vsoftcorp.kls.data.LandTypeData;

/**
 * 
 * @author a9152
 *
 */
public interface ILandTypeService {
	
	/**
	 * Save the land type data to the database.
	 * 
	 * @param landTypeData
	 */
	public void saveLandType(LandTypeData landTypeData);

	/**
	 * Updates the land type data to the database.
	 * 
	 * @param landTypeData
	 */
	public void updateLandType(LandTypeData landTypeData);

	/**
	 * Fetch all the land types from the database.
	 * 
	 * @return
	 */
	public List<LandTypeData> getAllLandTypes();
	

}
