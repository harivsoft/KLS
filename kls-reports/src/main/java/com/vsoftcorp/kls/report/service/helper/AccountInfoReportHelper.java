package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.kls.report.service.data.AccountInfoData;

public class AccountInfoReportHelper {

	public static List<AccountInfoData> getAccountInfoReportDataList(List<Object[]> accInfoList, String businessType) {
		List<AccountInfoData> accDataList = new ArrayList<AccountInfoData>();
		AccountInfoData data = null;
		try {
			for (Object[] object : accInfoList) {
				data = new AccountInfoData();
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				if ((Date) object[0] != null) {
					String openDate = df.format((Date) object[0]);
					data.setDateOfOpening(openDate);
				}
				if ((Date) object[1] != null) {
					String closeDate = df.format((Date) object[1]);
					data.setDateOfClosure(closeDate);
				}
				String memberNumber = (String) object[4];
				data.setMemberNumber(memberNumber);
				data.setMemberName((String) object[3]);
				data.setAccountNumber((String) object[2]);

				data.setProductName((String) object[5]);

				if (businessType.equalsIgnoreCase("S")) {
					BigDecimal prodId = (BigDecimal) object[6];
					data.setProductId(prodId.intValue());
				} else {
					data.setProductId((Integer) object[6]);
				}
				accDataList.add(data);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return accDataList;
	}
}
