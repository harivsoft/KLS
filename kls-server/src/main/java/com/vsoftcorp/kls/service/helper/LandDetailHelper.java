package com.vsoftcorp.kls.service.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.LandDetail;
import com.vsoftcorp.kls.business.entities.LandType;
import com.vsoftcorp.kls.business.entities.Taluka;
import com.vsoftcorp.kls.business.entities.TenantLandDetails;
import com.vsoftcorp.kls.business.entities.Village;
import com.vsoftcorp.kls.data.LandData;
import com.vsoftcorp.kls.data.LandDetailData;
import com.vsoftcorp.kls.data.TenantLandDetailsData;
import com.vsoftcorp.kls.dataaccess.ILandDetailDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.impl.LandDetailDAO;

/**
 * This helper class is used to conversion b/w POJO and Entity class and vice
 * versa
 * 
 * @author a1605
 */
public class LandDetailHelper {
	private static final Logger logger = Logger.getLogger(LandDetailHelper.class);
	
	public static LandDetailData getLandDetailsData(LandDetail landDetail) {
		
		LandDetailData data = new LandDetailData();
		List<LandData> landDataList = new ArrayList<LandData>();
		LandData landData = new LandData();
		landData.setId(String.valueOf(landDetail.getId()));
		landData.setVillageId(landDetail.getVillage().getId().toString());
		landData.setVillageName(landDetail.getVillage().getName());
		landData.setSubVillageName(landDetail.getSubVillageCode());
		landData.setSurveyNo(landDetail.getSurveyNo());
		landData.setbSNo(landDetail.getBsrNo());
		landData.setrSNo(landDetail.getRsrNo());
		landData.setLandTypeId(landDetail.getLandType().getId().toString());
		landData.setLandTypeName(landDetail.getLandType().getName());
		landData.setTotalLandArea(String.valueOf(landDetail.getAreaOwned()));
		landData.setCultivatedArea(String.valueOf(landDetail
				.getAreaCultivated()));
		landData.setMortgazed(landDetail.getIsMortgazed());
		landData.setMortgazedRemark(landDetail.getMortgazedRemarks());
		landData.setChargeDeclared(landDetail.getIsCharged());
		landData.setChargeDeclaredRemarks(landDetail.getChargedRemarks());
		landDataList.add(landData);
		data.setLandRegister(landDataList);
		return data;
	}
	 

	public static List<LandDetail> getLandDetail(LandDetailData data) {
		List<LandDetail> list = new ArrayList<LandDetail>();
		LandDetail landDetail = null;
		List<LandData> landDataList = data.getLandRegister();

		for (LandData landData : landDataList) {
			landDetail = new LandDetail();

			if (landData.getId() != null)
				landDetail.setId(Long.parseLong(landData.getId()));
			
//			Customer customer=new Customer();
//			customer.setId(Long.parseLong(data.getCustId()));
			landDetail.setCustomerId(Long.parseLong(data.getCustId()));
			if(landData.getAvailableCulLand() == null){
				landDetail.setAvailableCulLand(Double.parseDouble(landData
						.getCultivatedArea()));
			}else{
				landDetail.setAvailableCulLand(landData.getAvailableCulLand());
			}
			Village village = new Village();
			village.setId(Integer.parseInt(landData.getVillageId()));
			village.setName(landData.getVillageName());
			landDetail.setVillage(village);

			landDetail.setSubVillageCode(landData.getSubVillageName());
			landDetail.setSurveyNo(landData.getSurveyNo());
			landDetail.setBsrNo(landData.getbSNo());
			landDetail.setRsrNo(landData.getrSNo());
			LandType landType = new LandType();
			landType.setId(Integer.parseInt(landData.getLandTypeId()));
			landType.setName(landData.getLandTypeName());

			landDetail.setLandType(landType);
			landDetail.setAreaOwned(Double.parseDouble(landData
					.getTotalLandArea()));
			landDetail.setAreaCultivated(Double.parseDouble(landData
					.getCultivatedArea()));
			landDetail.setIsCharged(landData.getChargeDeclared());
			landDetail.setChargedRemarks(landData.getChargeDeclaredRemarks());
			landDetail.setIsMortgazed(landData.getMortgazed());
			landDetail.setMortgazedRemarks(landData.getMortgazedRemark());
			list.add(landDetail);
			
			Taluka taluka = new Taluka();
			if(landData.getTalukaId()!=null){
			taluka.setId(Integer.parseInt(landData.getTalukaId()));
			taluka.setName(landData.getTalukaName());
			landDetail.setTaluka(taluka);
			}
		}
		return list;
	}

	public static LandDetailData getLandDetailData(
			List<LandDetail> landDetailList) {
		LandDetailData data = new LandDetailData();
		List<LandData> landDataList = new ArrayList<LandData>();
		LandData landData = null;

		for (LandDetail landDetail : landDetailList) {
			landData = new LandData();
			data.setCustId(landDetail.getCustomerId().toString());
			landData.setId(landDetail.getId().toString());
			landData.setVillageId(landDetail.getVillage().getId().toString());
			landData.setVillageName(landDetail.getVillage().getName());
			landData.setSubVillageName(landDetail.getSubVillageCode());
			landData.setSurveyNo(landDetail.getSurveyNo());
			landData.setbSNo(landDetail.getBsrNo());
			landData.setrSNo(landDetail.getRsrNo());
			landData.setLandTypeId(landDetail.getLandType().getId().toString());
			landData.setLandTypeName(landDetail.getLandType().getName());
			landData.setTotalLandArea(String.valueOf(landDetail.getAreaOwned()));
			landData.setCultivatedArea(String.valueOf(landDetail
					.getAreaCultivated()));
			landData.setMortgazed(landDetail.getIsMortgazed());
			landData.setMortgazedRemark(landDetail.getMortgazedRemarks());
			landData.setChargeDeclared(landDetail.getIsCharged());
			landData.setChargeDeclaredRemarks(landDetail.getChargedRemarks());
			if(landDetail.getAvailableCulLand() != null){
				landData.setAvailableCulLand(landDetail.getAvailableCulLand());
				landData.setTenantsLand(landDetail.getAreaCultivated()-landDetail.getAvailableCulLand());
			}else{
				landData.setAvailableCulLand(landDetail.getAreaCultivated());
				landData.setTenantsLand(0d);
			}
			
			if(landDetail.getTaluka()!=null && landDetail.getTaluka().getId()!=null)
			{
				landData.setTalukaId(landDetail.getTaluka().getId().toString());
				landData.setTalukaName(landDetail.getTaluka().getName());
			}
			
			landDataList.add(landData);
		}
		data.setLandRegister(landDataList);

		return data;

	}
	
	public static TenantLandDetails getTenantLandDetails(TenantLandDetailsData data){
		TenantLandDetails master = new TenantLandDetails();
		if(data.getId() != null){
			master.setId(data.getId());
		}
		master.setAreaCultivated(data.getAreaCultivated());
		master.setCustomerId(data.getCustomerId());
		master.setGuarantorId(data.getGuarantorId());
		master.setLandRefId(data.getLandRefId());
		master.setLandType(data.getLandType());
		
		return master;
	}
	
	public static TenantLandDetailsData getTenantLandDetailsData(TenantLandDetails master){
		TenantLandDetailsData data = new TenantLandDetailsData();
		if(master.getId() != null){
			data.setId(master.getId());
		}
		data.setAreaCultivated(master.getAreaCultivated());
		data.setCustomerId(master.getCustomerId());
		data.setGuarantorId(master.getGuarantorId());
		data.setLandRefId(master.getLandRefId());
		ILandDetailDAO dao = KLSDataAccessFactory.getLandDetailDAO();
		LandDetail land=dao.getLandDetailsById(master.getLandRefId());
		data.setbSNo(land.getBsrNo());
		data.setLandTypeName(land.getLandType().getName());
		data.setrSNo(land.getRsrNo());
		data.setSurveyNo(land.getSurveyNo());
		data.setVillageName(land.getVillage().getName());
		data.setTotalLandArea(master.getAreaCultivated());
		data.setLandType(master.getLandType());
		data.setMortgazed(land.getIsMortgazed());
		data.setMortgazedRemark(land.getMortgazedRemarks());
		data.setChargeDeclared(land.getIsCharged());
		data.setChargeDeclaredRemarks(land.getChargedRemarks());
		if(land.getTaluka()!=null && land.getTaluka().getId()!=null)
			data.setTalukaName(land.getTaluka().getName());
		return data;
	}

}