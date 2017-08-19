package com.vsoftcorp.kls.service.loan.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.accounting.types.DebitOrCredit;
import com.vsoftcorp.core.banking.data.values.RequestType;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.LoanDisbursementSchedule;
import com.vsoftcorp.kls.business.entity.loan.LoanRecovery;
import com.vsoftcorp.kls.business.entity.loan.LoanRepaymentSchedule;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.AccountData;
import com.vsoftcorp.kls.data.BorrowingsAccountPropertyData;
import com.vsoftcorp.kls.data.DisbursementDetailsData;
import com.vsoftcorp.kls.data.LoanDisbursementEntryData;
import com.vsoftcorp.kls.data.LoanDisbursementEntryDataList;
import com.vsoftcorp.kls.data.LoanLineOfCreditData;
import com.vsoftcorp.kls.data.MTMultipleDisburseData;
import com.vsoftcorp.kls.data.PacsLoanApplicationData;
import com.vsoftcorp.kls.data.PacsLoanDisbursementData;
import com.vsoftcorp.kls.data.SmsData;
import com.vsoftcorp.kls.data.gateway.datahelpers.KLSResponse;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.data.gateway.datahelpers.ShareAccountData;
import com.vsoftcorp.kls.data.gateway.datahelpers.ShareTransactionData;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.IPacsGlMappingDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.ILoanDisbursementDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanRepaymentScheduleDAO;
import com.vsoftcorp.kls.service.account.IAccountPropertyService;
import com.vsoftcorp.kls.service.account.ILoanAccountPropertyService;
import com.vsoftcorp.kls.service.account.ILoanLineOfCreditService;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.LoanDisbursementHelper;
import com.vsoftcorp.kls.service.helper.MasterHelper;
import com.vsoftcorp.kls.service.helper.PacsLoanDisbursementHelper;
import com.vsoftcorp.kls.service.loan.ILoanDisbursementEntryService;
import com.vsoftcorp.kls.service.loan.ILoanDisbursementService;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.kls.service.util.SuvikasService;
import com.vsoftcorp.kls.service.util.VoucherNumberUtil;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.CBSMethodEnum;
import com.vsoftcorp.kls.valuetypes.PacsStatus;
import com.vsoftcorp.kls.valuetypes.transaction.ChannelType;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;
import com.vsoftcorp.kls.data.DisbursementPassingData;

public class LoanDisbursementService implements ILoanDisbursementService {

	private static final Logger logger = Logger.getLogger(LoanDisbursementService.class);

	public String saveLoanDisbursement(PacsLoanDisbursementData loanDisbursementData) {

		logger.info("Start:Calling saveLoanDisbursement() method in LoanDisbursementService.. ");
		Integer voucherNumber = null;
		String response=null;
		
		try {
			ILoanDisbursementDAO disbursementDAO = KLSDataAccessFactory.getLoanDisbursementDAO();
			// Find out whether is first disbursement???
			boolean isFirstDisbursement = disbursementDAO.isFirstDisbursement(loanDisbursementData.getLineOfCreditId());
			LoanLineOfCredit lineOfCredit = KLSDataAccessFactory.getLoanLineOfCreditDAO().getLoanLineOfCreditById(
					loanDisbursementData.getLineOfCreditId());
			PacsLoanApplicationData loanApplicationData = KLSServiceFactory.getPacsLoanApplicationService()
					.getLoanApplicationById(lineOfCredit.getPacsLoanApplication().getId());
			LoanLineOfCreditData loanLocData = KLSServiceFactory.getLoanLineOfCreditService().getLineOfCreditDataById(
					loanDisbursementData.getLineOfCreditId());
			/*BankParameter bankParameter = KLSDataAccessFactory.getBankParameter();
			EntityManager em = EntityManagerUtil.getEntityManager();
			if(!"O".equalsIgnoreCase(bankParameter.getCbsIntegrationMethod().getValue()) && "V".equalsIgnoreCase(bankParameter.getOther_cbs())){
				 em.getTransaction().begin();
			 }else{
				 EntityManagerUtil.beginTransaction();
			 }*/
			EntityManagerUtil.beginTransaction();
			voucherNumber = VoucherNumberUtil.getVoucherNumber(lineOfCredit.getAccount().getAccountProperty().getPacs()
					.getId().toString(), TransactionType.Transfer.getValue());

			loanDisbursementData.setVoucherNumber(voucherNumber);

			loanDisbursementData.setPacsId(lineOfCredit.getAccount().getAccountProperty().getPacs().getId());

			loanDisbursementData.setBranchId(lineOfCredit.getAccount().getAccountProperty().getBranch().getId());

			AccountingMoney balance = AccountingMoney.ZERO;
			String pacsSuvikasVoucherNumber = loanDisbursementData.getPacsSuvikasVoucherNumber();
			
			//Generate the Repayment Schedule for Disbursment Amount.
			List<LoanRepaymentSchedule> loanRepaymentScheduleList = new ArrayList<LoanRepaymentSchedule>();
			List<LoanRepaymentSchedule> newLoanRepaymentScheduleList = new ArrayList<LoanRepaymentSchedule>();
			ILoanAccountPropertyService loanAccountPropertyService = KLSServiceFactory.getLoanAccountPropertyService();
			Money amountDisbursed = MoneyUtil.getAccountingMoney(MasterHelper.populateAmountParam(loanDisbursementData.getAmountDisbursed())).getMoney();
			ILoanRepaymentScheduleDAO loanRepaymentScheduleDAO = KLSDataAccessFactory.getLoanRepaymentScheduleDAO();
			
			
			if (isFirstDisbursement) {
				if (pacsSuvikasVoucherNumber != null) {
					loanDisbursementData.setPacsSuvikasVoucherNumber(pacsSuvikasVoucherNumber.replaceAll("\\s", ""));
					boolean isSuvikasVoucherExit = disbursementDAO.isPacsSuvikasVoucherExist(
							loanDisbursementData.getPacsSuvikasVoucherNumber(),
							loanDisbursementData.getPacsSuvikasVoucherDate());
					if (isSuvikasVoucherExit) {
						logger.error("Error : Voucher amount is Disbursed:" + isSuvikasVoucherExit);
						throw new DataAccessException("Margin Voucher already used - "
								+ loanDisbursementData.getPacsSuvikasVoucherNumber());

					}
				}
				
				AccountingMoney shareAmountToBeDeducted = MoneyUtil.getAccountingMoney(MasterHelper
						.populateAmountParam(loanLocData.getShareAmount()));
				AccountingMoney insuranceAmountToBeDeducted = MoneyUtil.getAccountingMoney(MasterHelper
						.populateAmountParam(loanLocData.getInsuranceAmount()));
				AccountingMoney processingFee = MoneyUtil.getAccountingMoney(MasterHelper
						.populateAmountParam(loanLocData.getProcessingFee()));
				AccountingMoney marginAmount = MoneyUtil.getAccountingMoney(MasterHelper
						.populateAmountParam(loanApplicationData.getMarginalAmount()));

				balance = balance.add(shareAmountToBeDeducted).add(insuranceAmountToBeDeducted).add(processingFee); // Deductions

				AccountingMoney marginTransAmount = AccountingMoney.ZERO;

				if (loanDisbursementData.getDeductFrom().equals("L")) {
					marginTransAmount = marginAmount;
					logger.info("balance checking===="+loanDisbursementData.getAmountDisbursed()+",share amount=="+shareAmountToBeDeducted+"insuranceAmountToBeDeducted=="+insuranceAmountToBeDeducted+",processingFee=="+processingFee);
					balance = MoneyUtil.getAccountingMoney(
							MasterHelper.populateAmountParam(loanDisbursementData.getAmountDisbursed())).subtract(
							balance);
					if (balance.isDebit() && !balance.isZero()) {
						logger.error("Error : Disbursed amount is insufficient for deduction. Balance:" + balance);
						throw new DataAccessException("Disbursed amount is insufficient for deduction");
					}
				} else {

					marginTransAmount = balance.add(marginAmount);
					balance = MoneyUtil.getAccountingMoney(MasterHelper.populateAmountParam(loanDisbursementData
							.getAmountDisbursed()));
				}
				
				
				newLoanRepaymentScheduleList = loanAccountPropertyService.generateRepaymentScheduleByMultipleDisbursement(lineOfCredit, amountDisbursed, loanRepaymentScheduleList, DateUtil.getVSoftDateByString(loanDisbursementData.getDateOfDisbursement()), DateUtil.getVSoftDateByString(loanDisbursementData.getInstallmentDate()),"L",null);
				loanRepaymentScheduleDAO.saveLoanRepaymentScheduleList(newLoanRepaymentScheduleList);
				

				loanDisbursementData.setPacsSuvikasVoucherAmount(marginTransAmount.getMoney().getAmount().toString());
				if (loanDisbursementData.getPacsSuvikasVoucherNumber() != null) {
					String suvikasVoucherValidate = RestClientUtil.checkifMargnAmountAvailable(loanDisbursementData);

					if (!suvikasVoucherValidate.equals("0")) {
						logger.error("Error : Pacs Saviks voucher check failed.");
						throw new DataAccessException("Margin Voucher check failed. Loan Cannot Disburse");
					}
				}

				if (shareAmountToBeDeducted != null && !shareAmountToBeDeducted.isZero()) {
					loanDisbursementTransactionsForShare(loanDisbursementData, lineOfCredit, shareAmountToBeDeducted,
							loanApplicationData.getCustomerId(), voucherNumber);
					// loanDisbursementTransactionsForBorrowing(loanDisbursementData,
					// shareAmountToBeDeducted, lineOfCredit);
				}

				if (insuranceAmountToBeDeducted != null && !insuranceAmountToBeDeducted.isZero()) {
					loanDisbursementTransactionsForInsurance(loanDisbursementData, lineOfCredit,
							insuranceAmountToBeDeducted, voucherNumber);
					// loanDisbursementTransactionsForBorrowing(loanDisbursementData,
					// insuranceAmountToBeDeducted, lineOfCredit);
				}

				if (processingFee != null && !processingFee.isZero()) {
					loanDisbursementTransactionsForProcessingFee(loanDisbursementData, lineOfCredit, processingFee,
							voucherNumber);
					// loanDisbursementTransactionsForBorrowing(loanDisbursementData,
					// processingFee, lineOfCredit);
				}

				if (marginAmount != null && !marginAmount.isZero()) {
					loanDisbursementTransactionsForMargin(loanDisbursementData, lineOfCredit, marginAmount,
							voucherNumber);
				}

			} else {
				balance = MoneyUtil.getAccountingMoney(MasterHelper.populateAmountParam(loanDisbursementData
						.getAmountDisbursed()));
				
				if("M".equals(lineOfCredit.getProduct().getDisbursementType().getValue())) {
					loanRepaymentScheduleList = KLSDataAccessFactory.getLoanRepaymentScheduleDAO().getLoanRepaymentScheduleByLocIdAndBusinessDateForDisburshment(lineOfCredit.getId(), DateUtil.getVSoftDateByString(loanDisbursementData.getDateOfDisbursement()));
					newLoanRepaymentScheduleList = loanAccountPropertyService.generateRepaymentScheduleByMultipleDisbursement(lineOfCredit, amountDisbursed, loanRepaymentScheduleList, DateUtil.getVSoftDateByString(loanDisbursementData.getDateOfDisbursement()), DateUtil.getVSoftDateByString(loanDisbursementData.getInstallmentDate()) , "L",null);
					loanRepaymentScheduleDAO.updateLoanRepaymentScheduleList(newLoanRepaymentScheduleList);
				}
			}
			
			
			
			// Disbursement Transactions for remaining balance
			if (!balance.isZero()) {
				loanDisbursementTransactions(loanDisbursementData, lineOfCredit, balance, voucherNumber);
			}

			disbursementDAO.saveLoanDisbursement(PacsLoanDisbursementHelper.getLoanDisbursement(loanDisbursementData));

			updateDisbursementScheduledAmount(loanDisbursementData); // update
																		// the
																		// DisbursedAmount
																		// in
																		// DisbursementSchedule.
			Money withdrawBal = lineOfCredit.getSanctionedAmount().subtract(lineOfCredit.getCurrentBalance().getMoney());
			SmsData smsData = SmsDataService.getSmsData(lineOfCredit, balance, "W", "D", "DI", withdrawBal, "Amount Disbursed Successfully");
			//RestClientUtil.postSMSAlertData(smsData);
			response="T-"+voucherNumber.toString();
			/*if(!"O".equalsIgnoreCase(bankParameter.getCbsIntegrationMethod().getValue()) && "V".equalsIgnoreCase(bankParameter.getOther_cbs())){
				
				IPostVirmatiCBSTransactions postVirmatiCBSTransactions = KLSServiceFactory.getPostVirmatiCBSTransaction();
				boolean status=postVirmatiCBSTransactions.postVirmatiCBSDebitTransactionsForMT(loanDisbursementData);
				if(status){
					em.getTransaction().commit();
				}else{
					response="Error while posting CBS Transactions";
					throw new Exception();
				}
				
			 }else{
				 EntityManagerUtil.CommitOrRollBackTransaction(true);
			 }*/
			EntityManagerUtil.CommitOrRollBackTransaction(true);
			
			
		} catch (DataAccessException de) {
			EntityManagerUtil.CommitOrRollBackTransaction(false);
			logger.error("Unable to save PacsLoanDisbursement data ..in saveLoanDisbursement() method..");
			throw de;
		} catch (Exception e) {
			e.printStackTrace();
			EntityManagerUtil.CommitOrRollBackTransaction(false);
			logger.error("Unable to save PacsLoanDisbursement data ..in saveLoanDisbursement() method..");
			throw new DataAccessException("Unable to save Loan Disbursement Data", e.getCause());
		}

		logger.info("END: Successfully Saved saveLoanDisbursement data");
		return response;
	}

	public void updateDisbursementScheduledAmount(PacsLoanDisbursementData loanDisbursementData) {
		Money mainAmount = MasterHelper.populateAmountParam(loanDisbursementData.getAmountDisbursed());

		List<LoanDisbursementSchedule> getDisbursementScheduleList = KLSDataAccessFactory.getLoanDisbursementDAO()
				.getDisbursementSchedulesByDate(loanDisbursementData.getLineOfCreditId(),
						loanDisbursementData.getDateOfDisbursement());
		for (LoanDisbursementSchedule disbursementSchedule : getDisbursementScheduleList) {
			Money disbursmentDiffAmount = disbursementSchedule.getDisbursementAmount().subtract(
					disbursementSchedule.getDisbursedAmount());
			if (mainAmount.getAmount().doubleValue() > disbursmentDiffAmount.getAmount().doubleValue()) {
				mainAmount = mainAmount.subtract(disbursmentDiffAmount);
				disbursementSchedule.setDisbursedAmount(disbursementSchedule.getDisbursementAmount());
				KLSDataAccessFactory.getLoanDisbursementScheduleDAO()
						.saveLoanDisbursementSchedule(disbursementSchedule);
				logger.info("Start:deduct the Share Account Balance..(1) ");
			} else {
				disbursementSchedule.setDisbursedAmount(disbursementSchedule.getDisbursedAmount().add(mainAmount));
				logger.info("Start:deduct the Share Account Balance.. (2)");
				KLSDataAccessFactory.getLoanDisbursementScheduleDAO()
						.saveLoanDisbursementSchedule(disbursementSchedule);
				break;
			}
		}
	}

	public String getDisburshmentScheduleAmout(long id, String date) {
		logger.info("Start:Calling getDisburshmentScheduleAmout() method in LoanDisbursementService.. ");
		String disburshementAmount = null;
		try {
			disburshementAmount = KLSDataAccessFactory.getLoanDisbursementDAO().getDisburshmentScheduleAmout(id, date);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Unable to save loanApplication data ..in getDisburshmentScheduleAmout() method..");
			throw new DataAccessException("Unable to get Disburshment Schedule Amount in  Loan Disbursement data",
					e.getCause());
		}
		return disburshementAmount;
	}

	public void loanDisbursementTransactions(PacsLoanDisbursementData loanDisbursementData, LoanLineOfCredit lineOfCredit,
			AccountingMoney amountAfterDeductions, Integer voucherNumber) {
		// Debit to Loan Account and Credit to Savings Account.
		String applicationDate="";
		List<CurrentTransaction> currentTransactionsList = new ArrayList<CurrentTransaction>();
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		PersonData person=RestClientUtil.getCustomerById(loanDisbursementData.getCustomerId());
		Pacs pacs=pacsDao.getPacs(person.getPacsId());
		logger.info("pacs type::"+pacs.getPacsStatus());
		applicationDate=RestClientUtil.getPacsBusinessDate(pacs.getId(),pacs.getBranch().getId());
		// Debit
		CurrentTransaction fromCurrentTransaction = new CurrentTransaction();
		fromCurrentTransaction.setAccount(lineOfCredit.getAccount());
		fromCurrentTransaction.setAccountNumber(lineOfCredit.getAccount().getAccountNumber());
		
		if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
			
			fromCurrentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(applicationDate));
			
		}else{
			fromCurrentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(loanDisbursementData.getDateOfDisbursement()));
		}
		
		fromCurrentTransaction.setChannelType(ChannelType.SYSTEM);
		fromCurrentTransaction.setCrDr(-1);
		fromCurrentTransaction.setLineOfCredit(lineOfCredit);
		fromCurrentTransaction.setOpeningBalance(lineOfCredit.getCurrentBalance());
		fromCurrentTransaction.setPacs(lineOfCredit.getAccount().getAccountProperty().getPacs());
		fromCurrentTransaction.setPostedStatus(1);
		fromCurrentTransaction.setRemarks("For Loan amount Debit.");// To change
		fromCurrentTransaction.setTransactionAmount(amountAfterDeductions);
		fromCurrentTransaction.setTransactionCode(TransactionCode.PRINCIPAL_BAL);
		fromCurrentTransaction.setTransactionType(TransactionType.Transfer);
		fromCurrentTransaction.setVoucherNumber(TransactionType.Transfer.getValue() + "-" + voucherNumber);
		currentTransactionsList.add(fromCurrentTransaction);

		String modeOfDisbursement = loanDisbursementData.getModeOfDisbursed();

		logger.info("modeOfDisbursement: " + modeOfDisbursement);

		logger.info("Is Standalone status :" + loanDisbursementData.isStandAloneStatus());

		boolean flag = true;

		if (!loanDisbursementData.isStandAloneStatus()) {
			flag = false;
			// Credit the Saving Account is Pending
			if (modeOfDisbursement.equalsIgnoreCase("B")) {
				
				//Replace savings acc number with ccb acc number in Dr transaction.
				
				BorrowingsAccountPropertyData borPropertyData = KLSServiceFactory.getBorrowingAccountPropertyService().getAccountPropertyByProductIdPacsId(
						lineOfCredit.getAccount().getAccountProperty().getPacs().getId(), lineOfCredit.getProduct().getId());
				String transactionResponse ="";
				if(CBSMethodEnum.PROPRIETARY.equals(KLSDataAccessFactory.getBankParameter().getCbsIntegrationMethod())){
				transactionResponse = SuvikasService.postSuvikasTransaction(borPropertyData.getCcbAccountNumber(), 
						RequestType.DebitTxn, lineOfCredit.getAccount().getAccountProperty().getPacs().getBranch().getId(), 
						amountAfterDeductions, "towards disbursement");
				}
				else
					transactionResponse="0";
				if (transactionResponse.equals("0"))
				{
			
					// Credit the Saving Account is Pending
					if(CBSMethodEnum.PROPRIETARY.equals(KLSDataAccessFactory.getBankParameter().getCbsIntegrationMethod())){
					transactionResponse = SuvikasService.postSuvikasTransaction(lineOfCredit.getAccount().getAccountProperty()
							.getSavingsAccountNumber(), RequestType.CreditTxn, lineOfCredit.getAccount().getAccountProperty().getPacs().getBranch()
							.getId(), amountAfterDeductions, "towards disbursement");
					}
					else
						transactionResponse="0";
					if (transactionResponse.equals("0"))
						flag = true;
					
				}
			}

			//CashGl Transactions
			if (modeOfDisbursement.equalsIgnoreCase("C")) {
				//Pacs pacs = lineOfCredit.getAccount().getAccountProperty().getPacs();
				CurrentTransaction cashGLTransaction = new CurrentTransaction();
				cashGLTransaction.setAccount(lineOfCredit.getAccount());
				cashGLTransaction.setAccountNumber(pacs.getCashGl());
				
				if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
					
					cashGLTransaction.setBusinessDate(DateUtil.getVSoftDateByString(applicationDate));
					
				}else{
					cashGLTransaction.setBusinessDate(DateUtil.getVSoftDateByString(loanDisbursementData.getDateOfDisbursement()));
				}
				
				cashGLTransaction.setChannelType(ChannelType.SYSTEM);
				cashGLTransaction.setCrDr(1);
				cashGLTransaction.setPacs(pacs);
				cashGLTransaction.setPostedStatus(1);
				cashGLTransaction.setRemarks("For Cash GL Credit.");// To change
				cashGLTransaction.setTransactionAmount(amountAfterDeductions);
				cashGLTransaction.setTransactionCode(TransactionCode.CASH_TRANSFER);
				cashGLTransaction.setTransactionType(TransactionType.Transfer);
				cashGLTransaction.setVoucherNumber(TransactionType.Transfer.getValue() + "-" + voucherNumber);
				currentTransactionsList.add(cashGLTransaction);

				flag = true;
			}
		}
		if (flag) {
			// Debit and Credit the Transactions in Current Transaction table.
			KLSDataAccessFactory.getCurrentTransactionDAO().saveCurrentTransactionsList(currentTransactionsList);

			// Update Saving account balance for Credit transaction.

			// Update loan account balance for Debit transaction.
			lineOfCredit.setCurrentBalance(lineOfCredit.getCurrentBalance().add(
					AccountingMoney.valueOf(amountAfterDeductions.getMoney(), DebitOrCredit.DEBIT)));
			lineOfCredit.setFirstDueDate(DateUtil.getVSoftDateByString(loanDisbursementData.getInstallmentDate()));
			lineOfCredit.setLoanExpiryDate(DateUtil.getVSoftDateByString(loanDisbursementData.getLoanExpiryDate()));
			updateLineOfCredit(lineOfCredit);
		} else {
			logger.error("Suvikas Transaction Posting was not successful.Unable to post Loan Disbursement Transaction.");
			throw new DataAccessException("Suvikas Transaction Posting was not successful.Unable to post Loan Disbursement Transaction.");
		}

	}

	public void loanDisbursementTransactionsForShare(PacsLoanDisbursementData loanDisbursementData,
			LoanLineOfCredit lineOfCredit, AccountingMoney shareAmountToBeDeducted, Long customerId,
			Integer voucherNumber) {
		logger.info("Inside the loanDisbursementTransactionsForShare");

		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		PersonData person=RestClientUtil.getCustomerById(loanDisbursementData.getCustomerId());
		Pacs pacs=pacsDao.getPacs(person.getPacsId());
		logger.info("pacs type::"+pacs.getPacsStatus());
		String applicationDate=RestClientUtil.getPacsBusinessDate(pacs.getId(),pacs.getBranch().getId());
		List<CurrentTransaction> currentTransactionsList = new ArrayList<CurrentTransaction>();

		// Debit the Loan Account
		CurrentTransaction fromCurrentTransaction = new CurrentTransaction();
		if (loanDisbursementData.getDeductFrom().equals("L")) {
			fromCurrentTransaction.setAccount(lineOfCredit.getAccount());
			fromCurrentTransaction.setAccountNumber(lineOfCredit.getAccount().getAccountNumber());
			fromCurrentTransaction.setLineOfCredit(lineOfCredit);
			fromCurrentTransaction.setTransactionCode(TransactionCode.SHARE_TRANSFER);
			fromCurrentTransaction.setOpeningBalance(lineOfCredit.getCurrentBalance());
		} else {
			fromCurrentTransaction.setAccountNumber(lineOfCredit.getAccount().getAccountProperty().getPacs()
					.getMarginGl());
			fromCurrentTransaction.setTransactionCode(TransactionCode.MARGINAL_GL);
			fromCurrentTransaction.setOpeningBalance(AccountingMoney.ZERO);
		}
		
		if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
			
			fromCurrentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(applicationDate));
			
		}else{
			fromCurrentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(loanDisbursementData.getDateOfDisbursement()));
		}
		
		
		fromCurrentTransaction.setChannelType(ChannelType.SYSTEM);
		fromCurrentTransaction.setCrDr(-1);
		fromCurrentTransaction.setPacs(lineOfCredit.getAccount().getAccountProperty().getPacs());
		fromCurrentTransaction.setPostedStatus(1);
		fromCurrentTransaction.setRemarks("Share Amount.");
		fromCurrentTransaction.setTransactionAmount(shareAmountToBeDeducted);
		fromCurrentTransaction.setTransactionType(TransactionType.Transfer);
		fromCurrentTransaction.setVoucherNumber(TransactionType.Transfer.getValue() + "-" + voucherNumber);
		currentTransactionsList.add(fromCurrentTransaction);

		// Credit the Share Account
		// Commented as part of story SUB-843. Done
		ShareAccountData shareAccount = RestClientUtil.getMemberShareAccountByCustomerId(customerId);
		Money shareBalance;
		// Update share account balance for Credit transaction.

		if (shareAccount.getCurrentBalance() != null) {
			shareBalance = MoneyUtil.getMoney(shareAccount.getCurrentBalance());
		} else {
			shareBalance = Money.ZERO;
		}

		Integer noOfShares = (int) Math.round((shareAmountToBeDeducted.getMoney().getAmount().intValue())
				/ Integer.parseInt(shareAccount.getFaceValue().toString()));
		if (noOfShares > 0) {
			shareAmountToBeDeducted = MoneyUtil.getAccountingMoney(MasterHelper.populateAmountParam(
					noOfShares.toString()).multiply(shareAccount.getFaceValue()));
		} else {
			shareAmountToBeDeducted = AccountingMoney.ZERO;
		}

		// shareBalance.add(shareAmountToBeDeducted.getMoney());
		// shareAccount.setCurrentBalance(shareBalance.getAmount().doubleValue());
		if (!shareAmountToBeDeducted.isZero()) {
			ShareTransactionData shareTrnData = new ShareTransactionData();
			shareTrnData.setShareAccountId(shareAccount.getAccountId());
			shareTrnData.setTransDate(loanDisbursementData.getDateOfDisbursement()); // Date.now().toString()
			// shareTranData.setModeOfPayment();
			shareTrnData.setAmountTransfer(shareAmountToBeDeducted.getMoney().getAmount().doubleValue());
			shareTrnData.setRemarks("Credit transaction from KLS for MTLT");
			shareTrnData.setCrDr(new Integer("1"));
			shareTrnData.setPacsId(lineOfCredit.getAccount().getAccountProperty().getPacs().getId());
			if (loanDisbursementData.getDeductFrom().equals("L")) {
				shareTrnData.setToAccountNumber(lineOfCredit.getAccount().getAccountNumber());
			} else {
				shareTrnData.setToAccountNumber(lineOfCredit.getAccount().getAccountProperty().getPacs().getMarginGl());
			}
			shareTrnData.setVoucherNumber(TransactionType.Transfer.getValue() + "-" + voucherNumber);

			String response = RestClientUtil.addNewSharesToExistingAccountByMemberCode(shareTrnData); // Update
																										// the
																										// Share
																										// Account
																										// Balance.
			logger.info("Response for share transactin posted: " + response);
			if (response.equals("0")) {
				// Debit and Credit the Transactions in Current Transaction
				// table.
				KLSDataAccessFactory.getCurrentTransactionDAO().saveCurrentTransactionsList(currentTransactionsList);

				// Update loan account balance for Debit transaction.
				if (loanDisbursementData.getDeductFrom().equals("L")) {
					lineOfCredit.setCurrentBalance(lineOfCredit.getCurrentBalance().add(
							AccountingMoney.valueOf(shareAmountToBeDeducted.getMoney(), DebitOrCredit.DEBIT)));
					lineOfCredit.setFirstDueDate(DateUtil.getVSoftDateByString(loanDisbursementData.getInstallmentDate()));
					lineOfCredit.setLoanExpiryDate(DateUtil.getVSoftDateByString(loanDisbursementData.getLoanExpiryDate()));
					updateLineOfCredit(lineOfCredit);
				}
			} else {
				logger.error("Share Transaction Posting was not successful.Unable to post Loan Disbursement Transaction.");
				throw new DataAccessException(
						"Share Transaction Posting was not successful.Unable to post Loan Disbursement Transaction.");
			}
		}
	}

	public void loanDisbursementTransactionsForInsurance(PacsLoanDisbursementData loanDisbursementData,
			LoanLineOfCredit lineOfCredit, AccountingMoney insuranceAmountToBeDeducted, Integer voucherNumber) {
		logger.info("Inside the loanDisbursementTransactionsForInsurance");
		
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		PersonData person=RestClientUtil.getCustomerById(loanDisbursementData.getCustomerId());
		Pacs pacs=pacsDao.getPacs(person.getPacsId());
		logger.info("pacs type::"+pacs.getPacsStatus());
		String applicationDate=RestClientUtil.getPacsBusinessDate(pacs.getId(),pacs.getBranch().getId());
		
		IPacsGlMappingDAO glMapDao = KLSDataAccessFactory.getPacsGlMappingDAO();
		String insGL = glMapDao.getPacsGlMapping(lineOfCredit.getProduct().getId(),
				lineOfCredit.getAccount().getAccountProperty().getPacs().getId()).getInsuranceGl();
		List<CurrentTransaction> currentTransactionsList = new ArrayList<CurrentTransaction>();

		//
		// Credit
		CurrentTransaction toCurrentTransaction = new CurrentTransaction();
		toCurrentTransaction.setAccount(null);
		toCurrentTransaction.setAccountNumber(insGL);
		
		if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
			
			toCurrentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(applicationDate));
			
		}else{
			toCurrentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(loanDisbursementData.getDateOfDisbursement()));
		}
		
		toCurrentTransaction.setChannelType(ChannelType.SYSTEM);
		toCurrentTransaction.setCrDr(1);
		toCurrentTransaction.setLineOfCredit(null);
		toCurrentTransaction.setOpeningBalance(AccountingMoney.ZERO);
		toCurrentTransaction.setPacs(lineOfCredit.getAccount().getAccountProperty().getPacs());
		toCurrentTransaction.setPostedStatus(1);
		toCurrentTransaction.setRemarks("For Insurance Amount.(GL)");
		toCurrentTransaction.setTransactionAmount(insuranceAmountToBeDeducted);
		toCurrentTransaction.setTransactionCode(TransactionCode.INSURANCE_DEDUCTION_GL);
		toCurrentTransaction.setTransactionType(TransactionType.Transfer);
		toCurrentTransaction.setVoucherNumber(TransactionType.Transfer.getValue() + "-" + voucherNumber);
		currentTransactionsList.add(toCurrentTransaction);

		// Debit
		CurrentTransaction fromCurrentTransaction = new CurrentTransaction();
		if (loanDisbursementData.getDeductFrom().equals("L")) {
			fromCurrentTransaction.setAccount(lineOfCredit.getAccount());
			fromCurrentTransaction.setAccountNumber(lineOfCredit.getAccount().getAccountNumber());
			fromCurrentTransaction.setLineOfCredit(lineOfCredit);
			fromCurrentTransaction.setTransactionCode(TransactionCode.INSURANCE_DEDUCTION);
			fromCurrentTransaction.setOpeningBalance(lineOfCredit.getCurrentBalance());
		} else {
			fromCurrentTransaction.setAccountNumber(lineOfCredit.getAccount().getAccountProperty().getPacs()
					.getMarginGl());
			fromCurrentTransaction.setTransactionCode(TransactionCode.MARGINAL_GL);
			fromCurrentTransaction.setOpeningBalance(AccountingMoney.ZERO);
		}
	
		fromCurrentTransaction.setChannelType(ChannelType.SYSTEM);
		fromCurrentTransaction.setCrDr(-1);
		
	if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
			
			toCurrentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(applicationDate));
			
		}else{
			toCurrentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(loanDisbursementData.getDateOfDisbursement()));
		}
		
		fromCurrentTransaction.setPacs(lineOfCredit.getAccount().getAccountProperty().getPacs());
		fromCurrentTransaction.setPostedStatus(1);
		fromCurrentTransaction.setRemarks("For Insurance amount.");
		fromCurrentTransaction.setTransactionAmount(insuranceAmountToBeDeducted);
		fromCurrentTransaction.setTransactionType(TransactionType.Transfer);
		fromCurrentTransaction.setVoucherNumber(TransactionType.Transfer.getValue() + "-" + voucherNumber);
		currentTransactionsList.add(fromCurrentTransaction);

		// Debit and Credit the Transactions in Current Transaction table.
		KLSDataAccessFactory.getCurrentTransactionDAO().saveCurrentTransactionsList(currentTransactionsList);

		// Update share account balance for Credit transaction.

		if (loanDisbursementData.getDeductFrom().equals("L")) {
			// Update loan account balance for Debit transaction.
			lineOfCredit.setCurrentBalance(lineOfCredit.getCurrentBalance().add(
					AccountingMoney.valueOf(insuranceAmountToBeDeducted.getMoney(), DebitOrCredit.DEBIT)));
			lineOfCredit.setFirstDueDate(DateUtil.getVSoftDateByString(loanDisbursementData.getInstallmentDate()));
			lineOfCredit.setLoanExpiryDate(DateUtil.getVSoftDateByString(loanDisbursementData.getLoanExpiryDate()));
			updateLineOfCredit(lineOfCredit);
		}

	}

	public void loanDisbursementTransactionsForProcessingFee(PacsLoanDisbursementData loanDisbursementData,
			LoanLineOfCredit lineOfCredit, AccountingMoney processingFee, Integer voucherNumber) {
		logger.info("Inside the loanDisbursementTransactionsForProcessingFee");

		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		PersonData person=RestClientUtil.getCustomerById(loanDisbursementData.getCustomerId());
		Pacs pacs=pacsDao.getPacs(person.getPacsId());
		logger.info("pacs type::"+pacs.getPacsStatus());
		String applicationDate=RestClientUtil.getPacsBusinessDate(pacs.getId(),pacs.getBranch().getId());
		
		IPacsGlMappingDAO glMapDao = KLSDataAccessFactory.getPacsGlMappingDAO();
		String feeGL = glMapDao.getPacsGlMapping(lineOfCredit.getProduct().getId(),
				lineOfCredit.getAccount().getAccountProperty().getPacs().getId()).getProcessingFeeGl();

		// Credit
		List<CurrentTransaction> currentTransactionsList = new ArrayList<CurrentTransaction>();

		CurrentTransaction toCurrentTransaction = new CurrentTransaction();
		toCurrentTransaction.setAccount(null);
		toCurrentTransaction.setAccountNumber(feeGL);
		
		if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
			
			toCurrentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(applicationDate));
			
		}else{
			toCurrentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(loanDisbursementData.getDateOfDisbursement()));
		}
		
		toCurrentTransaction.setChannelType(ChannelType.SYSTEM);
		toCurrentTransaction.setCrDr(1);
		toCurrentTransaction.setLineOfCredit(null);
		toCurrentTransaction.setOpeningBalance(AccountingMoney.ZERO);
		toCurrentTransaction.setPacs(lineOfCredit.getAccount().getAccountProperty().getPacs());
		toCurrentTransaction.setPostedStatus(1);
		toCurrentTransaction.setRemarks("For Processing Fee.(GL)");
		toCurrentTransaction.setTransactionAmount(processingFee);
		toCurrentTransaction.setTransactionCode(TransactionCode.PROCESSING_FEE_GL);
		toCurrentTransaction.setTransactionType(TransactionType.Transfer);
		toCurrentTransaction.setVoucherNumber(TransactionType.Transfer.getValue() + "-" + voucherNumber);
		currentTransactionsList.add(toCurrentTransaction);

		// Debit
		CurrentTransaction fromCurrentTransaction = new CurrentTransaction();
		if (loanDisbursementData.getDeductFrom().equals("L")) {
			fromCurrentTransaction.setAccount(lineOfCredit.getAccount());
			fromCurrentTransaction.setAccountNumber(lineOfCredit.getAccount().getAccountNumber());
			fromCurrentTransaction.setLineOfCredit(lineOfCredit);
			fromCurrentTransaction.setTransactionCode(TransactionCode.PROCESSING_FEE);
			fromCurrentTransaction.setOpeningBalance(lineOfCredit.getCurrentBalance());
		} else {
			fromCurrentTransaction.setAccountNumber(lineOfCredit.getAccount().getAccountProperty().getPacs()
					.getMarginGl());
			fromCurrentTransaction.setTransactionCode(TransactionCode.MARGINAL_GL);
			fromCurrentTransaction.setOpeningBalance(AccountingMoney.ZERO);
		}
		
	if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
			
		fromCurrentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(applicationDate));
			
		}else{
			fromCurrentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(loanDisbursementData.getDateOfDisbursement()));
		}
		
		fromCurrentTransaction.setChannelType(ChannelType.SYSTEM);
		fromCurrentTransaction.setCrDr(-1);
		fromCurrentTransaction.setPacs(lineOfCredit.getAccount().getAccountProperty().getPacs());
		fromCurrentTransaction.setPostedStatus(1);
		fromCurrentTransaction.setRemarks("For Processing Fee.");
		fromCurrentTransaction.setTransactionAmount(processingFee);
		fromCurrentTransaction.setTransactionType(TransactionType.Transfer);
		fromCurrentTransaction.setVoucherNumber(TransactionType.Transfer.getValue() + "-" + voucherNumber);
		currentTransactionsList.add(fromCurrentTransaction);

		// Debit and Credit the Transactions in Current Transaction table.
		KLSDataAccessFactory.getCurrentTransactionDAO().saveCurrentTransactionsList(currentTransactionsList);

		// Update share account balance for Credit transaction.

		if (loanDisbursementData.getDeductFrom().equals("L")) {

			// Update loan account balance for Debit transaction.
			lineOfCredit.setCurrentBalance(lineOfCredit.getCurrentBalance().add(
					AccountingMoney.valueOf(processingFee.getMoney(), DebitOrCredit.DEBIT)));
			lineOfCredit.setFirstDueDate(DateUtil.getVSoftDateByString(loanDisbursementData.getInstallmentDate()));
			lineOfCredit.setLoanExpiryDate(DateUtil.getVSoftDateByString(loanDisbursementData.getLoanExpiryDate()));
			updateLineOfCredit(lineOfCredit);
		}

	}

	public void loanDisbursementTransactionsForMargin(PacsLoanDisbursementData loanDisbursementData,
			LoanLineOfCredit lineOfCredit, AccountingMoney marginAmount, Integer voucherNumber) {
		logger.info("Inside the loanDisbursementTransactionsForProcessingFee");
		// Debit Margin GL and credit Loan Account

		
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		PersonData person=RestClientUtil.getCustomerById(loanDisbursementData.getCustomerId());
		Pacs pacs=pacsDao.getPacs(person.getPacsId());
		logger.info("pacs type::"+pacs.getPacsStatus());
		String applicationDate=RestClientUtil.getPacsBusinessDate(pacs.getId(),pacs.getBranch().getId());
		
		
		
		// This Transaction done in Day End process.
		// Debit
		List<CurrentTransaction> currentTransactionsList = new ArrayList<CurrentTransaction>();

		CurrentTransaction toCurrentTransaction = new CurrentTransaction();
		// toCurrentTransaction.setAccount(lineOfCredit.getAccount());
		toCurrentTransaction.setAccountNumber(lineOfCredit.getAccount().getAccountProperty().getPacs().getMarginGl());
		
		if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
			
			toCurrentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(applicationDate));
				
			}else{
				toCurrentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(loanDisbursementData.getDateOfDisbursement()));
			}
		
		
		toCurrentTransaction.setChannelType(ChannelType.SYSTEM);
		toCurrentTransaction.setCrDr(-1);
		// toCurrentTransaction.setLineOfCredit(lineOfCredit);
		toCurrentTransaction.setOpeningBalance(AccountingMoney.ZERO);
		toCurrentTransaction.setPacs(lineOfCredit.getAccount().getAccountProperty().getPacs());
		toCurrentTransaction.setPostedStatus(1);
		toCurrentTransaction.setRemarks("For Marginal Amount Fee.");
		toCurrentTransaction.setTransactionAmount(marginAmount);
		toCurrentTransaction.setTransactionCode(TransactionCode.MARGINAL_GL);
		toCurrentTransaction.setTransactionType(TransactionType.Transfer);
		toCurrentTransaction.setVoucherNumber(TransactionType.Transfer.getValue() + "-" + voucherNumber);
		currentTransactionsList.add(toCurrentTransaction);

		// Credit
		CurrentTransaction fromCurrentTransaction = new CurrentTransaction();
		fromCurrentTransaction.setAccount(lineOfCredit.getAccount());
		fromCurrentTransaction.setAccountNumber(lineOfCredit.getAccount().getAccountNumber());
		
		
		if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
			
			fromCurrentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(applicationDate));
				
			}else{
				fromCurrentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(loanDisbursementData.getDateOfDisbursement()));
			}
		
		fromCurrentTransaction.setChannelType(ChannelType.SYSTEM);
		fromCurrentTransaction.setCrDr(1);
		fromCurrentTransaction.setLineOfCredit(lineOfCredit);
		fromCurrentTransaction.setOpeningBalance(lineOfCredit.getCurrentBalance());
		fromCurrentTransaction.setPacs(lineOfCredit.getAccount().getAccountProperty().getPacs());
		fromCurrentTransaction.setPostedStatus(1);
		fromCurrentTransaction.setRemarks("For Marginal Amount Fee.");
		fromCurrentTransaction.setTransactionAmount(marginAmount);
		fromCurrentTransaction.setTransactionCode(TransactionCode.MARGINAL);
		fromCurrentTransaction.setTransactionType(TransactionType.Transfer);
		fromCurrentTransaction.setVoucherNumber(TransactionType.Transfer.getValue() + "-" + voucherNumber);
		currentTransactionsList.add(fromCurrentTransaction);

		// Debit and Credit the Transactions in Current Transaction table.
		KLSDataAccessFactory.getCurrentTransactionDAO().saveCurrentTransactionsList(currentTransactionsList);

		// Update share account balance for Credit transaction.
		lineOfCredit.setCurrentBalance(lineOfCredit.getCurrentBalance().add(
				AccountingMoney.valueOf(marginAmount.getMoney(), DebitOrCredit.CREDIT)));
		lineOfCredit.setFirstDueDate(DateUtil.getVSoftDateByString(loanDisbursementData.getInstallmentDate()));
		lineOfCredit.setLoanExpiryDate(DateUtil.getVSoftDateByString(loanDisbursementData.getLoanExpiryDate()));
		updateLineOfCredit(lineOfCredit);

		// Save the new record in Loan Recovery Table for Marginal Amount.

		LoanRecovery loanRecovery = new LoanRecovery();
		loanRecovery.setLoanLineOfCredit(lineOfCredit);
		loanRecovery.setOutStandingBalance(Money.ZERO);
		loanRecovery.setInstallmentAmount(Money.ZERO);
		loanRecovery.setTotalInterestReceivable(Money.ZERO);
		loanRecovery.setTotalPenalInterestReceivable(Money.ZERO);
		loanRecovery.setTotalChargesReceivable(Money.ZERO);
		loanRecovery.setTotalReceivableAmount(Money.ZERO);
		loanRecovery.setPrincipalPaid(marginAmount.getMoney());
		loanRecovery.setInterestPaid(Money.ZERO);
		loanRecovery.setPenalInterestPaid(Money.ZERO);
		loanRecovery.setChargesPaid(Money.ZERO);
		loanRecovery.setAmountPaid(Money.ZERO);
		loanRecovery.setRecoveredDate(DateUtil.getVSoftDateByString(loanDisbursementData.getDateOfDisbursement()));
		loanRecovery.setTotalPrincipalReceivable(Money.ZERO);
		loanRecovery.setRecoveredBy("From Loan");

		KLSDataAccessFactory.getLoanRecoveryDAO().saveLoanRecovery(loanRecovery);
		;

	}

	public void loanDisbursementTransactionsForBorrowing(PacsLoanDisbursementData loanDisbursementData,
			AccountingMoney money, LoanLineOfCredit lineOfCredit) {

		// Credit
		// IBorrowingsAccountDAO borrowingsAccountDAO =
		// KLSDataAccessFactory.getBorrowingsAccountDAO();
		// String borrowingAccNo =
		// borrowingsAccountDAO.getBorrowingsAccNo(lineOfCredit.getAccount().getAccountProperty().getBranch().getCode(),
		// lineOfCredit.getAccount().getAccountProperty().getBranch().getId(),
		// lineOfCredit.getLoanAccountProperty().getPacs().getId(),
		// lineOfCredit.getAccount().getAccountProperty().getProduct().getId());
		// logger.info("Borrowing Account Number is::::::::::::: "+borrowingAccNo);
		// // Pending for Testing
		List<CurrentTransaction> currentTransactionsList = new ArrayList<CurrentTransaction>();
		CurrentTransaction toCurrentTransaction = new CurrentTransaction();
		toCurrentTransaction.setAccount(lineOfCredit.getAccount()); // Replace
		// with
		// Borrowing
		// related
		toCurrentTransaction.setAccountNumber("BRR10101010"); // Borrowing
		// Account.
		toCurrentTransaction
				.setBusinessDate(DateUtil.getVSoftDateByString(loanDisbursementData.getDateOfDisbursement()));
		toCurrentTransaction.setChannelType(ChannelType.SYSTEM);
		toCurrentTransaction.setCrDr(1);
		toCurrentTransaction.setLineOfCredit(lineOfCredit); // Replace with
		// Borrowing related
		toCurrentTransaction.setOpeningBalance(lineOfCredit.getCurrentBalance()); // Replace
		// with
		// Borrowing
		// related
		toCurrentTransaction.setPacs(lineOfCredit.getAccount().getAccountProperty().getPacs());
		toCurrentTransaction.setPostedStatus(1);
		toCurrentTransaction.setRemarks("For Borrowiing Amount Credit.");
		toCurrentTransaction.setTransactionAmount(money);
		toCurrentTransaction.setTransactionCode(TransactionCode.PRINCIPAL_BAL);
		toCurrentTransaction.setTransactionType(TransactionType.Transfer);
		toCurrentTransaction.setVoucherNumber(TransactionType.Transfer.getValue() + "-" + 1);
		currentTransactionsList.add(toCurrentTransaction);

		// Debit
		CurrentTransaction fromCurrentTransaction = new CurrentTransaction();
		fromCurrentTransaction.setAccount(lineOfCredit.getAccount()); // Replace
		// with
		// Bank
		// related
		fromCurrentTransaction.setAccountNumber("PBA1010101010"); // Pacs Bank
		// account.
		fromCurrentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(loanDisbursementData
				.getDateOfDisbursement()));
		fromCurrentTransaction.setChannelType(ChannelType.SYSTEM);
		fromCurrentTransaction.setCrDr(-1);
		fromCurrentTransaction.setLineOfCredit(lineOfCredit); // Replace with
		// Bank related
		fromCurrentTransaction.setOpeningBalance(lineOfCredit.getCurrentBalance()); // Replace
		// with
		// Bank
		// related
		fromCurrentTransaction.setPacs(lineOfCredit.getAccount().getAccountProperty().getPacs());
		fromCurrentTransaction.setPostedStatus(1);
		fromCurrentTransaction.setRemarks("For Marginal Amount Debit.");
		fromCurrentTransaction.setTransactionAmount(money);
		fromCurrentTransaction.setTransactionCode(TransactionCode.PRINCIPAL_BAL);
		fromCurrentTransaction.setTransactionType(TransactionType.Transfer);
		fromCurrentTransaction.setVoucherNumber(TransactionType.Transfer.getValue() + "-" + 1);
		currentTransactionsList.add(fromCurrentTransaction);

		// Debit and Credit the Transactions in Current Transaction table.
		KLSDataAccessFactory.getCurrentTransactionDAO().saveCurrentTransactionsList(currentTransactionsList);

		// Update Borrowing account balance for Credit transaction.

		// Update Bank account balance for Debit transaction.
	}

	public void loanDisbursementTransactionsForPacsLoanGl(PacsLoanDisbursementData loanDisbursementData,
			LoanLineOfCredit lineOfCredit) {
		// Credit
		List<CurrentTransaction> currentTransactionsList = new ArrayList<CurrentTransaction>();
		CurrentTransaction toCurrentTransaction = new CurrentTransaction();
		toCurrentTransaction.setAccount(lineOfCredit.getAccount());
		toCurrentTransaction.setAccountNumber("PSB10101010"); // Pacs SB Account
		// Credit.
		toCurrentTransaction
				.setBusinessDate(DateUtil.getVSoftDateByString(loanDisbursementData.getDateOfDisbursement()));
		toCurrentTransaction.setChannelType(ChannelType.SYSTEM);
		toCurrentTransaction.setCrDr(1);
		toCurrentTransaction.setLineOfCredit(lineOfCredit);
		toCurrentTransaction.setOpeningBalance(lineOfCredit.getCurrentBalance());
		toCurrentTransaction.setPacs(lineOfCredit.getAccount().getAccountProperty().getPacs());
		toCurrentTransaction.setPostedStatus(1);
		toCurrentTransaction.setRemarks("For Processing Fee.");
		toCurrentTransaction.setTransactionAmount(AccountingMoney.valueOf(
				MasterHelper.populateAmountParam(loanDisbursementData.getSanctionedAmount()), DebitOrCredit.CREDIT));
		toCurrentTransaction.setTransactionCode(TransactionCode.PRINCIPAL_BAL);
		toCurrentTransaction.setTransactionType(TransactionType.Transfer);
		toCurrentTransaction.setVoucherNumber(TransactionType.Transfer.getValue() + "-" + 1);
		currentTransactionsList.add(toCurrentTransaction);

		// Debit
		CurrentTransaction fromCurrentTransaction = new CurrentTransaction();
		fromCurrentTransaction.setAccount(lineOfCredit.getAccount());
		fromCurrentTransaction.setAccountNumber("PLGL1010101010"); // Pacs Loan
		// GL
		// account
		// Debit.
		fromCurrentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(loanDisbursementData
				.getDateOfDisbursement()));
		fromCurrentTransaction.setChannelType(ChannelType.SYSTEM);
		fromCurrentTransaction.setCrDr(-1);
		fromCurrentTransaction.setLineOfCredit(lineOfCredit);
		fromCurrentTransaction.setOpeningBalance(lineOfCredit.getCurrentBalance());
		fromCurrentTransaction.setPacs(lineOfCredit.getAccount().getAccountProperty().getPacs());
		fromCurrentTransaction.setPostedStatus(1);
		fromCurrentTransaction.setRemarks("For Marginal Amount Fee.");
		fromCurrentTransaction.setTransactionAmount(AccountingMoney.valueOf(
				MasterHelper.populateAmountParam(loanDisbursementData.getAmountDisbursed()), DebitOrCredit.DEBIT));
		fromCurrentTransaction.setTransactionCode(TransactionCode.PRINCIPAL_BAL);
		fromCurrentTransaction.setTransactionType(TransactionType.Transfer);
		fromCurrentTransaction.setVoucherNumber(TransactionType.Transfer.getValue() + "-" + 1);
		currentTransactionsList.add(fromCurrentTransaction);

		// Debit and Credit the Transactions in Current Transaction table.
		KLSDataAccessFactory.getCurrentTransactionDAO().saveCurrentTransactionsList(currentTransactionsList);

		// Update Borrowing account balance for Credit transaction.

		// Update Bank account balance for Debit transaction.
	}

	public void updateLineOfCredit(LineOfCredit lineOfCredit) {
		KLSDataAccessFactory.getLineOfCreditDAO().updateLineOfCredit(lineOfCredit);
	}
	
  @Override
	public LoanDisbursementEntryDataList getloanDisburseEntryList(String pacsId,String loanType){
		List<DisbursementPassingData> passingData=new ArrayList<DisbursementPassingData>();
		List<Object[]> passingDataList=new ArrayList<Object[]>();
		LoanDisbursementEntryDataList entryList=new LoanDisbursementEntryDataList();
		ILoanDisbursementDAO disbursementDAO=KLSDataAccessFactory.getLoanDisbursementDAO();
		passingDataList=disbursementDAO.getloanDisburseEntryList(pacsId,loanType);
		DisbursementPassingData passingdisburseData=new DisbursementPassingData();
		for(Object[] disburseData:passingDataList){
			passingdisburseData=LoanDisbursementHelper.getLoanDisbursementPassingData(disburseData);
			passingData.add(passingdisburseData);
		}
		entryList.setDisbursementEntryData(passingData);
		return entryList;
	}

  @Override
  public String updateMultipleDisbursement(MTMultipleDisburseData mtpassingList){
	  
	  Map<String, Object> infoMap = new HashMap<String, Object>();
		String jsonString = "";
		LoanLineOfCreditData list = new LoanLineOfCreditData();
	
		ILoanLineOfCreditService service = KLSServiceFactory.getLoanLineOfCreditService();
		list = service.getLineOfCreditDataById(Long.parseLong(mtpassingList.getLocId()));
			LoanDisbursementEntryData dataLst = new LoanDisbursementEntryData();
			Integer status=1;
			List<PacsLoanApplicationData> loanApplicationDataList = new ArrayList<PacsLoanApplicationData>();
			PacsLoanApplicationData loanApplicationData=new PacsLoanApplicationData();
			List<AccountData> accDataList= new ArrayList<AccountData>();
			AccountData accData=new AccountData();
			
			 ILoanDisbursementEntryService serviceData = KLSServiceFactory.getLoanDisbursementEntryService();
			 dataLst = serviceData.getDisbursementEntriesbasedOnLocId( Long.parseLong(mtpassingList.getMemberNum()), status, Long.parseLong(mtpassingList.getLocId()), "L","25/10/2016");
			loanApplicationData = KLSServiceFactory.getPacsLoanApplicationService().getLoanApplicationById(list.getLoanApplicationId());
			loanApplicationDataList.add(loanApplicationData);
			IAccountPropertyService accPropService = KLSServiceFactory.getAccountPropertyService();
			accData = accPropService.getAccountInfoByCustId(list.getLoanAccountPropertyData().getCustomerId());
			accDataList.add(accData);
		
			PacsLoanDisbursementData disburseDataList=new PacsLoanDisbursementData();
			disburseDataList=LoanDisbursementHelper.getPacsLoanDisbursementData(list,dataLst,loanApplicationDataList,accDataList);
			logger.info("from helper::"+disburseDataList);
			disburseDataList.setAmountDisbursed(mtpassingList.getDisburseAmt());
			disburseDataList.setModeOfDisbursed(mtpassingList.getDisburseMode());
			disburseDataList.setRemarks(mtpassingList.getRemarks());
			String voucherNumber = null;
			KLSResponse klsResponse=new KLSResponse();
		//	for(PacsLoanDisbursementData pacsDisbursementData:disburseDataList){
				try {
					//data = gson.fromJson(str, PacsLoanDisbursementData.class);
					ILoanDisbursementService disbursementService = KLSServiceFactory.getLoanDisbursementService();
					voucherNumber = disbursementService.saveLoanDisbursement(disburseDataList);
					klsResponse.setVoucherNumber("T-"+voucherNumber); //MT Disbursement always use Transfer voucher.
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("End : Calling LoanDisbursement service in update() method.");
					logger.info("Error:" + e.getMessage());
					klsResponse.setErrorMessage(e.getMessage());
					klsResponse.setResponseStatus("1");//failure
					
//					return "Loan Cannot disbursed!"; //Dont change this message. This message used in UI for validation
				} finally {
					EntityManagerUtil.closeSession();
				}
			//}
			//LoanDisbursementEntryData data = new LoanDisbursementEntryData();
			
			ILoanDisbursementEntryService disburseservice = KLSServiceFactory.getLoanDisbursementEntryService();
			disburseservice.updateLoanDisbursementEntry(dataLst);
			
  return jsonString;
  }

 public List<Map> getBulkDisbursementDetails(String loanType,String disbursementDate,String instrumentNo,String pacsId){
	 List<Map> disbursementMap=new ArrayList<Map>();
	 ILoanDisbursementDAO dao = KLSDataAccessFactory.getLoanDisbursementDAO();
	 List<Object[]> disbursementlist=new ArrayList<Object[]>();
	 DisbursementDetailsData disburseDetails=new DisbursementDetailsData();
	 IAccountPropertyService accPropService = KLSServiceFactory.getAccountPropertyService();
	 AccountData accData= new AccountData();
	 ILoanDisbursementEntryService service = KLSServiceFactory.getLoanDisbursementEntryService();
	 Map<String, Object> infoMap = new HashMap<String, Object>();
	 try{
		 disbursementlist= dao.getBulkDisbursementDetails(loanType,disbursementDate,instrumentNo,pacsId);
		 for(Object[] data:disbursementlist){
			 disburseDetails=LoanDisbursementHelper.getLoanDisbursementDetailsData(data);
			 PersonData personData=RestClientUtil.getAllCustomerDataByCusomterId(Long.parseLong(disburseDetails.getCustomerId()));
			 accData = accPropService.getAccountInfoByCustId(Long.parseLong(disburseDetails.getCustomerId()));
			infoMap = service.getLoanInfo(accData.getAccountId(),loanType, "D");
			infoMap.put("disbursementAmount", disburseDetails.getDisbursementAmt());
			infoMap.put("memberId", personData.getCustomerId());
			infoMap.put("memberNumber", personData.getMemberNumber());
			infoMap.put("memberName", personData.getName());
			infoMap.put("kccAccountNo",accData.getAccountNo());
			infoMap.put("accountId", accData.getAccountId());
			infoMap.put("id", disburseDetails.getId());
			infoMap.put("cardStatus", personData.getBankDetailList().get(0).getCardStatus());
			if(accData.getSavingBankAccNo()!=null)
			 infoMap.put("savingsNumber", accData.getSavingBankAccNo());
			disbursementMap.add(infoMap);
		 }
	 
	 }catch(Exception ex){
		 ex.printStackTrace();
	 }
	 return disbursementMap;
	 
 }
  
}
