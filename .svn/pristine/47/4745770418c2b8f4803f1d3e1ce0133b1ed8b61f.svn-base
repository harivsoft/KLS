package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.impl.BorrowingAccountLedgerReportService;
import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.kls.report.service.data.BorrowingAccountLedgerReportData;

public class BorrowingAccountLedgerReportHelper {
	private static final Logger logger = Logger
			.getLogger(BorrowingAccountLedgerReportHelper.class);
	public static List<BorrowingAccountLedgerReportData> getBorrowingAccountLedgerReportDataList(List<Object[]> getBorrowingAccountList) {

		List<BorrowingAccountLedgerReportData> borrowingAccountLedgerReportDataList1 = new ArrayList<BorrowingAccountLedgerReportData>();

		BorrowingAccountLedgerReportData data = null;

		try {
			Integer i = 1;
			for (Object[] object : getBorrowingAccountList) {

				data = new BorrowingAccountLedgerReportData();
				data.setSno(i);

				Date date = (Date) object[0];
				data.setDate(DateFormatUtils.format(date, "dd/MM/yyyy"));
				//data.setDate(date.toString());

				String remarks = (String) object[1];
				data.setParticular(remarks);

				BigDecimal debit = (BigDecimal) object[3];
				if (debit != null) {
					data.setDebit(debit.setScale(2));
				} else {
					debit = BigDecimal.ZERO.setScale(2);
				}

				BigDecimal credit = (BigDecimal) object[4];
				if (credit != null) {
					data.setCredit(credit.setScale(2));
				} else {
					credit = BigDecimal.ZERO.setScale(2);
				}

				BigDecimal intDebit = (BigDecimal) object[5];
				if (intDebit != null) {
					data.setInterestDue(intDebit.setScale(2));
				} else {
					intDebit = BigDecimal.ZERO.setScale(2);
				}
				/*	IPacsDAO pDao = KLSDataAccessFactory.getPacsDAO();
					Pacs pacs = pDao.getPacs((Integer) object[12]);*/

				data.setBranchId((Integer) object[14]);
				data.setBranchName((String) object[15]);

				BigDecimal intCrebit = (BigDecimal) object[6];
				if (intCrebit != null) {
					data.setInterestReceived(intCrebit.setScale(2));
				} else {
					intCrebit = BigDecimal.ZERO.setScale(2);
				}

				BigDecimal balance = (((BigDecimal) object[2]).subtract(debit)).add(credit);
				if (balance != null) {
					data.setBalance(balance.setScale(2));
				} else {
					balance = BigDecimal.ZERO.setScale(2);
				}

				String accNo = (String) object[11];
				data.setAccountId(accNo);
				data.setPurpose((String) object[10]);

				if ((!intDebit.equals(BigDecimal.ZERO)) || (!intCrebit.equals(BigDecimal.ZERO))) {
					data.setBalance(BigDecimal.ZERO.setScale(2));
				}
				data.setProduct((String) object[8]);
				data.setProductId((Integer) object[7]);
				data.setPacs_id((Integer) object[12]);
				data.setPacs_name((String) object[13]);
				 if(object[17]!=null)
		                data.setLocId(object[17].toString());
		                if(object[18]!=null){
		                	BigDecimal openigBal = new BigDecimal (object[18].toString()).setScale(2);
		                	data.setOpeningBal(openigBal);
		                }
				borrowingAccountLedgerReportDataList1.add(data);

				i = i + 1;
//				System.out.println("Account Number :" + data.getAccountId());
			}
			System.out.println("List Size :" + borrowingAccountLedgerReportDataList1.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return borrowingAccountLedgerReportDataList1;
	}
	
	public static List<BorrowingAccountLedgerReportData> getLendingAccountLedgerReportDataList(List<Object[]> getBorrowingAccountList) {

		List<BorrowingAccountLedgerReportData> borrowingAccountLedgerReportDataList = new ArrayList<BorrowingAccountLedgerReportData>();

		BorrowingAccountLedgerReportData data = null;

		try {
			Integer i = 1;
			for (Object[] object : getBorrowingAccountList) {

				data = new BorrowingAccountLedgerReportData();
				data.setSno(i);

				Date date = (Date) object[0];
				
				//if(data.getDebit() != null || data.getCredit()!=null)
				data.setDate(DateFormatUtils.format(date, "dd/MM/yyyy"));
				
				
				//data.setDate(date.toString());

				//String remarks = (String) object[1];
				//data.setParticular(remarks);

				BigDecimal debit = (BigDecimal) object[4];
				if (debit != null) {
					data.setDebit(debit.setScale(2));
				} else {
					debit = BigDecimal.ZERO.setScale(2);
				}

				BigDecimal credit = (BigDecimal) object[3];
				if (credit != null) {
					data.setCredit(credit.setScale(2));
				} else {
					credit = BigDecimal.ZERO.setScale(2);
				}

				BigDecimal intDebit = (BigDecimal) object[5];
				if (intDebit != null) {
					data.setInterestDue(intDebit.setScale(2));
				} else {
					intDebit = BigDecimal.ZERO.setScale(2);
				}
				/*	IPacsDAO pDao = KLSDataAccessFactory.getPacsDAO();
					Pacs pacs = pDao.getPacs((Integer) object[12]);*/

				data.setBranchId((Integer) object[14]);
				data.setBranchName((String) object[15]);

				BigDecimal intCrebit = (BigDecimal) object[6];
				if (intCrebit != null) {
					data.setInterestReceived(intCrebit.setScale(2));
				} else {
					intCrebit = BigDecimal.ZERO.setScale(2);
				}

				BigDecimal balance = (((BigDecimal) object[2]).subtract(credit)).add(debit);
				if (balance != null) {
					data.setBalance(balance.setScale(2));
				} else {
					balance = BigDecimal.ZERO.setScale(2);
				}

				String accNo = (String) object[11];
				data.setAccountId(accNo);
				data.setPurpose((String) object[10]);

				if ((!intDebit.equals(BigDecimal.ZERO)) || (!intCrebit.equals(BigDecimal.ZERO))) {
					data.setBalance(BigDecimal.ZERO.setScale(2));
				}
				
				data.setProduct((String) object[8]);
				data.setProductId((Integer) object[7]);
				data.setPacs_id((Integer) object[12]);
				data.setPacs_name((String) object[13]);
				logger.info("testing value==="+object[16]);
                if(object[16].equals(1))
                	data.setParticular("For Lending Recovery Principal Credit");
                if(object[16].equals(8))
                	data.setParticular("Lending Interest Accrued Posted Received.");
                if(object[16].equals(7))
                	data.setParticular("Lending Interest Accrued posted Receivable.");
                if(object[16].equals(2))
                	data.setParticular("For Lending Recovery Interest Credit.");
                if(object[16].equals(9))
                	data.setParticular("Lending Penal Interest Posting Receivable");
                if(object[16].equals(10))
                	data.setParticular("Lending Penal Interest Posting Received");
                if(object[16].equals(3))
                	data.setParticular("For Lending Recovery Penal Interest Credit.");
                if(object[17]!=null)
                data.setLocId(object[17].toString());
                if(object[18]!=null){
                	BigDecimal openigBal = new BigDecimal (object[18].toString()).setScale(2);
                	data.setOpeningBal(openigBal);
                }
               
                
				borrowingAccountLedgerReportDataList.add(data);
				
				i = i + 1;
//				System.out.println("Account Number :" + data.getAccountId());
			}
			System.out.println("List Size :" + borrowingAccountLedgerReportDataList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return borrowingAccountLedgerReportDataList;
	}

}
