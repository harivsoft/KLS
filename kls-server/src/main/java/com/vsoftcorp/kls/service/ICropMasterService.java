package com.vsoftcorp.kls.service;

import java.util.List;

import com.vsoftcorp.kls.data.CropMasterData;

public interface ICropMasterService {

	public void saveCrop(CropMasterData theCropMasterData);

	public void updateCrop(CropMasterData theCropMasterData);

	public CropMasterData getCropList(CropMasterData theCropMasterData);
	
	public List<CropMasterData> getAllCrops();
}
