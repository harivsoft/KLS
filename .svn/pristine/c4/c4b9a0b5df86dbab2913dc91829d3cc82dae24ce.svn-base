package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.kls.report.service.data.MTLTLoanLedgerReportData;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;

public class MTLTLoanLedgerReportHelper {

	private static final Logger logger = Logger.getLogger(MTLTLoanLedgerReportHelper.class);

	public static List<MTLTLoanLedgerReportData> getMTLTLoanLedgerReportDataList(List<Map> loanLedgerReportList) {

		logger.info("Start: inside method getMTLTLoanLedgerReportDataList()");

		List<MTLTLoanLedgerReportData> mtltLedgerReportDataList = new ArrayList<MTLTLoanLedgerReportData>();

		MTLTLoanLedgerReportData data = null;

		try {

			// To Get opening balance
			data = new MTLTLoanLedgerReportData();
			for (Map map : loanLedgerReportList) {
				data = new MTLTLoanLedgerReportData();

				Map firstRecord = loanLedgerReportList.get(0);
				AccountingMoney openingBalance = (AccountingMoney) firstRecord.get("openingBalance");
				data.setOpeningBalance(openingBalance.getMoney().getAmount().setScale(2).toString());

				String splittedDateArray[] = map.get("businessDate").toString().split("-");
				String businessDate = splittedDateArray[2] + "/" + splittedDateArray[1] + "/" + splittedDateArray[0];

				BigDecimal zeroDecimal = null;

				TransactionCode tranCode = (TransactionCode) map.get("transactionCode");
				AccountingMoney balance = (AccountingMoney) map.get("openingBalance");

				AccountingMoney transactionAmount = (AccountingMoney) map.get("transactionAmount");

				if (1 == (Integer) map.get("crDr"))
					balance = balance.add(transactionAmount);
				else
					balance = balance.subtract(transactionAmount);

				if (TransactionCode.PRINCIPAL_BAL.equals(tranCode) || TransactionCode.MARGINAL.equals(tranCode)) {
					data.setLocNo(Long.parseLong(map.get("lineOfCreditId").toString()));
					data.setBusinessDate(businessDate);

					logger.info("if " + map.get("lineOfCreditId").toString());

					data.setInterestBalance("");
					data.setInterestCredit(zeroDecimal);
					data.setInterestDebit(zeroDecimal);
					data.setInterestParticulars("");

					data.setChargesBalance("");
					data.setChargesCredit(zeroDecimal);
					data.setChargesDebit(zeroDecimal);
					data.setChargesParticulars("");

						data.setPrincipalBalance(balance.getMoney().getAmount().setScale(2).toString());
					if (1 == (Integer) map.get("crDr"))
						data.setPrincipalCredit(transactionAmount.getMoney().getAmount().setScale(2));
					else
						data.setPrincipalDebit(transactionAmount.getMoney().getAmount().setScale(2));

					data.setPrincipalParticulars((String) map.get("remarks"));

					mtltLedgerReportDataList.add(data);

				} else if (TransactionCode.INTEREST.equals(tranCode) || TransactionCode.INTEREST_RECEIVABLE.equals(tranCode)
						|| TransactionCode.PENAL_INTEREST.equals(tranCode) || TransactionCode.PENAL_INTEREST_RECEIVABLE.equals(tranCode)) {

					data.setLocNo(Long.parseLong(map.get("lineOfCreditId").toString()));
					data.setBusinessDate(businessDate);
					data.setPrincipalBalance("");
					data.setPrincipalCredit(zeroDecimal);
					data.setPrincipalDebit(zeroDecimal);
					data.setPrincipalParticulars("");

					data.setChargesBalance("");
					data.setChargesCredit(zeroDecimal);
					data.setChargesDebit(zeroDecimal);
					data.setChargesParticulars("");

						data.setInterestBalance(balance.getMoney().getAmount().setScale(2).toString());

					if (1 == (Integer) map.get("crDr"))
						data.setInterestCredit(transactionAmount.getMoney().getAmount().setScale(2));
					else
						data.setInterestDebit(transactionAmount.getMoney().getAmount().setScale(2));

					data.setInterestParticulars((String) map.get("remarks"));

					mtltLedgerReportDataList.add(data);

				} else if (TransactionCode.CHARGES.equals(tranCode) || TransactionCode.SHARE_TRANSFER.equals(tranCode) || TransactionCode.PROCESSING_FEE.equals(tranCode)
						|| TransactionCode.INSURANCE_DEDUCTION.equals(tranCode) || TransactionCode.CHARGES_RECEIVABLE.equals(tranCode)) {

					data.setLocNo(Long.parseLong(map.get("lineOfCreditId").toString()));

					data.setBusinessDate(businessDate);
					
					 if (TransactionCode.SHARE_TRANSFER.equals(tranCode) || TransactionCode.PROCESSING_FEE.equals(tranCode)
								|| TransactionCode.INSURANCE_DEDUCTION.equals(tranCode)) {
					
					data.setPrincipalBalance(balance.getMoney().getAmount().setScale(2).toString());
					 }else{
						 data.setPrincipalBalance("");
					 }
					
					data.setPrincipalCredit(zeroDecimal);
					data.setPrincipalDebit(zeroDecimal);
					data.setPrincipalParticulars("");

					data.setInterestBalance("");
					data.setInterestCredit(zeroDecimal);
					data.setInterestDebit(zeroDecimal);
					data.setInterestParticulars("");

						data.setChargesBalance(balance.getMoney().getAmount().setScale(2).toString());

					if (1 == (Integer) map.get("crDr"))
						data.setChargesCredit(transactionAmount.getMoney().getAmount().setScale(2));
					else
						data.setChargesDebit(transactionAmount.getMoney().getAmount().setScale(2));

					data.setChargesParticulars((String) map.get("remarks"));

					mtltLedgerReportDataList.add(data);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mtltLedgerReportDataList;

	}

}
