package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.kls.report.dao.IShareAccountBalancingReportDAO;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.ShareAccountBalancingReportData;
import com.vsoftcorp.time.Date;

public class ShareAccountBalancingReportHelper {

	public static List<ShareAccountBalancingReportData> getShareAccountBalancingReportDataList(
			List<Object[]> shareAccountList, Date asOnDate, Integer pacsId, Integer productId, String memberNo) {
		List<ShareAccountBalancingReportData> shareAccountDataList = new ArrayList<ShareAccountBalancingReportData>();
		ShareAccountBalancingReportData data = null;
		try {
			for (Object[] object : shareAccountList) {
				data = new ShareAccountBalancingReportData();

				BigDecimal noOfShares = (BigDecimal) object[1];
				BigDecimal shareAccountId = (BigDecimal) object[0];
				data.setNoOfShares(noOfShares.intValue());
				IShareAccountBalancingReportDAO sDao = KLSReportDataAccessFactory.getShareAccountBalancingReportDAO();
				Object[] obj = sDao.getShareAccountDetails(shareAccountId, pacsId, productId, memberNo);
				if (obj != null) {
					String memberNumber = (String) obj[1];
					data.setMemberNo(memberNumber);
					data.setMemberName((String) obj[0]);
					data.setShareAccountNumber((String) obj[2]);

					data.setShareProductName((String) obj[3]);
					BigDecimal prodId = (BigDecimal) obj[4];
					data.setShareProductId(prodId.intValue());

					BigDecimal faceVal = (BigDecimal) obj[5];
					System.out.println("faceVal==" + faceVal);
					System.out.println("noOfShares===" + noOfShares);
					BigDecimal shareVal = faceVal.multiply(noOfShares);
					System.out.println("shareVal===" + shareVal);
					data.setValueOfShares(shareVal);
				}
				shareAccountDataList.add(data);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return shareAccountDataList;
	}
}
