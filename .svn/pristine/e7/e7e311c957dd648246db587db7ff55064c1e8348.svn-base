package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.CropTypeMaster;
import com.vsoftcorp.kls.data.CropTypeMasterData;

public class CropTypeMasterHelper {

	public static CropTypeMasterData getCropTypeMasterData(
			CropTypeMaster theMaster) {
		CropTypeMasterData data = new CropTypeMasterData();
		data.setCropTypeCode(theMaster.getCropTypeCode());
		data.setCropTypeName(theMaster.getCropTypeName());
		return data;
	}

	public static CropTypeMaster getCropTypeMaster(CropTypeMasterData data) {
		CropTypeMaster master = new CropTypeMaster();
		master.setCropTypeCode(data.getCropTypeCode());
		master.setCropTypeName(data.getCropTypeName());
		return master;
	}
}
