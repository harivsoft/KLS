package com.vosftcorp.kls.report.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.IMTLTLoanLedgerReportService;
import com.vsoftcorp.kls.report.dao.IMTLTLoanLedgerReportDAO;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.MTLTLoanLedgerReportData;
import com.vsoftcorp.kls.report.service.helper.MTLTLoanLedgerReportHelper;
import com.vsoftcorp.time.Date;

public class MTLTLoanLedgerReportService implements IMTLTLoanLedgerReportService {
	
	private static final Logger logger = Logger.getLogger(MTLTLoanLedgerReportService.class);
	
	@Override
	public List<MTLTLoanLedgerReportData> getMTLTLoanLedgerReport(
			Long locNo, Date fromDate, Date toDate, Long customerId, Integer pacsId) {
		
		MTLTLoanLedgerReportData mtltData=new MTLTLoanLedgerReportData();

		logger.info("Start: Calling MtLtLoanLedgerReportDAO inside getMTLTLoanLedgerReport()");
		IMTLTLoanLedgerReportDAO dao= KLSReportDataAccessFactory.getMTLTLoanLedgerReportDAO();
		
		List<BigInteger> lineOfCreditIdList=null;
		List<MTLTLoanLedgerReportData> curtransList =null;
		List<MTLTLoanLedgerReportData> transList =null;
		List<MTLTLoanLedgerReportData> mtltLoanLedgerReportDataList = new ArrayList<MTLTLoanLedgerReportData>();

		
		if(locNo.intValue()==0)
		{
			lineOfCreditIdList = dao.getLineOfCreditListByCustId(customerId);
			
			for (BigInteger locId : lineOfCreditIdList) 
			{
				List<Map> accStmtTransactionHistoryList =dao.getAccountStatementReportFromTransactionHistory(locId.longValue(),fromDate, toDate, customerId, pacsId);
				
				logger.info("Dao Trans List size for loc "+locId+" Size "+accStmtTransactionHistoryList.size());
				transList=MTLTLoanLedgerReportHelper.getMTLTLoanLedgerReportDataList(accStmtTransactionHistoryList);
				logger.info("Helper Trans List size for loc "+locId+" Size "+transList.size());

				mtltLoanLedgerReportDataList.addAll(transList);
				
				List<Map> accStmtCurrentTransactionList =dao.getAccountStatementReport(locId.longValue(), fromDate, toDate, customerId, pacsId);
				logger.info("DAO curtransList size for loc "+locId+" Size "+accStmtCurrentTransactionList.size());

				curtransList=MTLTLoanLedgerReportHelper.getMTLTLoanLedgerReportDataList(accStmtCurrentTransactionList);
				logger.info("Helper curtransList size for loc "+locId+" Size "+curtransList.size());

				mtltLoanLedgerReportDataList.addAll(curtransList);
				
				logger.info("Final size for loc "+locId+" Size "+mtltLoanLedgerReportDataList.size());

			}
			
			logger.info("Final size for loc Size "+mtltLoanLedgerReportDataList.size());

		}
		else
		{
			List<Map> accStmtTransactionHistoryList =dao.getAccountStatementReportFromTransactionHistory(locNo,fromDate, toDate, customerId, pacsId);
			transList=MTLTLoanLedgerReportHelper.getMTLTLoanLedgerReportDataList(accStmtTransactionHistoryList);
			mtltLoanLedgerReportDataList.addAll(transList);
			List<Map> accStmtCurrentTransactionList =dao.getAccountStatementReport(locNo, fromDate, toDate, customerId, pacsId);
			curtransList=MTLTLoanLedgerReportHelper.getMTLTLoanLedgerReportDataList(accStmtCurrentTransactionList);
			
			mtltLoanLedgerReportDataList.addAll(curtransList);
		}
	
		return mtltLoanLedgerReportDataList;
		
	}
	

}
