package com.vsoftcorp.kls.service;

import java.util.List;

import com.vsoftcorp.kls.data.CropTypeMasterData;

/**
 * @author sponnam
 *
 */
public interface ICropTypeMasterService {

	public void saveCropType(CropTypeMasterData theCropTypeMasterData);

	public void updateCropType(CropTypeMasterData theCropTypeMasterData);

	public CropTypeMasterData getCropList(CropTypeMasterData theCropTypeMasterData);
	
	public List<CropTypeMasterData> getAllCropTypes();
}
