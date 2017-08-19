package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.LandType;
import com.vsoftcorp.kls.data.LandTypeData;

public class LandTypeHelper {

	public static LandTypeData getLandTypeData(LandType theMaster) {
		LandTypeData data = new LandTypeData();
		data.setId(String.valueOf(theMaster.getId()));
		data.setName(theMaster.getName());
		return data;
	}

	public static LandType getLandType(LandTypeData data) {
		LandType master = new LandType();
		if (data.getId() != null)
			master.setId(Integer.parseInt((data.getId())));
			master.setName(data.getName());
		return master;
	}
}
