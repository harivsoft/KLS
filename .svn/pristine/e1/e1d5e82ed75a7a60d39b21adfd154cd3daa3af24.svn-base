package com.vsoftcorp.kls.service;

import java.util.List;

import com.vsoftcorp.kls.data.LandDetailData;
import com.vsoftcorp.kls.data.TenantLandDetailsData;

/**
 * 
 * @author a1605
 *
 */
public interface ILandDetailService {
	
	public void saveLandDetails(LandDetailData landDetailsData);	

	public LandDetailData getLandDetailsByCustomerId(Long customerId);

	public Double getTotalLandDetailsByCustomerId(Long customerId,
			Integer landTypeId);
	
	public Double getTotalCultivatedLandDetailsByCustomerId(Long customerId);
	
	public List<TenantLandDetailsData> checkLandTenantDetails(Long landRefId);
	
	public Double getTotalAvailableLandByCustId(Long customerId);

	public LandDetailData getLandDetailsById(Long id);
	
	public Double getTotalLandByCustId(Long customerId);
}