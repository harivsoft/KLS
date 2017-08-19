package com.vsoftcorp.kls.service;

import java.util.List;

import com.vsoftcorp.kls.data.VillageData;
import com.vsoftcorp.kls.data.VillageDetailsData;

/**
 * 
 * @author a9152
 * 
 */
public interface IVillageService {

	/**
	 * Saves the village master data to the DB.
	 * 
	 * @param villageMasterData
	 */
	public void saveVillage(VillageData villageMasterData);

	/**
	 * Updates the village master data to the DB.
	 * 
	 * @param villageMasterData
	 */
	public void updateVillage(VillageData villageMasterData);
	
	/**
	 * Returns all the village master data to the client.
	 * @return
	 */
	public List<VillageData> getAllVillages();
	
	/**
	 * Returns all the village master data to the client based on Taluka.
	 * @param talukaId
	 * @return
	 */
	public List<VillageData> getAllVillagesByTaluka(Integer talukaId);

	public VillageDetailsData getVillageDetailsById(Integer villageId);
}
