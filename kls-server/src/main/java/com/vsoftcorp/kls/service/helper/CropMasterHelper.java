package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.Crop;
import com.vsoftcorp.kls.data.CropMasterData;

public class CropMasterHelper {

	public static CropMasterData getCropMasterData(Crop theMaster) {
		CropMasterData data = new CropMasterData();
		data.setId(theMaster.getId().toString());
		data.setName(theMaster.getName());
		return data;
	}

	public static Crop getCropMaster(CropMasterData data) {
		Crop master = new Crop();
		if(data.getId()!=null && !data.getId().equalsIgnoreCase(""))
		master.setId(Integer.parseInt(data.getId()));
		master.setName(data.getName());
		return master;
	}
}
