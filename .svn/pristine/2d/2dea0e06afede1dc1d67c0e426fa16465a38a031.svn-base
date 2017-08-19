package com.vsoftcorp.kls.service.loan.impl;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.ILoanInterestCalculationDAO;
import com.vsoftcorp.kls.dataaccess.transaction.ICurrentTransactionDAO;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.LoanInterestPostingServiceHelper;
import com.vsoftcorp.kls.service.loan.ILoanInterestService;
import com.vsoftcorp.kls.valuetypes.interest.InterestCalculationBasis;
import com.vsoftcorp.time.Date;

public class LoanInterestService implements ILoanInterestService {

	private static final Logger logger = Logger.getLogger(LoanInterestService.class);

	@Override
	public void calculateInterest(Date theBusinessDate,List<Integer> pacsList) {

		logger.info("Start: calling Loan Interest Calculation DAO in calculateInterest()"
				+ theBusinessDate.toString());
		try {
			ILoanInterestCalculationDAO dao = KLSDataAccessFactory.getLoanInterestCalculationDAO();
			dao.calculateInterest(theBusinessDate, InterestCalculationBasis.Daily,pacsList);
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Error in LoanInterestCalculationService calculateInterest()");
			throw new KlsRuntimeException("Interest not calculated, some  problem ouccured.",
					exception.getCause());
		}
		logger.info("End: in LoanInterestCalculationService  calculateInterest()");
	}

	@Override
	public void postInterest(Date theBusinessDate, String eventType,List<Integer> pacsIdsList) {
		try {
			List locList = KLSDataAccessFactory.getLoanInterestPostingDAO().fetchLocs(
					theBusinessDate,eventType,pacsIdsList);
			
			// Build Transactions for interest posting
			List<CurrentTransaction> intPostTransactionList = LoanInterestPostingServiceHelper
					.getCurrentTransaction(locList, theBusinessDate);

			ICurrentTransactionDAO dao = KLSDataAccessFactory.getCurrentTransactionDAO();
			
			dao.saveCurrentTransaction(intPostTransactionList,
					Collections.<String, List<Object>> emptyMap());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Error in posting interest");
			throw new KlsRuntimeException("Interest not Posted, some  problem ouccured.",
					exception.getCause());
		}
		logger.info("completed Loan posting");

	}


}
