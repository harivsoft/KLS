package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;

import com.vsoftcorp.kls.report.service.data.BatchWiseVoucherPrintData;

public class BatchWiseVoucherPrintReportHelper {

	public static List<BatchWiseVoucherPrintData> getBatchWiseVoucherPrintDataList(List<Object[]> batchWiseVoucherReportList, String toDate) {
		List<BatchWiseVoucherPrintData> batchWiseVoucherPrintDataList = new ArrayList<BatchWiseVoucherPrintData>();

		BatchWiseVoucherPrintData data = null;
		try {
			Integer i = 1;
			for (Object[] obj : batchWiseVoucherReportList) {
				data = new BatchWiseVoucherPrintData();

				data.setSno(i);

				String transType = (String) obj[10];
				if (transType.equalsIgnoreCase("D")) {
					data.setTransType("Disbursement");
				} else {
					data.setTransType("Recovery");
				}

				String remarks = (String) obj[3];
				data.setParticular(remarks);

				Date date = (Date) obj[1];
				data.setDate(DateFormatUtils.format(date, "dd/MM/yyyy"));
				//data.setDate(date.toString());

				BigDecimal debitAmount = (BigDecimal) obj[4];
				if (debitAmount != null) {
					data.setDebitAmount(debitAmount.setScale(2));
				} else {
					debitAmount = BigDecimal.ZERO.setScale(2);
				}

				BigDecimal creditAmount = (BigDecimal) obj[5];
				if (creditAmount != null) {
					data.setCreditAmount(creditAmount.setScale(2));
				} else {
					creditAmount = BigDecimal.ZERO.setScale(2);
				}

				BigDecimal balance = (BigDecimal) obj[6];
				if (balance != null) {
					data.setBalance(balance.setScale(2).abs());
				} else {
					balance = BigDecimal.ZERO.setScale(2);
				}
				String vouchrNumber = (String) obj[8];
				System.out.println("vouchrNumber : " + vouchrNumber);
				if (vouchrNumber != null)
					data.setVoucherNumber(vouchrNumber);
				batchWiseVoucherPrintDataList.add(data);
				i = i + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return batchWiseVoucherPrintDataList;
	}

}
