package com.vsoftcorp.kls.service.transaction.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entity.account.BorrowingsAccountProperty;
import com.vsoftcorp.kls.business.entity.account.BorrowingsLineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.LoanRepaymentSchedule;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.BorrowingRecoveryEntryData;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.IProductDAO;
import com.vsoftcorp.kls.dataaccess.IRecoveryOrderDao;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IBorrowingsAccountPropertyDAO;
import com.vsoftcorp.kls.dataaccess.loan.IBorrowingsLineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanRepaymentScheduleDAO;
import com.vsoftcorp.kls.dataaccess.transaction.ICurrentTransactionDAO;
import com.vsoftcorp.kls.service.account.ILoanAccountPropertyService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.transaction.IBorrowingTransactionService;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.service.util.VoucherNumberUtil;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.valuetypes.loan.LoanType;
import com.vsoftcorp.kls.valuetypes.transaction.ChannelType;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;
import com.vsoftcorp.time.Date;

public class BorrowingTransactionService implements IBorrowingTransactionService {

	private static final Logger logger = Logger.getLogger(BorrowingTransactionService.class);

	@Override
	public void saveBorrowingTransactionsOnetoOneStraight(List<CurrentTransaction> currTransList, Date businessDate,
			LoanType loanType,List<Integer> pacsIdsList) {

		logger.info("Start: Saving the BorrowingTransaction list in database in saveBorrowingTransactionsOnetoOneStraight() method.");
		IBorrowingsLineOfCreditDAO borrLOCDAO = KLSDataAccessFactory.getBorrowingsLineOfCreditDAO();
		try {
			createBlocsOnetoOneMethod(businessDate, loanType,pacsIdsList);
			// Below is second part which is recovery
			if (currTransList != null && !currTransList.isEmpty()) {
				for (CurrentTransaction cTransaction : currTransList) {
					// For every recovery (credit) transaction adjust bloc and
					// post corresponding transaction
					LineOfCredit loc = cTransaction.getLineOfCredit();
					if (cTransaction.getCrDr() == 1 && loc != null && !TransactionType.Borrowings.equals(cTransaction.getTransactionType())) {
						if(loc.getProduct().getBorrowingRequired() != null && "Y".equalsIgnoreCase(loc.getProduct().getBorrowingRequired())){
							
							List<BorrowingsLineOfCredit> borrowingLocDetailsList = borrLOCDAO.getBlocsPerLoanLoc(
									loc.getId(), loc.getProduct().getId(), cTransaction.getPacs().getId());
							createBorrowingRecoveryTransactions(cTransaction.getTransactionAmount().getMoney().getAmount(),
								cTransaction.getTransactionCode().getValue(), borrowingLocDetailsList, businessDate);
						}
						else{
							logger.info(" OnetoOneStraight: Stopped Borrowing Recovery transactions due to Borrowing Product"+
									"is not defined for this product id := "+loc.getProduct().getId());
						}
					}
				}
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			// excp.printStackTrace();
			logger.error(" BorrowingTransaction  cannot be Saved in saveBorrowingTransactionsOnetoOneStraight()");
			if (excp instanceof DataAccessException)
				throw excp;
			throw new KlsRuntimeException(
					" BorrowingTransaction  cannot be Saved in saveBorrowingTransactionsOnetoOneStraight",
					excp.getCause());
		}
		logger.info("End: Saving the BorrowingTransaction list in database in saveBorrowingTransactionService() method.");
	}

	@Override
	public void saveBorrowingTransactionsOnetoOneEarly(List<CurrentTransaction> currTransList, Date businessDate,
			LoanType loanType,List<Integer> pacsIdsList) {

		logger.info("Start: Saving the BorrowingTransaction list in database in saveBorrowingTransactionsOnetoOneStraight() method.");
		IBorrowingsLineOfCreditDAO borrLOCDAO = KLSDataAccessFactory.getBorrowingsLineOfCreditDAO();
		IBorrowingsAccountPropertyDAO borrAccountPropertyDAO = KLSDataAccessFactory.getBorrowingsAccountPropertyDAO();
		try {
			createBlocsOnetoOneMethod(businessDate, loanType,pacsIdsList);

			// Below is second part which is recovery
			if (currTransList != null && !currTransList.isEmpty()) {
				for (CurrentTransaction cTransaction : currTransList) {
					// For every recovery (credit) transaction adjust bloc and
					// post corresponding transaction
					LineOfCredit loc = cTransaction.getLineOfCredit();
					if (cTransaction.getCrDr() == 1 && loc != null
							&& !TransactionType.Borrowings.equals(cTransaction.getTransactionType())) {
						if(loc.getProduct().getBorrowingRequired() != null && "Y".equalsIgnoreCase(loc.getProduct().getBorrowingRequired())){
								
							BorrowingsAccountProperty borrowingsAccountProperty = borrAccountPropertyDAO
									.getAccountPropertyByProductId(loc.getProduct().getId());
							List<BorrowingsLineOfCredit> borrowingLocDetailsList = borrLOCDAO
									.getOrderedBorrowingLocListByAccountId(borrowingsAccountProperty.getAccount().getId());
	
							createBorrowingRecoveryTransactions(cTransaction.getTransactionAmount().getMoney().getAmount(),
									cTransaction.getTransactionCode().getValue(), borrowingLocDetailsList, businessDate);
						}else{
							logger.info(" OnetoOneEarly: Stopped Borrowing Recovery transactions due to Borrowing Product"+
									"is not defined for this product id := "+loc.getProduct().getId());
						}
					}
				}
			}
		} catch (Exception excp) {
			// excp.printStackTrace();
			logger.error(" BorrowingTransaction  cannot be Saved in saveBorrowingTransactionsOnetoOneStraight()");
			if (excp instanceof DataAccessException)
				throw excp;
			throw new KlsRuntimeException(
					" BorrowingTransaction  cannot be Saved in saveBorrowingTransactionsOnetoOneStraight",
					excp.getCause());
		}
		logger.info("End: Saving the BorrowingTransaction list in database in saveBorrowingTransactionService() method.");
	}

	@Override
	public void saveBorrowingTransactionsGroupingMethod(Date businessDate, LoanType loanType,List<Integer> pacsIdsList)
			throws KlsRuntimeException {
		logger.info("Start: Calling saveBorrowingTransactionsGroupingMethod()");
		try {
			List<Map> groupedTransList = null;

			ICurrentTransactionDAO currentTransactionDAO = KLSDataAccessFactory.getCurrentTransactionDAO();
			IBorrowingsLineOfCreditDAO borrLOCDAO = KLSDataAccessFactory.getBorrowingsLineOfCreditDAO();
			// IProductDAO productDAO =
			// KLSDataAccessFactory.getProductMasterDAO();
			 ILoanAccountPropertyService loanProperty=KLSServiceFactory.getLoanAccountPropertyService();
			groupedTransList = currentTransactionDAO.getTransactionsGroupedByDateProductPacs(-1, loanType.getValue(),pacsIdsList);
			List<Map> groupedTransList1 = null;
			BorrowingsAccountProperty borrowingsAccountProperty = null;
			BorrowingsLineOfCredit borrowLoc=new BorrowingsLineOfCredit();
			IBorrowingsAccountPropertyDAO bAccountPropertyDAO = KLSDataAccessFactory.getBorrowingsAccountPropertyDAO();
			List<LoanRepaymentSchedule> myltipleRepaymentScheduleList=new ArrayList<LoanRepaymentSchedule>();
			IProductDAO productDAO = KLSDataAccessFactory.getProductMasterDAO();
			Product product = null;
			Integer voucherNumber = null;
			if (groupedTransList != null && !groupedTransList.isEmpty()) {
				for (Map group : groupedTransList) {
					
					BorrowingsLineOfCredit borrowingsLineOfCredit = new BorrowingsLineOfCredit();
					CurrentTransaction currTrans = new CurrentTransaction();
					borrowingsAccountProperty = bAccountPropertyDAO.getAccountPropertyByProductIdPacsId(
							(Integer) group.get("productId"), (Integer) group.get("pacsId"));

					borrowingsLineOfCredit.setAccount(borrowingsAccountProperty.getAccount());
					borrowingsLineOfCredit.setInterestCategory(borrowingsAccountProperty.getInterestCategory());
					borrowingsLineOfCredit.setCustomerId(borrowingsAccountProperty.getAccount().getAccountProperty()
							.getCustomerId());
					borrowingsLineOfCredit.setLoanAccountProperty(borrowingsAccountProperty);
					borrowingsLineOfCredit.setProduct(borrowingsAccountProperty.getProduct());
					borrowingsLineOfCredit.setInterestCategory(borrowingsAccountProperty.getInterestCategory());

					AccountingMoney totalAmount = ((AccountingMoney) group.get("totalAmount"));
					borrowingsLineOfCredit.setCurrentBalance(totalAmount);

					borrowingsLineOfCredit.setSanctionedAmount(totalAmount.getMoney());

					Date bussDate = (Date) group.get("businessDate");
					borrowingsLineOfCredit.setSanctionedDate(businessDate);

					borrowingsLineOfCredit.setKindLimit(Money.ZERO);
					borrowingsLineOfCredit.setKindBalance(AccountingMoney.ZERO);
					borrowingsLineOfCredit.setOperatingCashLimit(Money.ZERO);

					currTrans.setAccount(borrowingsAccountProperty.getAccount());
					currTrans.setAccountNumber(borrowingsAccountProperty.getAccount().getAccountNumber());

					currTrans.setBusinessDate(businessDate);
					currTrans.setChannelType(ChannelType.SYSTEM);
					currTrans.setCrDr(1);
					currTrans.setLineOfCredit(borrowingsLineOfCredit);
					currTrans.setOpeningBalance(AccountingMoney.ZERO);

					IPacsDAO pacsDAO = KLSDataAccessFactory.getPacsDAO();
					Pacs pacs = pacsDAO.getPacs((Integer) group.get("pacsId"));

					currTrans.setPacs(pacs);
					currTrans.setPostedStatus(1);
					currTrans.setRemarks("While Borrowings LOC creation by grouping");
					// currTrans.setTerminalId(terminalId);
					currTrans.setTransactionAmount(totalAmount);
					currTrans.setTransactionCode(TransactionCode.PRINCIPAL_BAL);
					currTrans.setTransactionType(TransactionType.Borrowings);
					voucherNumber = VoucherNumberUtil.getVoucherNumber(pacs.getId().toString(),
							TransactionType.Borrowings.getValue());
					currTrans.setVoucherNumber(TransactionType.Borrowings.getValue() + "-" + voucherNumber);
					// borrLOCDAO.deleteAllBorrowingsLocs();
					borrowLoc=borrLOCDAO.saveBorrowingsLOCAndPostTransaction(borrowingsLineOfCredit, currTrans);
					if(borrowLoc!=null  && loanType.getValue().equals("L")){
					LoanLineOfCredit loc=KLSDataAccessFactory.getLoanLineOfCreditDAO().getLoanLineOfCreditById((Long)group.get("locId"));
					List<LoanRepaymentSchedule> repaymentScheduleList=loanProperty.generateRepaymentScheduleByMultipleDisbursement(loc, totalAmount.getMoney(), myltipleRepaymentScheduleList, businessDate, loc.getFirstDueDate(),"B",borrowLoc.getId());
					KLSDataAccessFactory.getLoanRepaymentScheduleDAO().saveLoanRepaymentScheduleList(repaymentScheduleList);
					}
				}
			}

			// Fetch loan recoveries.......
			groupedTransList1 = currentTransactionDAO.getTransactionsGroupedByDateProductPacsTransCode(1,
					loanType.getValue(),pacsIdsList);

			if (groupedTransList1 != null && !groupedTransList1.isEmpty()) {
				TransactionCode tranCode=null;
				AccountingMoney subsidyAmt=AccountingMoney.ZERO;
				for (Map group : groupedTransList1) {
					Integer productId = (Integer) group.get("productId");
					if(productId != null){
						product = productDAO.getProductById(productId);
					}
					if(product.getBorrowingRequired() != null && "Y".equalsIgnoreCase(product.getBorrowingRequired())){
				
						tranCode=(TransactionCode)group.get("transactionCode");
						borrowingsAccountProperty = bAccountPropertyDAO.getAccountPropertyByProductIdPacsId(
								(Integer) group.get("productId"), (Integer) group.get("pacsId"));
						if (borrowingsAccountProperty == null)
							throw new KlsRuntimeException(
									"Error while recovery of borrowing transactions grouping. Borrowings account not found.");
						List<BorrowingsLineOfCredit> borrowingLocDetailsList = borrLOCDAO
								.getOrderedBorrowingLocListByAccountId(borrowingsAccountProperty.getAccount().getId());
						if(tranCode.equals(TransactionCode.INTEREST)){
							subsidyAmt=currentTransactionDAO.getSubsidyTransactionsByLocId((Long)group.get("locId"), (Integer)group.get("productId"), (Date)group.get("businessDate"));
							logger.info("subsidy amount in borrowing:::"+subsidyAmt);
							AccountingMoney transAmt=(AccountingMoney) group.get("totalAmount");
							if(subsidyAmt!=null && !subsidyAmt.isZero())
							transAmt=transAmt.subtract(subsidyAmt);
							else
								transAmt=transAmt;
							createBorrowingRecoveryTransactions(transAmt.getMoney().getAmount().setScale(2), ((TransactionCode) group.get("transactionCode")).getValue(),
								borrowingLocDetailsList, businessDate);
						}else{
							createBorrowingRecoveryTransactions(((AccountingMoney) group.get("totalAmount")).getMoney()
									.getAmount(), ((TransactionCode) group.get("transactionCode")).getValue(),
									borrowingLocDetailsList, businessDate);
						}
					}else{
						logger.info("GroupingMethod: Borrowing product id not defined for this loan product id := "+productId);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while saving borrowings transactions (grouping method)");
			if (e instanceof DataAccessException)
				throw e;
			throw new KlsRuntimeException("Error while saving borrowings transactions ", e.getCause());
		}
		logger.info("End: Calling saveBorrowingTransactionsGroupingMethod()");
	}

	private void createBlocsOnetoOneMethod(Date businessDate, LoanType loanType,List<Integer> pacsIdsList) {

		logger.info("Start: Creating borrowings LOCs for One to One in method createBlocsOnetoOneMethod().");
		try {
			IBorrowingsLineOfCreditDAO borrLOCDAO = KLSDataAccessFactory.getBorrowingsLineOfCreditDAO();
			BorrowingsAccountProperty borrowingsAccountProperty = null;
			IBorrowingsAccountPropertyDAO bAccountPropertyDAO = KLSDataAccessFactory.getBorrowingsAccountPropertyDAO();
			ICurrentTransactionDAO currentTransactionDAO = KLSDataAccessFactory.getCurrentTransactionDAO();

            ILoanAccountPropertyService loanProperty=KLSServiceFactory.getLoanAccountPropertyService();

			IProductDAO productDAO = KLSDataAccessFactory.getProductMasterDAO();
			Product product = null;
			Integer productId = null;

			List<Map> groupedTransList = null;
			BorrowingsLineOfCredit borrowLoc=new BorrowingsLineOfCredit();
			List<LoanRepaymentSchedule> myltipleRepaymentScheduleList=new ArrayList<LoanRepaymentSchedule>();
			
			groupedTransList = currentTransactionDAO
					.getTransactionsGroupedByDateProductPacsLoc(-1, loanType.getValue(),pacsIdsList);
			logger.info("testing===="+groupedTransList.size());
			Integer voucherNumber = null;
			if (groupedTransList != null && !groupedTransList.isEmpty()) {
				for (Map group : groupedTransList) {

					product = productDAO.getProductById((Integer) group.get("productId"));
					productId = product.getId();
					if(productId != null){
						product = productDAO.getProductById(productId);
					}
					if(product.getBorrowingRequired() != null && "Y".equalsIgnoreCase(product.getBorrowingRequired())){
					
						BorrowingsLineOfCredit borrowingsLineOfCredit = new BorrowingsLineOfCredit();
						CurrentTransaction currTrans = new CurrentTransaction();
						borrowingsAccountProperty = bAccountPropertyDAO.getAccountPropertyByProductIdPacsId(
								(Integer) group.get("productId"), (Integer) group.get("pacsId"));
	
						borrowingsLineOfCredit.setAccount(borrowingsAccountProperty.getAccount());
						borrowingsLineOfCredit.setInterestCategory(borrowingsAccountProperty.getInterestCategory());
						borrowingsLineOfCredit.setCustomerId(borrowingsAccountProperty.getAccount().getAccountProperty()
								.getCustomerId());
						borrowingsLineOfCredit.setLoanAccountProperty(borrowingsAccountProperty);
						borrowingsLineOfCredit.setProduct(borrowingsAccountProperty.getProduct());
						borrowingsLineOfCredit.setInterestCategory(borrowingsAccountProperty.getInterestCategory());
	
						AccountingMoney totalAmount = ((AccountingMoney) group.get("totalAmount"));
						borrowingsLineOfCredit.setCurrentBalance(totalAmount);
	
						borrowingsLineOfCredit.setSanctionedAmount(totalAmount.getMoney());
	
						borrowingsLineOfCredit.setSanctionedDate(businessDate);
	
						borrowingsLineOfCredit.setKindLimit(Money.ZERO);
						borrowingsLineOfCredit.setKindBalance(AccountingMoney.ZERO);
						borrowingsLineOfCredit.setOperatingCashLimit(Money.ZERO);
						borrowingsLineOfCredit.setBorrowingsLocId((Long) group.get("locId"));
	
						currTrans.setAccount(borrowingsAccountProperty.getAccount());
						currTrans.setAccountNumber(borrowingsAccountProperty.getAccount().getAccountNumber());
	
						currTrans.setBusinessDate(businessDate);
						currTrans.setChannelType(ChannelType.SYSTEM);
						currTrans.setCrDr(1);
						currTrans.setLineOfCredit(borrowingsLineOfCredit);
						currTrans.setOpeningBalance(AccountingMoney.ZERO);
	
						IPacsDAO pacsDAO = KLSDataAccessFactory.getPacsDAO();
						Pacs pacs = pacsDAO.getPacs((Integer) group.get("pacsId"));
	
						currTrans.setPacs(pacs);
						currTrans.setPostedStatus(1);
						currTrans.setRemarks("While Borrowings LOC creation by one to one method");
						// currTrans.setTerminalId(terminalId);
						currTrans.setTransactionAmount(totalAmount);
						currTrans.setTransactionCode(TransactionCode.PRINCIPAL_BAL);
						currTrans.setTransactionType(TransactionType.Borrowings);
						voucherNumber = VoucherNumberUtil.getVoucherNumber(pacs.getId().toString(),
								TransactionType.Borrowings.getValue());
						currTrans.setVoucherNumber(TransactionType.Borrowings.getValue() + "-" + voucherNumber);
						// borrLOCDAO.deleteAllBorrowingsLocs();
						borrowLoc=borrLOCDAO.saveBorrowingsLOCAndPostTransaction(borrowingsLineOfCredit, currTrans);
						if(borrowLoc!=null && loanType.getValue().equals("L")){
							LoanLineOfCredit loc=KLSDataAccessFactory.getLoanLineOfCreditDAO().getLoanLineOfCreditById((Long)group.get("locId"));
							List<LoanRepaymentSchedule> repaymentScheduleList=loanProperty.generateRepaymentScheduleByMultipleDisbursement(loc, totalAmount.getMoney(), myltipleRepaymentScheduleList, businessDate, loc.getFirstDueDate(),"B",borrowLoc.getId());
							KLSDataAccessFactory.getLoanRepaymentScheduleDAO().saveLoanRepaymentScheduleList(repaymentScheduleList);
							}
					}else{
						logger.info(" OneToOneStraight : Borrowing product is not defined.");
					}

				}
			}
		} catch (Exception excp) {
			// excp.printStackTrace();
			logger.error(" Error while creating borrowings locs for give loc list");
			if (excp instanceof DataAccessException)
				throw excp;
			throw new KlsRuntimeException("Error while creating borrowings locs for give loc list. ", excp.getCause());
		}

		logger.info("End: Creating borrowings LOCs for One to One in method createBlocsOnetoOneMethod().");
	}

	private void createBorrowingRecoveryTransactions(BigDecimal amount, Integer transactionCode,
			List<BorrowingsLineOfCredit> borrowingLocDetailsList, Date businessDate) {
		logger.info("Start: Creating borrowings recovery transactions in createBorrowingRecoveryTrasactions()");
		IBorrowingsLineOfCreditDAO bDao = KLSDataAccessFactory.getBorrowingsLineOfCreditDAO();
		IProductDAO productDAO = KLSDataAccessFactory.getProductMasterDAO();
		BigDecimal amountPaid = amount;
		Integer voucherNumber = null;

		for (BorrowingsLineOfCredit bLoc : borrowingLocDetailsList) {
			voucherNumber = VoucherNumberUtil.getVoucherNumber(bLoc.getLoanAccountProperty().getAccount()
					.getAccountProperty().getPacs().getId().toString(), TransactionType.Borrowings.getValue());
			CurrentTransaction currTrans = new CurrentTransaction();
			currTrans.setAccount(bLoc.getAccount());
			currTrans.setAccountNumber(bLoc.getAccount().getAccountNumber());
			currTrans.setLineOfCredit(bLoc);
			currTrans.setOpeningBalance(bLoc.getCurrentBalance());
			currTrans.setPacs(bLoc.getLoanAccountProperty().getAccount().getAccountProperty().getPacs());
			currTrans.setBusinessDate(businessDate);
			currTrans.setChannelType(ChannelType.SYSTEM);
			currTrans.setCrDr(-1);
			currTrans.setTransactionType(TransactionType.Transfer);
			currTrans.setVoucherNumber(TransactionType.Transfer.getValue()+"-"+voucherNumber);
			currTrans.setPostedStatus(1);
			currTrans.setTransactionType(TransactionType.Borrowings);
			Product product1 = productDAO.getProduct(bLoc.getProduct(), false);
			IRecoveryOrderDao rDao = KLSDataAccessFactory.getRecoveryOrderDAO();

			int a = 0;
			if (transactionCode == TransactionCode.PRINCIPAL_BAL.getValue()) {
				// principal credit
				currTrans.setRemarks("For Borrowing Recovery Principal Credit.");// To
				currTrans.setTransactionCode(TransactionCode.PRINCIPAL_BAL);
				a = amountPaid.compareTo(BigDecimal.ZERO);
				if (a == 1) {
					a = bLoc.getCurrentBalance().compareTo(AccountingMoney.ZERO);
					if (a == 1) {
						a = amountPaid.compareTo(bLoc.getCurrentBalance().getMoney().getAmount());
						AccountingMoney payingAmt = MoneyUtil.getAccountingMoney(amountPaid);
						if (a == 1 || a == 0) {
							currTrans.setTransactionAmount(bLoc.getCurrentBalance());
							amountPaid = amountPaid.subtract(bLoc.getCurrentBalance().getMoney().getAmount());
							bLoc.setCurrentBalance(AccountingMoney.ZERO);

						} else {
							currTrans.setTransactionAmount(payingAmt);
							amountPaid = BigDecimal.ZERO;
							bLoc.setCurrentBalance(bLoc.getCurrentBalance().subtract(payingAmt));
						}

						bDao.saveBorrowingsLOCAndPostTransaction(bLoc, currTrans);
					}
				}
			} else if (transactionCode == TransactionCode.INTEREST.getValue()) {

				// interest credit
				currTrans.setRemarks("For Borrowing Recovery Interest Credit.");
				currTrans.setTransactionCode(TransactionCode.INTEREST);

				a = amountPaid.compareTo(BigDecimal.ZERO);
				if (a == 1) {
					a = bLoc.getInterestReceivable().compareTo(BigDecimal.ZERO);
					AccountingMoney payingAmt = MoneyUtil.getAccountingMoney(amountPaid);
					if (a == 1) {
						a = amountPaid.compareTo(bLoc.getInterestReceivable());
						if (a == 1 || a == 0) {
							currTrans.setTransactionAmount(MoneyUtil.getAccountingMoney(bLoc.getInterestReceivable()));
							amountPaid = amountPaid.subtract(bLoc.getInterestReceivable());
							bLoc.setInterestReceivable(BigDecimal.ZERO);

						} else {
							currTrans.setTransactionAmount(payingAmt);
							bLoc.setInterestReceivable(bLoc.getInterestReceivable().subtract(amountPaid));
							amountPaid = BigDecimal.ZERO;
						}
						bDao.saveBorrowingsLOCAndPostTransaction(bLoc, currTrans);
					}
				}
			} else if (transactionCode == TransactionCode.PENAL_INTEREST.getValue()) {

				// penal interest credit
				currTrans.setRemarks("For Borrowing Recovery Penal Interest Credit.");
				currTrans.setTransactionCode(TransactionCode.PENAL_INTEREST);

				a = amountPaid.compareTo(BigDecimal.ZERO);
				if (a == 1) {
					a = bLoc.getPenalInterestReceivable().compareTo(BigDecimal.ZERO);
					if (a == 1) {
						AccountingMoney payingAmt = MoneyUtil.getAccountingMoney(amountPaid);
						a = amountPaid.compareTo(bLoc.getPenalInterestReceivable());
						if (a == 1 || a == 0) {
							currTrans.setTransactionAmount(MoneyUtil.getAccountingMoney(bLoc
									.getPenalInterestReceivable()));
							amountPaid = amountPaid.subtract(bLoc.getPenalInterestReceivable());
							bLoc.setPenalInterestReceivable(BigDecimal.ZERO);

						} else {
							currTrans.setTransactionAmount(payingAmt);
							bLoc.setPenalInterestReceivable(bLoc.getPenalInterestReceivable().subtract(amountPaid));
							amountPaid = BigDecimal.ZERO;
						}
						bDao.saveBorrowingsLOCAndPostTransaction(bLoc, currTrans);
					}
				}
			}
			// Charges received amount wont be adjusted for borrowing account

			// Boolean flag = bDao.isAllAmountsClear(bLoc.getId());
			if (amountPaid.compareTo(BigDecimal.ZERO) == 0) { // !flag
				return;
			}
		}
		// TODO: Need to adjust remaining amount against additional borrowing
		// account
		if (amountPaid.compareTo(BigDecimal.ZERO) == 1)
			logger.info("Remaining amount after borrowing recovery :" + amountPaid);
		logger.info("End: Creating borrowings recovery transactions in createBorrowingRecoveryTrasactions()");
	}
	
	
	@Override
	public void borrowingDirectCollectionPassing(Long  recoveryId ,String businessDate){
		BorrowingRecoveryEntryData data = KLSServiceFactory.getBorrowingRecoveryEntryService().getBorrowingRecoveryEntryById(recoveryId);
		IBorrowingsLineOfCreditDAO borrLOCDAO = KLSDataAccessFactory.getBorrowingsLineOfCreditDAO();
		BorrowingsLineOfCredit borrowingLocDetails = borrLOCDAO.getBorrowingsLineOfCreditById(data.getLineOfCredit());
		//List<BorrowingsLineOfCredit> borrowingLocDetailsList = new ArrayList<BorrowingsLineOfCredit>();
		//borrowingLocDetailsList.add(borrowingLocDetails);
		IBorrowingsLineOfCreditDAO bDao = KLSDataAccessFactory.getBorrowingsLineOfCreditDAO();
		Integer voucherNumber = VoucherNumberUtil.getVoucherNumber(borrowingLocDetails.getLoanAccountProperty().getAccount()
				.getAccountProperty().getPacs().getId().toString(), TransactionType.Borrowings.getValue());
		
		List<CurrentTransaction> currentTransactionList = new ArrayList<CurrentTransaction>();
		IPacsDAO pdao = KLSDataAccessFactory.getPacsDAO();
		Pacs pac = pdao.getPacs(data.getPacsId());
		String pacsBankAccount = pac.getPacsBankAccNumber();
		
		if(data.getRecoveryAmount().compareTo(BigDecimal.ZERO) > 0){
		populateCurrentTransactionRecord(borrowingLocDetails, pacsBankAccount, currentTransactionList, TransactionCode.PACS_BANK_ACC_BORROWING, "To Collection on "+ data.getCcbLoanAccountNumber()+"/"+data.getPacsId()+"/"+data.getLineOfCredit(), -1,
				MoneyUtil.getAccountingMoney(data.getRecoveryAmount()), false, TransactionType.Transfer, TransactionType.Transfer.getValue()+"-"+voucherNumber.toString());
		
		populateCurrentTransactionRecord(borrowingLocDetails, borrowingLocDetails.getAccount().getAccountNumber(), currentTransactionList, TransactionCode.COLLECTION, "To KCC  Drawls "+"/"+data.getPacsId()+"/"+data.getLineOfCredit(), 1,
				MoneyUtil.getAccountingMoney(data.getRecoveryAmount()), false, TransactionType.Transfer, TransactionType.Transfer.getValue()+"-"+voucherNumber.toString());
		
		populateCurrentTransactionRecord(borrowingLocDetails, borrowingLocDetails.getAccount().getAccountNumber(), currentTransactionList, TransactionCode.COLLECTION, "To Recovery Bifurcation account" , -1,
				MoneyUtil.getAccountingMoney(data.getRecoveryAmount()), false, TransactionType.Transfer, TransactionType.Transfer.getValue()+"-"+voucherNumber.toString());
		}
		
		KLSDataAccessFactory.getCurrentTransactionDAO().saveCurrentTransactionsList(currentTransactionList);
		
		if(data.getPrinciplaBalance().compareTo(BigDecimal.ZERO) > 0){
			currentTransactionList = new ArrayList<CurrentTransaction>();
		populateCurrentTransactionRecord(borrowingLocDetails, borrowingLocDetails.getAccount().getAccountNumber(), currentTransactionList, TransactionCode.PRINCIPAL_BAL, "By Principle Collection" , 1,
				MoneyUtil.getAccountingMoney(data.getPrinciplaBalance()), false, TransactionType.Transfer, TransactionType.Transfer.getValue()+"-"+voucherNumber.toString());
		
		borrowingLocDetails.setCurrentBalance(borrowingLocDetails.getCurrentBalance().subtract(MoneyUtil.getAccountingMoney(data.getPrinciplaBalance())));
		
		bDao.saveBorrowingsLOCAndPostTransaction(borrowingLocDetails, currentTransactionList.get(0));
		}
		
		if(data.getInterestPayable().compareTo(BigDecimal.ZERO) > 0){
			currentTransactionList = new ArrayList<CurrentTransaction>();
		populateCurrentTransactionRecord(borrowingLocDetails, borrowingLocDetails.getAccount().getAccountNumber(), currentTransactionList, TransactionCode.INTEREST, "By Interest on "+data.getCcbLoanAccountNumber() , 1,
				MoneyUtil.getAccountingMoney(data.getInterestPayable()), false, TransactionType.Transfer, TransactionType.Transfer.getValue()+"-"+voucherNumber.toString());
		
		borrowingLocDetails.setInterestReceivable(borrowingLocDetails.getInterestReceivable().subtract(data.getInterestPayable()));
		
		bDao.saveBorrowingsLOCAndPostTransaction(borrowingLocDetails, currentTransactionList.get(0));
		}
		
		if(data.getPenalInterestPayable().compareTo(BigDecimal.ZERO) > 0){
			currentTransactionList = new ArrayList<CurrentTransaction>();
		populateCurrentTransactionRecord(borrowingLocDetails, borrowingLocDetails.getAccount().getAccountNumber(), currentTransactionList, TransactionCode.PENAL_INTEREST, "By Interest on "+data.getCcbLoanAccountNumber() , 1,
				MoneyUtil.getAccountingMoney(data.getPenalInterestPayable()), false, TransactionType.Transfer, TransactionType.Transfer.getValue()+"-"+voucherNumber.toString());
		
		borrowingLocDetails.setPenalInterestReceivable(borrowingLocDetails.getPenalInterestReceivable().subtract(data.getPenalInterestPayable()));
		
		bDao.saveBorrowingsLOCAndPostTransaction(borrowingLocDetails, currentTransactionList.get(0));
		}
		
		
		//createBorrowingDirectRecoveryTransactions(data.getPrinciplaBalance(),1,borrowingLocDetailsList,DateUtil.getVSoftDateByString(businessDate));
		//createBorrowingDirectRecoveryTransactions(data.getInterestPayable(),2,borrowingLocDetailsList,DateUtil.getVSoftDateByString(businessDate));
		//createBorrowingDirectRecoveryTransactions(data.getPenalInterestPayable(),3,borrowingLocDetailsList,DateUtil.getVSoftDateByString(businessDate));
		
		data.setStatus(2);
		data.setPassingDate(businessDate);
		KLSServiceFactory.getBorrowingRecoveryEntryService().updateBorrowingRecoveryEntry(data);
	}
	
	public void populateCurrentTransactionRecord(BorrowingsLineOfCredit loanLoc, String accountNumber, List<CurrentTransaction> currentTransactionList,
			TransactionCode transactionCode, String remarks, Integer crdr, AccountingMoney transactionAmount, boolean isCashGl,
			TransactionType transactionType, String voucherNumber) {
		CurrentTransaction currentTransaction = new CurrentTransaction();
		if(loanLoc!=null){
			currentTransaction.setAccount(loanLoc.getAccount());
			currentTransaction.setPacs(loanLoc.getLoanAccountProperty().getAccount().getAccountProperty().getPacs());
		}
		currentTransaction.setAccountNumber(accountNumber);
		currentTransaction.setBusinessDate(LoanServiceUtil.getBusinessDate());
		currentTransaction.setChannelType(ChannelType.SYSTEM);
		currentTransaction.setCrDr(crdr);
		if (isCashGl) {
			currentTransaction.setLineOfCredit(null);
			currentTransaction.setOpeningBalance(null);
		} else {
			currentTransaction.setLineOfCredit(loanLoc);
			currentTransaction.setOpeningBalance(loanLoc.getCurrentBalance());
		}
		currentTransaction.setPostedStatus(1);
		currentTransaction.setRemarks(remarks);
		currentTransaction.setTerminalId(null);
		currentTransaction.setTransactionAmount(transactionAmount);
		currentTransaction.setTransactionCode(transactionCode);
		currentTransaction.setTransactionType(transactionType);
		currentTransaction.setVoucherNumber(voucherNumber);
		currentTransactionList.add(currentTransaction);
	}
	
	
	
	/*private void createBorrowingDirectRecoveryTransactions(BigDecimal amount,Integer transactionCode,
			List<BorrowingsLineOfCredit> borrowingLocDetailsList, Date businessDate) {
		
		logger.info("Start: Creating borrowings recovery transactions in createBorrowingRecoveryTrasactions()");
		IBorrowingsLineOfCreditDAO bDao = KLSDataAccessFactory.getBorrowingsLineOfCreditDAO();
		IProductDAO productDAO = KLSDataAccessFactory.getProductMasterDAO();
		BigDecimal amountPaid = amount;
		Integer voucherNumber = null;
          
		for (BorrowingsLineOfCredit bLoc : borrowingLocDetailsList) {
			voucherNumber = VoucherNumberUtil.getVoucherNumber(bLoc.getLoanAccountProperty().getAccount()
					.getAccountProperty().getPacs().getId().toString(), TransactionType.Borrowings.getValue());
			CurrentTransaction currTrans = new CurrentTransaction();
			currTrans.setAccount(bLoc.getAccount());
			currTrans.setAccountNumber(bLoc.getAccount().getAccountNumber());
			currTrans.setLineOfCredit(bLoc);
			currTrans.setOpeningBalance(bLoc.getCurrentBalance());
			currTrans.setPacs(bLoc.getLoanAccountProperty().getAccount().getAccountProperty().getPacs());
			currTrans.setBusinessDate(businessDate);
			currTrans.setChannelType(ChannelType.SYSTEM);
			currTrans.setCrDr(-1);
			currTrans.setTransactionType(TransactionType.Transfer);
			currTrans.setVoucherNumber(TransactionType.Transfer.getValue()+"-"+voucherNumber);
			currTrans.setPostedStatus(1);
			currTrans.setTransactionType(TransactionType.Borrowings);
			
			int a = 0;
			if (transactionCode == TransactionCode.PRINCIPAL_BAL.getValue()) {
				// principal credit
				currTrans.setRemarks("For Borrowing Recovery Principal Credit.");// To
				currTrans.setTransactionCode(TransactionCode.PRINCIPAL_BAL);
				a = amountPaid.compareTo(BigDecimal.ZERO);
				if (a == 1) {
					a = bLoc.getCurrentBalance().compareTo(AccountingMoney.ZERO);
					if (a == 1) {
						a = amountPaid.compareTo(bLoc.getCurrentBalance().getMoney().getAmount());
						AccountingMoney payingAmt = MoneyUtil.getAccountingMoney(amountPaid);
						if (a == 1 || a == 0) {
							currTrans.setTransactionAmount(bLoc.getCurrentBalance());
							amountPaid = amountPaid.subtract(bLoc.getCurrentBalance().getMoney().getAmount());
							bLoc.setCurrentBalance(AccountingMoney.ZERO);

						} else {
							currTrans.setTransactionAmount(payingAmt);
							amountPaid = BigDecimal.ZERO;
							bLoc.setCurrentBalance(bLoc.getCurrentBalance().subtract(payingAmt));
						}

						bDao.saveBorrowingsLOCAndPostTransaction(bLoc, currTrans);
					}
				}
			} else if (transactionCode == TransactionCode.INTEREST.getValue()) {

				// interest credit
				currTrans.setRemarks("For Borrowing Recovery Interest Credit.");
				currTrans.setTransactionCode(TransactionCode.INTEREST);

				a = amountPaid.compareTo(BigDecimal.ZERO);
				if (a == 1) {
					a = bLoc.getInterestReceivable().compareTo(BigDecimal.ZERO);
					AccountingMoney payingAmt = MoneyUtil.getAccountingMoney(amountPaid);
					if (a == 1) {
						a = amountPaid.compareTo(bLoc.getInterestReceivable());
						if (a == 1 || a == 0) {
							currTrans.setTransactionAmount(MoneyUtil.getAccountingMoney(bLoc.getInterestReceivable()));
							bLoc.setInterestReceivable(BigDecimal.ZERO);
							amountPaid = amountPaid.subtract(bLoc.getInterestReceivable());

						} else {
							currTrans.setTransactionAmount(payingAmt);
							bLoc.setInterestReceivable(bLoc.getInterestReceivable().subtract(amountPaid));
							amountPaid = BigDecimal.ZERO;
						}
						bDao.saveBorrowingsLOCAndPostTransaction(bLoc, currTrans);
					}
				}
			} else if (transactionCode == TransactionCode.PENAL_INTEREST.getValue()) {

				// penal interest credit
				currTrans.setRemarks("For Borrowing Recovery Penal Interest Credit.");
				currTrans.setTransactionCode(TransactionCode.PENAL_INTEREST);

				a = amountPaid.compareTo(BigDecimal.ZERO);
				if (a == 1) {
					a = bLoc.getPenalInterestReceivable().compareTo(BigDecimal.ZERO);
					if (a == 1) {
						AccountingMoney payingAmt = MoneyUtil.getAccountingMoney(amountPaid);
						a = amountPaid.compareTo(bLoc.getPenalInterestReceivable());
						if (a == 1 || a == 0) {
							currTrans.setTransactionAmount(MoneyUtil.getAccountingMoney(bLoc
									.getPenalInterestReceivable()));
							bLoc.setPenalInterestReceivable(BigDecimal.ZERO);
							amountPaid = amountPaid.subtract(bLoc.getPenalInterestReceivable());

						} else {
							currTrans.setTransactionAmount(payingAmt);
							bLoc.setPenalInterestReceivable(bLoc.getPenalInterestReceivable().subtract(amountPaid));
							amountPaid = BigDecimal.ZERO;
						}
						bDao.saveBorrowingsLOCAndPostTransaction(bLoc, currTrans);
					}
				}
			}
			else if (transactionCode == TransactionCode.PENAL_INTEREST.getValue()) {

				// penal interest credit
				currTrans.setRemarks("For Borrowing Recovery Penal Interest Credit.");
				currTrans.setTransactionCode(TransactionCode.PENAL_INTEREST);

				a = amountPaid.compareTo(BigDecimal.ZERO);
				if (a == 1) {
					a = bLoc.getPenalInterestReceivable().compareTo(BigDecimal.ZERO);
					if (a == 1) {
						AccountingMoney payingAmt = MoneyUtil.getAccountingMoney(amountPaid);
						a = amountPaid.compareTo(bLoc.getPenalInterestReceivable());
						if (a == 1 || a == 0) {
							currTrans.setTransactionAmount(MoneyUtil.getAccountingMoney(bLoc
									.getPenalInterestReceivable()));
							bLoc.setPenalInterestReceivable(BigDecimal.ZERO);
							amountPaid = amountPaid.subtract(bLoc.getPenalInterestReceivable());

						} else {
							currTrans.setTransactionAmount(payingAmt);
							bLoc.setPenalInterestReceivable(bLoc.getPenalInterestReceivable().subtract(amountPaid));
							amountPaid = BigDecimal.ZERO;
						}
						bDao.saveBorrowingsLOCAndPostTransaction(bLoc, currTrans);
					}
				}
			}
			
			
			// Charges received amount wont be adjusted for borrowing account

			// Boolean flag = bDao.isAllAmountsClear(bLoc.getId());
			if (amountPaid.compareTo(BigDecimal.ZERO) == 0) { // !flag
				return;
			}
		}
		// TODO: Need to adjust remaining amount against additional borrowing
		// account
		if (amountPaid.compareTo(BigDecimal.ZERO) == 1)
			logger.info("Remaining amount after borrowing recovery :" + amountPaid);
		logger.info("End: Creating borrowings recovery transactions in createBorrowingRecoveryTrasactions()");
	}*/
	
	
}