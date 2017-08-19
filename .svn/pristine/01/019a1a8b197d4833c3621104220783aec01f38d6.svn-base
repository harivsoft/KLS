package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vsoftcorp.kls.business.entities.Branch;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.Scheme;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.report.service.data.DCBReportData;
import com.vsoftcorp.kls.service.util.RestClientUtil;

/**
 * @author a1605
 */
public class DCBReportHelper {

	public static List<DCBReportData> getDCBReportData(List<Map> dbDataList) {

		List<DCBReportData> dcbList = new ArrayList<DCBReportData>();

		DCBReportData data = null;

		for (Map map : dbDataList) {
			data = new DCBReportData();
			Branch branch = (Branch) map.get("branch");
			data.setBranchName(branch.getName());
			data.setBranchId(branch.getId().toString());

			Pacs pacs = (Pacs) map.get("pacs");
			data.setPacName(pacs.getName());
			data.setPacsId(pacs.getId().toString());
			Scheme scheme = (Scheme) map.get("scheme");
			data.setSchemeCode(scheme.getId().toString());
			data.setSchemeName(scheme.getName());
			// Customer customer = (Customer) map.get("customer");
			Long customerId = (Long) map.get("customerId");
			PersonData personData = RestClientUtil.getCustomerById(customerId);
			if (personData != null) {
				data.setMemberName(personData.getName());
				data.setMemberNumber(personData.getMemberNumber());
			}
			data.setAccountNumber((String) map.get("accNo"));
			BigDecimal interestAccured=(BigDecimal)map.get("interestAccrued");
			data.setInterestAccrued(interestAccured);
            data.setAsOnWhen((boolean)map.get("asOnWhen"));
			dcbList.add(data);

		}

		List<DCBReportData> finalDataList = new ArrayList<DCBReportData>();

		for (DCBReportData dcbReportData : dcbList) {
			Boolean flag = true;
			for (DCBReportData dcbReportData1 : finalDataList) {
				if (dcbReportData.toString().equals(dcbReportData1.toString()))
					flag = false;
			}
			if (flag)
				finalDataList.add(dcbReportData);
		}

		return finalDataList;

	}
}
