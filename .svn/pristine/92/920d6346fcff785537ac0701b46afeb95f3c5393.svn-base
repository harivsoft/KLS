package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.SBAccountMapping;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.SBAccountMappingData;
import com.vsoftcorp.kls.valuetypes.SBAccountStatus;

public class SBAccountMappingHelper {
	
	public static SBAccountMapping getSbAccountMapping(SBAccountMappingData data){
		SBAccountMapping master = new SBAccountMapping();
		master.setBusinessDate(DateUtil.getVSoftDateByString(data.getBusinessDate()));
		master.setPacsId(data.getPacsId());
		master.setPartyId(data.getPartyId());
		if(data.getRemarks() != null)
			master.setRemarks(data.getRemarks());
		master.setSavingsAccountNo(data.getSavingsAccountNo());
		master.setStatus(SBAccountStatus.getType(data.getStatus()));
		return master;
	}

}
