package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.kls.report.service.data.AccountStatementReportData;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;

public class AccountStatementReportHelper {

	private static final Logger logger = Logger.getLogger(AccountStatementReportHelper.class);

	public static List<AccountStatementReportData> getAccountStatementReportDataList(List<Map> accountStatementReportList) {

		logger.info("Start: inside method getAccountStatementReportDataList()");

		List<AccountStatementReportData> accountStatementReportDataList = new ArrayList<AccountStatementReportData>();
		try {

			// To Get opening balance
			AccountingMoney openingBalance =AccountingMoney.ZERO;
			AccountingMoney shareInsurance =AccountingMoney.ZERO;
			AccountStatementReportData data = new AccountStatementReportData();
			if (!accountStatementReportList.isEmpty()) {
				Map firstRecord = accountStatementReportList.get(0);
				openingBalance = (AccountingMoney) firstRecord.get("openingBalance");
					data.setPrincipalBalance(openingBalance.getMoney().getAmount().setScale(2).toString());
				accountStatementReportDataList.add(data);
			}

			for (Map map : accountStatementReportList) {
				data = new AccountStatementReportData();
				data.setLocNo(map.get("lineOfCreditId").toString());

				String cropSeasonYr = map.get("cropName").toString() + "/" + map.get("seasonName").toString();
				data.setCropSeasonYear(cropSeasonYr);
				String splittedDateArray[] = map.get("businessDate").toString().split("-");
				String businessDate = splittedDateArray[2] + "/" + splittedDateArray[1] + "/" + splittedDateArray[0];
				data.setBusinessDate(businessDate);
				BigDecimal zeroDecimal = null;

				data.setPrincipalBalance("");
				data.setPrincipalCredit(zeroDecimal);
				data.setPrincipalDebit(zeroDecimal);
				data.setPrincipalParticulars("");

				data.setInterestBalance("");
				data.setInterestCredit(zeroDecimal);
				data.setInterestDebit(zeroDecimal);
				data.setInterestParticulars("");

				data.setChargesBalance("");
				data.setChargesCredit(zeroDecimal);
				data.setChargesDebit(zeroDecimal);
				data.setChargesParticulars("");

				TransactionCode tranCode = (TransactionCode) map.get("transactionCode");

				AccountingMoney balance = (AccountingMoney) map.get("openingBalance");
				AccountingMoney transactionAmount = (AccountingMoney) map.get("transactionAmount");
				if (1 == (Integer) map.get("crDr"))
					balance = balance.add(transactionAmount);
				else
					balance = balance.subtract(transactionAmount);

				if (TransactionCode.PRINCIPAL_BAL.equals(tranCode)) {
					if(shareInsurance!=AccountingMoney.ZERO){
						AccountingMoney totalprinciple=shareInsurance.subtract(balance);
					data.setPrincipalBalance(totalprinciple.getMoney().getAmount().setScale(2).toString());
					shareInsurance=AccountingMoney.ZERO;
					}
					else
						data.setPrincipalBalance(balance.getMoney().getAmount().setScale(2).toString());
				
					if (1 == (Integer) map.get("crDr"))
						data.setPrincipalCredit(transactionAmount.getMoney().getAmount().setScale(2));
					else
						data.setPrincipalDebit(transactionAmount.getMoney().getAmount().setScale(2));

					data.setPrincipalParticulars((String) map.get("remarks"));

				} else if (TransactionCode.INTEREST.equals(tranCode) || TransactionCode.INTEREST_RECEIVABLE.equals(tranCode)
						|| TransactionCode.PENAL_INTEREST.equals(tranCode) || TransactionCode.PENAL_INTEREST_RECEIVABLE.equals(tranCode)) {
					data.setInterestBalance(balance.getMoney().getAmount().setScale(2).toString());

					if (1 == (Integer) map.get("crDr"))
						data.setInterestCredit(transactionAmount.getMoney().getAmount().setScale(2));
					else
						data.setInterestDebit(transactionAmount.getMoney().getAmount().setScale(2));

					data.setInterestParticulars((String) map.get("remarks"));

				} else if (TransactionCode.SHARE_TRANSFER.equals(tranCode) || TransactionCode.INSURANCE_DEDUCTION.equals(tranCode)) {
					data.setChargesBalance(balance.getMoney().getAmount().setScale(2).toString());
					

					if (1 == (Integer) map.get("crDr"))
						data.setChargesCredit(transactionAmount.getMoney().getAmount().setScale(2));
					else
						data.setChargesDebit(transactionAmount.getMoney().getAmount().setScale(2));
					shareInsurance=shareInsurance.add(transactionAmount);
					data.setPrincipalBalance(shareInsurance.getMoney().getAmount().setScale(2).toString());
					logger.info("opening balnce in share:::"+data.getPrincipalBalance());

					data.setChargesParticulars((String) map.get("remarks"));
				} else if (TransactionCode.KIND.equals(tranCode)) {
					data.setKindBalance(balance.getMoney().getAmount().setScale(2).toString());

					if (1 == (Integer) map.get("crDr"))
						data.setKindCredit(transactionAmount.getMoney().getAmount().setScale(2));
					else
						data.setKindDebit(transactionAmount.getMoney().getAmount().setScale(2));
					data.setPrincipalBalance(balance.getMoney().getAmount().setScale(2).toString());
					logger.info("opening balnce kind:::"+data.getPrincipalBalance());
					data.setKindParticulars((String) map.get("remarks"));
				}
				accountStatementReportDataList.add(data);
			}
		} catch (Exception e) {
			logger.error("Exception while retriving records for Account statement report");
			e.printStackTrace();
		}
		logger.info("End: inside method getAccountStatementReportDataList()");

		return accountStatementReportDataList;
	}
}
