package com.vsoftcorp.kls.report.service.helper;

import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.kls.business.entities.LandDetail;
import com.vsoftcorp.kls.business.entities.TenantLandDetails;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.CustomerLandDetailsData;
import com.vsoftcorp.kls.report.service.data.LandChargesData;
import com.vsoftcorp.kls.report.service.data.RentLandData;
import com.vsoftcorp.kls.report.service.data.TenantLandData;

public class JindagiReportHelper {

	public static List<CustomerLandDetailsData> getCustomerLandtDataList(List<LandDetail> landDetailsList) {
		List<CustomerLandDetailsData> customerLandDetailsData = new ArrayList<CustomerLandDetailsData>();
		/*if(landDetailsList.size() > 0) {
			for(LandDetail landDetail : landDetailsList) {
				CustomerLandDetailsData data = new CustomerLandDetailsData();
				if(landDetail.getTaluka() != null) {
					data.setTaluka(landDetail.getTaluka().getName());
				} else {
					data.setTaluka("N/A");
				}
				if(landDetail.getVillage() != null) {
					data.setVillage(landDetail.getVillage().getName());
				} else {
					data.setVillage("N/A");
				}
				if(landDetail.getSubVillageCode() != null) {
					data.setSubVillage(landDetail.getSubVillageCode());
				} else {
					data.setSubVillage("N/A");
				}
				if(landDetail.getSurveyNo() != null) {
				data.setSurveyNumber(landDetail.getSurveyNo());
				} else {
					landDetail.setSurveyNo("N/A");
				}
				
				data.setKhataNumber(landDetail.getBsrNo() == null ? "N/A" : landDetail.getBsrNo());
				
				if(landDetail.getCultivatedLand() != null) {
					data.setCultivatedArea(landDetail.getCultivatedLand().toString()+" "+landDetail.getCultivatedLandUnits().getName());
					if(landDetail.getCultivatedDecimalLand() != null) {
						data.setCultivatedArea(data.getCultivatedArea()+" and "+landDetail.getCultivatedDecimalLand()+" "+landDetail.getCultivatedDecimalLandUnits().getName());
					}
				} else {
					data.setCultivatedArea("N/A");
				}
				
				if(landDetail.getWaterFacility() != null) {
					data.setWaterSource(landDetail.getWaterFacility().name());
				} else {
					data.setWaterSource("N/A");
				}
				if(landDetail.getLandValue() != null) {
				data.setLandValue(landDetail.getLandValue().toString());
				} else {
					data.setLandValue("N/A");
				}
				if(landDetail.getTotalLand() != null) {
					data.setTotalLandArea(landDetail.getTotalLand().toString()+" "+landDetail.getTotalLandUnits().getName());
					if(landDetail.getTotalDecimalLand() != null) {
						data.setTotalLandArea(data.getTotalLandArea()+" and "+landDetail.getTotalDecimalLand()+" "+landDetail.getTotalDecimalLandUnits().getName());
					}
				} else {
					data.setTotalLandArea("N/A");
				}
				customerLandDetailsData.add(data);
			}
		} else {
			CustomerLandDetailsData data = new CustomerLandDetailsData();
			customerLandDetailsData.add(data);
		}
	*/	return customerLandDetailsData;
	}
	
	public static List<LandChargesData> getLandChargesDataList(List<LandDetail> landDetailsList) {
		List<LandChargesData> landChargesDataList = new ArrayList<LandChargesData>();
		/*if(landDetailsList.size() > 0) {
			for(LandDetail landDetail : landDetailsList) {
				if("Y".equals(landDetail.getIsCharged())) {
					LandChargesData data = new LandChargesData();
					data.setBankName(landDetail.getChargedBankName() == null ? "N/A" : landDetail.getChargedBankName());
					if(landDetail.getChargedAmount() != null) {
						data.setChargeAmount(landDetail.getChargedAmount().toString());
					} else {
						data.setChargeAmount("N/A");
					}
					if(landDetail.getChargedDate() != null) {
						data.setChargeDate(landDetail.getChargedDate().toString());
					} else {
						data.setChargeDate("N/A");
					}
					data.setChargeType(landDetail.getChargeType() == null ? "N/A" : landDetail.getChargeType());
					landChargesDataList.add(data);
				}
				if( "Y".equals(landDetail.getIsMortgazed())) {
					LandChargesData data = new LandChargesData();
					data.setBankName(landDetail.getMortgazedBankName() == null ? "N/A" : landDetail.getMortgazedBankName());
					if(landDetail.getMortgazedChargedAmount() != null) {
						data.setChargeAmount(landDetail.getMortgazedChargedAmount().toString());
					} else {
						data.setChargeAmount("N/A");
					}
					if(landDetail.getMortgazedChargedDate() != null) {
						data.setChargeDate(landDetail.getMortgazedChargedDate().toString());
					} else {
						data.setChargeDate("N/A");
					}
					data.setChargeType(landDetail.getMortgazedType() == null ? "N/A" : landDetail.getMortgazedType());
					landChargesDataList.add(data);
				}
			}
		} else {
			LandChargesData data = new LandChargesData();
			landChargesDataList.add(data);
		}
	*/	return landChargesDataList;
	}
	
	public static List<TenantLandData> getTenantLandDataList(List<TenantLandDetails> tenantLandDetailsList) {
		List<TenantLandData> tenantLandDataList = new ArrayList<TenantLandData>();
		/*if(tenantLandDetailsList.size() > 0) {
			for(TenantLandDetails details : tenantLandDetailsList) {
				TenantLandData data = new TenantLandData();
				Object[] cusObj = KLSReportDataAccessFactory.getJindagiReportDAO().getCustomerDetails(details.getCustomerId().intValue());
				LandDetail landDetail = KLSReportDataAccessFactory.getJindagiReportDAO().getLandDetailsById(details.getLandRefId());
				if(cusObj[1] != null) {
				data.setTenantname(cusObj[1].toString());
				} else {
					data.setTenantname("N/A");
				}
				if(details.getPeriod() != null) {
					data.settPeriod(details.getPeriod().toString());
				} else {
					data.settPeriod("N/A");
				}
				if(landDetail.getTotalLand() != null) {
					data.settArea(landDetail.getTotalLand().toString()+" "+landDetail.getTotalLandUnits().getName());
					if(landDetail.getTotalDecimalLand() != null) {
						data.settArea(data.gettArea()+" and "+landDetail.getTotalDecimalLand()+" "+landDetail.getTotalDecimalLandUnits().getName());
					}
				} else {
					data.settArea("N/A");
				}
				data.settSurveyNumber(landDetail.getSurveyNo() == null ? "N/A" : landDetail.getSurveyNo());
				tenantLandDataList.add(data);
			}
		} else {
			TenantLandData data = new TenantLandData();
			tenantLandDataList.add(data);
		}
	*/	return tenantLandDataList;
	}
	
	public static List<RentLandData> getRentedLandDataList(List<TenantLandDetails> rentedLandDetailsList) {
		List<RentLandData> rentedLandDataList = new ArrayList<RentLandData>();
		/*if(rentedLandDetailsList.size() > 0) {
			for(TenantLandDetails details : rentedLandDetailsList) {
				RentLandData data = new RentLandData();
				Object[] cusObj = KLSReportDataAccessFactory.getJindagiReportDAO().getCustomerDetails(details.getCustomerId().intValue());
				LandDetail landDetail = KLSReportDataAccessFactory.getJindagiReportDAO().getLandDetailsById(details.getLandRefId());
				if(cusObj[1] != null) {
					data.setRentname(cusObj[1].toString());
				}
				if(details.getPeriod() != null) {
					data.setrPeriod(details.getPeriod().toString());
				}
				if(landDetail.getTotalLand() != null) {
					data.setrArea(landDetail.getTotalLand().toString()+" "+landDetail.getTotalLandUnits().getName());
					if(landDetail.getTotalDecimalLand() != null) {
						data.setrArea(data.getrArea()+" and "+landDetail.getTotalDecimalLand()+" "+landDetail.getTotalDecimalLandUnits().getName());
					}
				} else {
					data.setrArea("N/A");
				}
				data.setrSurveyNumber(landDetail.getSurveyNo() == null ? "N/A" : landDetail.getSurveyNo());
				rentedLandDataList.add(data);
			}
		} else {
			RentLandData data = new RentLandData();
			rentedLandDataList.add(data);
		}
*/		return rentedLandDataList;
	}

}
