package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.LandDetail;
import com.vsoftcorp.kls.business.entities.TenantLandDetails;

public interface ILandDetailDAO {

public void saveLandDetail(LandDetail landDetail);	

public void updateLandDetail(LandDetail landDetail);

public List<LandDetail> getLandDetailsByCustomerId(Long customerId);

public void deleteLandDetail(String id);

public List<LandDetail> getLandDetailsByCustIdAndLandType(Long customerId,
		Integer landTypeId);

public void saveTenantLandDetail(TenantLandDetails tenantLandDetail);	

public void updateTenantLandDetail(TenantLandDetails tenantLandDetail);

public void deleteTenantLandDetail(Long id);

public List<TenantLandDetails> getTenantLandDetailsByCustomerId(Long customerId);

public LandDetail getLandDetailsById(Long id);

public TenantLandDetails getTenantLandDetailsById(Long id);

public List<TenantLandDetails> getTenantLandDetailsByRefId(Long refId);

public List<TenantLandDetails> getTenantLandDetailsByCustIdAndLandType(Long customerId,
		Integer landTypeId);

}