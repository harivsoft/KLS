package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.Village;

/**
 * 
 * @author a9152
 * 
 */
public interface IVillageDAO {

	/**
	 * Saves the village master data to the database.
	 * @param village
	 */
	public void saveVillage(Village village);

	/**
	 * Updates the existing village master data to the database.
	 * @param village
	 */
	public void updateVillage(Village village);

	/**
	 * Checks if the village code exists in the database.
	 * @param village
	 * @return VillageMaster
	 */
	public Village getVillage(Village village);
	
	/**
	 * Returns all the village master records to the client.
	 * @return
	 */
	public List<Village> getAllVillages();
	
	
	/**
	 * Returns all the village master records to the client based on taluka.
	 * @param talukaId 
	 * @return
	 */
	
	public List<Village> getAllVillagesByTaluka(Integer talukaId);
	
	public Village getVillageById(Integer id);
}
