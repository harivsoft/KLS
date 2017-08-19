package com.vsoftcorp.kls.report.service.helper;

import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.kls.report.service.data.SBAccountAssignedData;
import com.vsoftcorp.kls.valuetypes.SBAccountStatus;

public class AccountAssignHelper {

	public static List<SBAccountAssignedData> getAccountInfoReportList(
			List<Object[]> accInfoList, Integer pacsId, String Status) {

		List<SBAccountAssignedData> accDataList = new ArrayList<SBAccountAssignedData>();

		try {
			for (Object[] object : accInfoList) {
				SBAccountAssignedData data = new SBAccountAssignedData();
				String memberNo = (String) object[1];

				data.setMemberNo(memberNo);

				String farmerName = (String) object[0];

				data.setFarmerName(farmerName);

				String accountNo = (String) object[2];

				data.setAccountNo(accountNo);

				String sbNo = (String) object[3];

				data.setSbNo(sbNo);
				if(object[4] !=null){
					String status = SBAccountStatus.getType((Integer)object[4]).getName();
					data.setStatus(status);
				}
				String remarks=(String)object[5];
				data.setRemarks(remarks);
				accDataList.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return accDataList;

	}

}
