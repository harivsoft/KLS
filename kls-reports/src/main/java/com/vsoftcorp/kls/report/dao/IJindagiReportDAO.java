package com.vsoftcorp.kls.report.dao;

import java.util.List;

import com.vsoftcorp.kls.business.entities.LandDetail;
import com.vsoftcorp.kls.business.entities.TenantLandDetails;

public interface IJindagiReportDAO {
	
	public List<LandDetail> getLandDetailsListByCustomerId(Long customerId);
	
	public LandDetail getLandDetailsById(Long landId);
	
	public List<TenantLandDetails> getTenantLandDetailsByCustomerId(Long customerId);
	
	public List<TenantLandDetails> getRentedDetailsByCustomerId(Long customerId);
	
	public Object[] getCustomerDetails(Integer customerId);

}
