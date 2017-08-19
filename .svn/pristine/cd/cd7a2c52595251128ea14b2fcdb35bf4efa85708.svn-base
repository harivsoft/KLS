package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.report.dao.IShareAccountLedgerReportDAO;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.ShareAccountLedgerReportData;
import com.vsoftcorp.time.Date;

public class ShareAccountLedgerReportHelper {

	public static List<ShareAccountLedgerReportData> getShareAccountLedgerReportDataList(
			List<Object[]> shareAccountList, Date fromDate) {
		List<ShareAccountLedgerReportData> shareAccountDataList = new ArrayList<ShareAccountLedgerReportData>();
		ShareAccountLedgerReportData data = null;
		try {

			BigDecimal totVal = BigDecimal.ZERO;
			String custOld = null;
			int totShares = 0;
			for (Object[] object : shareAccountList) {
				String customerId = (String) object[0];
				
				BigDecimal valOfSharesReedemed = BigDecimal.ZERO;
				BigDecimal valOfSharesAltd = BigDecimal.ZERO;
				BigDecimal shareAllowted = BigDecimal.ZERO;
				BigDecimal sharesRedeemed = BigDecimal.ZERO;
				Object[] openingBal = null;
				IShareAccountLedgerReportDAO sDao = KLSReportDataAccessFactory.getShareAccountLedgerReportDAO();
				data = new ShareAccountLedgerReportData();
				Integer shareAlInt = new Integer(0);
				BigDecimal openingShare = BigDecimal.ZERO;
				int shareREInt = new Integer(0);
				Object[] objectVal = sDao.getAllottedNoOfSharesAndValueByTransactionId((BigDecimal) object[11],
						new BigDecimal("1"), new BigDecimal("2"));

				if (objectVal != null) {
					valOfSharesAltd = (BigDecimal) objectVal[1];
					shareAllowted = (BigDecimal) objectVal[0];
					data.setNoOfSharesAllotted(shareAllowted.intValue());
					data.setValueOfSharesAllotted(valOfSharesAltd.setScale(2));
				}
				Object[] objectVal1 = sDao.getRedeemedNoOfSharesAndValueByTransactionId((BigDecimal) object[11],
						new BigDecimal("4"), new BigDecimal("3"));

				if (objectVal1 != null) {
					valOfSharesReedemed = (BigDecimal) objectVal1[1];
					sharesRedeemed = (BigDecimal) objectVal1[0];
					data.setNoOfSharesRedeemed(sharesRedeemed.intValue());
					data.setValueOfSharesRedeemed(valOfSharesReedemed.setScale(2));
				}
				shareREInt = sharesRedeemed.intValue();

				shareAlInt = shareAllowted.intValue();

				if (!customerId.equals(custOld)) {
					totVal = BigDecimal.ZERO;
					totShares = 0;
					openingBal = sDao.getOpeningBal(fromDate, customerId);

					openingShare = (BigDecimal) openingBal[0];
					if (openingShare != null)
						totShares = totShares + (shareAlInt - (shareREInt)) + openingShare.intValue();
					
					else
						totShares = totShares + (shareAlInt - (shareREInt));
						data.setNoOfBalanceShares(totShares);
					BigDecimal openBal = (BigDecimal) openingBal[1];
					if (openBal != null)
						totVal = totVal.add(valOfSharesReedemed.multiply(new BigDecimal("-1")).add(valOfSharesAltd))
								.add(openBal);
					else
						totVal = totVal.add(valOfSharesReedemed.multiply(new BigDecimal("-1")).add(valOfSharesAltd));
					data.setValueOfBalanceShares(totVal.setScale(2));
				} else {
					// if(openingShare == null)
					totShares = totShares + (shareAlInt - (shareREInt));
					data.setNoOfBalanceShares(totShares);

					// if(openingBal == null)
					totVal = totVal.add(valOfSharesReedemed.multiply(new BigDecimal("-1")).add(valOfSharesAltd));
					// System.out.println("cc afetr==" + cc);
					data.setValueOfBalanceShares(totVal.setScale(2));
				}

				Integer pacsId = (Integer) object[1];
				data.setPacsId(pacsId);
				IPacsDAO pDao = KLSDataAccessFactory.getPacsDAO();
				Pacs pacs = pDao.getPacs(pacsId);
				data.setPacName(pacs.getName());
				custOld = (String) object[0];

				BigDecimal productId = (BigDecimal) object[2];
				data.setProductId(productId.intValue());
				String productName = (String) object[3];
				data.setProductName(productName);

				// PersonData customer =
				// RestClientUtil.getCustomerById(customerId.longValue());
				// if (customer != null)
				data.setMemberNo(customerId);
				data.setMemberName((String) object[13]);
				java.sql.Date tranDate = (java.sql.Date) object[5];
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String text = df.format(tranDate);
				data.setTransDate(text);
				data.setShareCertificateNo((String) object[6]);
				String from = (String) object[7];
				String to = (String) object[8];
				data.setDistNumbersOfShares(from + "-" + to);
				data.setTrasactionType((String) object[9]);
				data.setPerticulars((String) object[10]);

				/*
				 * Object[] openingBal =
				 * sDao.getOpeningBal(fromDate,partyId.toString());
				 * 
				 * BigDecimal openingShare = (BigDecimal)openingBal[0];
				 * System.out.println("openingShare=="+openingShare);
				 * System.out.println("shareAlInt=="+shareAlInt);
				 * System.out.println("shareREInt=="+shareREInt);
				 * if(openingShare!=null) totShares =
				 * totShares+(shareAlInt-(shareREInt))+openingShare.intValue();
				 */
				// else

				shareAccountDataList.add(data);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return shareAccountDataList;
	}
}