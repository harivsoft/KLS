package com.vsoftcorp.kls.report.service.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.report.service.data.MasterReportData;
import com.vsoftcorp.kls.valuetypes.charges.ChargesType;

public class MasterReportHelper {

	public static List<MasterReportData> getMasterReportDataList(List<Map> masterList) {
		// TODO Auto-generated method stub

		List<MasterReportData> list = new ArrayList<>();
		MasterReportData data = null;
		System.out.println("masterList---" + masterList.size());
		for (Map map : masterList) {
			data = new MasterReportData();
			// System.out.println("name---" + map.get("name").toString());
			if (map.get("name") != null)
				data.setName(map.get("name").toString());
			if (map.get("productName") != null)
				data.setProductName(map.get("productName").toString());
			if (map.get("purposeName") != null)
				data.setPurposeName(map.get("purposeName").toString());
			if (map.get("subPurposeName") != null)
				data.setSubPurposeName(map.get("subPurposeName").toString());
			if (map.get("activityName") != null)
				data.setActivityName(map.get("activityName").toString());
			if (map.get("chargesDescription") != null)
				data.setChargesDescription(map.get("chargesDescription").toString());
			if (map.get("chargesCode") != null)
				data.setChargesCode(map.get("chargesCode").toString());
			if (map.get("minAmount") != null)
				data.setMinAmount(((Money) map.get("minAmount")).getAmount());
			if (map.get("maxAmount") != null)
				data.setMaxAmount(((Money) map.get("maxAmount")).getAmount());
			if (map.get("chargesType") != null) {
				Integer changeType = ((ChargesType) map.get("chargesType")).getValue();
				System.out.println("changeType==" + changeType);
				data.setChangeType(ChargesType.getType(changeType).getName());
			}

			if (map.get("chargesReceivedGL") != null)
				data.setChargesReceivedGl(map.get("chargesReceivedGL").toString());
			if (map.get("chargesReceivableGL") != null)
				data.setChargesReceivableGl(map.get("chargesReceivableGL").toString());
			list.add(data);
		}
		return list;
	}

}
