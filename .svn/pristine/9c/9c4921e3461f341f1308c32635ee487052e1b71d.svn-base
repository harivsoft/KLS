package com.vsoftcorp.kls.service.transaction.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.EventType;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.AccountData;
import com.vsoftcorp.kls.data.BulkCustomerData;
import com.vsoftcorp.kls.data.CurrentTransactionData;
import com.vsoftcorp.kls.data.CustXLData;
import com.vsoftcorp.kls.data.gateway.datahelpers.KLSResponse;
import com.vsoftcorp.kls.data.gateway.datahelpers.STLoanRecoveryData;
import com.vsoftcorp.kls.dataaccess.IRecoveryOrderDao;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IAccountDAO;
import com.vsoftcorp.kls.dataaccess.loan.IKLSCustomerDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILineOfCreditDAO;
import com.vsoftcorp.kls.service.account.IAccountPropertyService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.loan.ILoanDisbursementEntryService;
import com.vsoftcorp.kls.service.transaction.ICurrentTransactionService;
import com.vsoftcorp.kls.service.transaction.ISTLoanRecoveryTransactionService;
import com.vsoftcorp.kls.service.util.ISODataElements;
import com.vsoftcorp.kls.service.util.ISOResponseCodes;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.transaction.ChannelType;
import com.vsoftcorp.kls.valuetypes.transaction.RecoveryOrder;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionMode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1605
 * 
 */
public class STLoanRecoveryTransactionService implements ISTLoanRecoveryTransactionService {

	private static final Logger logger = Logger.getLogger(STLoanRecoveryTransactionService.class);

	private static final BigDecimal ZERO = BigDecimal.ZERO.setScale(2);

	@Override
	public STLoanRecoveryData getBifurcationAmountsToBePaid(STLoanRecoveryData stLoanRecoveryData) {
		try {

			logger.info("start : getting bifurcation amounts");

			ILineOfCreditDAO locDAO = KLSDataAccessFactory.getLineOfCreditDAO();

			BigDecimal amountPaid = stLoanRecoveryData.getAmountPaid();

			List<Long> locList = stLoanRecoveryData.getLocList();

			logger.info("loc list size: " + locList.size());
			IRecoveryOrderDao rDao = KLSDataAccessFactory.getRecoveryOrderDAO();
			int a = 0;

			BigDecimal principalPaid = ZERO;
			BigDecimal interestPaid = ZERO;
			BigDecimal penalInterestPaid = ZERO;

			for (Long loc : locList) {
				LineOfCredit lineOfCredit = locDAO.getLineOfCreditById(loc, false);

				List<EventType> seqList = rDao.getAllEventTypeBasedOnEventDefinition(lineOfCredit.getProduct().getRecoverySequence().getId(), false);

				if (seqList.size() > 0) {
					for (EventType e : seqList) {

						if (e.getRecoverySequence() == RecoveryOrder.PRINCIPAL.getValue()) {
							a = amountPaid.compareTo(ZERO);
							if (a == 1) {
								a = stLoanRecoveryData.getTotalPrincipalReceivable().compareTo(ZERO);
								if (a == 1) {
									a = amountPaid.compareTo(stLoanRecoveryData.getTotalPrincipalReceivable());
									if (a == 1 || a == 0) {
										principalPaid = principalPaid.add(stLoanRecoveryData.getTotalPrincipalReceivable());
										amountPaid = amountPaid.subtract(stLoanRecoveryData.getTotalPrincipalReceivable());

									} else {
										principalPaid = principalPaid.add(amountPaid);
										amountPaid = ZERO;
									}
								} else
									principalPaid = principalPaid.add(ZERO);
							} else
								principalPaid = principalPaid.add(ZERO);
						}

						if (e.getRecoverySequence() == RecoveryOrder.INTEREST.getValue()) {
							a = amountPaid.compareTo(ZERO);
							if (a == 1) {

								a = stLoanRecoveryData.getTotalInterestReceivable().compareTo(ZERO);
								if (a == 1) {
									a = amountPaid.compareTo(stLoanRecoveryData.getTotalInterestReceivable());
									if (a == 1 || a == 0) {
										interestPaid = interestPaid.add(stLoanRecoveryData.getTotalInterestReceivable());
										amountPaid = amountPaid.subtract(stLoanRecoveryData.getTotalInterestReceivable());

									} else {
										interestPaid = interestPaid.add(amountPaid);
										amountPaid = ZERO;
									}
								} else
									interestPaid = interestPaid.add(ZERO);
							} else
								interestPaid = interestPaid.add(ZERO);
						}
						a = amountPaid.compareTo(ZERO);

						if (e.getRecoverySequence() == RecoveryOrder.PENAL_INTEREST.getValue()) {
							a = amountPaid.compareTo(ZERO);
							if (a == 1) {
								a = stLoanRecoveryData.getTotalPenalInterestReceivable().compareTo(ZERO);
								if (a == 1) {
									a = amountPaid.compareTo(stLoanRecoveryData.getTotalPenalInterestReceivable());
									if (a == 1 || a == 0) {
										penalInterestPaid = penalInterestPaid.add(stLoanRecoveryData.getTotalPenalInterestReceivable());
										amountPaid = amountPaid.subtract(stLoanRecoveryData.getTotalPenalInterestReceivable());
									} else {
										penalInterestPaid = penalInterestPaid.add(amountPaid);
										amountPaid = ZERO;
									}
								} else
									penalInterestPaid = penalInterestPaid.add(ZERO);
							} else
								penalInterestPaid = penalInterestPaid.add(ZERO);
						}

					}
				}
			}
			stLoanRecoveryData.setPrincipalPaid(principalPaid);
			stLoanRecoveryData.setInterestPaid(interestPaid);
			stLoanRecoveryData.setPenalInterestPaid(penalInterestPaid);
			a = amountPaid.compareTo(ZERO);
			if (a == 1) {

				stLoanRecoveryData.setPrincipalPaid(stLoanRecoveryData.getTotalPrincipalReceivable().add(amountPaid));

			}
			/*stLoanRecoveryData.setOutstandingBalanceAfterPayment(stLoanRecoveryData.getOutstandingBalance().subtract(
					stLoanRecoveryData.getPrincipalPaid()));*/

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting the ST LoanRecovery data");
			throw new KlsRuntimeException("Error while getting the ST LoanRecovery data", e);
		}
		logger.info("Successfully got ST LoanRecovery details from getBifurcationAmountsToBePaid() method.");
		return stLoanRecoveryData;

	}

	@Override
	public List<CustXLData> validateAndProcessRecovery(List<CustXLData> uploadedExcelData,Integer pacsId) {
		
		logger.info("Start: Validating Uploaded Customer Data in validateExcelData()");
		Long partyId=null;
		List<CustXLData> rejectedList = new ArrayList<CustXLData>();
		Map<String, Object> loanDataMap = new HashMap<String, Object>();
		BigDecimal loanAmount=BigDecimal.ZERO;
		BigDecimal currentBalance=BigDecimal.ZERO;
		BigDecimal interestReceivable=BigDecimal.ZERO;
		BigDecimal penalInterestReceivable=BigDecimal.ZERO;
		BigDecimal subsidyInterestReceivable= BigDecimal.ZERO;
		Account account = new Account();
		KLSResponse klsResponse = null;
		Integer listSize=uploadedExcelData.size();
		try{
			IKLSCustomerDAO dao = KLSDataAccessFactory.getKLSCustomerDAO();
			ILoanDisbursementEntryService service = KLSServiceFactory.getLoanDisbursementEntryService();
			IAccountDAO accountDAO = KLSDataAccessFactory.getAccountDAO();
			ICurrentTransactionService currentService = KLSServiceFactory.getCurrentTransactionService();
			ListIterator<CustXLData> iterator = uploadedExcelData.listIterator();
			while(iterator.hasNext()) {//for (int i=0;i<listSize;i++)
				CustXLData custData = iterator.next();
				String memberNumber = custData.getMemberNumber();
				memberNumber = StringUtils.leftPad(memberNumber, 15, '0');
				custData.setMemberNumber(memberNumber);
				partyId = dao.getCustomerDetails(memberNumber,pacsId);
				if (partyId == null) {
					custData.setRemarks("Member does not exist");
					rejectedList.add(custData);
					iterator.remove();
				} else {
					account = accountDAO.getAccountByCustomer(partyId.toString());
					if(account == null){
						custData.setRemarks("Loan Not there for this Customer");
						rejectedList.add(custData);
						iterator.remove();
					} else {
						loanDataMap = service.getLoanInfo(account.getId(), "C",	"C");
						currentBalance = loanDataMap.get("currentBalance") == null ? BigDecimal.ZERO
								:  new BigDecimal((String)(loanDataMap.get("currentBalance")));
						interestReceivable = loanDataMap.get("interestReceivable") == null ? BigDecimal.ZERO: 
							new BigDecimal((String)(loanDataMap.get("interestReceivable")));
						penalInterestReceivable = loanDataMap.get("penalInterestReceivable") == null ? BigDecimal.ZERO: 
							new BigDecimal((String)(loanDataMap.get("penalInterestReceivable")));
						subsidyInterestReceivable = loanDataMap.get("subsidyInterestReceivable") == null ? BigDecimal.ZERO
								: (BigDecimal)(loanDataMap.get("subsidyInterestReceivable"));
						loanAmount = currentBalance.add(interestReceivable).add(penalInterestReceivable).subtract(subsidyInterestReceivable);
						if (loanAmount.compareTo(BigDecimal.ZERO)==0) {
							custData.setRemarks("No Active Loans");
							rejectedList.add(custData);
							iterator.remove();
						} else if (loanAmount.compareTo(custData.getRecoverdAmount()) == -1 
								|| custData.getRecoverdAmount().compareTo(BigDecimal.ZERO)==0 
								|| custData.getRecoverdAmount().compareTo(BigDecimal.ZERO)==-1) {//Recovered amount is more than the loan amount 
							custData.setRemarks("Invalid Amount");
							rejectedList.add(custData);
							iterator.remove();

						} else {//Valid customer list
							
							CurrentTransactionData data = new CurrentTransactionData();
							data.setVoucherNumber(null);
							data.setSavingsAccountNumber(account.getAccountNumber());
							data.setStandAloneStatus(true);
							Date businessDate = LoanServiceUtil.getBusinessDate();
							java.util.Date d = DateUtil.getFormattedDate(DateUtil.getDateString(businessDate));
							String date = "" + d.getDate();
							String month = "" + (d.getMonth() + 1);
							String year = "" + d.getYear();
							// MMDDYYHHMMSS
							if (month.length() == 1)
								month = "0" + month;

							if (date.length() == 1)
								date = "0" + date;
							if (date.length() == 1)
								year = "0" + year;
							year = year.substring(1, 3);

							String dateAndTime = date + month + year + "000000";//business date don't have time.so I am adding "000000" in in place of time 
							data.setBusinessDate(dateAndTime);
							data.setTransactionAmount(custData.getRecoverdAmount().toString());
							data.setTransactionTowards("1");
							data.setChannelType("S");
							data.setTerminalId(null);
							data.setCrDr("1");
							data.setTransactionType("D");
							data.setBulkRecovery("YES");
							try{
								klsResponse=currentService.handleCreditTransaction(data);
							} catch (Exception e) {
								e.printStackTrace();
								custData.setRemarks("System Error");
								rejectedList.add(custData);
							} finally {
								switch (klsResponse.getResponseStatus()) {
								case ISOResponseCodes.SYSTEM_ERROR:
									custData.setRemarks("System Error");
									rejectedList.add(custData);
									break;
								case ISOResponseCodes.DUPLICATE_TRANSACTION:
									custData.setRemarks("Duplicate Transaction");
									rejectedList.add(custData);
									break;
								case ISOResponseCodes.NO_SAVINGS_ACCOUNT:
									custData.setRemarks("No savings account");
									rejectedList.add(custData);
									break;
								case ISOResponseCodes.UNABLE_TO_PROCESS_INVALID_ACCOUNT:
									custData.setRemarks("Unable to process invalid account");
									rejectedList.add(custData);
									break;
								case ISOResponseCodes.INVALID_TRANSACTION_ACCOUNT_CLOSED:
									custData.setRemarks("Invalid transaction account closed");
									rejectedList.add(custData);
									break;
								case ISOResponseCodes.INVALID_ACCOUNT:
									custData.setRemarks("Invalid account");
									rejectedList.add(custData);
									break;
								case ISOResponseCodes.NO_CREDIT_ACCOUNT:
									custData.setRemarks("No eligible line of credits found");
									rejectedList.add(custData);
									break;
								case ISOResponseCodes.NO_FUNDS:
									custData.setRemarks("No funds");
									rejectedList.add(custData);
									break;
								case ISOResponseCodes.TRANSACTION_NOT_ALLOWED:
									custData.setRemarks("Transaction not allowed");
									rejectedList.add(custData);
									break;
								case ISOResponseCodes.INVALID_BUSINESS_DATE:
									custData.setRemarks("Invalid business date");
									rejectedList.add(custData);
									break;
								}
								//EntityManagerUtil.closeSession();
							}

						}
					}
				}
			}
			
		}catch(Exception e){
			logger.error("Error: while Validating Uploaded Customer Data in validateExcelData()");
			e.printStackTrace();
		}
		
		logger.info("End: Validating Uploaded Customer Data in validateExcelData()");
		return rejectedList;
	}

	@Override
	public List<BulkCustomerData> validateAndProcessBulkLoanRecovery(
			List<BulkCustomerData> bulkCustData) {

		List<BulkCustomerData> rejectedList = new ArrayList<BulkCustomerData>();

		logger.info("Start: Validating Customer Data in validateAndProcessBulkLoanRecovery() ");
		Long partyId=null;
		Map<String, Object> loanDataMap = new HashMap<String, Object>();
		BigDecimal loanAmount=BigDecimal.ZERO;
		BigDecimal currentBalance=BigDecimal.ZERO;
		BigDecimal interestReceivable=BigDecimal.ZERO;
		BigDecimal penalInterestReceivable=BigDecimal.ZERO;
		BigDecimal subsidyInterestReceivable= BigDecimal.ZERO;
		Account account = new Account();
		KLSResponse klsResponse = null;
		try{
			IKLSCustomerDAO dao = KLSDataAccessFactory.getKLSCustomerDAO();
			ILoanDisbursementEntryService service = KLSServiceFactory.getLoanDisbursementEntryService();
			IAccountDAO accountDAO = KLSDataAccessFactory.getAccountDAO();
			ICurrentTransactionService currentService = KLSServiceFactory.getCurrentTransactionService();
			ListIterator<BulkCustomerData> iterator = bulkCustData.listIterator();
			while(iterator.hasNext()) {//for (int i=0;i<listSize;i++)
				BulkCustomerData custData = iterator.next();
				logger.info("in while loop : "+custData.getModeOfPayment());
				String memberNumber = custData.getMemberNumber();
				memberNumber = StringUtils.leftPad(memberNumber, 15, '0');
				custData.setMemberNumber(memberNumber);
			//	logger.info(" member num & pacsId : "+memberNumber+"   &  "+custData.getPacsId());
				partyId = dao.getCustomerDetails(memberNumber,Integer.parseInt(custData.getPacsId()));
				if (partyId == null) {
					custData.setRemarks("Member does not exist");
					rejectedList.add(custData);
					iterator.remove();
				} else {
					account = accountDAO.getAccountByCustomer(partyId.toString());
					if(account == null){
						custData.setRemarks("Loan Not there for this Customer");
						rejectedList.add(custData);
						iterator.remove();
				} else {
						logger.info("loan account number  : "+account.getAccountNumber());							
						loanDataMap = service.getLoanInfo(account.getId(), "C",	"C");
						currentBalance = loanDataMap.get("currentBalance") == null ? BigDecimal.ZERO
								:  new BigDecimal((String)(loanDataMap.get("currentBalance")));
						interestReceivable = loanDataMap.get("interestReceivable") == null ? BigDecimal.ZERO: 
							new BigDecimal((String)(loanDataMap.get("interestReceivable")));
						penalInterestReceivable = loanDataMap.get("penalInterestReceivable") == null ? BigDecimal.ZERO: 
							new BigDecimal((String)(loanDataMap.get("penalInterestReceivable")));
						subsidyInterestReceivable = loanDataMap.get("subsidyInterestReceivable") == null ? BigDecimal.ZERO
								: (BigDecimal)(loanDataMap.get("subsidyInterestReceivable"));
						loanAmount = currentBalance.add(interestReceivable).add(penalInterestReceivable).subtract(subsidyInterestReceivable);
						if (loanAmount.compareTo(BigDecimal.ZERO)==0) {
							custData.setRemarks("No Active Loans");
							rejectedList.add(custData);
							iterator.remove();
						} else if (loanAmount.compareTo(custData.getRecoverdAmount()) == -1 
								|| custData.getRecoverdAmount().compareTo(BigDecimal.ZERO)==0 
								|| custData.getRecoverdAmount().compareTo(BigDecimal.ZERO)==-1) {//Recovered amount is more than the loan amount 
							custData.setRemarks("Invalid Amount");
							rejectedList.add(custData);
							iterator.remove();

						} else {//Valid customer list
							logger.info("Valid customer list CBS AC NO : "+account.getAccountNumber());
							CurrentTransactionData data = new CurrentTransactionData();
							data.setVoucherNumber(null);
							data.setSavingsAccountNumber(account.getAccountNumber());
							data.setStandAloneStatus(true);
							Date businessDate = LoanServiceUtil.getBusinessDate();
							java.util.Date d = DateUtil.getFormattedDate(DateUtil.getDateString(businessDate));
							String date = "" + d.getDate();
							String month = "" + (d.getMonth() + 1);
							String year = "" + d.getYear();
							// MMDDYYHHMMSS
							if (month.length() == 1)
								month = "0" + month;

							if (date.length() == 1)
								date = "0" + date;
							if (date.length() == 1)
								year = "0" + year;
							year = year.substring(1, 3);

							String dateAndTime = date + month + year + "000000";//business date don't have time.so I am adding "000000" in in place of time 
							data.setBusinessDate(dateAndTime);
							data.setTransactionAmount(custData.getRecoverdAmount().toString());
							data.setTransactionTowards("1");
							data.setChannelType(ChannelType.BRANCH.getValue());
							data.setTerminalId(null);
							data.setCrDr("1");
							data.setTransactionType(TransactionType.Deposit.getValue());  // added for borrowing transactions required in pacs gl extract for IRKLS-404
							data.setModeOfPayment(custData.getModeOfPayment().toString());
							data.setBulkRecoveryFromEntry(true);
							data.setPartyId(partyId.toString());
							try{
								klsResponse=currentService.handleCreditTransaction(data);
							} catch (Exception e) {
								e.printStackTrace();
								custData.setRemarks("System Error");
								rejectedList.add(custData);
							} finally {
								switch (klsResponse.getResponseStatus()) {
								case ISOResponseCodes.SYSTEM_ERROR:
									custData.setRemarks("System Error");
									rejectedList.add(custData);
									break;
								case ISOResponseCodes.DUPLICATE_TRANSACTION:
									custData.setRemarks("Duplicate Transaction");
									rejectedList.add(custData);
									break;
								case ISOResponseCodes.NO_SAVINGS_ACCOUNT:
									custData.setRemarks("No savings account");
									rejectedList.add(custData);
									break;
								case ISOResponseCodes.UNABLE_TO_PROCESS_INVALID_ACCOUNT:
									custData.setRemarks("Unable to process invalid account");
									rejectedList.add(custData);
									break;
								case ISOResponseCodes.INVALID_TRANSACTION_ACCOUNT_CLOSED:
									custData.setRemarks("Invalid transaction account closed");
									rejectedList.add(custData);
									break;
								case ISOResponseCodes.INVALID_ACCOUNT:
									custData.setRemarks("Invalid account");
									rejectedList.add(custData);
									break;
								case ISOResponseCodes.NO_CREDIT_ACCOUNT:
									custData.setRemarks("No eligible line of credits found");
									rejectedList.add(custData);
									break;
								case ISOResponseCodes.NO_FUNDS:
									custData.setRemarks("No funds");
									rejectedList.add(custData);
									break;
								case ISOResponseCodes.TRANSACTION_NOT_ALLOWED:
									custData.setRemarks("Transaction not allowed");
									rejectedList.add(custData);
									break;
								case ISOResponseCodes.INVALID_BUSINESS_DATE:
									custData.setRemarks("Invalid business date");
									rejectedList.add(custData);
									break;
								}
								//EntityManagerUtil.closeSession();
							}
						}
					}
				}
			}
				
		}catch(Exception e){
			logger.error("Error: Validating Customer Data in validateAndProcessBulkLoanRecovery()--- Exception");
			e.printStackTrace();
		}
	
		logger.info("End: Validating Customer Data in validateAndProcessBulkLoanRecovery() ");
		return rejectedList;
	}
}
