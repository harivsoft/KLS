package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.Purpose;
import com.vsoftcorp.kls.data.PurposeData;
/*
 *  @author a1565
 */


public class PurposeHelper {
	
	public static PurposeData  getPurposeData(Purpose master) {
	 PurposeData data=new PurposeData();
	 data.setId(master.getId());
	 data.setName(master.getName());
	 data.setActivityRequired(master.getActivityRequired());
	 data.setMasterAppRequired(master.getMasterAppReq());
	 data.setSubPurposeRequired(master.getSubPurposeReq());
	 data.setRemarks(master.getRemarks());
	
	 return data;
	}
	
	public static Purpose getPurpose(PurposeData data) {
		String activityRequired="Y";
		Purpose master =new Purpose();
		if(data.getId()!=null)
		master.setId(data.getId());
		master.setName(data.getName());
		master.setActivityRequired(activityRequired);
		master.setMasterAppReq(data.getMasterAppRequired());
		master.setSubPurposeReq(data.getSubPurposeRequired());
		master.setRemarks(data.getRemarks());
		return master;
		
	}
}
