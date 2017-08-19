package com.vsoftcorp.kls.report.service.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.Village;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.gateway.datahelpers.AddressData;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.SanctionReportData;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.time.Date;

public class SanctionReportHelper {

	public static List<SanctionReportData> getSanctionReportDataList(List<Map> sanctionList) {
		List<SanctionReportData> sanctionDataList = new ArrayList<SanctionReportData>();
		SanctionReportData data = null;
		try {
			for (Map map : sanctionList) {
				data = new SanctionReportData();
				data.setApplicationNo(map.get("applicationNo").toString());
				// data.setCustomerName(map.get("custName").toString());
				Long customerId = Long.parseLong(map.get("memberNum").toString());
				PersonData customer = RestClientUtil.getCustomerById(customerId);
				if (customer != null) {
					data.setCustomerName(customer.getName());
					List<AddressData> addressDatas = customer.getContactDetailList();
					//data.setCustomerId(customerId.toString());
					data.setCustomerId(customer.getMemberNumber());
					if (!addressDatas.isEmpty()) {
						AddressData addressData = addressDatas.get(0);
						// data.setVillageName(map.get("villageName").toString());
						Village village = new Village();
						village.setId(addressData.getVillageId());
						KLSDataAccessFactory.getVillageDAO().getVillage(village);
						data.setVillageName(KLSDataAccessFactory.getVillageDAO().getVillage(village).getName());

					}
				}
				data.setSeasonName(map.get("seasonName").toString());
				data.setCropName(map.get("cropName").toString());
				Money appliedAmount = (Money) map.get("appliedAmount");
				data.setAmountApplied(appliedAmount.getAmount().setScale(2));
				Money sanctionedAmount = (Money) map.get("sanctionedAmount");
				if (sanctionedAmount != null) {
					data.setSanctionedAmount(sanctionedAmount.getAmount().setScale(2));
				}
				Money shareAmount = (Money) map.get("shareAmount");
				if (shareAmount != null) {
					data.setShareAmount(shareAmount.getAmount().setScale(2));
				}
				Money insuranceAmount = (Money) map.get("insuranceAmount");
				if (insuranceAmount != null) {
					data.setInsuranceAmount(insuranceAmount.getAmount().setScale(2));
				}
				if (map.get("sanctionedDate") != null) {
					Date sanctionedDate = (Date) map.get("sanctionedDate");
					data.setSanctionedDate(DateUtil.getDateString(sanctionedDate));
				}
				sanctionDataList.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sanctionDataList;
	}
}
