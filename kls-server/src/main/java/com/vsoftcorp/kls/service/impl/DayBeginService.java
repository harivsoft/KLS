package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.BankParameter;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.data.BankParameterData;
import com.vsoftcorp.kls.data.BorrowingProductData;
import com.vsoftcorp.kls.data.BorrowingsAccountPropertyData;
import com.vsoftcorp.kls.data.GlData;
import com.vsoftcorp.kls.dataaccess.IDayBeginDao;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.transaction.ICurrentTransactionDAO;
import com.vsoftcorp.kls.dataaccess.transaction.ITransactionHistoryDAO;
import com.vsoftcorp.kls.service.IBorrowingProductService;
import com.vsoftcorp.kls.service.IDayBeginService;
import com.vsoftcorp.kls.service.account.IBorrowingAccountPropertyService;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.BankParameterHelper;
import com.vsoftcorp.kls.service.loan.ILoanInterestService;
import com.vsoftcorp.kls.service.transaction.IBorrowingTransactionService;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.account.AccountType;
import com.vsoftcorp.kls.valuetypes.loan.LoanType;
import com.vsoftcorp.kls.valuetypes.transaction.BorrowingTransactionMethod;
import com.vsoftcorp.time.Date;

public class DayBeginService implements IDayBeginService {
	private static final Logger logger = Logger.getLogger(DayBeginService.class);

	@Override
	public String updateDayBegin(BankParameterData bankParameterData, String eventType, Date nextBusinessDate, List<GlData> glDataList) {

		logger.info("Start : Calling daybegin dao in updateDayBegin() method.");
		IDayBeginDao dao = KLSDataAccessFactory.getDayBeginDAO();
		BankParameter master = BankParameterHelper.getBankParameter(bankParameterData);
		// a1565
		// IAccountWiseConsistencyService inconsistency = KLSServiceFactory
		// .getAccountWiseConsistencyService();
		String status = "";
		EntityManagerUtil.beginTransaction();
		try {
			logger.info("Day Status " + master.getBankProcessStatus().getValue());
			if (master.getBankProcessStatus().getValue() == 3) {
				Date currentDate = master.getBusinessDate();
				logger.info("Not Calling checkAccountWiseConsistency to Check Inconsistency");

				// *** Checking account wise inconsistency *** //
				// List<AccountWiseConsistency> inConsistencyList = new
				// ArrayList<AccountWiseConsistency>();
				// inConsistencyList =
				// inconsistency.checkAccountWiseConsistency(currentDate);
				// logger.info("Success*fully Called Inconsistency data ..." +
				// inConsistencyList);
				IPacsDAO pacsDAO = KLSDataAccessFactory.getPacsDAO();
				List<Pacs> pacsList = pacsDAO.getSycedPacs();
				List<Integer> pacsIdsList = getPacIdList(pacsList);
				ICurrentTransactionDAO cDao = KLSDataAccessFactory.getCurrentTransactionDAO();
				// List<CurrentTransaction> currTransLst =
				// cDao.getCurrentTransactionByStatus();
				ITransactionHistoryDAO trDao = KLSDataAccessFactory.getTransactionHistoryDAO();

				// *** Borrowing transactions ***//

				// For ST loans there can be 3 methods as handled below
				List<CurrentTransaction> currTransLst = cDao.getCurrentTransactionByStatus(LoanType.SHORT_TERM.getValue(),pacsIdsList);
				createBorrowingAccount(currTransLst);
				IBorrowingTransactionService bTransService = KLSServiceFactory.getBorrowingTransactionService();
				if (master.getBorrowingTransactionMethod().getValue().equals(BorrowingTransactionMethod.Grouping.getValue()))
					bTransService.saveBorrowingTransactionsGroupingMethod(currentDate, LoanType.SHORT_TERM,pacsIdsList);
				else if (master.getBorrowingTransactionMethod().getValue().equals(BorrowingTransactionMethod.OneToOneStright.getValue()))
					bTransService.saveBorrowingTransactionsOnetoOneStraight(currTransLst, currentDate, LoanType.SHORT_TERM,pacsIdsList);
				else if (master.getBorrowingTransactionMethod().getValue().equals(BorrowingTransactionMethod.OneToOneEarly.getValue()))
					bTransService.saveBorrowingTransactionsOnetoOneEarly(currTransLst, currentDate, LoanType.SHORT_TERM,pacsIdsList);

				// For MT, LT and LT loans one to one straight method is
				// followed
				currTransLst = cDao.getCurrentTransactionByStatus(LoanType.MEDIUM_TERM.getValue(),pacsIdsList);
				createBorrowingAccount(currTransLst);
				bTransService.saveBorrowingTransactionsOnetoOneStraight(currTransLst, currentDate, LoanType.MEDIUM_TERM,pacsIdsList);
				currTransLst = cDao.getCurrentTransactionByStatus(LoanType.LONG_TERM.getValue(),pacsIdsList);
				createBorrowingAccount(currTransLst);
				bTransService.saveBorrowingTransactionsOnetoOneStraight(currTransLst, currentDate, LoanType.LONG_TERM,pacsIdsList);

				// *** calculating interest ***//
				ILoanInterestService loanInterestService = KLSServiceFactory.getLoanInterestService();

				
				loanInterestService.calculateInterest(currentDate,pacsIdsList);

				// *** Posting Interest ***//
				loanInterestService.postInterest(currentDate, eventType,pacsIdsList);

				// *** Resetting vouchers *** //
				dao.resetVoucherNumbers(eventType,pacsIdsList);

				// *** Generating PACGL entries excel file ***//
				currTransLst = cDao.getCurrentTransactionByStatus(pacsIdsList);

				if (!currTransLst.isEmpty())
					 status = KLSServiceFactory.getPacsGLEntryService().extractPacsGLEntries(currTransLst,glDataList);
				//if(status != "")
					//return status;
				// *** Moving current transaction records to transaction_history
				// which are status=1 *** //
				if(status.equals("")) {
					cDao.moveCurrentTransactionRecordsToTransactionHistory(pacsIdsList);
				}

			}
			// if (master.getMaxDaysForInconsistency() <= 3) {
			// master.setBankProcessStatus(BankProcessStatus.DAY_END);
			// }


			if(status != "") {
				EntityManagerUtil.CommitOrRollBackTransaction(false);				
			}
			else {
				// *** updating day status *** //
				dao.updateDayBegin(master, nextBusinessDate);

				EntityManagerUtil.CommitOrRollBackTransaction(true);
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			EntityManagerUtil.CommitOrRollBackTransaction(false);
			excp.printStackTrace();
			logger.error("day activity data cannot be updated");
			throw excp;
			// throw new KlsRuntimeException("Day End cannot be done.",
			// excp.getCause());
		}
		
		logger.info("End : Calling daybegin dao in updateDayBegin() method.");
		return status;
	}
	
	public List<Integer> getPacIdList(List<Pacs> pacsList){
		
		List<Integer> pacsIdList = new ArrayList<Integer>();
		try {
			for(Pacs p :pacsList){
				pacsIdList.add(p.getId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pacsIdList;
	}
	public void createBorrowingAccount(List<CurrentTransaction> currTransLst){
		logger.info("testing borrowing account creation");
		Integer prdId=0;
		IBorrowingProductService service = KLSServiceFactory.getBorrowingProductService();
	    for(CurrentTransaction transaction:currTransLst){
		if(prdId==0 || (transaction.getLineOfCredit()!=null && prdId!=transaction.getLineOfCredit().getProduct().getId())){
		if(transaction.getLineOfCredit().getProduct().getBorrowingRequired().equals("Y") && transaction.getCrDr()==-1){
			LineOfCredit loc=transaction.getLineOfCredit();
			Integer borrowingPrdId=loc.getProduct().getBorrowingProductId();
			
			BorrowingProductData data = service.getProductById(borrowingPrdId);
		    BorrowingsAccountPropertyData borrowingsAccountPropertyData=new BorrowingsAccountPropertyData();
		    borrowingsAccountPropertyData.setBorrowingProductId(borrowingPrdId);
		    borrowingsAccountPropertyData.setBorrowingProductName(data.getProductName());
		    borrowingsAccountPropertyData.setBorrowingType(data.getBorrowingType());
		    borrowingsAccountPropertyData.setPacsId(transaction.getPacs().getId());
		    borrowingsAccountPropertyData.setBranchId(transaction.getPacs().getBranch().getId());
		    borrowingsAccountPropertyData.setInterestCategoryId(data.getInterestCategoryId());
		   // borrowingsAccountPropertyData.setAccountId(loc.getAccount().getId());
		   // borrowingsAccountPropertyData.setAccountNumber(loc.getAccount().getAccountNumber());
		    borrowingsAccountPropertyData.setCcbAccountNumber(data.getGlCode());
		    borrowingsAccountPropertyData.setProductId(loc.getProduct().getId());
		    IBorrowingAccountPropertyService borrowService=KLSServiceFactory.getBorrowingAccountPropertyService();
			String returnMsg=borrowService.saveBorrowingAccountProperty(borrowingsAccountPropertyData);
			prdId=transaction.getLineOfCredit().getProduct().getId();
		}
		else
			prdId=transaction.getLineOfCredit().getProduct().getId();
		}		
	}
	
	}
}
