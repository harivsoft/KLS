package com.vsoftcorp.kls.service.loan.impl;

import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.core.banking.data.CoreBankingResponse;
import com.vsoftcorp.core.banking.data.values.BalanceType;
import com.vsoftcorp.core.banking.data.values.RequestType;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.EventType;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.StLoanRecovery;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LoanAccountProperty;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.ChargesDebit;
import com.vsoftcorp.kls.business.entity.loan.ChargesRecovery;
import com.vsoftcorp.kls.business.entity.loan.LoanRecovery;
import com.vsoftcorp.kls.business.entity.loan.LoanRepaymentSchedule;
import com.vsoftcorp.kls.business.entity.loan.ProductChargesMapping;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.BulkCustomerData;
import com.vsoftcorp.kls.data.CustXLData;
import com.vsoftcorp.kls.data.LoanRecoveryData;
import com.vsoftcorp.kls.data.StLoanRecoveryData;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.IRecoveryOrderDao;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IChargesDebitDAO;
import com.vsoftcorp.kls.dataaccess.loan.IKLSCustomerDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanDisbursementDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanLineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanRecoveryDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanRepaymentScheduleDAO;
import com.vsoftcorp.kls.dataaccess.loan.IProductChargesMappingDAO;
import com.vsoftcorp.kls.service.account.ILoanLineOfCreditService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.LoanPaymentRecoveryHelper;
import com.vsoftcorp.kls.service.helper.StLoanRecoveryEntryHelper;
import com.vsoftcorp.kls.service.loan.ILoanRecoveryService;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.kls.service.util.SuvikasService;
import com.vsoftcorp.kls.service.util.VoucherNumberUtil;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.PacsStatus;
import com.vsoftcorp.kls.valuetypes.transaction.ChannelType;
import com.vsoftcorp.kls.valuetypes.transaction.RecoveryOrder;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionMode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;

/**
 * 
 * @author a1623
 * 
 */
public class LoanRecoveryService implements ILoanRecoveryService {
	private static final Logger logger = Logger.getLogger(LoanRecoveryService.class);

	@Override
	public LoanRecoveryData getLoanRecoveryDataByLocId(Long locId) {
		logger.info("Start : getting LoanRecovery details in getLoanRecoveryDataByLocId() method.");
		LoanRecoveryData loanRecoveryData = new LoanRecoveryData();
		try {
			ILoanLineOfCreditDAO lDao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
			LoanLineOfCredit loanLineOfCredit = lDao.getLoanLineOfCreditById(locId);

			BigDecimal totInterestReceivable = BigDecimal.ZERO;
			BigDecimal totPenalIntReceivable = BigDecimal.ZERO;
			BigDecimal totChargesReceivable = BigDecimal.ZERO;
			if (loanLineOfCredit.getInterestReceivable() != null)
				totInterestReceivable = loanLineOfCredit.getInterestReceivable();
			if (loanLineOfCredit.getPenalInterestReceivable() != null)
				totPenalIntReceivable = loanLineOfCredit.getPenalInterestReceivable();
			if (loanLineOfCredit.getChargesReceivable() != null)
				totChargesReceivable = loanLineOfCredit.getChargesReceivable().setScale(0, RoundingMode.HALF_UP);
			loanRecoveryData.setTotalChargesReceivable(totChargesReceivable.abs());

			if (loanLineOfCredit.getProduct().getAsAndWhenImplemented()) {
				totInterestReceivable = totInterestReceivable.add(loanLineOfCredit.getInterestAccrued());
				totPenalIntReceivable = totPenalIntReceivable.add(loanLineOfCredit.getOverdueInterest());
			}
			totInterestReceivable = totInterestReceivable.setScale(0, RoundingMode.HALF_UP);
			totPenalIntReceivable = totPenalIntReceivable.setScale(0, RoundingMode.HALF_UP);
			loanRecoveryData.setTotalInterestReceivable(totInterestReceivable.abs());
			loanRecoveryData.setTotalPenalInterestReceivable(totPenalIntReceivable.abs());

			logger.info("totChargesReceivable : " + totChargesReceivable);

			ILoanDisbursementDAO dDao = KLSDataAccessFactory.getLoanDisbursementDAO();
			Money disbursedAmount = dDao.getDisbursedAmountByLocId(locId);

			logger.info("disbursedAmount : " + disbursedAmount);

			ILoanRepaymentScheduleDAO rDao = KLSDataAccessFactory.getLoanRepaymentScheduleDAO();
			List<LoanRepaymentSchedule> repaymentList = rDao.getLoanRepaymentScheduleByLocIdAndBusinessDate(locId, LoanServiceUtil.getBusinessDate());
			BigDecimal sumPrincipalAmount = BigDecimal.ZERO;
			for (LoanRepaymentSchedule l : repaymentList) {
				sumPrincipalAmount = sumPrincipalAmount.add(l.getPrincipalAmount().getAmount());
			}
			logger.info("sumPrincipalAmount : " + sumPrincipalAmount);
			BigDecimal principalPaid = BigDecimal.ZERO;
			ILoanRecoveryDAO loanRecoveryDAO = KLSDataAccessFactory.getLoanRecoveryDAO();
			List<LoanRecovery> loanRecoveryList = loanRecoveryDAO.getLoanRecoveryByLocId(locId);
			logger.info("loanRecovery size : " + loanRecoveryList.size());

			for (LoanRecovery l : loanRecoveryList) {
				principalPaid = principalPaid.add(l.getPrincipalPaid().getAmount());
			}
			BigDecimal outStandingBalance = BigDecimal.ZERO;
			if (principalPaid != null)
				outStandingBalance = disbursedAmount.getAmount().subtract(principalPaid).setScale(0, RoundingMode.HALF_UP);
			else
				outStandingBalance = disbursedAmount.getAmount().setScale(2, RoundingMode.HALF_UP);
			loanRecoveryData.setOutstandingBalance(outStandingBalance.abs());

			logger.info("out---" + principalPaid);
			BigDecimal totalPrincipalReceivable = sumPrincipalAmount.subtract(principalPaid).setScale(0, RoundingMode.HALF_UP);
			logger.info("totalPrincipalReceivable : " + totalPrincipalReceivable);
			logger.info("totPenalIntReceivable : " + totPenalIntReceivable);
			// written below condition for first drawl.

			if (totalPrincipalReceivable.compareTo(BigDecimal.ZERO) == -1) {
				totalPrincipalReceivable = BigDecimal.ZERO;
			}
			loanRecoveryData.setTotalPrincipalReceivable(totalPrincipalReceivable.abs());
			BigDecimal totalReceivableAmount = (totInterestReceivable.add(totPenalIntReceivable).add(totChargesReceivable)
					.add(totalPrincipalReceivable.multiply(new BigDecimal("-1"))).setScale(2, RoundingMode.HALF_UP)).multiply(new BigDecimal("-1"));
			logger.info("totalReceivableAmount : " + totalReceivableAmount);
			loanRecoveryData.setTotalReceivableAmount(totalReceivableAmount.abs());
		} catch (Exception e) {
			logger.error("Error while getting the LoanRecovery data");
			throw new KlsRuntimeException("Error while getting the LoanRecovery data", e);
		}
		logger.info("Successfully got LoanRecovery details from getLoanRecoveryDataByLocId() method.");
		// TODO Auto-generated method stub
		return loanRecoveryData;
	}

	@Override
	public LoanRecoveryData getRocoveryInfoBasedOnAmountPaid(BigDecimal amountPaid, Integer recoverySequenceId, Long loanLocId) {
		logger.info("Start : getting LoanRecovery details in getRocoveryInfoBasedOnAmountPaid() method.");
		LoanRecoveryData loanRecoveryData = new LoanRecoveryData();
		try {
			IRecoveryOrderDao rDao = KLSDataAccessFactory.getRecoveryOrderDAO();
			List<EventType> seqList = rDao.getAllEventTypeBasedOnEventDefinition(recoverySequenceId, false);

			LoanRecoveryData loanRecoveryData1 = getLoanRecoveryDataByLocId(loanLocId);

			int a = 0;

			if (seqList.size() > 0) {
				for (EventType e : seqList) {

					if (e.getRecoverySequence() == RecoveryOrder.PRINCIPAL.getValue()) {
						a = amountPaid.compareTo(BigDecimal.ZERO);
						if (a == 1) {
							a = loanRecoveryData1.getTotalPrincipalReceivable().compareTo(BigDecimal.ZERO);
							if (a == 1) {
								a = amountPaid.compareTo(loanRecoveryData1.getTotalPrincipalReceivable());
								if (a == 1 || a == 0) {
									loanRecoveryData.setPrincipalPaid(loanRecoveryData1.getTotalPrincipalReceivable());
									amountPaid = amountPaid.subtract(loanRecoveryData1.getTotalPrincipalReceivable());

								} else {

									loanRecoveryData.setPrincipalPaid(amountPaid);
									amountPaid = BigDecimal.ZERO;
								}
							} else {
								loanRecoveryData.setPrincipalPaid(BigDecimal.ZERO);
							}
						} else {
							loanRecoveryData.setPrincipalPaid(BigDecimal.ZERO);
						}
					}

					if (e.getRecoverySequence() == RecoveryOrder.INTEREST.getValue()) {
						a = amountPaid.compareTo(BigDecimal.ZERO);
						if (a == 1) {

							a = loanRecoveryData1.getTotalInterestReceivable().compareTo(BigDecimal.ZERO);
							if (a == 1) {
								a = amountPaid.compareTo(loanRecoveryData1.getTotalInterestReceivable());
								if (a == 1 || a == 0) {
									loanRecoveryData.setInterestPaid(loanRecoveryData1.getTotalInterestReceivable());
									amountPaid = amountPaid.subtract(loanRecoveryData1.getTotalInterestReceivable());

								} else {
									loanRecoveryData.setInterestPaid(amountPaid);
									amountPaid = BigDecimal.ZERO;
								}
							} else {
								loanRecoveryData.setInterestPaid(BigDecimal.ZERO);
							}
						} else {
							loanRecoveryData.setInterestPaid(BigDecimal.ZERO);
						}

					}
					a = amountPaid.compareTo(BigDecimal.ZERO);

					if (e.getRecoverySequence() == RecoveryOrder.PENAL_INTEREST.getValue()) {
						a = amountPaid.compareTo(BigDecimal.ZERO);
						if (a == 1) {
							a = loanRecoveryData1.getTotalPenalInterestReceivable().compareTo(BigDecimal.ZERO);
							if (a == 1) {
								a = amountPaid.compareTo(loanRecoveryData1.getTotalPenalInterestReceivable());
								if (a == 1 || a == 0) {
									loanRecoveryData.setPenalInterestPaid(loanRecoveryData1.getTotalPenalInterestReceivable());
									amountPaid = amountPaid.subtract(loanRecoveryData1.getTotalPenalInterestReceivable());
								} else {
									loanRecoveryData.setPenalInterestPaid(amountPaid);
									amountPaid = BigDecimal.ZERO;
								}
							} else {
								loanRecoveryData.setPenalInterestPaid(BigDecimal.ZERO);
							}
						} else {
							loanRecoveryData.setPenalInterestPaid(BigDecimal.ZERO);
						}
					}

					if (e.getRecoverySequence() == RecoveryOrder.CHARGES.getValue()) {
						a = amountPaid.compareTo(BigDecimal.ZERO);
						if (a == 1) {
							a = loanRecoveryData1.getTotalChargesReceivable().compareTo(BigDecimal.ZERO);
							if (a == 1) {
								a = amountPaid.compareTo(loanRecoveryData1.getTotalChargesReceivable());
								if (a == 1 || a == 0) {

									loanRecoveryData.setChargesPaid(loanRecoveryData1.getTotalChargesReceivable());
									amountPaid = amountPaid.subtract(loanRecoveryData1.getTotalChargesReceivable());
								} else {
									loanRecoveryData.setChargesPaid(amountPaid);
									amountPaid = BigDecimal.ZERO;
								}
							} else {
								loanRecoveryData.setChargesPaid(BigDecimal.ZERO);
							}
						} else {
							loanRecoveryData.setChargesPaid(BigDecimal.ZERO);
						}

					}

				}
				a = amountPaid.compareTo(BigDecimal.ZERO);
				if (a == 1) {

					loanRecoveryData.setPrincipalPaid(loanRecoveryData1.getTotalPrincipalReceivable().add(amountPaid));

				}
				loanRecoveryData.setOutstandingBalanceAfterPayment(loanRecoveryData1.getOutstandingBalance().subtract(
						loanRecoveryData.getPrincipalPaid()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting the LoanRecovery data");
			throw new KlsRuntimeException("Error while getting the LoanRecovery data", e);
		}
		logger.info("Successfully got LoanRecovery details from getRocoveryInfoBasedOnAmountPaid() method.");
		return loanRecoveryData;
	}

	@Override
	public String saveLoanRecovery(LoanRecoveryData data) {

		logger.info("Start: Inside loan recovery service to recover the loan in saveLoanRecovery()");
		ILoanLineOfCreditDAO ldao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
		ILoanRecoveryDAO dao = KLSDataAccessFactory.getLoanRecoveryDAO();
		logger.info(" loan loc id : " + data.getLocId());
		String voucherNumber = null;
		try {

			LoanLineOfCredit loanLoc = ldao.getLoanLineOfCreditById(data.getLocId());

			String transactionMode = data.getModeOfPayment();
			TransactionMode transMode = null;
			if (transactionMode != null) {
				transMode = TransactionMode.getType(transactionMode);
			}
			logger.info(" transaction mode : " + transMode);
			BigDecimal transactionAmount = data.getAmountPaid();
			AccountingMoney transAmount = AccountingMoney.ZERO;
			if (transactionAmount != null) {
				transAmount = MoneyUtil.getAccountingMoney(transactionAmount);
			}
			boolean isCashMode = TransactionMode.Cash.equals(transMode);
			logger.info(" transaction amount : " + transAmount);
			if(TransactionMode.TRANSFER_FROM_PACS_SB_ACCOUNT.equals(transMode)){
				voucherNumber = saveRecovery(dao, data, loanLoc);
			} else if (isCashMode) {
				voucherNumber = saveRecovery(dao, data, loanLoc);
				
				/*SmsData smsData = SmsDataService.getSmsData(loanLoc, transAmount, "D", "C", "RE", Money.ZERO, "Amount Recovered Successfully");
				RestClientUtil.postSMSAlertData(smsData);*/
			}else if (TransactionMode.TRANSFER_FROM_MEMBER_SB_ACCOUNT.equals(transMode)) {
				voucherNumber = saveRecovery(dao, data, loanLoc);
			}
			else {
				LoanAccountProperty loanAccountProperty = loanLoc.getLoanAccountProperty();
				logger.info(" Savings account number : " + loanAccountProperty.getAccount().getAccountProperty().getSavingsAccountNumber());
				if (loanAccountProperty.getAccount().getAccountProperty().getSavingsAccountNumber() != null) {
					CoreBankingResponse response = SuvikasService.checkAccountAndBalance(loanAccountProperty.getAccount().getAccountProperty()
							.getSavingsAccountNumber(), RequestType.BalInq);
					if (response != null) {
						AccountingMoney savingsBalance = response.getBalances().get(BalanceType.Avail);
						logger.info(" savingsBalance : " + savingsBalance);
						if (!transAmount.isZero() && transAmount.isCredit() && (transAmount.compareTo(savingsBalance) <= 0)) {
							Integer branchId = loanAccountProperty.getAccount().getAccountProperty().getPacs().getBranch().getId();
							logger.info(" branchId : " + branchId);
							String transactionResponse = SuvikasService.postSuvikasTransaction(loanAccountProperty.getAccount().getAccountProperty()
									.getSavingsAccountNumber(), RequestType.DebitTxn, branchId, transAmount, "towards recovery");
							if (transactionResponse.equals("0")) {
								voucherNumber = saveRecovery(dao, data, loanLoc);
								/*SmsData smsData = SmsDataService.getSmsData(loanLoc, transAmount, "T", "C", "RE", Money.ZERO, "Amount Recovered Successfully");
								RestClientUtil.postSMSAlertData(smsData);*/
							} else {
								return "Suvikas Transaction Posting was not successful.Loan Recovery cannot be done.";
							}
						} else {
							return "Transaction Amount is zero or greater than Savings Balance Amount.Loan Recovery cannot be done.";
						}
					} else {
						return "Savings Balance cannot be fetched.Loan Recovery cannot be done.";
					}
				} else {
					logger.info("Savings Account Number does not exists.Loan Recovery cannot be done.");
					return "Savings Account Number does not exists.Loan Recovery cannot be done.";
				}
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error(" Exception while doing loan recovery.");
			throw new KlsRuntimeException("Exception while doing loan recovery.");
		}
		logger.info("End: Inside loan recovery service to recover the loan in saveLoanRecovery()");
		return "Loan Recovered Successfully, with voucher number : " + voucherNumber;

	}

	@Override
	public List<Map> getAccountByModeOfPayment(String modeOfPayment, Long loanLocId) {

		logger.info("Start: Get the loan loc list using customer id in getAccountByModeOfPayment() method.");
		logger.info("modeOfPayment : " + modeOfPayment);
		ILoanLineOfCreditDAO dao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
		List<Map> listMap = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		try {
			LoanLineOfCredit loanLoc = dao.getLoanLineOfCreditById(loanLocId);
			if (loanLoc != null) {
				String savingsAccNumber = loanLoc.getLoanAccountProperty().getAccount().getAccountProperty().getSavingsAccountNumber();
				if (modeOfPayment.equalsIgnoreCase(TransactionMode.SB_Account_Of_Branch_Of_Bank.getValue())) {
					if (savingsAccNumber != null) {
						map.put("savingsAccountNumber", savingsAccNumber);
					}
				}
			}
			listMap.add(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving the loan loc list based on cust id from the database");
			throw new KlsRuntimeException("Error while retriving the loan loc list based on cust id.");
		}
		return listMap;
	}

	public String loanRecoveryTransactions(LoanRecoveryData loanRecoveryData, LoanLineOfCredit lineOfCredit) {
		logger.info("Start: inside loanRecoveryTransactions()");
		Long custId = lineOfCredit.getLoanAccountProperty().getAccount().getAccountProperty().getCustomerId();
		logger.info(" customer id : "+ custId);
		// lineOfCredit.getLoanAccountProperty().getAccount().getAccountProperty().getCustomerId();
		// Debit to Loan Account and Credit to Savings Account.
		int val = 0;
		Integer voucherNumber = -1;
		Pacs pacs = lineOfCredit.getLoanAccountProperty().getAccount().getAccountProperty().getPacs();
		Integer pacsId = pacs.getId();
		logger.info("pacsId : " + pacsId);
		List<CurrentTransaction> currentTransactionsList = new ArrayList<CurrentTransaction>();
		String cashGl = pacs.getCashGl();
		String accountNumber = lineOfCredit.getAccount().getAccountNumber();
		String transactionModeString = loanRecoveryData.getModeOfPayment();
		String channelType = null;
		TransactionMode transactionMode = TransactionMode.Cash;
		if (transactionModeString != null) {
			transactionMode = TransactionMode.getType(transactionModeString);
		}
		if(loanRecoveryData.getChannelType() != null){
			channelType = loanRecoveryData.getChannelType();
		}
		TransactionType transactionType = null;
		if (TransactionMode.Cash.equals(transactionMode))  
			transactionType = TransactionType.Deposit;
		 else 
			transactionType = TransactionType.Transfer;
		 
		
		voucherNumber = VoucherNumberUtil.getVoucherNumber(pacsId.toString(), transactionType.getValue());
		logger.info("voucherNumber : " + voucherNumber);
		String voucher = transactionType.getValue() + "-" + voucherNumber;
		logger.info("voucher : " + voucher);
		
		
		if (TransactionMode.TRANSFER_FROM_MEMBER_SB_ACCOUNT.equals(transactionMode)) {
			// Debit collection amount for pacs SB acc while repayment at branch
			channelType = ChannelType.BRANCH_MEM_SB.getValue();
			populateCurrentTransactionRecordForBranchTransaction(lineOfCredit, pacs.getPacsBankAccNumber(), currentTransactionsList, TransactionCode.PACS_BANK_ACC, "collection amount from Former SB A/c", -1,
					MoneyUtil.getAccountingMoney(Money.valueOf("INR", loanRecoveryData.getAmountPaid())), false, transactionType, voucher,channelType);
		}
		if (TransactionMode.Cash.equals(transactionMode) && ChannelType.BRANCH.getValue().equals(channelType)) {
			// Debit collection amount for pacs SB acc while repayment at branch
			channelType = ChannelType.BRANCH.getValue();
			populateCurrentTransactionRecordForBranchTransaction(lineOfCredit, pacs.getPacsBankAccNumber(), currentTransactionsList, TransactionCode.PACS_BANK_ACC, "collection amount from CashInHand", -1,
					MoneyUtil.getAccountingMoney(Money.valueOf("INR", loanRecoveryData.getAmountPaid())), false, transactionType, voucher, channelType);
		} 
		if (TransactionMode.Cash.equals(transactionMode) && !ChannelType.BRANCH.getValue().equals(channelType)) {
			// Debit
			populateCurrentTransactionRecord(lineOfCredit, cashGl, currentTransactionsList, TransactionCode.CASH_TRANSFER, "collection amount for pacs Cash Gl", -1,
					MoneyUtil.getAccountingMoney(Money.valueOf("INR", loanRecoveryData.getAmountPaid())), false, transactionType, voucher);
		}
		if (TransactionMode.TRANSFER_FROM_PACS_SB_ACCOUNT.equals(transactionMode) && ChannelType.BRANCH.getValue().equals(channelType)) {
			// Debit collection amount for pacs SB acc while repayment at branch
			channelType = ChannelType.BRANCH_PACS_SB.getValue();
			populateCurrentTransactionRecordForBranchTransaction(lineOfCredit, pacs.getPacsBankAccNumber(), currentTransactionsList, TransactionCode.PACS_BANK_ACC, "collection amount from pacs SB A/c", -1,
					MoneyUtil.getAccountingMoney(Money.valueOf("INR", loanRecoveryData.getAmountPaid())), false, transactionType, voucher, channelType);
			channelType = ChannelType.BRANCH.getValue(); // changing chanel type to restrict below debit transaction.
		}
		if (TransactionMode.TRANSFER_FROM_PACS_SB_ACCOUNT.equals(transactionMode) && !ChannelType.BRANCH.getValue().equals(channelType)) {
			// Debit
			populateCurrentTransactionRecord(lineOfCredit, pacs.getPacsBankAccNumber(), currentTransactionsList, TransactionCode.PACS_BANK_ACC, "collection amount for pacs SB A/c", -1,
					MoneyUtil.getAccountingMoney(Money.valueOf("INR", loanRecoveryData.getAmountPaid())), false, transactionType, voucher);
		}
		channelType = ChannelType.BRANCH.getValue();
		// Credit
		populateCurrentTransactionRecord(lineOfCredit, accountNumber, currentTransactionsList, TransactionCode.COLLECTION,
				"For Loan Recovery Credit.", 1, MoneyUtil.getAccountingMoney(Money.valueOf("INR", loanRecoveryData.getAmountPaid())), false,
				transactionType, voucher);

		// Debit
		populateCurrentTransactionRecord(lineOfCredit, accountNumber, currentTransactionsList, TransactionCode.COLLECTION,
				"For Loan Recovery Debit.", -1, MoneyUtil.getAccountingMoney(Money.valueOf("INR", loanRecoveryData.getAmountPaid())), false,
				transactionType, voucher);
		Boolean asAndWhenImplemented = lineOfCredit.getProduct().getAsAndWhenImplemented();
		val = loanRecoveryData.getPrincipalPaid().compareTo(BigDecimal.ZERO);
		if (val == 1) {
			//new change
			lineOfCredit.setCurrentBalance(lineOfCredit.getCurrentBalance().add(MoneyUtil.getAccountingMoney(loanRecoveryData.getPrincipalPaid())));

			// principal credit
			if(ChannelType.BRANCH.getValue().equals(channelType)){
				populateCurrentTransactionRecordForBranchTransaction(lineOfCredit, accountNumber, currentTransactionsList, TransactionCode.PRINCIPAL_BAL,
					"For Loan Recovery Principal Credit.", 1,
					MoneyUtil.getAccountingMoney(Money.valueOf("INR", loanRecoveryData.getPrincipalPaid())), false, transactionType, voucher,channelType);
			}else{
				populateCurrentTransactionRecord(lineOfCredit, accountNumber, currentTransactionsList, TransactionCode.PRINCIPAL_BAL,
						"For Loan Recovery Principal Credit.", 1,
						MoneyUtil.getAccountingMoney(Money.valueOf("INR", loanRecoveryData.getPrincipalPaid())), false, transactionType, voucher);
			}
		}
		
		// interest credit
		val = loanRecoveryData.getInterestPaid().compareTo(BigDecimal.ZERO);
		if (val == 1) {
			if (!asAndWhenImplemented)
				lineOfCredit.setInterestReceivable(lineOfCredit.getInterestReceivable().add(loanRecoveryData.getInterestPaid()));
			if(ChannelType.BRANCH.getValue().equals(channelType)){
				populateCurrentTransactionRecordForBranchTransaction(lineOfCredit, accountNumber, currentTransactionsList, TransactionCode.INTEREST,
					"For Loan Recovery Interest Credit.", 1, MoneyUtil.getAccountingMoney(Money.valueOf("INR", loanRecoveryData.getInterestPaid())),
					false, transactionType, voucher, channelType);
			}else{
				populateCurrentTransactionRecord(lineOfCredit, accountNumber, currentTransactionsList, TransactionCode.INTEREST,
						"For Loan Recovery Interest Credit.", 1, MoneyUtil.getAccountingMoney(Money.valueOf("INR", loanRecoveryData.getInterestPaid())),
						false, transactionType, voucher);
			}
		}
		// penal interest credit
		val = loanRecoveryData.getPenalInterestPaid().compareTo(BigDecimal.ZERO);
		if (val == 1) {
			if (!asAndWhenImplemented)
				lineOfCredit.setPenalInterestReceivable(lineOfCredit.getPenalInterestReceivable().add(loanRecoveryData.getPenalInterestPaid()));
			
			if(ChannelType.BRANCH.getValue().equals(channelType)){
				populateCurrentTransactionRecordForBranchTransaction(lineOfCredit, accountNumber, currentTransactionsList, TransactionCode.PENAL_INTEREST,
						"For Loan Recovery Penal Interest Credit.", 1,
						MoneyUtil.getAccountingMoney(Money.valueOf("INR", loanRecoveryData.getPenalInterestPaid())), false, transactionType, voucher, channelType);
			}else{
				populateCurrentTransactionRecord(lineOfCredit, accountNumber, currentTransactionsList, TransactionCode.PENAL_INTEREST,
						"For Loan Recovery Penal Interest Credit.", 1,
						MoneyUtil.getAccountingMoney(Money.valueOf("INR", loanRecoveryData.getPenalInterestPaid())), false, transactionType, voucher);
			}
		}
		// charges credit
		val = loanRecoveryData.getChargesPaid().compareTo(BigDecimal.ZERO);
		if (val == 1) {
			lineOfCredit.setChargesReceivable(lineOfCredit.getChargesReceivable().add(loanRecoveryData.getChargesPaid()));

			populateCurrentTransactionRecord(lineOfCredit, accountNumber, currentTransactionsList, TransactionCode.CHARGES_RECEIVABLE,
					"For Loan Recovery Charges Credit.", 1, MoneyUtil.getAccountingMoney(Money.valueOf("INR", loanRecoveryData.getChargesPaid())),
					false, transactionType, voucher);
		}
		AccountingMoney transactionAmount = AccountingMoney.ZERO;
		BigDecimal totalAmtPaid = loanRecoveryData.getAmountPaid();
		if (totalAmtPaid != null) {
			transactionAmount = MoneyUtil.getAccountingMoney(totalAmtPaid);
		}
		if (TransactionMode.SB_Account_Of_Branch_Of_Bank.equals(transactionMode)) {
			String savingsAccNumber = loanRecoveryData.getSavingsAccountNumber();
			logger.info("savingsAccNumber : " + savingsAccNumber);
			populateCurrentTransactionRecord(lineOfCredit, savingsAccNumber, currentTransactionsList, TransactionCode.COLLECTION, "recovery loc no. "
					+ lineOfCredit.getId() + " from SB account in branch", -1, transactionAmount, false, transactionType, voucher);
		}
		// Cash in Transit GL
		Boolean isStandaloneMode = loanRecoveryData.getStandAloneStatus();
		transactionAmount = MoneyUtil.getAccountingMoney(loanRecoveryData.getAmountPaid().subtract(loanRecoveryData.getChargesPaid()));
		val = transactionAmount.compareTo(AccountingMoney.ZERO);
		if (val == 1) {
			if (isStandaloneMode) {
				String transRequired = pacs.getIsBorrwingTransRequiredInGLExtract();
				logger.info("transRequired : " + transRequired);
				if (transRequired.equalsIgnoreCase("Y")) {
					String cashInTransitGl = pacs.getCashInTransitGL();
					if (cashInTransitGl != null) {
						if (!TransactionMode.TRANSFER_FROM_PACS_SB_ACCOUNT.equals(transactionMode) && !TransactionMode.TRANSFER_FROM_MEMBER_SB_ACCOUNT.equals(transactionMode)
								&& !ChannelType.BRANCH.getValue().equals(channelType) ) {
//							transactionAmount = MoneyUtil.getAccountingMoney(loanRecoveryData.getAmountPaid().subtract(loanRecoveryData.getChargesPaid()));
							logger.info("Cash In Transit GL: " + cashInTransitGl);
							populateCurrentTransactionRecord(lineOfCredit, cashInTransitGl, currentTransactionsList, TransactionCode.CASH_IN_TRANSIT,
									"recovery loc no. " + lineOfCredit.getId() + "Cash in transit GL", 1, transactionAmount, true, transactionType, voucher);
						}
					} else {
						logger.info("Cash in transit Gl is not availabe for pacs id : " + pacs.getId());
						throw new KlsRuntimeException("Cash in transit Gl is not availabe for pacs id : " + pacs.getId());
					}
				} else {
					logger.info("Cash in transit Gl is not required..");
				}
			} else {
				String pacsBankAccount = pacs.getPacsBankAccNumber();
				if (pacsBankAccount != null) {
					//				transactionAmount = MoneyUtil.getAccountingMoney(loanRecoveryData.getAmountPaid().subtract(loanRecoveryData.getChargesPaid()));
					logger.info("Pacs bank account: " + pacsBankAccount);
					populateCurrentTransactionRecord(lineOfCredit, pacsBankAccount, currentTransactionsList, TransactionCode.PACS_BANK_ACC,
							"recovery loc no. " + lineOfCredit.getId() + "Pacs bank account", 1, transactionAmount, true, transactionType, voucher);
				} else {
					logger.info("Pacs bank account is not availabe for pacs id : " + pacs.getId());
					throw new KlsRuntimeException("Pacs bank account is not availabe for pacs id : " + pacs.getId());
				}
			}
		}
		// As and When functionality
		if (asAndWhenImplemented) {
			logger.info("---As and When Implemented----");
			BigDecimal intAccrued = lineOfCredit.getInterestAccrued().abs().setScale(0, RoundingMode.HALF_UP);
			BigDecimal penalIntAccrued = lineOfCredit.getOverdueInterest().abs().setScale(0, RoundingMode.HALF_UP);
			int temp = intAccrued.compareTo(BigDecimal.ZERO);
			int temp2 = penalIntAccrued.compareTo(BigDecimal.ZERO);
			if (temp != 0 || temp2 != 0) {
				voucherNumber = VoucherNumberUtil.getVoucherNumber(pacsId.toString(), TransactionType.Transfer.getValue());
				voucher = TransactionType.Transfer.getValue() + "-" + voucherNumber;

				if (temp != 0) {
					BigDecimal interestPaid = loanRecoveryData.getInterestPaid();
					BigDecimal totalIntReceivable = loanRecoveryData.getTotalInterestReceivable();
					BigDecimal intRec = lineOfCredit.getInterestReceivable().abs();

					if (totalIntReceivable.compareTo(interestPaid) == 0) {
						AccountingMoney transAmt = MoneyUtil.getAccountingMoney(intAccrued.abs().setScale(0, RoundingMode.HALF_UP));
						lineOfCredit.setInterestAccrued(BigDecimal.ZERO);
						lineOfCredit.setInterestReceivable(BigDecimal.ZERO);
						populateCurrentTransactionRecord(lineOfCredit, accountNumber, currentTransactionsList, TransactionCode.INTEREST_RECEIVABLE,
								"Interest Accrued posted Receivable ", -1, transAmt, false, transactionType, voucher);
						populateCurrentTransactionRecord(lineOfCredit, accountNumber, currentTransactionsList, TransactionCode.INTEREST_RECEIVED,
								"Interest Accrued posted Received", 1, transAmt, false, transactionType, voucher);
					} else {
						if (intRec.compareTo(interestPaid) <= 0) {
							lineOfCredit.setInterestReceivable(BigDecimal.ZERO);
							BigDecimal intAccruedToBePaid = interestPaid.subtract(intRec);
							intAccrued = intAccrued.subtract(intAccruedToBePaid);
							lineOfCredit.setInterestAccrued(intAccrued.negate());
							AccountingMoney transAmt = MoneyUtil.getAccountingMoney(intAccruedToBePaid.abs().setScale(0, RoundingMode.HALF_UP));
							populateCurrentTransactionRecord(lineOfCredit, accountNumber, currentTransactionsList,
									TransactionCode.INTEREST_RECEIVABLE, "Interest Accrued posted Receivable ", -1, transAmt, false, transactionType,
									voucher);
							populateCurrentTransactionRecord(lineOfCredit, accountNumber, currentTransactionsList, TransactionCode.INTEREST_RECEIVED,
									"Interest Accrued posted Received", 1, transAmt, false, transactionType, voucher);
						} else
							lineOfCredit.setInterestReceivable(intRec.subtract(interestPaid).negate());
					}
				}
				if (temp2 != 0) {
					BigDecimal penalInterestPaid = loanRecoveryData.getPenalInterestPaid();
					BigDecimal totalPenalIntReceivable = loanRecoveryData.getTotalPenalInterestReceivable();
					BigDecimal penalIntRec = lineOfCredit.getPenalInterestReceivable().abs();
					if (totalPenalIntReceivable.compareTo(penalInterestPaid) == 0) {
						AccountingMoney transAmt = MoneyUtil.getAccountingMoney(penalIntAccrued.abs().setScale(0, RoundingMode.HALF_UP));
						lineOfCredit.setOverdueInterest(BigDecimal.ZERO);
						lineOfCredit.setPenalInterestReceivable(BigDecimal.ZERO);
						populateCurrentTransactionRecord(lineOfCredit, accountNumber, currentTransactionsList,
								TransactionCode.PENAL_INTEREST_RECEIVABLE, "Penal Interest Accrued posted Receivable ", -1, transAmt, false,
								transactionType, voucher);
						populateCurrentTransactionRecord(lineOfCredit, accountNumber, currentTransactionsList,
								TransactionCode.PENAL_INTEREST_RECEIVED, "Penal Interest Accrued posted Received", 1, transAmt, false,
								transactionType, voucher);
					} else {
						if (penalIntRec.compareTo(penalInterestPaid) <= 0) {
							lineOfCredit.setPenalInterestReceivable(BigDecimal.ZERO);
							BigDecimal penalIntAccruedToBePaid = penalInterestPaid.subtract(penalIntRec);
							penalIntAccrued = penalIntAccrued.subtract(penalIntAccruedToBePaid);
							lineOfCredit.setOverdueInterest(penalIntAccrued.negate());
							AccountingMoney transAmt = MoneyUtil.getAccountingMoney(penalIntAccruedToBePaid.abs().setScale(0, RoundingMode.HALF_UP));
							populateCurrentTransactionRecord(lineOfCredit, accountNumber, currentTransactionsList,
									TransactionCode.PENAL_INTEREST_RECEIVABLE, "Penal Interest Accrued posted Receivable ", -1, transAmt, false,
									transactionType, voucher);
							populateCurrentTransactionRecord(lineOfCredit, accountNumber, currentTransactionsList,
									TransactionCode.PENAL_INTEREST_RECEIVED, "Penal Interest Accrued posted Received", 1, transAmt, false,
									transactionType, voucher);

						} else
							lineOfCredit.setPenalInterestReceivable(penalIntRec.subtract(penalInterestPaid).negate());
					}
				}

			}
		}
		logger.info("currentTransactionsList size : " + currentTransactionsList.size());
		KLSDataAccessFactory.getCurrentTransactionDAO().saveCurrentTransactionsList(currentTransactionsList);

		updateLineOfCredit(lineOfCredit);
		
		adjustCharges(lineOfCredit, loanRecoveryData, transactionType, voucherNumber);
		
		logger.info("End: inside loanRecoveryTransactions()");
		return transactionType.getValue() + " - " + voucherNumber;
	}
	
	private void adjustCharges(LineOfCredit lineOfCredit, LoanRecoveryData loanRecoveryData,
			TransactionType transactionType, Integer voucherNumber) {

		IChargesDebitDAO cDao = KLSDataAccessFactory.getChargesDebitDAO();
		List<ChargesDebit> chargesDebitList = cDao.getAllChargesDebit(lineOfCredit.getId());

		BigDecimal updatedAmountPaid = loanRecoveryData.getChargesPaid();
		for (ChargesDebit cb : chargesDebitList) {

			if (cb.getBalanceAmount().isZero()) {
				continue;
			}
			logger.info("cb.getBalanceAmount()==="+cb.getBalanceAmount());
			ChargesRecovery chargesRecovery = new ChargesRecovery();
			Money amt = cb.getBalanceAmount();
			BigDecimal updatedAmt = BigDecimal.ZERO;
			BigDecimal recoveryAmt = BigDecimal.ZERO;

			int test = updatedAmountPaid.compareTo(amt.getAmount());
			if (test == 1 || test == 0) {
				updatedAmt = BigDecimal.ZERO;
				recoveryAmt = amt.getAmount();
			} else {
				updatedAmt = amt.getAmount().subtract(updatedAmountPaid);
				recoveryAmt = updatedAmountPaid;
			}

			ChargesDebit chargesDebit = new ChargesDebit();
			chargesDebit.setId(cb.getId());
			chargesRecovery.setChargeDebitId(chargesDebit);
			chargesRecovery.setVoucherNumber(transactionType.getValue() + "-" + voucherNumber);
			chargesRecovery.setAmount(Money.valueOf("INR", recoveryAmt));
			chargesRecovery.setBankAmount(Money.ZERO);
			chargesRecovery.setBusinessDate(LoanServiceUtil.getBusinessDate());
			logger.info("prod Id : "+lineOfCredit.getProduct().getId());
			IProductChargesMappingDAO pDao = KLSDataAccessFactory.getProductChargesMappingDAO();
			ProductChargesMapping master = pDao.getProductChargesMappingByProductIdAndChargesId(lineOfCredit
					.getProduct().getId(), cb.getChargesMaster().getId());

			Double totAmount = recoveryAmt.doubleValue();
			logger.info("totAmount : " + totAmount);
			Double bankPercentage = master.getBankPercentage();

			Double bankAmount = 0.0;
			Double pacAmt = 0.0;

			if (bankPercentage <= 100 && bankPercentage > 0) {
				bankAmount = (recoveryAmt.doubleValue() * bankPercentage) / 100;
				pacAmt = totAmount - bankAmount;

				logger.info("bankAmount : " + bankAmount);
				logger.info("pacAmt : " + pacAmt);
				logger.info("pacAmt String val : " + pacAmt.toString());
				
				chargesRecovery.setAmount(Money.valueOf("INR", pacAmt.toString()));
				chargesRecovery.setBankAmount(Money.valueOf("INR", bankAmount.toString()));
			}

			cDao.saveChargesRecovery(chargesRecovery);

			cb.setBalanceAmount(Money.valueOf("INR", updatedAmt));
			cDao.updateChargesDebit(cb);
			updatedAmountPaid = updatedAmountPaid.subtract(recoveryAmt);
			if (updatedAmountPaid.compareTo(BigDecimal.ZERO) == 0)
				break;

		}
	}



	public void updateLineOfCredit(LineOfCredit lineOfCredit) {
		KLSDataAccessFactory.getLineOfCreditDAO().updateLineOfCredit(lineOfCredit);
	}

	@Override
	public void populateCurrentTransactionRecord(LoanLineOfCredit loanLoc, String accountNumber, List<CurrentTransaction> currentTransactionList,
			TransactionCode transactionCode, String remarks, Integer crdr, AccountingMoney transactionAmount, boolean isCashGl,
			TransactionType transactionType, String voucherNumber) {

		logger.info("Start: Populating the recovery current transaction record in populateCurrentTransactionRecord()");
		
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		PersonData person=RestClientUtil.getCustomerById(loanLoc.getCustomerId());
		Pacs pacs=pacsDao.getPacs(person.getPacsId());
		logger.info("pacs type::"+pacs.getPacsStatus());
		logger.info("pacs type::"+transactionCode.getValue());
		String applicationDate=RestClientUtil.getPacsBusinessDate(pacs.getId(),pacs.getBranch().getId());
		
		
		CurrentTransaction currentTransaction = new CurrentTransaction();
		currentTransaction.setAccount(loanLoc.getAccount());
		currentTransaction.setAccountNumber(accountNumber);
		
		if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
			
			currentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(applicationDate));
				
			}else{
				currentTransaction.setBusinessDate(LoanServiceUtil.getBusinessDate());
			}
		
		currentTransaction.setChannelType(ChannelType.SYSTEM);
		currentTransaction.setCrDr(crdr);
		if (isCashGl) {
			currentTransaction.setLineOfCredit(null);
			currentTransaction.setOpeningBalance(null);
		} else {
			currentTransaction.setLineOfCredit(loanLoc);
			currentTransaction.setOpeningBalance(loanLoc.getCurrentBalance());
		}
		currentTransaction.setPacs(loanLoc.getLoanAccountProperty().getAccount().getAccountProperty().getPacs());
		currentTransaction.setPostedStatus(1);
		currentTransaction.setRemarks(remarks);
		currentTransaction.setTerminalId(null);
		currentTransaction.setTransactionAmount(transactionAmount);
		currentTransaction.setTransactionCode(transactionCode);
		currentTransaction.setTransactionType(transactionType);
		currentTransaction.setVoucherNumber(voucherNumber);
		currentTransactionList.add(currentTransaction);
		logger.info("End: Populating the recovery current transaction record in populateCurrentTransactionRecord()");

	}
	
	// code added for doing Branch transaction while repayment at branch (IRKLS-404)
	// Added chanelType as BRANCH 
	@Override
	public void populateCurrentTransactionRecordForBranchTransaction(LoanLineOfCredit loanLoc, String accountNumber, List<CurrentTransaction> currentTransactionList,
			TransactionCode transactionCode, String remarks, Integer crdr, AccountingMoney transactionAmount, boolean isCashGl,
			TransactionType transactionType, String voucherNumber, String channelType) {

		logger.info("Start: Populating the recovery current transaction record for Branch Transaction in populateCurrentTransactionRecordForBranchTransaction()");
		
		

		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		PersonData person=RestClientUtil.getCustomerById(loanLoc.getCustomerId());
		Pacs pacs=pacsDao.getPacs(person.getPacsId());
		logger.info("pacs type::"+pacs.getPacsStatus());
		logger.info("pacs type::"+transactionCode.getValue());
		String applicationDate=RestClientUtil.getPacsBusinessDate(pacs.getId(),pacs.getBranch().getId());
		
		
		CurrentTransaction currentTransaction = new CurrentTransaction();
		currentTransaction.setAccount(loanLoc.getAccount());
		currentTransaction.setAccountNumber(accountNumber);
		
		if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
			
			currentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(applicationDate));
				
			}else{
				currentTransaction.setBusinessDate(LoanServiceUtil.getBusinessDate());
			}
		
		
		currentTransaction.setChannelType(ChannelType.getType(channelType));
		currentTransaction.setCrDr(crdr);
		if (isCashGl) {
			currentTransaction.setLineOfCredit(null);
			currentTransaction.setOpeningBalance(null);
		} else {
			currentTransaction.setLineOfCredit(loanLoc);
			currentTransaction.setOpeningBalance(loanLoc.getCurrentBalance());
		}
		currentTransaction.setPacs(loanLoc.getLoanAccountProperty().getAccount().getAccountProperty().getPacs());
		currentTransaction.setPostedStatus(1);
		currentTransaction.setRemarks(remarks);
		currentTransaction.setTerminalId(null);
		currentTransaction.setTransactionAmount(transactionAmount);
		currentTransaction.setTransactionCode(transactionCode);
		currentTransaction.setTransactionType(transactionType);
		currentTransaction.setVoucherNumber(voucherNumber);
		currentTransactionList.add(currentTransaction);
		logger.info("End:  Populating the recovery current transaction record for Branch Transaction in populateCurrentTransactionRecordForBranchTransaction()");

	}

	private String saveRecovery(ILoanRecoveryDAO dao, LoanRecoveryData data, LoanLineOfCredit loanLoc) {

		logger.info("Start: saving the loan recovery information in saveRecovery()");
//		LoanRecovery dbMaster = null;
		LoanRecovery master = null;
		String voucherNumber = null;
		/*if(master.getId() != null) {
			dbMaster = dao.getLoanRecovery(master, false);
		}*/
		// if (dbMaster == null) {
		LoanLineOfCredit lineOfCredit = KLSDataAccessFactory.getLoanLineOfCreditDAO().getLoanLineOfCreditById(data.getLocId());

		logger.info("lineOfCredit---" + lineOfCredit);
		boolean flgTrans = false;
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
//			EntityManagerUtil.beginTransaction();
			if(!em.getTransaction().isActive()) {
				em.getTransaction().begin();
				flgTrans = true;
			}
			voucherNumber = loanRecoveryTransactions(data, lineOfCredit);
			if(data.getRecoveryEntryId()==null){
			master = LoanPaymentRecoveryHelper.getLoanRecovery(data);
			dao.saveLoanRecovery(master);
			}
			if(flgTrans)
				em.getTransaction().commit();
		} catch (Exception excp) {
			if (flgTrans && em.getTransaction().isActive())
				em.getTransaction().rollback();
			excp.printStackTrace();
			logger.error("Error occured while saving loan recovery data");
			throw new KlsRuntimeException("Could not save loan recovery data, some problem occured");
		}
		logger.info("End: saving the loan recovery information in saveRecovery()");
		// }
		return voucherNumber;
	}
	
	@Override
	public List<CustXLData> validateAndProcessMtLtRecovery(List<CustXLData> uploadedExcelData,Long pacsId) {

		logger.info("Start: Validating Uploaded Customer Data in validateExcelData()");
		Long partyId=null;
		List<CustXLData> rejectedList = new ArrayList<CustXLData>();
		BigDecimal loanAmount=BigDecimal.ZERO;
		LoanRecoveryData loanRecoveryData = null;
		IKLSCustomerDAO dao = KLSDataAccessFactory.getKLSCustomerDAO();
		ILoanLineOfCreditDAO linedao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
		ILoanLineOfCreditService service = KLSServiceFactory.getLoanLineOfCreditService();
		LineOfCredit lineOfCredit = null;
		Long locId = null;
		ListIterator<CustXLData> iterator = uploadedExcelData.listIterator();
		boolean flgTrans = false;
		EntityManager em = EntityManagerUtil.getEntityManager();
//		pacsId = 595l;
		try {
			while (iterator.hasNext()) {
				CustXLData custData = iterator.next();
				String memberNumber = custData.getMemberNumber();
				memberNumber = StringUtils.leftPad(memberNumber, 15, '0');
				custData.setMemberNumber(memberNumber);
				partyId = dao.getCustomerDetails(custData.getMemberNumber(),pacsId.intValue());
				if (partyId == null) {
					custData.setRemarks("Member does not exist");
					rejectedList.add(custData);
					iterator.remove();
				} else {
					locId = custData.getLocId();
					if(locId == null) {
						custData.setRemarks("Invalid Line Of Credit Id");
						rejectedList.add(custData);
						iterator.remove();
					} else {
						lineOfCredit = linedao.getLoanLineOfCreditById(locId);
						logger.info("Recovery Sequency: "+lineOfCredit.getProduct().getRecoverySequence().getId());
						if(lineOfCredit.getId() == null || lineOfCredit.getLineOfCreditStatus().getValue() == 0) {
							custData.setRemarks("Invalid Line Of Credit Id");
							rejectedList.add(custData);
							iterator.remove();
						} else {
							loanRecoveryData = getLoanRecoveryDataByLocId(lineOfCredit.getId());
							if(loanRecoveryData.getOutstandingBalance() == null) {
								custData.setRemarks("Invalid Line Of Credit Id");
								rejectedList.add(custData);
								iterator.remove();
							} else {
								loanAmount = loanRecoveryData.getOutstandingBalance();
								if (loanAmount == BigDecimal.ZERO) {
									custData.setRemarks("No Active Loans");
									rejectedList.add(custData);
									iterator.remove();
								} else if (loanAmount.compareTo(custData.getRecoverdAmount()) == -1 
										|| custData.getRecoverdAmount().compareTo(BigDecimal.ZERO)==0 
										|| custData.getRecoverdAmount().compareTo(BigDecimal.ZERO)==-1)  {//Recovered amount is more than the loan amount 
									custData.setRemarks("Invalid Amount");
									rejectedList.add(custData);
									iterator.remove();
								} else {//Valid customer list
									LoanRecoveryData loanData = getRocoveryInfoBasedOnAmountPaid(custData.getRecoverdAmount(), lineOfCredit.getProduct().getRecoverySequence().getId(), lineOfCredit.getId()); 
									loanRecoveryData.setAmountPaid(custData.getRecoverdAmount());
									loanRecoveryData.setModeOfPayment("P");
									loanRecoveryData.setLocId(lineOfCredit.getId());
									loanRecoveryData.setPrincipalPaid(loanData.getPrincipalPaid());
									loanRecoveryData.setChargesPaid(loanData.getChargesPaid());
									loanRecoveryData.setInterestPaid(loanData.getInterestPaid());
									loanRecoveryData.setPenalInterestPaid(loanData.getPenalInterestPaid());
									loanRecoveryData.setStandAloneStatus(true);
									loanRecoveryData.setId(0l);
									String installmentAmount = service.getLineOfCreditDataById(lineOfCredit.getId()).getInstallmentAmount(); 
									if(!"".equals(installmentAmount) && installmentAmount != null) {
										loanRecoveryData.setInstallmentAmount(new BigDecimal(installmentAmount));
									}
									loanRecoveryData.setOutstandingBalanceAfterPayment(loanData.getOutstandingBalanceAfterPayment());
									logger.info("Saving Account Number: "+lineOfCredit.getAccount().getAccountProperty().getSavingsAccountNumber());
									loanRecoveryData.setSavingsAccountNumber(lineOfCredit.getAccount().getAccountProperty().getSavingsAccountNumber());
									if(!em.getTransaction().isActive()) {
										em.getTransaction().begin();
										flgTrans = true;
									}
									saveLoanRecovery(loanRecoveryData);
									if(flgTrans)
										em.getTransaction().commit();
								}
							}
						}
					}
				} 
			} 
		}catch(Exception e) {
			e.printStackTrace();
			if (flgTrans && em.getTransaction().isActive())
				em.getTransaction().rollback();
		}

		return rejectedList;
	}
	
	
	@Override
	public List<BulkCustomerData> validateAndProcessBulkMtLtRecovery( List<BulkCustomerData> bulkCustData ) {

		logger.info("Start: Validating Uploaded Customer Data in validateBulkData()");
		Long partyId=null;
		List<BulkCustomerData> rejectedList = new ArrayList<BulkCustomerData>();
		BigDecimal loanAmount=BigDecimal.ZERO;
		LoanRecoveryData loanRecoveryData = null;
		IKLSCustomerDAO dao = KLSDataAccessFactory.getKLSCustomerDAO();
		ILoanLineOfCreditDAO linedao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
		ILoanLineOfCreditService service = KLSServiceFactory.getLoanLineOfCreditService();
		LineOfCredit lineOfCredit = null;
		Long locId = null;
		ListIterator<BulkCustomerData> iterator = bulkCustData.listIterator();
		boolean flgTrans = false;
		EntityManager em = EntityManagerUtil.getEntityManager();
 
		try {
			while (iterator.hasNext()) {
				BulkCustomerData custData = iterator.next();
				String memberNumber = custData.getMemberNumber();
				memberNumber = StringUtils.leftPad(memberNumber, 15, '0');
				custData.setMemberNumber(memberNumber);
				partyId = dao.getCustomerDetails(custData.getMemberNumber(), Integer.parseInt(custData.getPacsId()));
				if (partyId == null) {
					custData.setRemarks("Member does not exist");
					rejectedList.add(custData);
					iterator.remove();
				} else {
					locId = custData.getLocId();
					if(locId == null) {
						custData.setRemarks("Invalid Line Of Credit Id");
						rejectedList.add(custData);
						iterator.remove();
					} else {
						lineOfCredit = linedao.getLoanLineOfCreditById(locId);
						logger.info("Recovery Sequency: "+lineOfCredit.getProduct().getRecoverySequence().getId());
						if(lineOfCredit.getId() == null || lineOfCredit.getLineOfCreditStatus().getValue() == 0) {
							custData.setRemarks("Invalid Line Of Credit Id");
							rejectedList.add(custData);
							iterator.remove();
						} else {
							loanRecoveryData = getLoanRecoveryDataByLocId(lineOfCredit.getId());
							if(loanRecoveryData.getOutstandingBalance() == null) {
								custData.setRemarks("Invalid Line Of Credit Id");
								rejectedList.add(custData);
								iterator.remove();
							} else {
								loanAmount = loanRecoveryData.getOutstandingBalance();
								if (loanAmount == BigDecimal.ZERO) {
									custData.setRemarks("No Active Loans");
									rejectedList.add(custData);
									iterator.remove();
								} else if (loanAmount.compareTo(custData.getRecoverdAmount()) == -1 
										|| custData.getRecoverdAmount().compareTo(BigDecimal.ZERO)==0 
										|| custData.getRecoverdAmount().compareTo(BigDecimal.ZERO)==-1)  {//Recovered amount is more than the loan amount 
									custData.setRemarks("Invalid Amount");
									rejectedList.add(custData);
									iterator.remove();
								} else {//Valid customer list
									LoanRecoveryData loanData = getRocoveryInfoBasedOnAmountPaid(custData.getRecoverdAmount(), lineOfCredit.getProduct().getRecoverySequence().getId(), lineOfCredit.getId()); 
									loanRecoveryData.setAmountPaid(custData.getRecoverdAmount());
									loanRecoveryData.setModeOfPayment(custData.getModeOfPayment());
									loanRecoveryData.setLocId(lineOfCredit.getId());
									loanRecoveryData.setPrincipalPaid(loanData.getPrincipalPaid());
									loanRecoveryData.setChargesPaid(loanData.getChargesPaid());
									loanRecoveryData.setInterestPaid(loanData.getInterestPaid());
									loanRecoveryData.setPenalInterestPaid(loanData.getPenalInterestPaid());
									loanRecoveryData.setStandAloneStatus(true);
									loanRecoveryData.setId(0l);
									String installmentAmount = service.getLineOfCreditDataById(lineOfCredit.getId()).getInstallmentAmount(); 
									if(!"".equals(installmentAmount) && installmentAmount != null) {
										loanRecoveryData.setInstallmentAmount(new BigDecimal(installmentAmount));
									}
									loanRecoveryData.setOutstandingBalanceAfterPayment(loanData.getOutstandingBalanceAfterPayment());
									logger.info("Saving Account Number: "+lineOfCredit.getAccount().getAccountProperty().getSavingsAccountNumber());
									loanRecoveryData.setSavingsAccountNumber(lineOfCredit.getAccount().getAccountProperty().getSavingsAccountNumber());
									if(!em.getTransaction().isActive()) {
										em.getTransaction().begin();
										flgTrans = true;
									}
									loanRecoveryData.setChannelType("B");
									saveLoanRecovery(loanRecoveryData);
									if(flgTrans)
										em.getTransaction().commit();
								}
							}
						}
					}
				} 
			} 
		}catch(Exception e) {
			e.printStackTrace();
			if (flgTrans && em.getTransaction().isActive())
				em.getTransaction().rollback();
		}

		return rejectedList;
	}
	
	@Override
	public String saveStLoanRecoveryEntry(StLoanRecoveryData data){
		String returnMsg="";
		StLoanRecovery master=new StLoanRecovery();
		ILoanRecoveryDAO dao = KLSDataAccessFactory.getLoanRecoveryDAO();
		try{
			master = StLoanRecoveryEntryHelper.getLoanRecovery(data);
			returnMsg=dao.saveSTRecovery(master);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnMsg;
	}
	
	@Override
	public List<StLoanRecoveryData> getStLoanRecoveryEntry(String pacsId){
		List<StLoanRecovery> recoveryList=new ArrayList<StLoanRecovery>();
		List<StLoanRecoveryData> recoveryDataList=new ArrayList<StLoanRecoveryData>();
		StLoanRecoveryData recoveryData=new StLoanRecoveryData();
		ILoanRecoveryDAO recoveryDAO=KLSDataAccessFactory.getLoanRecoveryDAO();
		Integer pacId=Integer.parseInt(pacsId);
		try{
			recoveryList=recoveryDAO.getStLoanRecoveryData(pacId);
			for(StLoanRecovery recovery:recoveryList){
				recoveryData=StLoanRecoveryEntryHelper.getLoanRecoveryData(recovery);
				recoveryDataList.add(recoveryData);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return recoveryDataList;
	}
	
	@Override
	public String updateStLoanRecoveryEntry(StLoanRecoveryData data){
		String returnMsg="";
		StLoanRecovery master=new StLoanRecovery();
		try{
			ILoanRecoveryDAO recoveryDAO=KLSDataAccessFactory.getLoanRecoveryDAO();
			master = StLoanRecoveryEntryHelper.getLoanRecovery(data);
			returnMsg=recoveryDAO.saveSTRecovery(master);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnMsg;
	}
	
	@Override
	public String saveMtLoanRecoveryEntry(LoanRecoveryData recoveryData){
		logger.info("Start : saveMtLoanRecoveryEntry method");
		String returnMsg="";
		LoanRecovery master=new LoanRecovery();
		ILoanRecoveryDAO dao=KLSDataAccessFactory.getLoanRecoveryDAO();
		try{
			master = LoanPaymentRecoveryHelper.getLoanRecovery(recoveryData);
			returnMsg=dao.saveMtLoanRecovery(master);
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("End : saveMtLoanRecoveryEntry method");
		return returnMsg;
	}
	
	@Override
	public List<LoanRecoveryData> getMtLoanRecovery(Integer pacsId){
		logger.info("Start : getMtLoanRecovery method");
		List<LoanRecoveryData> recoveryData=new ArrayList<LoanRecoveryData>();
		List<LoanRecovery> master=new ArrayList<LoanRecovery>();
		ILoanRecoveryDAO dao=KLSDataAccessFactory.getLoanRecoveryDAO();
		LoanRecoveryData data=new LoanRecoveryData();
		try{
			master=dao.getMtLoanRecovery(pacsId);
			for(LoanRecovery recovery:master){
				data=LoanPaymentRecoveryHelper.getLoanRecoveryData(recovery);
				 PersonData personData=RestClientUtil.getAllCustomerDataByCusomterId(Long.parseLong(data.getCustomerNumber()));
				 data.setCustomerName(personData.getName());
				 data.setMemberNumber(personData.getMemberNumber());
				recoveryData.add(data);
			}
			}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("End : getMtLoanRecovery method");
		return recoveryData;
	}
	
	@Override
	public String updateMtRecoveryStatus(LoanRecoveryData data){
		logger.info("Start : updateMtRecoveryStatus method");
		String successMsg="";
		ILoanRecoveryDAO dao=KLSDataAccessFactory.getLoanRecoveryDAO();
		LoanRecovery master=new LoanRecovery();
		try{
			master = LoanPaymentRecoveryHelper.getLoanRecovery(data);
			successMsg=dao.saveMtLoanRecovery(master);
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("End : updateMtRecoveryStatus method");
		return successMsg;
	}
}