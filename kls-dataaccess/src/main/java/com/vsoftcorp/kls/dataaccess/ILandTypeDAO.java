package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.LandType;

/**
 * 
 * @author a9152
 * 
 */
public interface ILandTypeDAO {

	/**
	 * Saves the land type master data to the database.
	 * 
	 * @param LandType
	 */
	public void saveLandType(LandType landType);

	/**
	 * Updates the existing land type master data to the database.
	 * 
	 * @param LandType
	 */
	public void updateLandType(LandType landType);

	/**
	 * Checks if the land type master code exists in the database.
	 * 
	 * @param LandType
	 * @return LandType
	 */
	public LandType getLandType(LandType landType);

	/**
	 * Returns all the land type master records to the client.
	 * 
	 * @return
	 */
	public List<LandType> getAllLandTypes();

	/**
	 * 
	 * @param landTypeId
	 * @return
	 */
	public LandType getLandType(Integer landTypeId);

}
