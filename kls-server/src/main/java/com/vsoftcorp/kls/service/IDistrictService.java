package com.vsoftcorp.kls.service;

import java.util.List;

import com.vsoftcorp.kls.data.DistrictData;

/**
 * 
 * @author a9152
 * 
 */
public interface IDistrictService {

	/**
	 * 
	 * @param theDistrictData
	 */
	public void saveDistrict(DistrictData theDistrictData);

	/**
	 * 
	 * @param theDistrictData
	 */
	public void updateDistrict(DistrictData theDistrictData);

	/**
	 * Retrieves all districts from the database.
	 * 
	 * @return
	 */
	public List<DistrictData> getAllDistricts();
}
