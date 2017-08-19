/**
 * 
 */
package com.vsoftcorp.kls.service.transaction.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.accounting.types.DebitOrCredit;
import com.vsoftcorp.camelintg.util.Constants;
import com.vsoftcorp.camelintg.util.JPOSUtil;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.BankParameter;
import com.vsoftcorp.kls.business.entities.BorrowingProductGlMapping;
import com.vsoftcorp.kls.business.entities.EventType;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.PacsGlMapping;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.SeasonParameter;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.AccountProperty;
import com.vsoftcorp.kls.business.entity.account.BorrowingsAccountProperty;
import com.vsoftcorp.kls.business.entity.account.BorrowingsLineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LoanAccountProperty;
import com.vsoftcorp.kls.business.entity.subsidy.SubsidyInterestAmounts;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.entity.transaction.TransactionHistory;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.CamelRequest;
import com.vsoftcorp.kls.data.CurrentTransactionData;
import com.vsoftcorp.kls.data.ISOTransactionResponse;
import com.vsoftcorp.kls.data.MiniStatementData;
import com.vsoftcorp.kls.data.VoucherData;
import com.vsoftcorp.kls.data.gateway.datahelpers.KLSRequest;
import com.vsoftcorp.kls.data.gateway.datahelpers.KLSResponse;
import com.vsoftcorp.kls.data.gateway.datahelpers.PartyBankInfoData;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.data.gateway.datahelpers.STLoanRecoveryData;
import com.vsoftcorp.kls.data.gateway.datahelpers.ShareAccountData;
import com.vsoftcorp.kls.data.gateway.datahelpers.ShareTransactionData;
import com.vsoftcorp.kls.dataaccess.IBankPacsGlDAO;
import com.vsoftcorp.kls.dataaccess.IBorrowingProductDAO;
import com.vsoftcorp.kls.dataaccess.IBorrowingsAccountDAO;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.IPacsGlMappingDAO;
import com.vsoftcorp.kls.dataaccess.IProductDAO;
import com.vsoftcorp.kls.dataaccess.IRecoveryOrderDao;
import com.vsoftcorp.kls.dataaccess.ISeasonParameterDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IAccountDAO;
import com.vsoftcorp.kls.dataaccess.loan.IAccountPropertyDAO;
import com.vsoftcorp.kls.dataaccess.loan.IBorrowingsAccountPropertyDAO;
import com.vsoftcorp.kls.dataaccess.loan.IBorrowingsLineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.loan.IChargesDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanAccountPropertyDAO;
import com.vsoftcorp.kls.dataaccess.loan.result.classes.ChargesForLineOfCredit;
import com.vsoftcorp.kls.dataaccess.subsidy.ISubsidyInterestAmountsDAO;
import com.vsoftcorp.kls.dataaccess.transaction.ICurrentTransactionDAO;
import com.vsoftcorp.kls.dataaccess.transaction.ITransactionHistoryDAO;
import com.vsoftcorp.kls.service.constants.ServiceConstants;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.CurrentTransactionHelper;
import com.vsoftcorp.kls.service.thread.PostTransactionThread;
import com.vsoftcorp.kls.service.transaction.ICurrentTransactionService;
import com.vsoftcorp.kls.service.util.ISODataElements;
import com.vsoftcorp.kls.service.util.ISOResponseCodes;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.service.util.PropertiesUtil;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.kls.service.util.StringConstant;
import com.vsoftcorp.kls.service.util.VoucherNumberUtil;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.AccountStatus;
import com.vsoftcorp.kls.valuetypes.CBSMethodEnum;
import com.vsoftcorp.kls.valuetypes.PacsStatus;
import com.vsoftcorp.kls.valuetypes.loan.LoanType;
import com.vsoftcorp.kls.valuetypes.transaction.BorrowingTransactionMethod;
import com.vsoftcorp.kls.valuetypes.transaction.ChannelType;
import com.vsoftcorp.kls.valuetypes.transaction.RecoveryOrder;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionMode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;
import com.vsoftcorp.time.Date;

/**
 * @author a9152
 * 
 */
public class CurrentTransactionService implements ICurrentTransactionService {

	private static final Logger logger = Logger.getLogger(CurrentTransactionService.class);

	private final Map<String, AccountingMoney> moneyMap = new HashMap<String, AccountingMoney>();
	private List<ChargesForLineOfCredit> _shareChargesList;
	private List<ChargesForLineOfCredit> _insuranceChargesList;

	@Override
	public KLSResponse handleDebitTransaction(CurrentTransactionData currentTransactionData, String suvikasBalance) {

		logger.info("Start : Calling current transaction dao in handleDebitTransaction() method.");
		CurrentTransaction master = CurrentTransactionHelper.getCurrentTransaction(currentTransactionData);
		KLSResponse klsResponse = new KLSResponse();
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		Integer pacsId=0;
		Integer productId=0;
		// check whether the savings account number exists.
		boolean isSavingsAccountNumberValid=false;
		if("bd".equals(currentTransactionData.getBulkDisbursement())){
			isSavingsAccountNumberValid = isSavingsAccountNumberValidForBulk(currentTransactionData.getSavingsAccountNumber(), master, klsResponse,
				currentTransactionData.isStandAloneStatus(),currentTransactionData.getModeOfPayment());
		}
		else
			isSavingsAccountNumberValid = isSavingsAccountNumberValid(currentTransactionData.getSavingsAccountNumber(), master, klsResponse,
					currentTransactionData.isStandAloneStatus());
		CamelRequest request=new CamelRequest();
		logger.info("isSavingsAccountNumberValid in handle debit transaction method."+isSavingsAccountNumberValid);
		
		if (isSavingsAccountNumberValid) {
			List<CurrentTransaction> currentTransactionList = new ArrayList<CurrentTransaction>();
			// check line of credit accounts balance.
			ReturnStatus returnStatus = checkLineOfCreditAccountsBalance(master, suvikasBalance, klsResponse, currentTransactionList);
			if (returnStatus != null && returnStatus.isReturnValue()) {
				// get the current transaction dao.
				ICurrentTransactionDAO dao = KLSDataAccessFactory.getCurrentTransactionDAO();
				// TransactionHistoryHelper.getTransactionHistoryList(currentTransactionList);
				try {
					if (master.getId() == null) {
						for(CurrentTransaction transaction:currentTransactionList)
						{
							String businessDate="";
							Pacs pacs=pacsDao.getPacs(transaction.getPacs().getId());
							logger.info("pacs type::"+pacs.getPacsStatus());
							if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
								businessDate=RestClientUtil.getPacsBusinessDate(transaction.getPacs().getId(),pacs.getBranch().getId());
								//businessDate="2017-05-01";
								transaction.setBusinessDate(DateUtil.getVSoftDateByString(businessDate));
								
							}
							if(transaction.getLineOfCredit()!=null){
							LineOfCredit loc=transaction.getLineOfCredit();
							productId = loc.getProduct().getId();
							}
						}
						BankParameter bankParam=KLSDataAccessFactory.getBankParameter();
						if(bankParam.getCbsIntegrationMethod().equals(CBSMethodEnum.ISO8583)){
							logger.info("checking inside ISO method");
							
						pacsId=currentTransactionList.get(0).getPacs().getId();
						CamelRequest camelRequest=retrieveCamelRequest(pacsId,productId,currentTransactionData);
						camelRequest.setBankCashInHandGL(bankParam.getCashGl());
						Gson gson = new GsonBuilder().create();
						//String jsonString = gson.toJson(camelRequest);
						//logger.info("json for camel::"+jsonString);
						String camelResponse=RestClientUtil.getTransactionStatus(camelRequest,"disbursement");
						ISOTransactionResponse response=gson.fromJson(camelResponse, ISOTransactionResponse.class);
						if(response.getStatus())
						dao.saveCurrentTransaction(currentTransactionList, returnStatus.getDebitAccountMap());
						else{
							klsResponse.setResponseStatus(ISOResponseCodes.SYSTEM_ERROR);
							klsResponse.setErrorMessage("Problem with Camel server");
						}
						}
						else
							dao.saveCurrentTransaction(currentTransactionList, returnStatus.getDebitAccountMap());
						Map<String, String> recoveryTransMap = new<String, String> HashMap();
						recoveryTransMap.put("TOTAL_PRINCIPAL", "0");
						recoveryTransMap.put("TOTAL_SHARE", "0");
						recoveryTransMap.put("TOTAL_INSURANCE", "0");

						for (CurrentTransaction currentTransaction : currentTransactionList) {
							BigDecimal amount = BigDecimal.ZERO;
							BigDecimal transAmount = BigDecimal.ZERO;
							switch (currentTransaction.getTransactionCode()) {
							case PRINCIPAL_BAL:
								amount = new BigDecimal(recoveryTransMap.get("TOTAL_PRINCIPAL"));
								transAmount = currentTransaction.getTransactionAmount().getMoney().getAmount();
								amount = amount.add(transAmount);
								recoveryTransMap.put("TOTAL_PRINCIPAL", amount.toString());
								break;
							case SHARE_CHARGES:
								amount = new BigDecimal(recoveryTransMap.get("TOTAL_SHARE"));
								transAmount = currentTransaction.getTransactionAmount().getMoney().getAmount();
								amount = amount.add(transAmount);
								recoveryTransMap.put("TOTAL_SHARE", amount.toString());
								break;
							case INSURANCE_DEDUCTION:
								amount = new BigDecimal(recoveryTransMap.get("TOTAL_INSURANCE"));
								transAmount = currentTransaction.getTransactionAmount().getMoney().getAmount();
								amount = amount.add(transAmount);
								recoveryTransMap.put("TOTAL_INSURANCE", amount.toString());
								break;
							}

						}

					} else {
						logger.error("Current Transaction id already exists.");
						klsResponse.setResponseStatus(ISOResponseCodes.DUPLICATE_TRANSACTION);
						klsResponse.setErrorMessage("Current Transaction id already exists.");
					}
				} catch (Exception excp) {
					logger.error("Current Transaction data cannot be saved.");
					klsResponse.setResponseStatus(ISOResponseCodes.SYSTEM_ERROR);
					klsResponse.setErrorMessage("Current Transaction data cannot be saved.");
				}
			}
		}
		logger.info("End : Calling current transaction dao in handleDebitTransaction() method.");
		return klsResponse;
	}

	@Override
	public void updateCurrentTransaction(CurrentTransactionData currentTransactionData) {

		logger.info("Start : Calling current transaction dao in updateCurrentTransaction() method.");
		// get the current transaction dao.
		ICurrentTransactionDAO dao = KLSDataAccessFactory.getCurrentTransactionDAO();
		CurrentTransaction master = CurrentTransactionHelper.getCurrentTransaction(currentTransactionData);
		// update the current transaction to the db.
		try {
			dao.updateCurrentTransaction(master);
		} catch (Exception excp) {
			logger.error("Current Transaction data cannot be updated as current transaction id does not exist");
			throw new KlsRuntimeException("Current Transaction data cannot be updated as current transaction id does not exist", excp.getCause());
		}
		logger.info("End : Calling current transaction dao in updateCurrentTransaction() method.");
	}

	/**
	 * 
	 * @param accountNumber
	 * @return
	 */
	private boolean isSavingsAccountNumberValid(String savingsAccountNumber, CurrentTransaction master, KLSResponse klsResponse,
			boolean isStandaloneMode) {

		logger.info("Start : Checking whether the savings account number is valid in isSavingsAccountValid() method.");
		logger.info(" Savings account number : " + savingsAccountNumber);
		boolean returnValue = false;
		// get the account property dao.
		IAccountPropertyDAO dao = KLSDataAccessFactory.getAccountPropertyDAO();
		AccountProperty accountProperty = null;
		Account account = null;
		IAccountDAO accountdao=KLSDataAccessFactory.getAccountDAO();
		logger.info(" isStandaloneMode in issaving method : " + isStandaloneMode);
		
			try {
				if(isStandaloneMode){
					logger.info(" in StandaloneMode in accountnumber method : " + master.getAccountNumber());
					account=accountdao.getAccount(savingsAccountNumber);
					if(account.getAccountProperty()!=null)
					{
						logger.info(" in StandaloneMode inside accountproperty : " );
						accountProperty=account.getAccountProperty();
					}
					
				}
				if (savingsAccountNumber != null && !isStandaloneMode) {
				accountProperty = dao.getAccountProperty(savingsAccountNumber, false);
				if (accountProperty == null) {
					logger.error("Savings account number does not exists in the database.");
					String message = PropertiesUtil.getProperty(ISOResponseCodes.NO_SAVINGS_ACCOUNT);
					klsResponse.setResponseStatus(ISOResponseCodes.NO_SAVINGS_ACCOUNT);
					klsResponse.setErrorMessage(message);
				}
				}
				if (accountProperty != null) {
					returnValue = isValidTransaction(accountProperty, isStandaloneMode, klsResponse);
					if (returnValue)
						returnValue = isAccountValid(accountProperty, master, klsResponse);
				}
			} catch (Exception excp) {
				if (accountProperty == null) {
					logger.error("Savings/Account  account number does not exists in the database.");
					String message = PropertiesUtil.getProperty(ISOResponseCodes.NO_SAVINGS_ACCOUNT);
					klsResponse.setResponseStatus(ISOResponseCodes.NO_SAVINGS_ACCOUNT);
					klsResponse.setErrorMessage(message);
				}
			}

		
		logger.info("End : Checking whether the savings account number is valid in isSavingsAccountValid() method.");
		return returnValue;
	}

	private boolean isValidTransaction(AccountProperty accountProperty, boolean isStandaloneMode, KLSResponse klsResponse) {
		boolean returnValue = true;
		String cardStatus = "";
		PersonData personData = RestClientUtil.getAllCustomerDataByCusomterId(accountProperty.getCustomerId());
		List<PartyBankInfoData> bankDetailList = personData.getBankDetailList();
		if (bankDetailList != null && !bankDetailList.isEmpty()) {
			PartyBankInfoData bankData = bankDetailList.get(0);
			cardStatus = bankData.getCardStatus();
		}
		/*if (!isStandaloneMode && !cardStatus.equalsIgnoreCase("I")) {
			logger.info("Transactions not allowed as Card not not issued");
			klsResponse.setResponseStatus(ISOResponseCodes.TRANSACTION_NOT_ALLOWED);
			klsResponse.setErrorMessage("Transactions are not allowed");
			returnValue = false;
		} else if (isStandaloneMode && cardStatus.equalsIgnoreCase("I")) {
			logger.info("Transactions not allowed as Card not not issued");
			klsResponse.setResponseStatus(ISOResponseCodes.TRANSACTION_NOT_ALLOWED);
			klsResponse.setErrorMessage("Transactions are not allowed");
			returnValue = false;
		}*/
		return returnValue;
	}
		

	/**
	 * 
	 * @param master
	 * @return
	 */
	private boolean isAccountValid(AccountProperty accountProperty, CurrentTransaction master, KLSResponse klsResponse) {

		logger.info("Start : Checking whether the account is valid and in active status in isAccountValid() method.");
		logger.info(" account property id : " + accountProperty.getId());
		boolean returnValue = false;
		// get the account dao.
		IAccountDAO dao = KLSDataAccessFactory.getAccountDAO();
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		IBankPacsGlDAO bankPacsGlDao = KLSDataAccessFactory.getBankPacsGlDAO();
		// IBorrowingsAccountDAO borrowingsAccDao =
		// KLSDataAccessFactory.getBorrowingsAccountDAO();

		Account account = null;
		try {
			account = dao.getAccount(accountProperty.getId(), false);
			if (account == null) {
				logger.error("Account does not exists in the database.");
				String message = PropertiesUtil.getProperty(ISOResponseCodes.UNABLE_TO_PROCESS_INVALID_ACCOUNT);
				klsResponse.setResponseStatus(ISOResponseCodes.UNABLE_TO_PROCESS_INVALID_ACCOUNT);
				klsResponse.setErrorMessage(message);
			} else {
				logger.info(" account id : " + account.getId());
				Pacs pacs = pacsDao.getPacs(accountProperty.getPacs().getId());
				logger.info(" pacs id : " + pacs.getId());
				if (pacs != null) {
					master.setPacs(pacs);
					int branchId = accountProperty.getBranch().getId();
					int pacsId = accountProperty.getPacs().getId();
					String bankCode = accountProperty.getBranch().getCode();
					String deviceNo = master.getTerminalId();

					klsResponse.setPacsReconcilationAccNo(pacs.getBankPacsAccNumber());
					klsResponse.setPacsDeviceAccNo(bankPacsGlDao.getBankPacsGlAccNo(bankCode, branchId, pacsId, deviceNo));
					klsResponse.setLoanAccountNo(account.getAccountNumber());
				}

				if (AccountStatus.Active.equals(account.getStatus())) {
					master.setAccountNumber(account.getAccountNumber());
					master.setAccount(account);
					returnValue = true;
				}
				if (AccountStatus.CLOSED.equals(account.getStatus())) {
					logger.error("Account exists in the database but in closed status");
					String message = PropertiesUtil.getProperty(ISOResponseCodes.INVALID_TRANSACTION_ACCOUNT_CLOSED);
					klsResponse.setResponseStatus(ISOResponseCodes.INVALID_TRANSACTION_ACCOUNT_CLOSED);
					klsResponse.setErrorMessage(message);
				}
				if (!(AccountStatus.Active.equals(account.getStatus()))) {
					logger.error("Account exists in the database but in active status");
					String message = PropertiesUtil.getProperty(ISOResponseCodes.INVALID_ACCOUNT);
					klsResponse.setResponseStatus(ISOResponseCodes.INVALID_ACCOUNT);
					klsResponse.setErrorMessage(message);
				}
			}
		} catch (Exception excp) {
			if (account == null) {
				logger.error("Account does not exists in the database.");
				String message = PropertiesUtil.getProperty(ISOResponseCodes.UNABLE_TO_PROCESS_INVALID_ACCOUNT);
				klsResponse.setResponseStatus(ISOResponseCodes.UNABLE_TO_PROCESS_INVALID_ACCOUNT);
				klsResponse.setErrorMessage(message);
			}
		}
		logger.info("End : Checking whether the account is valid and in active status in isAccountValid() method.");
		return returnValue;
	}

	/**
	 * 
	 * @param master
	 * @return
	 */
	/*	@Override
		private void checkTransactionDateValidity(CurrentTransaction master, List lineOfCreditAccountsList) {

			logger.info("Start : Checking whether the transaction date is valid in isTransactionDateValid() method.");
			Date transactionDate = master.getBusinessDate();
			if (transactionDate != null) {
				List copyList = new ArrayList();
				copyList.addAll(lineOfCreditAccountsList);
				for (Object object : copyList) {
					if (object instanceof LineOfCredit) {
						LineOfCredit lineOfCredit = (LineOfCredit) object;
						logger.info(" Loan Type : " + lineOfCredit.getLoanType());
						Date seasonStartDate = lineOfCredit.getSeason().getDrawalStartDate();
						Date seasonEndDate = lineOfCredit.getSeason().getDrawalEndDate();
						if (!((transactionDate.getTimeInMillis() >= seasonStartDate.getTimeInMillis()) && (transactionDate.getTimeInMillis() <= seasonEndDate
								.getTimeInMillis()))) {
							lineOfCreditAccountsList.remove(lineOfCredit);
						}
					}
				}
			} else {
				lineOfCreditAccountsList = new ArrayList();
			}
			logger.info("End : Checking whether the transaction date is valid in isTransactionDateValid() method.");
		}*/

	private ReturnStatus checkLineOfCreditAccountsBalance(CurrentTransaction master, String suvikasBalance, KLSResponse klsResponse,
			List<CurrentTransaction> currentTransactionList) {

		logger.info("Start : Checking the transaction amount against the line of " + "credit accounts in checkLineOfCreditAccountsBalance() method.");
		ReturnStatus returnStatus = null;
		ILineOfCreditDAO dao = KLSDataAccessFactory.getLineOfCreditDAO();
		List<LineOfCredit> firstDrawlList = new ArrayList<LineOfCredit>();
		String query = PropertiesUtil.getProperty(StringConstant.LINE_Of_CREDIT_DEBIT_QUERY);
		logger.info(" query is : " + query);
		List lineOfCreditAccountsList = dao.getLineOfCreditAccountsList(master.getAccount().getId(), false, query);
		logger.info(" line of credits account list size : " + lineOfCreditAccountsList.size());
		checkTransactionDateValidity(master, lineOfCreditAccountsList);
		logger.info(" line of credits account list size after transaction date validity : " + lineOfCreditAccountsList.size());
		boolean isChargesCalculated = true;
		populateAllDefaultValues();
		Integer voucherNumber = null;
		AccountingMoney sumBalance = AccountingMoney.ZERO;
		String businessDate = DateUtil.getDateString(LoanServiceUtil.getBusinessDate());
		String startDate="";
		ISeasonParameterDAO seasonParamDao = KLSDataAccessFactory.getSeasonParameterDAO();
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		if (!lineOfCreditAccountsList.isEmpty()) {
			for (Object object : lineOfCreditAccountsList) {
				LineOfCredit lineOfCredit = (LineOfCredit) object;
				master.setLineOfCredit(lineOfCredit);
				Money operatingCashLimit = lineOfCredit.getOperatingCashLimit();
				logger.info(" line of credit id : " + lineOfCredit.getId() + " operatingCashLimit : " + operatingCashLimit);
				BigDecimal kindBalance = lineOfCredit.getKindBalance().getMoney().getAmount();
				logger.info(" kindBalance : " + kindBalance);
				AccountingMoney kindAcctMoney = MoneyUtil.getAccountingMoney(kindBalance);
				logger.info(" kindAcctMoney : " + kindAcctMoney);
				AccountingMoney currentBalance = lineOfCredit.getCurrentBalance();
				logger.info(" currentBalance : " + currentBalance);
				AccountingMoney operatingCashLmt = MoneyUtil.getAccountingMoney(operatingCashLimit);
				AccountingMoney balance = operatingCashLmt.add(currentBalance).add(kindAcctMoney);
				balance = balance.subtract(moneyMap.get(ServiceConstants.TOTAL_CHARGES));
				
				String insuranceDate = null;
				SeasonParameter seasonParameter = seasonParamDao.getSeasonParameter(lineOfCredit.getSeason().getId(), lineOfCredit.getCrop().getId(),lineOfCredit.getAccount().getAccountProperty().getPacs().getId());
				startDate=DateUtil.getDateString(lineOfCredit.getSeason().getDrawalStartDate());
				
				if(seasonParameter !=  null){
					insuranceDate = DateUtil.getDateByAddingNoOfDays(startDate, seasonParameter.getInsurancePeriod());
				}
				if (lineOfCredit.getIsFirstDrawal() != null && lineOfCredit.getIsFirstDrawal() == 0 && isChargesCalculated) {
					logger.info(" first withdrawl  list added " );
					firstDrawlList.addAll(lineOfCreditAccountsList);
					Boolean insuranceEntry = false;
					if(insuranceDate != null){
						if(DateUtil.compareDate(businessDate,insuranceDate)<=0){
							insuranceEntry = true;
						}
					}
					voucherNumber = calculateCharges(master, lineOfCreditAccountsList, currentTransactionList,insuranceEntry);
					balance = balance.subtract(moneyMap.get(ServiceConstants.TOTAL_CHARGES));
					moneyMap.put(ServiceConstants.TOTAL_CHARGES, AccountingMoney.ZERO);
					isChargesCalculated = false;
					klsResponse.setFirstDrawal(true);

					HashMap<String, AccountingMoney> pacsBorrowingsShareMap = new HashMap<String, AccountingMoney>();
					HashMap<String, AccountingMoney> pacsBorrowingsInsuMap = new HashMap<String, AccountingMoney>();

					IBorrowingsAccountDAO borrowingsAccDao = KLSDataAccessFactory.getBorrowingsAccountDAO();
					AccountProperty accountProperty = master.getAccount().getAccountProperty();
					int branchId = accountProperty.getBranch().getId();
					int pacsId = accountProperty.getPacs().getId();
					String bankCode = accountProperty.getBranch().getCode();
					if(lineOfCredit.getProduct().getBorrowingRequired() != null && "Y".equalsIgnoreCase(lineOfCredit.getProduct().getBorrowingRequired())){
						for (ChargesForLineOfCredit chargesForLoc : _shareChargesList) {
							LineOfCredit loc = dao.getLineOfCreditById(chargesForLoc.getLineOfCreditId(), false);
							int productId = loc.getProduct().getId();
							String borrowingsAcc = borrowingsAccDao.getBorrowingsAccNo(bankCode, branchId, pacsId, productId);
							pacsBorrowingsShareMap.put(borrowingsAcc, MoneyUtil.getAccountingMoney(chargesForLoc.getChargeAmount()));
						}
						
						if(insuranceDate != null){
							if(DateUtil.compareDate(businessDate,insuranceDate)<=0){
								for (ChargesForLineOfCredit chargesForLoc : _insuranceChargesList) {
									LineOfCredit loc = dao.getLineOfCreditById(chargesForLoc.getLineOfCreditId(), false);
									int productId = loc.getProduct().getId();
									String borrowingsAcc = borrowingsAccDao.getBorrowingsAccNo(bankCode, branchId, pacsId, productId);
									pacsBorrowingsInsuMap.put(borrowingsAcc, MoneyUtil.getAccountingMoney(chargesForLoc.getChargeAmount()));
								}
							}
						}
					}
					else{
						logger.error("getLoanInfo : Borrowing Account not defined for this pacs with product id:"+lineOfCredit.getProduct().getId());
					}
					klsResponse.setPacsBorrowingsInsuMap(pacsBorrowingsInsuMap);
					klsResponse.setPacsBorrowingsShareMap(pacsBorrowingsShareMap);

				}
				logger.info(" balance : " + balance);
				sumBalance = sumBalance.add(balance);
			}
			if (suvikasBalance != null) {
				BigDecimal bd = new BigDecimal(suvikasBalance);
				moneyMap.put(ServiceConstants.SUVIKAS_BALANCE, MoneyUtil.getAccountingMoney(bd));
			}
			if (voucherNumber == null) {
				voucherNumber = VoucherNumberUtil.getVoucherNumber(master.getPacs().getId().toString(), master.getTransactionType().getValue());
			}
			moneyMap.put(ServiceConstants.SUM, sumBalance);
			logger.info(" sumBalance : " + sumBalance);
			returnStatus = checkForBalance(master, lineOfCreditAccountsList, klsResponse, currentTransactionList, firstDrawlList, voucherNumber);
			//Voucher number stored in Loan Disurshment entry table. 
			klsResponse.setVoucherNumber(master.getTransactionType().getValue() + "-" + voucherNumber);
			if (returnStatus != null) {
				AccountProperty accountProperty = master.getAccount().getAccountProperty();
				int pacsId = accountProperty.getPacs().getId();

				List<LineOfCredit> locList = (ArrayList<LineOfCredit>) returnStatus.getDebitAccountMap().get(ServiceConstants.ACCOUNTS_LIST);
				List<AccountingMoney> moneyList = (ArrayList<AccountingMoney>) returnStatus.getDebitAccountMap().get(ServiceConstants.AMOUNTS_LIST);
				HashMap<String, AccountingMoney> pacsBorrowingsMap = new HashMap<String, AccountingMoney>();

				if (!locList.isEmpty()) {
					LineOfCredit loc;
					for (int i = 0; i < locList.size(); i++) {
						loc = locList.get(i);
						loc = dao.getLineOfCreditById(loc.getId(), false);
						int productId = loc.getProduct().getId();
						if(loc.getProduct().getBorrowingRequired() != null && "Y".equalsIgnoreCase(loc.getProduct().getBorrowingRequired())){
							BorrowingsAccountProperty br  = KLSDataAccessFactory.getBorrowingsAccountPropertyDAO().getAccountPropertyByLoanProductIdPacsId(pacsId, productId);
							if(br != null){
								pacsBorrowingsMap.put(br.getCcbAccountNumber(), moneyList.get(i));
							}else{
								logger.error("Borrowing Account not exits for ths pacs with product");
								klsResponse.setErrorMessage("No borrowing account(CCB) found");
								klsResponse.setResponseStatus(ISOResponseCodes.NO_CREDIT_ACCOUNT);
							}
						}else{
							logger.error("getLoanInfo 1: Borrowing Account not defined for this pacs with product id:"+loc.getProduct().getId());
						}
						/*String borrowingsAcc = borrowingsAccDao.getBorrowingsAccNo(bankCode, branchId, pacsId, productId);
						if (borrowingsAcc != null)
							pacsBorrowingsMap.put(borrowingsAcc, moneyList.get(i));*/
					}
				}
				logger.info("ccb accounts map:::"+pacsBorrowingsMap);
				klsResponse.setPacsBorrowingsMap(pacsBorrowingsMap);
			}
		} else {
			logger.error("No eligible line of credits found");
			klsResponse.setErrorMessage("No eligible line of credits found");
			klsResponse.setResponseStatus(ISOResponseCodes.NO_CREDIT_ACCOUNT);
		}
		logger.info("End : Checking the transaction amount against the line of " + "credit accounts in checkLineOfCreditAccountsBalance() method.");
		return returnStatus;
	}

	/**
	 * 
	 */
	private void populateAllDefaultValues() {

		logger.info("Start : Populating the default values for all the charges in populateAllDefaultValues() method.");
		moneyMap.put(ServiceConstants.SHARE_CHARGES, AccountingMoney.ZERO);
		moneyMap.put(ServiceConstants.INSURANCE_CHARGES, AccountingMoney.ZERO);
		moneyMap.put(ServiceConstants.TOTAL_CHARGES, AccountingMoney.ZERO);
		moneyMap.put(ServiceConstants.SUM, AccountingMoney.ZERO);
		moneyMap.put(ServiceConstants.SUVIKAS_BALANCE, AccountingMoney.ZERO);
		moneyMap.put(ServiceConstants.CHARGES_SUM, AccountingMoney.ZERO);
		logger.info("End : Populating the default values for all the charges in populateAllDefaultValues() method.");
	}

	/**
	 * 
	 * @param master
	 * @param lineOfCreditAccountsList
	 * @param currentTransactionList
	 */
	private Integer calculateCharges(CurrentTransaction master, List lineOfCreditAccountsList, List<CurrentTransaction> currentTransactionList, Boolean insuranceEntryinct) {

		logger.info("Start : Calculating the total charges amount for all the line of " + "credit accounts in calculateCharges() method.");
		IChargesDAO chargesDao = KLSDataAccessFactory.getChargesDAO();
		List<Long> seasonIdList = new ArrayList<Long>();
		for (Object object : lineOfCreditAccountsList) {
			LineOfCredit lineOfCredit = (LineOfCredit) object;
			Long seasonId = lineOfCredit.getSeason().getId();
			if (!(seasonIdList.contains(seasonId))) {
				seasonIdList.add(seasonId);
			}
		}
		List<ChargesForLineOfCredit> shareChargesList = chargesDao.getShareCharges(master.getAccount().getId(), seasonIdList);
		
		
		
		
		
		//AccountingMoney shareChargesMoney = calculateTotalCharges(shareChargesList);
		Map<Long,Money> shareMap = new HashMap<Long,Money>();
		shareMap=LoanServiceUtil.getShareAmountWhileDisbursement(lineOfCreditAccountsList);
		AccountingMoney shareChargesMoney = MoneyUtil.getAccountingMoney(shareMap.get(0l));
		_shareChargesList = LoanServiceUtil.getUpdatedShareAmts(shareChargesList, shareMap);
		
		
		
		
		
		Integer voucherNumber = VoucherNumberUtil.getVoucherNumber(master.getPacs().getId().toString(), master.getTransactionType().getValue());
		String voucher = master.getTransactionType().getValue() + "-" + voucherNumber;
		logger.info(" voucher : " + voucher);
		if(shareChargesMoney.compareTo(AccountingMoney.ZERO)!=0)
			postShareTransaction(shareChargesMoney, master.getAccount(), voucher);
		logger.info(" share charges : " + shareChargesMoney);
		List<ChargesForLineOfCredit> insuranceChargesList= new ArrayList<ChargesForLineOfCredit>();
		if(insuranceEntryinct==true){
		 insuranceChargesList = chargesDao.getInsuranceCharges(master.getAccount().getId(), seasonIdList);
		_insuranceChargesList = insuranceChargesList;
		AccountingMoney insuranceChargesMoney = calculateTotalCharges(insuranceChargesList);
		logger.info(" insurance charges : " + insuranceChargesMoney);
		moneyMap.put(ServiceConstants.INSURANCE_CHARGES, insuranceChargesMoney);
		AccountingMoney totalCharges = shareChargesMoney.add(insuranceChargesMoney);
		moneyMap.put(ServiceConstants.TOTAL_CHARGES, totalCharges);
		moneyMap.put(ServiceConstants.CHARGES_SUM, totalCharges);
		logger.info(" share and insurance charges sum : " + totalCharges);
		}
		
		moneyMap.put(ServiceConstants.SHARE_CHARGES, shareChargesMoney);
		
		populateCurrentTransactionRecord(master, currentTransactionList, shareChargesList, TransactionCode.SHARE_TRANSFER.getValue(), voucherNumber);
		populateCurrentTransactionRecord(master, currentTransactionList, insuranceChargesList, TransactionCode.INSURANCE_DEDUCTION.getValue(),
				voucherNumber);
	
		logger.info("End : Calculating the total charges amount for all the line of " + "credit accounts in calculateCharges() method.");
		return voucherNumber;
	}

	/**
	 * 
	 * @param chargesList
	 * @return
	 */
	private AccountingMoney calculateTotalCharges(List<ChargesForLineOfCredit> chargesList) {

		logger.info(" Start : Calculating total charges in  calculateTotalCharges() method.");
		Money totalSum = AccountingMoney.ZERO.getMoney();
		for (ChargesForLineOfCredit chargeClass : chargesList) {
			Money chargeAmount = chargeClass.getChargeAmount();
			logger.info(" chargeAmount : " + chargeAmount + " for line of credit id : " + chargeClass.getLineOfCreditId());
			totalSum = totalSum.add(chargeAmount);
		}
		logger.info(" total sum charges : " + totalSum);
		logger.info(" End : Calculating total charges in  calculateTotalCharges() method.");
		AccountingMoney accountingMoney = MoneyUtil.getAccountingMoney(totalSum);
		return accountingMoney;
	}

	private void postShareTransaction(AccountingMoney shareChargesMoney, Account acc, String voucherNumber) {
		logger.info(" Start : Calling postShareTransaction() method.");
		ShareTransactionData shareTrnData = new ShareTransactionData();
		ShareAccountData shareAccountData = null;

		try {
			shareAccountData = RestClientUtil.getMemberShareAccountByCustomerId(acc.getAccountProperty().getCustomerId());
			IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
			if (shareAccountData != null) {
				shareTrnData.setShareAccountId(shareAccountData.getAccountId());
				//shareTrnData.setTransDate(DateUtil.getDateString(LoanServiceUtil.getBusinessDate()));
				// shareTranData.setModeOfPayment();
				shareTrnData.setAmountTransfer(shareChargesMoney.getMoney().getAmount().doubleValue());
				shareTrnData.setRemarks("Credit transaction from KLS");
				shareTrnData.setCrDr(new Integer("1"));
				shareTrnData.setTransactionType("Drawal");
				shareTrnData.setPacsId(acc.getAccountProperty().getPacs().getId());
				String businessDate="";
				Pacs pacs=pacsDao.getPacs(acc.getAccountProperty().getPacs().getId());
				logger.info("pacs type::"+pacs.getPacsStatus());
				if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
					businessDate=RestClientUtil.getPacsBusinessDate(acc.getAccountProperty().getPacs().getId(),pacs.getBranch().getId());
					//businessDate="2017-05-01";
					shareTrnData.setTransDate(businessDate);
				}
				else{
					businessDate=DateUtil.getDateString(LoanServiceUtil.getBusinessDate());
					shareTrnData.setTransDate(businessDate);
				}
				shareTrnData.setVoucherNumber(voucherNumber);
				String response = RestClientUtil.addNewSharesToExistingAccountByMemberCode(shareTrnData);
				logger.info("Response for share transactin posted: " + response);
			}

		} catch (Exception e) {
			logger.error("Error while posting share transaction. " + e.getMessage());
			new KlsRuntimeException("Error while posting share transaction", e.getCause());
		}

		logger.info(" End : Calling  postShareTransaction() method.");

	}

	private ReturnStatus checkForBalance(CurrentTransaction master, List lineOfCreditAccountsList, KLSResponse klsResponse,
			List<CurrentTransaction> currentTransactionList, List<LineOfCredit> firstDrawlList, Integer voucherNumber) {

		logger.info("Start : Checking the transaction amount against the line of " + "credit accounts sum in checkForBalance() method.");
		ReturnStatus returnStatus = null;
		AccountingMoney transactionAmount = master.getTransactionAmount();
		logger.info(" transactionAmount : " + transactionAmount);
		AccountingMoney suvikasBalanceMoney = moneyMap.get(ServiceConstants.SUVIKAS_BALANCE);
		logger.info(" suvikas balance : " + suvikasBalanceMoney);
		AccountingMoney sum = moneyMap.get(ServiceConstants.SUM);
		logger.info(" sum balance : " + sum);
		AccountingMoney totalBalance = suvikasBalanceMoney.add(sum);
		logger.info(" total balance : " + totalBalance);
		BigDecimal bdTransactionAmount = transactionAmount.getMoney().getAmount();
		if (totalBalance.isZero() || totalBalance.isCredit()) {
			if (sum.isCredit() && transactionAmount.isCredit()) {
				BigDecimal bdSum = sum.getMoney().getAmount();
				int result = bdSum.compareTo(bdTransactionAmount);
				logger.info(" compareTo result in sum credit if block : " + result);
				if (result >= 0) {
					returnStatus = debitTheAccount(master, lineOfCreditAccountsList, currentTransactionList, firstDrawlList, voucherNumber);
					klsResponse.setAvailableBalance(sum.getMoney().getAmount().toString());
					klsResponse.setResponseStatus(ISOResponseCodes.APPROVED);
				} else {
					if (totalBalance.isCredit() && transactionAmount.isCredit()) {
						BigDecimal bdTotalBalance = totalBalance.getMoney().getAmount();
						result = bdTotalBalance.compareTo(bdTransactionAmount);
						logger.info(" compareTo result in sum credit else block : " + result);
						if (result >= 0) {
							returnStatus = debitTheAccount(master, lineOfCreditAccountsList, currentTransactionList, firstDrawlList, voucherNumber);
							klsResponse.setAvailableBalance(sum.getMoney().getAmount().toString());
							klsResponse.setResponseStatus(ISOResponseCodes.APPROVED);
						} else {
							logger.info(" NO FUNDS ");
							klsResponse.setResponseStatus(ISOResponseCodes.NO_FUNDS);
						}
					}
				}
			} else if ((sum.isZero() || sum.isDebit()) && transactionAmount.isCredit()) {
				BigDecimal bdTotalBalance = totalBalance.getMoney().getAmount();
				int result = bdTotalBalance.compareTo(bdTransactionAmount);
				logger.info(" compareTo result in sum debit if block : " + result);
				if (result >= 0) {
					returnStatus = debitTheAccount(master, lineOfCreditAccountsList, currentTransactionList, firstDrawlList, voucherNumber);
					klsResponse.setAvailableBalance(sum.getMoney().getAmount().toString());
					klsResponse.setResponseStatus(ISOResponseCodes.APPROVED);
				} else {
					logger.info(" NO FUNDS ");
					klsResponse.setResponseStatus(ISOResponseCodes.NO_FUNDS);
				}
			} else {
				logger.info(" NO FUNDS ");
				klsResponse.setResponseStatus(ISOResponseCodes.NO_FUNDS);
			}
		} else {
			logger.info(" NO FUNDS ");
			klsResponse.setResponseStatus(ISOResponseCodes.NO_FUNDS);
		}
		logger.info("End : Checking the transaction amount against the line of " + "credit accounts sum in checkForBalance() method.");
		return returnStatus;
	}

	private ReturnStatus debitTheAccount(CurrentTransaction master, List lineOfCreditAccountsList, List<CurrentTransaction> currentTransactionList,
			List<LineOfCredit> firstDrawlList, Integer voucherNumber) {

		logger.info("Start : Debit the amount in line of " + "credit accounts in debitTheAccount() method.");
		ReturnStatus returnStatus = this.new ReturnStatus();
		Map<String, List<?>> debitMap = new HashMap<String, List<?>>();
		List<LineOfCredit> debitAccountList = new ArrayList<LineOfCredit>();
		List<AccountingMoney> amountsList = new ArrayList<AccountingMoney>();
		AccountingMoney transactionAmount = master.getTransactionAmount();
		for (Object object : lineOfCreditAccountsList) {
			LineOfCredit lineOfCredit = (LineOfCredit) object;
			Money operatingCashLimit = lineOfCredit.getOperatingCashLimit();
			master.setLineOfCredit(lineOfCredit);
			AccountingMoney currentBalance = lineOfCredit.getCurrentBalance();
			AccountingMoney operatingCashLmt = MoneyUtil.getAccountingMoney(operatingCashLimit);
			BigDecimal kindBalance = lineOfCredit.getKindBalance().getMoney().getAmount();
			AccountingMoney kindAcctMoney = MoneyUtil.getAccountingMoney(kindBalance);
			AccountingMoney balance = operatingCashLmt.add(currentBalance).add(kindAcctMoney);
			logger.info(" balance money before charges deduction : " + balance);
			if (balance.isZero()) {
				continue;
			}
			AccountingMoney chargesMoney = moneyMap.get(ServiceConstants.CHARGES_SUM);
			logger.info(" charges money : " + chargesMoney);
			balance = balance.subtract(chargesMoney);
			logger.info(" balance money after charges deduction : " + balance);
			moneyMap.put(ServiceConstants.CHARGES_SUM, AccountingMoney.ZERO);

			AccountingMoney netMoney = transactionAmount.subtract(balance);
			logger.info(" net money : " + netMoney);
			if (netMoney.isZero() || netMoney.isDebit()) {
				debitAccountList.add(lineOfCredit);
				master.setOpeningBalance(currentBalance);
				populateRemarks(master, lineOfCredit, "by cash:");
				currentBalance = transactionAmount.add(chargesMoney);
				logger.info(" currentBalance : " + currentBalance);
				currentBalance = AccountingMoney.valueOf(currentBalance.getMoney(), DebitOrCredit.DEBIT);
				amountsList.add(currentBalance);
				populateCurrentTransactionList(master, currentTransactionList, firstDrawlList, TransactionCode.PRINCIPAL_BAL, transactionAmount,
						voucherNumber);
				break;
			} else {
				transactionAmount = netMoney;
				debitAccountList.add(lineOfCredit);
				AccountingMoney currBalance = balance; //balance.add(chargesMoney);
				AccountingMoney debitAmount = AccountingMoney.valueOf(currBalance.getMoney(), DebitOrCredit.DEBIT);
				logger.info(" debit amount : " + debitAmount);
				//amountsList.add(debitAmount);
				master.setOpeningBalance(currentBalance);
				populateRemarks(master, lineOfCredit, "by cash:");
				AccountingMoney transAmount = AccountingMoney.valueOf(debitAmount.getMoney(), DebitOrCredit.CREDIT);
				logger.info(" trans amount : " + transAmount);
				populateCurrentTransactionList(master, currentTransactionList, firstDrawlList, TransactionCode.PRINCIPAL_BAL, transAmount,
						voucherNumber);
				// Add charges first time only. next time onwards it will be zero
				currBalance = balance.add(chargesMoney);
				debitAmount = AccountingMoney.valueOf(currBalance.getMoney(), DebitOrCredit.DEBIT);
				
				amountsList.add(debitAmount);
			}
		}
		debitMap.put(ServiceConstants.ACCOUNTS_LIST, debitAccountList);
		debitMap.put(ServiceConstants.AMOUNTS_LIST, amountsList);
		debitMap.put(ServiceConstants.FIRST_DRAWL_LIST, firstDrawlList);
		returnStatus.setReturnValue(true);
		returnStatus.setDebitAccountMap(debitMap);
		/*
		 * AccountingMoney shareCharges =
		 * moneyMap.get(ServiceConstants.SHARE_CHARGES); if
		 * (shareCharges.isZero()) { postShareAmountTransaction(master,
		 * shareCharges); }
		 */
		logger.info("End : Debit the amount in line of " + "credit accounts in debitTheAccount() method.");
		return returnStatus;
	}

	/**
	 * 
	 * @param master
	 * @param shareCharges
	 */
	private void postShareAmountTransaction(CurrentTransaction master, AccountingMoney shareCharges) {

		logger.info("Start : posting the share amount transaction to pacs in postShareAmountTransaction() method.");
		PostTransactionThread postTransaction = new PostTransactionThread();
		postTransaction.setCurrentTransaction(master);
		postTransaction.setTotalCharges(shareCharges.getMoney().getAmount());
		Thread thread = new Thread(postTransaction);
		thread.start();
		logger.info("End : posting the share amount transaction to pacs in postShareAmountTransaction() method.");
	}

	/**
	 * 
	 * @param master
	 * @param lineOfCredit
	 * @param appendString
	 */
	private void populateRemarks(CurrentTransaction master, LineOfCredit lineOfCredit, String appendString) {

		String remarks = master.getRemarks();
		StringBuilder remarksBuilder = new StringBuilder();
		if (remarks != null) {
			remarksBuilder.append(appendString);
			remarksBuilder.append(lineOfCredit.getId());
			remarksBuilder.append(remarks);
		} else {
			remarksBuilder.append(appendString);
			remarksBuilder.append(lineOfCredit.getId());
		}
		if (remarksBuilder.length() > 60) {
			master.setRemarks(remarksBuilder.substring(0, 60));
		} else {
			master.setRemarks(remarksBuilder.toString());
		}
	}

	/**
	 * 
	 * @param currentTransaction
	 * @param currentTransactionList
	 * @param firstDrawlList
	 * @param transactionCode
	 * @param transactionAmount
	 */
	private void populateCurrentTransactionList(CurrentTransaction currentTransaction, List<CurrentTransaction> currentTransactionList,
			List<LineOfCredit> firstDrawlList, TransactionCode transactionCode, AccountingMoney transactionAmount, Integer voucherNumber) {

		logger.info("Start : Populating the current transaction list in populateCurrentTransactionList() method.");
		// Add current transaction record.
		CurrentTransaction copyObject = populateCurrentTransaction(currentTransaction, transactionCode, transactionAmount, voucherNumber);
		currentTransactionList.add(copyObject);
		logger.info("End : Populating the current transaction list in populateCurrentTransactionList() method.");
	}

	/**
	 * 
	 * @param currentTransaction
	 * @param transactionCode
	 * @param transactionAmount
	 * @return
	 */
	private CurrentTransaction populateCurrentTransaction(CurrentTransaction currentTransaction, TransactionCode transactionCode,
			AccountingMoney transactionAmount, Integer voucherNumber) {

		logger.info("Start : Populating the current transaction in populateCurrentTransaction() method.");
		CurrentTransaction copyObject = new CurrentTransaction();
		copyObject.setAccount(currentTransaction.getAccount());
		copyObject.setAccountNumber(currentTransaction.getAccountNumber());
		copyObject.setBusinessDate(currentTransaction.getBusinessDate());
		copyObject.setChannelType(currentTransaction.getChannelType());
		copyObject.setCrDr(currentTransaction.getCrDr());
		copyObject.setLineOfCredit(currentTransaction.getLineOfCredit());
		copyObject.setOpeningBalance(currentTransaction.getOpeningBalance());
		copyObject.setPacs(currentTransaction.getPacs());
		copyObject.setPostedStatus(currentTransaction.getPostedStatus());
		copyObject.setRemarks(currentTransaction.getRemarks());
		copyObject.setTransactionAmount(transactionAmount);
		copyObject.setTerminalId(currentTransaction.getTerminalId());
		copyObject.setTransactionCode(transactionCode);
		copyObject.setTransactionType(currentTransaction.getTransactionType());
		copyObject.setVoucherNumber(currentTransaction.getTransactionType().getValue() + "-" + voucherNumber);
		logger.info("End : Populating the current transaction in populateCurrentTransaction() method.");
		return copyObject;
	}

	/**
	 * 
	 * @param currentTransaction
	 * @param currentTransactionList
	 * @param value
	 */
	private void populateCurrentTransactionRecord(CurrentTransaction currentTransaction, List<CurrentTransaction> currentTransactionList,
			List<ChargesForLineOfCredit> chargesList, Integer value, Integer voucherNumber) {

		logger.info("Start : Populating the share and insurance current transaction record in populateCurrentTransactionRecord() method.");
		for (ChargesForLineOfCredit chargesClass : chargesList) {
			if(chargesClass.getChargeAmount().compareTo(Money.ZERO)!=0){
			CurrentTransaction copyObject = populateCurrentTransaction(currentTransaction, null, null, voucherNumber);
			copyObject.setTransactionType(TransactionType.Transfer);
			copyObject.setChannelType(ChannelType.SYSTEM);
			copyObject.setTransactionCode(TransactionCode.getType(value));
			copyObject.setOpeningBalance(AccountingMoney.ZERO);
			copyObject.setCrDr(-1);
			//ILineOfCreditDAO dao = KLSDataAccessFactory.getLineOfCreditDAO();
			LineOfCredit lineOfCredit =  currentTransaction.getLineOfCredit(); // dao.getLineOfCreditById(chargesClass.getLineOfCreditId(), false);
			copyObject.setLineOfCredit(lineOfCredit);
			Money chargeAmount = chargesClass.getChargeAmount();
			AccountingMoney transactionCharges = MoneyUtil.getAccountingMoney(chargeAmount);
			copyObject.setTransactionAmount(transactionCharges);
			if (value != null && value == TransactionCode.SHARE_TRANSFER.getValue()) {
				copyObject.setRemarks("share amount deduction");
			} else if (value != null && value == TransactionCode.INSURANCE_DEDUCTION.getValue()) {
				copyObject.setRemarks("insurance amount deduction");
			}
			currentTransactionList.add(copyObject);
		}
		}
		logger.info("End : Populating the share and insurance current transaction record in populateCurrentTransactionRecord() method.");
	}

	@Override
	public List<CurrentTransactionData> getAllCurrentTransactions() {

		logger.info("Start : Calling current transaction dao in getAllBankParameters() method.");
		// get the current transaction dao.
		ICurrentTransactionDAO dao = KLSDataAccessFactory.getCurrentTransactionDAO();
		List<CurrentTransactionData> currentTransactionDataList = new ArrayList<CurrentTransactionData>();
		try {
			List<CurrentTransaction> bankParameterList = dao.getAllCurrentTransactions();
			for (CurrentTransaction data : bankParameterList) {
				currentTransactionDataList.add(CurrentTransactionHelper.getCurrentTransactionData(data));
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the current transaction records");
			throw new KlsRuntimeException("Error in retrieving all the current transaction records", excp.getCause());
		}
		logger.info("End : Calling current transaction dao in getAllBankParameters() method.");
		return currentTransactionDataList;
	}

	private class ReturnStatus<T> {

		private boolean returnValue;

		private Map<String, List<T>> debitAccountMap;

		/**
		 * @return the debitAccountMap
		 */
		public Map<String, List<T>> getDebitAccountMap() {
			return debitAccountMap;
		}

		/**
		 * @param debitAccountMap
		 *            the debitAccountMap to set
		 */
		public void setDebitAccountMap(Map<String, List<T>> debitAccountMap) {
			this.debitAccountMap = debitAccountMap;
		}

		/**
		 * @return the returnValue
		 */
		public boolean isReturnValue() {
			return returnValue;
		}

		/**
		 * @param returnValue
		 *            the returnValue to set
		 */
		public void setReturnValue(boolean returnValue) {
			this.returnValue = returnValue;
		}
	}

	/**
	 * 
	 */
	public KLSResponse handleCreditTransaction(CurrentTransactionData currentTransactionData) {

		logger.info("Start : Handling credit transaction in handleCreditTransaction() method.");
		CurrentTransaction master = CurrentTransactionHelper.getCurrentTransaction(currentTransactionData);
		KLSResponse klsResponse = new KLSResponse();
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		AccountingMoney principlePaid=AccountingMoney.ZERO;
		AccountingMoney intPaid=AccountingMoney.ZERO;
		AccountingMoney penalInt=AccountingMoney.ZERO;
		Map<String, Object> creditMap = new HashMap<String, Object>();
		creditMap.put(ServiceConstants.KLS_RESPONSE, klsResponse);
		String borrowingMethod="";
		creditMap.put(ServiceConstants.IS_STANDALONE_MODE, currentTransactionData.isStandAloneStatus());
		creditMap.put(ServiceConstants.MODE_OF_PAYMENT, currentTransactionData.getModeOfPayment());
		Map<Integer,CamelRequest> reqestMap=new HashMap<Integer,CamelRequest>();
		//creditMap.put(ServiceConstants.PARTY_ID, currentTransactionData.getPartyId()); // ADDED TO DO TRANSACTION FOR MEMBER SB AC
		if(currentTransactionData.getBulkRecovery() == null)
			creditMap.put(ServiceConstants.IS_BULK_RECOVERY, false);
		else
			creditMap.put(ServiceConstants.IS_BULK_RECOVERY, true);
		boolean transVal=false;
		creditMap.put(ServiceConstants.IS_BULK_RECOVERY_FROM_ENTRY, currentTransactionData.isBulkRecoveryFromEntry());
		// check whether the savings account number exists.
		logger.info("currentTransactionData.isStandAloneStatus() in handle credit transaction method."+currentTransactionData.isStandAloneStatus());
		logger.info("currentTransactionData.isStandAloneStatus() in handle credit transaction method."+currentTransactionData.getSavingsAccountNumber());
		
		boolean isSavingsAccountNumberValid = isSavingsAccountNumberValid(currentTransactionData.getSavingsAccountNumber(), master, klsResponse,
				currentTransactionData.isStandAloneStatus());
		
		logger.info("isSavingsAccountNumberValid in handle credit transaction method."+isSavingsAccountNumberValid);
		Integer pacsId=0;
		Integer productId=0;
		
		if (isSavingsAccountNumberValid) {
			// get line of credit accounts balance.
			if (calculateLineOfCreditAccountsBalance(master, creditMap)) {
				if (processCreditTransaction(master, creditMap)) {
					// get the current transaction dao.
					ICurrentTransactionDAO dao = KLSDataAccessFactory.getCurrentTransactionDAO();
					BankParameter bankParam=KLSDataAccessFactory.getBankParameter();
					

					List<CurrentTransaction> currTransactionList = (List<CurrentTransaction>) creditMap
							.get(ServiceConstants.CREDIT_CURRENT_TRANSACTION_LIST);
					Map<String, Object>  creditMap1 = (Map<String, Object>) creditMap.get(ServiceConstants.CREDIT_ACCOUNT_MAP);
					CurrentTransaction creditDebitTransaction = copyCurrentTransaction(master);

					try {
						/*
						 * doing Debit, Credit on loan account
						 */     
						for(CurrentTransaction transaction:currTransactionList)
						{
							String businessDate="";
							Pacs pacs=pacsDao.getPacs(transaction.getPacs().getId());
							logger.info("pacs type::"+pacs.getPacsStatus());
							if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
								businessDate=RestClientUtil.getPacsBusinessDate(transaction.getPacs().getId(),pacs.getBranch().getId());
								//businessDate="2017-05-01";
								transaction.setBusinessDate(DateUtil.getVSoftDateByString(businessDate));
								
							}
							
						}
						ISOTransactionResponse response=new ISOTransactionResponse();
						if(bankParam.getCbsIntegrationMethod().equals(CBSMethodEnum.ISO8583)){
							
						    response=getISOResponse(currTransactionList,currentTransactionData);
							if(response!=null && response.getStatus()){
						   postDebitCreditTransaction(creditDebitTransaction,currTransactionList.get(0).getVoucherNumber());
						   dao.saveCreditCurrentTransaction(currTransactionList, creditMap1);
							}
							else{
								klsResponse.setResponseStatus(ISOResponseCodes.SYSTEM_ERROR);
								klsResponse.setErrorMessage("Problem with Camel server");
							}
						}
						else{
							postDebitCreditTransaction(creditDebitTransaction,currTransactionList.get(0).getVoucherNumber());
							dao.saveCreditCurrentTransaction(currTransactionList, creditMap1);
						}
						Map<String, String> recoveryTransMap = new<String, String> HashMap();
						recoveryTransMap.put("TOTAL_PRINCIPAL", "0");
						recoveryTransMap.put("TOTAL_INTEREST", "0");
						recoveryTransMap.put("TOTAL_PENAL_INTEREST", "0");

						for (CurrentTransaction currentTransaction : currTransactionList) {
							BigDecimal amount = BigDecimal.ZERO;
							BigDecimal transAmount = BigDecimal.ZERO;
							switch (currentTransaction.getTransactionCode()) {
							case PRINCIPAL_BAL:
								amount = new BigDecimal(recoveryTransMap.get("TOTAL_PRINCIPAL"));
								transAmount = currentTransaction.getTransactionAmount().getMoney().getAmount();
								amount = amount.add(transAmount);
								recoveryTransMap.put("TOTAL_PRINCIPAL", amount.toString());
								break;
							case INTEREST:
							case INTEREST_SUBSIDY_ON_LOAN:
								amount = new BigDecimal(recoveryTransMap.get("TOTAL_INTEREST"));
								transAmount = currentTransaction.getTransactionAmount().getMoney().getAmount();
								amount = amount.add(transAmount);
								recoveryTransMap.put("TOTAL_INTEREST", amount.toString());
								break;
							case PENAL_INTEREST:
								amount = new BigDecimal(recoveryTransMap.get("TOTAL_PENAL_INTEREST"));
								transAmount = currentTransaction.getTransactionAmount().getMoney().getAmount();
								amount = amount.add(transAmount);
								recoveryTransMap.put("TOTAL_PENAL_INTEREST", amount.toString());
								break;
							}

						}
						klsResponse.setsTRecoveryTransactions((HashMap<String, String>) recoveryTransMap);
						
						klsResponse.setAvailableBalance(getBalance((List) 
								creditMap.get(ServiceConstants.LINE_OF_CREDIT_LIST)));
						logger.info("============================Abalance======="+klsResponse.getAvailableBalance());
						logger.info("transVal===="+transVal);
						if(bankParam.getCbsIntegrationMethod().equals(CBSMethodEnum.ISO8583)){
							
							if(response!=null && response.getStatus())
						EntityManagerUtil.CommitOrRollBackTransaction(true);
							else
							{
								klsResponse.setResponseStatus(ISOResponseCodes.SYSTEM_ERROR);
								klsResponse.setErrorMessage("Problem with Camel server");
							}
						}
						else{
							EntityManagerUtil.CommitOrRollBackTransaction(true);
						}
					} catch (Exception e) {
						EntityManagerUtil.CommitOrRollBackTransaction(false);
						logger.error("Error occured while doing credit transaction");
						e.printStackTrace();
						throw new KlsRuntimeException("Error occured while doing credit transaction");

					}
				}
			}
		}
		logger.info("End : Handling credit transaction in handleCreditTransaction() method.");
		return klsResponse;
	}

	/**
	 * This service is used for st loan recovery when the paid amounts are
	 * editable
	 */
	@Override
	public KLSResponse handleCreditTransaction(CurrentTransactionData currentTransactionData, STLoanRecoveryData stLoanRecoveryData) {

		logger.info("Start : Handling credit transaction in handleCreditTransaction() method.");
		CurrentTransaction master = CurrentTransactionHelper.getCurrentTransaction(currentTransactionData);
		KLSResponse klsResponse = new KLSResponse();
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		Map<String, Object> creditMap = new HashMap<String, Object>();
		creditMap.put(ServiceConstants.KLS_RESPONSE, klsResponse);
		creditMap.put(ServiceConstants.IS_STANDALONE_MODE, currentTransactionData.isStandAloneStatus());
		BankParameter bankParameter = KLSDataAccessFactory.getBankParameter();
		// check whether the savings account number exists.
		logger.info("currentTransactionData.isStandAloneStatus() in handle credit transaction method."+currentTransactionData.isStandAloneStatus());
		Integer pacsId=0;
		Integer productId=0;
		boolean isSavingsAccountNumberValid = isSavingsAccountNumberValid(currentTransactionData.getSavingsAccountNumber(), master, klsResponse,
				currentTransactionData.isStandAloneStatus());
		
		CurrentTransaction creditDebitTransaction = copyCurrentTransaction(master);
		logger.info("isSavingsAccountNumberValid in handle credit transaction method."+isSavingsAccountNumberValid);
		
		if (isSavingsAccountNumberValid) {
			// get line of credit accounts balance.
			if (calculateLineOfCreditAccountsBalance(master, creditMap)) {
				String CCB=(String) creditMap.get("CCB_ACCOUNT");
				Integer borrowingProductid=(Integer) creditMap.get("Borrowing_Product");
				String borrowingProduct=borrowingProductid.toString();
				if (doCreditTransaction(master, creditMap, stLoanRecoveryData)) {
					// get the current transaction dao.
					ICurrentTransactionDAO dao = KLSDataAccessFactory.getCurrentTransactionDAO();
					List<CurrentTransaction> currTransactionList = (List<CurrentTransaction>) creditMap
							.get(ServiceConstants.CREDIT_CURRENT_TRANSACTION_LIST);
					creditMap = (Map<String, Object>) creditMap.get(ServiceConstants.CREDIT_ACCOUNT_MAP);
					try {
						/*
						 * doing Debit, Credit on loan account
						 */   
						for(CurrentTransaction transaction:currTransactionList)
						{
							String businessDate="";
							Pacs pacs=pacsDao.getPacs(transaction.getPacs().getId());
							logger.info("pacs type::"+pacs.getPacsStatus());
							if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
								businessDate=RestClientUtil.getPacsBusinessDate(transaction.getPacs().getId(),pacs.getBranch().getId());
								//businessDate="2017-05-01";
								transaction.setBusinessDate(DateUtil.getVSoftDateByString(businessDate));
								
							}
							
						}
						if(bankParameter.getCbsIntegrationMethod().equals(CBSMethodEnum.ISO8583)){
							ISOTransactionResponse response=new ISOTransactionResponse();
							 response=getISOResponse(currTransactionList,currentTransactionData);
								if(response!=null && response.getStatus()){
							    postDebitCreditTransaction(creditDebitTransaction,currTransactionList.get(0).getVoucherNumber());
						        dao.saveCreditCurrentTransaction(currTransactionList, creditMap);
							}
							else{
								postDebitCreditTransaction(creditDebitTransaction,currTransactionList.get(0).getVoucherNumber());
							   dao.saveCreditCurrentTransaction(currTransactionList, creditMap);
							}
						}
						else{
						postDebitCreditTransaction(creditDebitTransaction,currTransactionList.get(0).getVoucherNumber());
						dao.saveCreditCurrentTransaction(currTransactionList, creditMap);
						}
						Map<String, String> recoveryTransMap = new<String, String> HashMap();
						recoveryTransMap.put("TOTAL_PRINCIPAL", "0");
						recoveryTransMap.put("TOTAL_INTEREST", "0");
						recoveryTransMap.put("TOTAL_PENAL_INTEREST", "0");
						recoveryTransMap.put("CCB_ACCOUNT",CCB );
						recoveryTransMap.put("BORROWING_PRODUCT",borrowingProduct );

						for (CurrentTransaction currentTransaction : currTransactionList) {
							BigDecimal amount = BigDecimal.ZERO;
							BigDecimal transAmount = BigDecimal.ZERO;
							switch (currentTransaction.getTransactionCode()) {
							case PRINCIPAL_BAL:
								amount = new BigDecimal(recoveryTransMap.get("TOTAL_PRINCIPAL"));
								transAmount = currentTransaction.getTransactionAmount().getMoney().getAmount();
								amount = amount.add(transAmount);
								recoveryTransMap.put("TOTAL_PRINCIPAL", amount.toString());
								break;
							case INTEREST:
								amount = new BigDecimal(recoveryTransMap.get("TOTAL_INTEREST"));
								transAmount = currentTransaction.getTransactionAmount().getMoney().getAmount();
								amount = amount.add(transAmount);
								recoveryTransMap.put("TOTAL_INTEREST", amount.toString());
								break;
							case PENAL_INTEREST:
								amount = new BigDecimal(recoveryTransMap.get("TOTAL_PENAL_INTEREST"));
								transAmount = currentTransaction.getTransactionAmount().getMoney().getAmount();
								amount = amount.add(transAmount);
								recoveryTransMap.put("TOTAL_PENAL_INTEREST", amount.toString());
								break;
							}

						}
						klsResponse.setsTRecoveryTransactions((HashMap<String, String>) recoveryTransMap);
						//For virmati CBS integration
						if(!"O".equalsIgnoreCase(bankParameter.getCbsIntegrationMethod().getValue()) && "V".equalsIgnoreCase(bankParameter.getOther_cbs())){
							logger.info("Transaction will be commited after posting transaction in Virmathi");
						 }else{
							 EntityManagerUtil.CommitOrRollBackTransaction(true);
						 }
						
					} catch (Exception e) {
						EntityManagerUtil.CommitOrRollBackTransaction(false);
						logger.error("Error occured while doing credit transaction");
						e.printStackTrace();
						throw new KlsRuntimeException("Error occured while doing credit transaction");

					}
				}
			}
		}
		logger.info("End : Handling credit transaction in handleCreditTransaction() method.");
		return klsResponse;
	}

	private boolean doCreditTransaction(CurrentTransaction master, Map<String, Object> creditMap, STLoanRecoveryData stLoanRecoveryData) {
		logger.info("Start : Processing credit transaction in doCreditTransaction() method.");
		List lineOfCreditList = (List) creditMap.get(ServiceConstants.LINE_OF_CREDIT_LIST);
		Map<String, Map<Long, AccountingMoney>> creditAccountMap = new HashMap<String, Map<Long, AccountingMoney>>();
		Map<Long, AccountingMoney> creditPrincipalAmtMap = new HashMap<Long, AccountingMoney>();
		Map<Long, AccountingMoney> creditInterestAmtMap = new HashMap<Long, AccountingMoney>();
		Map<Long, AccountingMoney> creditPenalInterestAmtMap = new HashMap<Long, AccountingMoney>();
		Map<Long, AccountingMoney> creditInterestAccruedAmtMap = new HashMap<Long, AccountingMoney>();
		Map<Long, AccountingMoney> creditOverdueInterestAmtMap = new HashMap<Long, AccountingMoney>();

		//AccountingMoney transactionAmount = master.getTransactionAmount();
		//logger.info(" transactionAmount : " + transactionAmount);
		List<CurrentTransaction> currentTransactionList = new ArrayList<CurrentTransaction>();
		boolean value = false;
		Pacs pacs = master.getPacs();
		Integer voucherNumber = VoucherNumberUtil.getVoucherNumber(pacs.getId().toString(), master.getTransactionType().getValue());
		Integer voucherNumberforIntPosting = VoucherNumberUtil.getVoucherNumber(pacs.getId().toString(), TransactionType.Transfer.getValue());
		String voucherForIntPost = TransactionType.Transfer.getValue() + "-" + voucherNumberforIntPosting;
		AccountingMoney totalAmountPaid = MoneyUtil.getAccountingMoney(stLoanRecoveryData.getAmountPaid());
		AccountingMoney principlePaid = MoneyUtil.getAccountingMoney(stLoanRecoveryData.getPrincipalPaid());
		AccountingMoney interestPaid = MoneyUtil.getAccountingMoney(stLoanRecoveryData.getInterestPaid());
		AccountingMoney penalInterestPaid = MoneyUtil.getAccountingMoney(stLoanRecoveryData.getPenalInterestPaid());

		// Cash in Transit GL 
		Boolean isStandaloneMode = (Boolean) creditMap.get(ServiceConstants.IS_STANDALONE_MODE);
		if (isStandaloneMode) {
			String cashInTransitGl = pacs.getCashInTransitGL();
			String cashInHandGl = pacs.getCashGl();
			if (cashInTransitGl != null && cashInHandGl != null) {
				logger.info("Cash In Transit GL: " + cashInTransitGl);
				if (pacs.getIsBorrwingTransRequiredInGLExtract().equals("Y")) {
					TransactionType transactionType =  master.getTransactionType();
					master.setTransactionType(TransactionType.Transfer);
					populateCurrentTransactionRecord(null, cashInTransitGl, currentTransactionList, TransactionCode.CASH_IN_TRANSIT,
							"Reocovery:Cash in transit GL", 1, totalAmountPaid, true, master, voucherNumber);
					master.setTransactionType(transactionType);
				}
				populateCurrentTransactionRecord(null, cashInHandGl, currentTransactionList, TransactionCode.CASH_TRANSFER,
						"Reocovery:Cash in hand GL", -1, totalAmountPaid, true, master, voucherNumber);

			} else {
				logger.info("Cash in transit gl is not available for pacs id : " + pacs.getId());
				return false;
			}
		} else {
			String pacsBankAccount = pacs.getPacsBankAccNumber();
			if (pacsBankAccount != null) {
				logger.info("Pacs bank account: " + pacsBankAccount);
				populateCurrentTransactionRecord(null, pacsBankAccount, currentTransactionList, TransactionCode.PACS_BANK_ACC,
						"Reocovery:Pacs bank account", 1, totalAmountPaid, true, master, voucherNumber);
			} else {
				logger.info("Pacs bank account is not availabe for pacs id : " + pacs.getId());
				throw new KlsRuntimeException("Pacs bank account is not availabe for pacs id : " + pacs.getId());
			}
		}
		//		LineOfCredit firstLoc = null;
		for (Object object : lineOfCreditList) {
			if (object instanceof LineOfCredit) {
				LineOfCredit lineOfCredit = (LineOfCredit) object;
				/*if (firstLoc == null)
					firstLoc = lineOfCredit;*/
				AccountingMoney currentBalance = lineOfCredit.getCurrentBalance();
				logger.info(" currentBalance : " + currentBalance);
				BigDecimal interestReceivable = lineOfCredit.getInterestReceivable();
				BigDecimal penalInterestReceivable = lineOfCredit.getPenalInterestReceivable();
				Boolean asAndWhenImplemented = lineOfCredit.getProduct().getAsAndWhenImplemented();
				logger.info(" interestReceivable : " + interestReceivable);
				logger.info(" penalInterestReceivable : " + penalInterestReceivable);
				master.setLineOfCredit(lineOfCredit);
				master.setOpeningBalance(currentBalance);
				if (!principlePaid.isZero() && principlePaid.isCredit()) {
					if (currentBalance.isZero() || currentBalance.isCredit()) {
					} else {
						AccountingMoney netMoney = currentBalance.add(principlePaid);
						logger.info(" netMoney : " + netMoney);
						if (netMoney.isZero() || netMoney.isDebit()) {
							creditPrincipalAmtMap.put(lineOfCredit.getId(), principlePaid);
							creditAccountMap.put(ServiceConstants.CREDIT_PRINCIPAL_AMOUNT_MAP, creditPrincipalAmtMap);
							populateRemarks(master, lineOfCredit, "towards principal:");
							populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.PRINCIPAL_BAL, principlePaid,
									voucherNumber);
							principlePaid = AccountingMoney.ZERO;
						} else {
							AccountingMoney creditAmount = MoneyUtil.getAccountingMoney(currentBalance.getMoney().getAmount(), DebitOrCredit.CREDIT);
							logger.info(" creditAmount : " + creditAmount);
							creditPrincipalAmtMap.put(lineOfCredit.getId(), creditAmount);
							creditAccountMap.put(ServiceConstants.CREDIT_PRINCIPAL_AMOUNT_MAP, creditPrincipalAmtMap);
							populateRemarks(master, lineOfCredit, "towards principal:");
							populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.PRINCIPAL_BAL, creditAmount,
									voucherNumber);
							principlePaid = netMoney;
						}
					}
				}
				if (!interestPaid.isZero() && interestPaid.isCredit()) {
					AccountingMoney interestAccruedMoney = MoneyUtil.getAccountingMoney(interestReceivable);
					if ((interestAccruedMoney.isZero() || interestAccruedMoney.isCredit()) && !asAndWhenImplemented) {
					} else if (!(interestAccruedMoney.isZero() || interestAccruedMoney.isCredit())) {
						AccountingMoney netMoney = interestAccruedMoney.add(interestPaid);
						logger.info(" netMoney : " + netMoney);
						if (netMoney.isZero() || netMoney.isDebit()) {
							creditInterestAmtMap.put(lineOfCredit.getId(), interestPaid);
							creditAccountMap.put(ServiceConstants.CREDIT_INTEREST_AMOUNT_MAP, creditInterestAmtMap);
							populateRemarks(master, lineOfCredit, "towards interest:");
							populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.INTEREST, interestPaid,
									voucherNumber);
							interestPaid = AccountingMoney.ZERO;
						} else {
							AccountingMoney creditAmount = MoneyUtil.getAccountingMoney(interestAccruedMoney.getMoney().getAmount(),
									DebitOrCredit.CREDIT);
							logger.info(" creditAmount : " + creditAmount);
							creditInterestAmtMap.put(lineOfCredit.getId(), creditAmount);
							creditAccountMap.put(ServiceConstants.CREDIT_INTEREST_AMOUNT_MAP, creditInterestAmtMap);
							populateRemarks(master, lineOfCredit, "towards interest:");
							populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.INTEREST, creditAmount,
									voucherNumber);
							interestPaid = netMoney;
						}
					}
					if (asAndWhenImplemented) {
						AccountingMoney interestAccrued = MoneyUtil.getAccountingMoney(lineOfCredit.getInterestAccrued().setScale(0,
								RoundingMode.HALF_UP));
						if (interestAccrued.isZero() || interestAccrued.isCredit()) {
						} else {
							AccountingMoney netMoney = interestAccrued.add(interestPaid);
							logger.info(" netMoney : " + netMoney);
							if (netMoney.isZero() || netMoney.isDebit()) {
								creditInterestAccruedAmtMap.put(lineOfCredit.getId(), interestPaid);
								creditAccountMap.put(ServiceConstants.CREDIT_INTEREST_ACCRUED_AMOUNT_MAP, creditInterestAccruedAmtMap);
								populateRemarks(master, lineOfCredit, "towards interest:");
								populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.INTEREST, interestPaid,
										voucherNumber);
								populateCurrentTransactionRecord(lineOfCredit, currentTransactionList, TransactionCode.INTEREST_RECEIVABLE,
										"Interest Accrued posted Receivable ", -1, interestPaid, TransactionType.Transfer, voucherForIntPost);
								populateCurrentTransactionRecord(lineOfCredit, currentTransactionList, TransactionCode.INTEREST_RECEIVED,
										"Interest Accrued posted Received", 1, interestPaid, TransactionType.Transfer, voucherForIntPost);
								interestPaid = AccountingMoney.ZERO;
							} else {
								AccountingMoney creditAmount = MoneyUtil.getAccountingMoney(interestAccrued.getMoney().getAmount(),
										DebitOrCredit.CREDIT);
								logger.info(" creditAmount : " + creditAmount);
								creditInterestAccruedAmtMap.put(lineOfCredit.getId(), creditAmount);
								creditAccountMap.put(ServiceConstants.CREDIT_INTEREST_ACCRUED_AMOUNT_MAP, creditInterestAccruedAmtMap);
								populateRemarks(master, lineOfCredit, "towards interest:");
								populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.INTEREST, creditAmount,
										voucherNumber);
								populateCurrentTransactionRecord(lineOfCredit, currentTransactionList, TransactionCode.INTEREST_RECEIVABLE,
										"Interest Accrued posted Receivable ", -1, creditAmount, TransactionType.Transfer, voucherForIntPost);
								populateCurrentTransactionRecord(lineOfCredit, currentTransactionList, TransactionCode.INTEREST_RECEIVED,
										"Interest Accrued posted Received", 1, creditAmount, TransactionType.Transfer, voucherForIntPost);

								interestPaid = netMoney;
							}
						}
					}
				}
				if (!penalInterestPaid.isZero() && penalInterestPaid.isCredit()) {
					AccountingMoney overdueInterestMoney = MoneyUtil.getAccountingMoney(penalInterestReceivable);
					if ((overdueInterestMoney.isZero() || overdueInterestMoney.isCredit()) && !asAndWhenImplemented) {
					} else if (!(overdueInterestMoney.isZero() || overdueInterestMoney.isCredit())) {
						AccountingMoney netMoney = overdueInterestMoney.add(penalInterestPaid);
						logger.info(" netMoney : " + netMoney);
						if (netMoney.isZero() || netMoney.isDebit()) {
							creditPenalInterestAmtMap.put(lineOfCredit.getId(), penalInterestPaid);
							creditAccountMap.put(ServiceConstants.CREDIT_PENAL_INTEREST_AMOUNT_MAP, creditPenalInterestAmtMap);
							populateRemarks(master, lineOfCredit, "towards penal interest:");
							populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.PENAL_INTEREST, penalInterestPaid,
									voucherNumber);
							penalInterestPaid = AccountingMoney.ZERO;
						} else {
							AccountingMoney creditAmount = MoneyUtil.getAccountingMoney(overdueInterestMoney.getMoney().getAmount(),
									DebitOrCredit.CREDIT);
							logger.info(" creditAmount : " + creditAmount);
							creditPenalInterestAmtMap.put(lineOfCredit.getId(), creditAmount);
							creditAccountMap.put(ServiceConstants.CREDIT_PENAL_INTEREST_AMOUNT_MAP, creditPenalInterestAmtMap);
							populateRemarks(master, lineOfCredit, "towards penal interest:");
							populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.PENAL_INTEREST, creditAmount,
									voucherNumber);
							penalInterestPaid = netMoney;
						}
					}
					if (asAndWhenImplemented) {
						AccountingMoney overdueInterest = MoneyUtil.getAccountingMoney(lineOfCredit.getOverdueInterest().setScale(0,
								RoundingMode.HALF_UP));
						if (overdueInterest.isZero() || overdueInterest.isCredit()) {
						} else {
							AccountingMoney netMoney = overdueInterest.add(penalInterestPaid);
							logger.info(" netMoney : " + netMoney);
							if (netMoney.isZero() || netMoney.isDebit()) {
								creditOverdueInterestAmtMap.put(lineOfCredit.getId(), penalInterestPaid);
								creditAccountMap.put(ServiceConstants.CREDIT_OVERDUE_INTEREST_AMOUNT_MAP, creditOverdueInterestAmtMap);
								populateRemarks(master, lineOfCredit, "towards penal interest:");
								populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.PENAL_INTEREST,
										penalInterestPaid, voucherNumber);
								populateCurrentTransactionRecord(lineOfCredit, currentTransactionList, TransactionCode.PENAL_INTEREST_RECEIVABLE,
										"Penal Interest Accrued posted Receivable ", -1, penalInterestPaid, TransactionType.Transfer,
										voucherForIntPost);

								populateCurrentTransactionRecord(lineOfCredit, currentTransactionList, TransactionCode.PENAL_INTEREST_RECEIVED,
										"Penal Interest Accrued posted Received", 1, penalInterestPaid, TransactionType.Transfer, voucherForIntPost);

								penalInterestPaid = AccountingMoney.ZERO;
							} else {
								AccountingMoney creditAmount = MoneyUtil.getAccountingMoney(overdueInterest.getMoney().getAmount(),
										DebitOrCredit.CREDIT);
								logger.info(" creditAmount : " + creditAmount);
								creditOverdueInterestAmtMap.put(lineOfCredit.getId(), creditAmount);
								creditAccountMap.put(ServiceConstants.CREDIT_OVERDUE_INTEREST_AMOUNT_MAP, creditOverdueInterestAmtMap);
								populateRemarks(master, lineOfCredit, "towards penal interest:");
								populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.PENAL_INTEREST, creditAmount,
										voucherNumber);
								populateCurrentTransactionRecord(lineOfCredit, currentTransactionList, TransactionCode.PENAL_INTEREST_RECEIVABLE,
										"Penal Interest Accrued posted Receivable ", -1, creditAmount, TransactionType.Transfer, voucherForIntPost);
								populateCurrentTransactionRecord(lineOfCredit, currentTransactionList, TransactionCode.PENAL_INTEREST_RECEIVED,
										"Penal Interest Accrued posted Received", 1, creditAmount, TransactionType.Transfer, voucherForIntPost);
								penalInterestPaid = netMoney;
							}
						}
					}
				}
			}
		}

		/*//extra interest and penal interest recovery is happening to the first loc, which is in priority
		master.setOpeningBalance(firstLoc.getCurrentBalance());
		master.setLineOfCredit(firstLoc);
		if (lineOfCreditList != null && !lineOfCreditList.isEmpty()) {
			if (!interestPaid.isZero() && interestPaid.isCredit()) {
				populateRemarks(master, firstLoc, "towards interest:");
				populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.INTEREST, interestPaid, voucherNumber);
				populateCurrentTransactionRecord(firstLoc, currentTransactionList, TransactionCode.INTEREST_RECEIVABLE,
						"Interest Accrued posted Receivable ", -1, interestPaid, TransactionType.Transfer, voucherForIntPost);
				populateCurrentTransactionRecord(firstLoc, currentTransactionList, TransactionCode.INTEREST_RECEIVED,
						"Interest Accrued posted Received", 1, interestPaid, TransactionType.Transfer, voucherForIntPost);

			}
			if (!penalInterestPaid.isZero() && penalInterestPaid.isCredit()) {
				populateRemarks(master, firstLoc, "towards penal interest:");
				populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.PENAL_INTEREST, penalInterestPaid, voucherNumber);
				populateCurrentTransactionRecord(firstLoc, currentTransactionList, TransactionCode.PENAL_INTEREST_RECEIVABLE,
						"Penal Interest Accrued posted Receivable ", -1, penalInterestPaid, TransactionType.Transfer, voucherForIntPost);
				populateCurrentTransactionRecord(firstLoc, currentTransactionList, TransactionCode.PENAL_INTEREST_RECEIVED,
						"Penal Interest Accrued posted Received", 1, penalInterestPaid, TransactionType.Transfer, voucherForIntPost);
		
			}
		}*/

		Object object = creditMap.get(ServiceConstants.KLS_RESPONSE);
		if (object instanceof KLSResponse) {
			KLSResponse klsResponse = (KLSResponse) object;
			klsResponse.setResponseStatus(ISOResponseCodes.APPROVED);
			String availableBalance = ((AccountingMoney) creditMap.get(ServiceConstants.SUM)).getMoney().getAmount().toString();
			klsResponse.setAvailableBalance(availableBalance);
		}
		creditMap.put(ServiceConstants.CREDIT_CURRENT_TRANSACTION_LIST, currentTransactionList);
		creditMap.put(ServiceConstants.CREDIT_ACCOUNT_MAP, creditAccountMap);
		value = true;
		logger.info("End : Processing credit transaction in doCreditTransaction() method.");
		logger.info(" return value in doCreditTransaction() method : " + value);
		return value;
	
	}

	/**
	 * 
	 * @param master
	 * @param creditMap
	 * @return
	 */
	private boolean processCreditTransaction(CurrentTransaction master, Map<String, Object> creditMap) {

		logger.info("Start : Processing credit transaction in processCreditTransaction() method.");
		boolean value = false;
		AccountingMoney transactionAmount = master.getTransactionAmount();
		AccountingMoney sumAccountingMoney = (AccountingMoney) creditMap.get(ServiceConstants.SUM);
		if (sumAccountingMoney != null) {
			int diff = transactionAmount.getMoney().getAmount().compareTo(sumAccountingMoney.getMoney().getAmount());
			logger.info(" diff : " + diff);
			if (diff <= 0) {
				value = doCreditTransaction(master, creditMap);
			} else {
				value = doCreditTransaction(master, creditMap);
			}
			Object object = creditMap.get(ServiceConstants.KLS_RESPONSE);
			if (object instanceof KLSResponse) {
				KLSResponse klsResponse = (KLSResponse) object;
				klsResponse.setResponseStatus(ISOResponseCodes.APPROVED);

				/**
				String availableBalance = ((AccountingMoney) creditMap.get(ServiceConstants.SUM)).getMoney().getAmount().toString();
				klsResponse.setAvailableBalance(availableBalance);
				**/

				klsResponse.setAvailableBalance(getBalance((List) 
						creditMap.get(ServiceConstants.LINE_OF_CREDIT_LIST)));
				logger.info("============================Abalance======="+klsResponse.getAvailableBalance());
			}
			
		}
		logger.info("End : Processing credit transaction in processCreditTransaction() method.");
		logger.info(" return value in getLineOfCreditAccountsBalance() method : " + value);
		return value;
	}

	/**
	 * 
	 */
	private String getBalance(List lineOfCreditAccountsList) {

		AccountingMoney totalCashLimit = AccountingMoney.ZERO;
		AccountingMoney totalKindLimit = AccountingMoney.ZERO;
		AccountingMoney totalCashBalance = AccountingMoney.ZERO;
		AccountingMoney totalKindBalance = AccountingMoney.ZERO;

		
		if (!lineOfCreditAccountsList.isEmpty()) {
			for (Object object : lineOfCreditAccountsList) {
				LineOfCredit lineOfCredit = (LineOfCredit) object;

				AccountingMoney cashLimit = AccountingMoney.ZERO;
				AccountingMoney kindLimit = AccountingMoney.ZERO;
				AccountingMoney cashBalance = AccountingMoney.ZERO;
				AccountingMoney kindBalance = AccountingMoney.ZERO;

				if (lineOfCredit.getOperatingCashLimit() != null)
					cashLimit = MoneyUtil.getAccountingMoney(lineOfCredit.getOperatingCashLimit());
				if (lineOfCredit.getKindLimit() != null)
					kindLimit = MoneyUtil.getAccountingMoney(lineOfCredit.getKindLimit());
				cashBalance = lineOfCredit.getCurrentBalance();
				kindBalance = lineOfCredit.getKindBalance();

				logger.info("\nLine of credit id : " + lineOfCredit.getId());
				logger.info("Operating Cash Limit : " + cashLimit + "  Current Balance : " + cashBalance + "  Kind Limit : " + kindLimit
						+ "  Kind balance  : " + kindBalance);

				totalCashLimit = totalCashLimit.add(cashLimit != null ? cashLimit : AccountingMoney.ZERO);
				totalKindLimit = totalKindLimit.add(kindLimit != null ? kindLimit : AccountingMoney.ZERO);
				totalCashBalance = totalCashBalance.add(cashBalance != null ? cashBalance : AccountingMoney.ZERO);
				totalKindBalance = totalKindBalance.add(kindBalance != null ? kindBalance : AccountingMoney.ZERO);

			}
		}
		/*klsResponse.setAvailableBalance((totalCashLimit.add(totalKindLimit).add(totalCashBalance).add(totalKindBalance)).getMoney().getAmount()
				.toString());*/
		return (totalCashLimit.add(totalCashBalance.subtract(totalKindBalance))).getMoney().getAmount()
				.toString();		
	}
	
	/**
	 * 
	 * @param master
	 * @param creditMap
	 */
	private boolean doCreditTransaction(CurrentTransaction master, Map<String, Object> creditMap) {

		logger.info("Start : Processing credit transaction in doCreditTransaction() method.");
		List lineOfCreditList = (List) creditMap.get(ServiceConstants.LINE_OF_CREDIT_LIST);
		Map<String, Map<Long, AccountingMoney>> creditAccountMap = new HashMap<String, Map<Long, AccountingMoney>>();
		Map<Long, AccountingMoney> creditPrincipalAmtMap = new HashMap<Long, AccountingMoney>();
		Map<Long, AccountingMoney> creditInterestAmtMap = new HashMap<Long, AccountingMoney>();
		Map<Long, AccountingMoney> creditPenalInterestAmtMap = new HashMap<Long, AccountingMoney>();
		Map<Long, AccountingMoney> creditInterestAccruedAmtMap = new HashMap<Long, AccountingMoney>();
		Map<Long, AccountingMoney> creditOverdueInterestAmtMap = new HashMap<Long, AccountingMoney>();
		BigDecimal interesBalance = BigDecimal.ZERO;
		//AccountingMoney totalSubsidy= AccountingMoney.ZERO;
		AccountingMoney transactionAmount = master.getTransactionAmount();
		logger.info(" transactionAmount : " + transactionAmount);
		List<CurrentTransaction> currentTransactionList = new ArrayList<CurrentTransaction>();
		boolean value = false;
		Pacs pacs = master.getPacs();
		Integer voucherNumber = VoucherNumberUtil.getVoucherNumber(pacs.getId().toString(), master.getTransactionType().getValue());
		Integer voucherNumberforIntPosting = VoucherNumberUtil.getVoucherNumber(pacs.getId().toString(), TransactionType.Transfer.getValue());
		String voucherForIntPost = TransactionType.Transfer.getValue() + "-" + voucherNumberforIntPosting;
		String cashInTransitGl=null;
		String cashInHandGl;
		String pacsBankAccount=null;
		TransactionMode transMode = null;
		// Cash in Transit GL
		Boolean isStandaloneMode = (Boolean) creditMap.get(ServiceConstants.IS_STANDALONE_MODE);
		Boolean isBulkRecovery = (Boolean) creditMap.get(ServiceConstants.IS_BULK_RECOVERY);
		Boolean isBulkRecoveryFromEntry = (Boolean) creditMap.get(ServiceConstants.IS_BULK_RECOVERY_FROM_ENTRY);
		String modeOfPayment = (String) creditMap.get(ServiceConstants.MODE_OF_PAYMENT);
		if(modeOfPayment != null){
			transMode = TransactionMode.getType(modeOfPayment); 
		}
		
		LineOfCredit loc = (LineOfCredit) lineOfCreditList.get(0);
        logger.info(">>>>>>>loc id and cust id : "+loc.getId()+" & "+ loc.getCustomerId());
        
			if (isStandaloneMode) {
			cashInTransitGl = pacs.getCashInTransitGL();
			cashInHandGl = pacs.getCashGl();
			pacsBankAccount = pacs.getPacsBankAccNumber();
			if(isBulkRecoveryFromEntry && transMode != null){  // added code for bulk recovery(IRKLS-404) - (Loan repayments at branch)
				// doing Debit transaction to PacsBankAccount with Collection amount.
				if (TransactionMode.TRANSFER_FROM_PACS_SB_ACCOUNT.equals(transMode)) {   
					//added different channel type for differentiating transactions of different transModes for BankGl Extract in dayend process.
					master.setChannelType(ChannelType.getType("Z")); 
				}
				if (TransactionMode.TRANSFER_FROM_MEMBER_SB_ACCOUNT.equals(transMode)) {  
					 master.setChannelType(ChannelType.getType("Y"));
				}
				if (pacsBankAccount != null) {
					populateCurrentTransactionRecord(loc, pacsBankAccount, currentTransactionList, TransactionCode.PACS_BANK_ACC,
							"Recovery Collection Amount", -1, transactionAmount, false, master, voucherNumber);
				} else {
					logger.info("Pacs bank Account is not availabe for pacs id : " + pacs.getId());
					throw new KlsRuntimeException("Pacs bank account is not availabe for pacs id : " + pacs.getId());
				}
				master.setChannelType(ChannelType.getType("B"));
			} else if (isBulkRecovery) {//code added for bulk recovery
				
				pacsBankAccount = pacs.getPacsBankAccNumber();
				if (pacsBankAccount != null) {
					logger.info("Pacs bank account: " + pacsBankAccount);
					populateCurrentTransactionRecord(null, pacsBankAccount, currentTransactionList, TransactionCode.PACS_BANK_ACC,
							"Reocovery:Pacs bank account", -1, transactionAmount, true, master, voucherNumber);
				} else {
					logger.info("Pacs bank account is not availabe for pacs id : " + pacs.getId());
					throw new KlsRuntimeException("Pacs bank account is not availabe for pacs id : " + pacs.getId());
				}

			} else if(!isBulkRecoveryFromEntry && !isBulkRecovery){
				if (cashInTransitGl != null && cashInHandGl != null) {
					logger.info("Cash In Transit GL: " + cashInTransitGl);
					if (pacs.getIsBorrwingTransRequiredInGLExtract().equals("Y")) {
						TransactionType transactionType =  master.getTransactionType();
						master.setTransactionType(TransactionType.Transfer);
						populateCurrentTransactionRecord(null, cashInTransitGl,currentTransactionList,TransactionCode.CASH_IN_TRANSIT,
								"Reocovery:Cash in transit GL", 1,transactionAmount, true, master, voucherNumber);
						master.setTransactionType(transactionType);
					}
					populateCurrentTransactionRecord(null, cashInHandGl,currentTransactionList,	TransactionCode.CASH_TRANSFER,
							"Reocovery:Cash in hand GL", -1, transactionAmount,true, master, voucherNumber);

				} else {
					logger.info("Cash in transit gl is not available for pacs id : "
							+ pacs.getId());
					return false;
				}
			}
		} /*else {  // commented to resolve the bug Id IKC-378 && IKS-227
			pacsBankAccount = pacs.getPacsBankAccNumber();
			if (pacsBankAccount != null) {
				logger.info("Pacs bank account: " + pacsBankAccount);
				populateCurrentTransactionRecord(null, pacsBankAccount, currentTransactionList, TransactionCode.PACS_BANK_ACC,
						"Reocovery:Pacs bank account", 1, transactionAmount, true, master, voucherNumber);
			} else {
				logger.info("Pacs bank account is not availabe for pacs id : " + pacs.getId());
				throw new KlsRuntimeException("Pacs bank account is not availabe for pacs id : " + pacs.getId());
			}
		}*/
		for (Object object : lineOfCreditList) {
			if (object instanceof LineOfCredit) {
				LineOfCredit lineOfCredit = (LineOfCredit) object;
				AccountingMoney currentBalance = lineOfCredit.getCurrentBalance();
				logger.info(" currentBalance : " + currentBalance);
				BigDecimal interestReceivable = lineOfCredit.getInterestReceivable();
				BigDecimal penalInterestReceivable = lineOfCredit.getPenalInterestReceivable();
				Boolean asAndWhenImplemented = lineOfCredit.getProduct().getAsAndWhenImplemented();
				logger.info(" interestReceivable : " + interestReceivable);
				logger.info(" penalInterestReceivable : " + penalInterestReceivable);
				master.setLineOfCredit(lineOfCredit);
				if (currentBalance.isZero()
						&& interestReceivable.equals(BigDecimal.ZERO)
						&& penalInterestReceivable.equals(BigDecimal.ZERO)
						|| (asAndWhenImplemented && lineOfCredit.getInterestAccrued().equals(BigDecimal.ZERO) && lineOfCredit.getOverdueInterest()
								.equals(BigDecimal.ZERO))) {
					continue;
				}
				master.setOpeningBalance(currentBalance);

				int[] recoverySequence = getRecoverySequence(lineOfCredit, creditMap);
				boolean interestRNR=true;
				if(lineOfCredit.getLoanType().equals("C")){
					String interestCalDate=DateUtil.getDateBySubtractingNoOfDays(DateUtil.getDateString(loc.getSeason().getOverdueDate()), loc.getSeason().getRecoveryPeriod());
					String businessDate = DateUtil.getDateString(LoanServiceUtil.getBusinessDate());
					//logger.info("checking dates::"+interestCalDate+"businessdate::"+businessDate+"condition::"+DateUtil.compareDate(interestCalDate,businessDate));
					if(interestCalDate!=null && interestCalDate!="" && DateUtil.compareDate(interestCalDate,businessDate)>0){
						interestRNR=false;
					}
				}
				
				AccountingMoney subsidyReceivable = AccountingMoney.ZERO;
				for (int i = 0; i < recoverySequence.length; i++) {
					if (transactionAmount.isZero() || transactionAmount.isDebit()) {
						break;
					}
					if (RecoveryOrder.PRINCIPAL.getValue().intValue() == recoverySequence[i]) {
						if (currentBalance.isZero() || currentBalance.isCredit()) {
							continue;
						} else {
							AccountingMoney netMoney = transactionAmount.add(currentBalance);
							logger.info(" netMoney : " + netMoney);
							if (netMoney.isZero() || netMoney.isDebit()) {
								creditPrincipalAmtMap.put(lineOfCredit.getId(), transactionAmount);
								creditAccountMap.put(ServiceConstants.CREDIT_PRINCIPAL_AMOUNT_MAP, creditPrincipalAmtMap);
								
								if(isBulkRecovery)
									master.setRemarks("BULK");
								else
									populateRemarks(master, lineOfCredit, "towards principal:");
								populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.PRINCIPAL_BAL,
										transactionAmount, voucherNumber);
								transactionAmount = netMoney;
								break;
							} else {
								AccountingMoney creditAmount = MoneyUtil.getAccountingMoney(currentBalance.getMoney().getAmount(),
										DebitOrCredit.CREDIT);
								logger.info(" creditAmount : " + creditAmount);
								creditPrincipalAmtMap.put(lineOfCredit.getId(), creditAmount);
								creditAccountMap.put(ServiceConstants.CREDIT_PRINCIPAL_AMOUNT_MAP, creditPrincipalAmtMap);
								populateRemarks(master, lineOfCredit, "towards principal:");
								populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.PRINCIPAL_BAL, creditAmount,
										voucherNumber);
								transactionAmount = netMoney;
							}
						}
					} else if (RecoveryOrder.INTEREST.getValue().intValue() == recoverySequence[i] && interestRNR==true) {
						AccountingMoney interestAccruedMoney = MoneyUtil.getAccountingMoney(interestReceivable);
						logger.info("InterestAccruedMoney==="+interestAccruedMoney);
						AccountingMoney interestAccrued = MoneyUtil.getAccountingMoney(lineOfCredit.getInterestAccrued().setScale(0,
								RoundingMode.HALF_UP));
						logger.info("interestAccured==="+interestAccrued);
						AccountingMoney tmpInterestAccruedMoney = AccountingMoney.ZERO;
						//AccountingMoney tmpInterestReceivableMoney = MoneyUtil.getAccountingMoney(interestReceivable);
						//subsidyReceivable = AccountingMoney.ZERO;
						AccountingMoney subInterestAccrued = AccountingMoney.ZERO;
						if ((interestAccruedMoney.isZero() || interestAccruedMoney.isCredit()) && !asAndWhenImplemented) {
							continue;
						} 
						
						tmpInterestAccruedMoney = doSubsidyCalculationAtRecovery(lineOfCredit, currentTransactionList, master, voucherNumber);
						logger.info("tmpInterestAccruedMoney out :"+tmpInterestAccruedMoney);
								
						if (!asAndWhenImplemented) {
						
							subsidyReceivable = interestAccruedMoney.subtract(tmpInterestAccruedMoney);
							//totalSubsidy= totalSubsidy.add(subsidyReceivable);
							interesBalance = interestAccruedMoney.getMoney().getAmount().abs().subtract(subsidyReceivable.getMoney().getAmount().abs());
							AccountingMoney netMoney = transactionAmount.subtract(MoneyUtil.getAccountingMoney(interesBalance));
							
							logger.info(" netMoney : " + netMoney);
							if (netMoney.isZero() || netMoney.isDebit()) {
								//transactionAmount = transactionAmount.subtract(subsidyReceivable); //subsidy receivable is debit amount
								AccountingMoney creditSubsidyAmount = MoneyUtil.getAccountingMoney(subsidyReceivable.getMoney().getAmount(),
										DebitOrCredit.CREDIT);
								creditInterestAmtMap.put(lineOfCredit.getId(), transactionAmount.subtract(subsidyReceivable));
								creditAccountMap.put(ServiceConstants.CREDIT_INTEREST_AMOUNT_MAP, creditInterestAmtMap);
								populateRemarks(master, lineOfCredit, "towards interest:");
								if(!creditSubsidyAmount.isZero())
								populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.INTEREST_SUBSIDY_ON_LOAN, 
										creditSubsidyAmount,
										voucherNumber);
								if(!transactionAmount.isZero())
								populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.INTEREST, 
										transactionAmount,
										voucherNumber);
								transactionAmount = netMoney;
								
								
								
								break;
							} else {
								AccountingMoney creditAmount = MoneyUtil.getAccountingMoney(interestAccruedMoney.getMoney().getAmount(),
										DebitOrCredit.CREDIT);
								AccountingMoney creditSubsidyAmount = MoneyUtil.getAccountingMoney(subsidyReceivable.getMoney().getAmount(),
										DebitOrCredit.CREDIT);
								AccountingMoney interestTransAmount = creditAmount.subtract(subsidyReceivable);
								logger.info(" creditAmount : " + creditAmount);
								logger.info(" interestTransAmount : " + interestTransAmount);
								
								creditInterestAmtMap.put(lineOfCredit.getId(), creditAmount);
								creditAccountMap.put(ServiceConstants.CREDIT_INTEREST_AMOUNT_MAP, creditInterestAmtMap);
								populateRemarks(master, lineOfCredit, "towards interest:");
								/*if(!creditSubsidyAmount.isZero())
								populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.INTEREST_SUBSIDY_ON_LOAN, 
										creditSubsidyAmount,
										voucherNumber);*/
								logger.info("in if part transaction amount==="+creditAmount);
								if(!creditAmount.isZero())
								populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.INTEREST, 
										creditAmount,
										voucherNumber);
								transactionAmount = netMoney;
							}
						}
						else {
							
							if (interestAccrued.isZero() || interestAccrued.isCredit()) { // Receivable is always zero
								continue;
							} else {
								
								subsidyReceivable = interestAccruedMoney.add(interestAccrued).subtract(tmpInterestAccruedMoney);
								logger.info("subsidy receivable==="+subsidyReceivable);
								interesBalance = interestAccrued.getMoney().getAmount().abs().subtract(subsidyReceivable.getMoney().getAmount().abs());
								logger.info("interest balance==="+interesBalance);
								AccountingMoney netMoney = transactionAmount.subtract(MoneyUtil.getAccountingMoney(interesBalance));
								logger.info(" netMoney : " + netMoney);
								if (netMoney.isZero() || netMoney.isDebit()) {
									AccountingMoney creditSubsidyAmount = MoneyUtil.getAccountingMoney(subsidyReceivable.getMoney().getAmount(),
											DebitOrCredit.CREDIT);
									transactionAmount = transactionAmount.subtract(subsidyReceivable);//subsidy receivable is debit amount
									creditInterestAccruedAmtMap.put(lineOfCredit.getId(), transactionAmount);
									creditAccountMap.put(ServiceConstants.CREDIT_INTEREST_ACCRUED_AMOUNT_MAP, creditInterestAccruedAmtMap);
									populateRemarks(master, lineOfCredit, "towards interest:");
									/*if(!creditSubsidyAmount.isZero())
									populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.INTEREST_SUBSIDY_ON_LOAN, 
											creditSubsidyAmount,
											voucherNumber);*/
									AccountingMoney tranAmount = transactionAmount; /*.add(subsidyReceivable);  //Not Required..*/
									logger.info("in else part transaction amt==="+tranAmount);
									if(!tranAmount.isZero() || tranAmount.isDebit())
									populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.INTEREST, tranAmount,
											voucherNumber);
									
									populateCurrentTransactionRecord(lineOfCredit, currentTransactionList, TransactionCode.INTEREST_RECEIVABLE,
											"Interest Accrued posted Receivable ", -1, transactionAmount, TransactionType.Transfer, voucherForIntPost);
									populateCurrentTransactionRecord(lineOfCredit, currentTransactionList, TransactionCode.INTEREST_RECEIVED,
											"Interest Accrued posted Received", 1, transactionAmount, TransactionType.Transfer, voucherForIntPost);
									transactionAmount = netMoney;
									break;
								} else {
									AccountingMoney creditAmount = MoneyUtil.getAccountingMoney(interestAccrued.getMoney().getAmount(),
											DebitOrCredit.CREDIT);
									AccountingMoney creditSubsidyAmount = MoneyUtil.getAccountingMoney(subsidyReceivable.getMoney().getAmount(),
											DebitOrCredit.CREDIT);
									AccountingMoney interestTransAmount = creditAmount;/*.add(subsidyReceivable);  //Not Required..*/;
									logger.info(" creditAmount : " + creditAmount);
									logger.info("interestTransAmount : "+interestTransAmount);
									creditInterestAccruedAmtMap.put(lineOfCredit.getId(), creditAmount);
									creditAccountMap.put(ServiceConstants.CREDIT_INTEREST_ACCRUED_AMOUNT_MAP, creditInterestAccruedAmtMap);
									populateRemarks(master, lineOfCredit, "towards interest:");
									/*if(!creditSubsidyAmount.isZero())
									populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.INTEREST_SUBSIDY_ON_LOAN, 
											creditSubsidyAmount,
											voucherNumber);*/
									if(!interestTransAmount.isZero())
									populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.INTEREST, interestTransAmount,
											voucherNumber);
									populateCurrentTransactionRecord(lineOfCredit, currentTransactionList, TransactionCode.INTEREST_RECEIVABLE,
											"Interest Accrued posted Receivable ", -1, creditAmount, TransactionType.Transfer, voucherForIntPost);
									populateCurrentTransactionRecord(lineOfCredit, currentTransactionList, TransactionCode.INTEREST_RECEIVED,
											"Interest Accrued posted Received", 1, creditAmount, TransactionType.Transfer, voucherForIntPost);

									transactionAmount = netMoney;
								}

							}
						}
					} else if (RecoveryOrder.PENAL_INTEREST.getValue().intValue() == recoverySequence[i]) {
						AccountingMoney overdueInterestMoney = MoneyUtil.getAccountingMoney(penalInterestReceivable);
						if ((overdueInterestMoney.isZero() || overdueInterestMoney.isCredit()) && !asAndWhenImplemented) {
							continue;
						} else if (!(overdueInterestMoney.isZero() || overdueInterestMoney.isCredit())) {
							AccountingMoney netMoney = transactionAmount.add(overdueInterestMoney);
							logger.info(" netMoney : " + netMoney);
							if (netMoney.isZero() || netMoney.isDebit()) {
								creditPenalInterestAmtMap.put(lineOfCredit.getId(), transactionAmount);
								creditAccountMap.put(ServiceConstants.CREDIT_PENAL_INTEREST_AMOUNT_MAP, creditPenalInterestAmtMap);
								populateRemarks(master, lineOfCredit, "towards penal interest:");
								populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.PENAL_INTEREST,
										transactionAmount, voucherNumber);
								transactionAmount = netMoney;
								break;
							} else {
								AccountingMoney creditAmount = MoneyUtil.getAccountingMoney(overdueInterestMoney.getMoney().getAmount(),
										DebitOrCredit.CREDIT);
								logger.info(" creditAmount : " + creditAmount);
								creditPenalInterestAmtMap.put(lineOfCredit.getId(), creditAmount);
								creditAccountMap.put(ServiceConstants.CREDIT_PENAL_INTEREST_AMOUNT_MAP, creditPenalInterestAmtMap);
								populateRemarks(master, lineOfCredit, "towards penal interest:");
								populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.PENAL_INTEREST, creditAmount,
										voucherNumber);
								transactionAmount = netMoney;
							}
						}
						if (asAndWhenImplemented) {
							AccountingMoney overdueInterest = MoneyUtil.getAccountingMoney(lineOfCredit.getOverdueInterest().setScale(0,
									RoundingMode.HALF_UP));
							if (overdueInterest.isZero() || overdueInterest.isCredit()) {
								continue;
							} else {
								AccountingMoney netMoney = transactionAmount.add(overdueInterest);
								logger.info(" netMoney : " + netMoney);
								if (netMoney.isZero() || netMoney.isDebit()) {
									creditOverdueInterestAmtMap.put(lineOfCredit.getId(), transactionAmount);
									creditAccountMap.put(ServiceConstants.CREDIT_OVERDUE_INTEREST_AMOUNT_MAP, creditOverdueInterestAmtMap);
									populateRemarks(master, lineOfCredit, "towards penal interest:");
									populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.PENAL_INTEREST,
											transactionAmount, voucherNumber);
									populateCurrentTransactionRecord(lineOfCredit, currentTransactionList, TransactionCode.PENAL_INTEREST_RECEIVABLE,
											"Penal Interest Accrued posted Receivable ", -1, transactionAmount, TransactionType.Transfer,
											voucherForIntPost);

									populateCurrentTransactionRecord(lineOfCredit, currentTransactionList, TransactionCode.PENAL_INTEREST_RECEIVED,
											"Penal Interest Accrued posted Received", 1, transactionAmount, TransactionType.Transfer,
											voucherForIntPost);

									transactionAmount = netMoney;
									break;
								} else {
									AccountingMoney creditAmount = MoneyUtil.getAccountingMoney(overdueInterest.getMoney().getAmount(),
											DebitOrCredit.CREDIT);
									logger.info(" creditAmount : " + creditAmount);
									creditOverdueInterestAmtMap.put(lineOfCredit.getId(), creditAmount);
									creditAccountMap.put(ServiceConstants.CREDIT_OVERDUE_INTEREST_AMOUNT_MAP, creditOverdueInterestAmtMap);
									populateRemarks(master, lineOfCredit, "towards penal interest:");
									populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.PENAL_INTEREST,
											creditAmount, voucherNumber);
									populateCurrentTransactionRecord(lineOfCredit, currentTransactionList, TransactionCode.PENAL_INTEREST_RECEIVABLE,
											"Penal Interest Accrued posted Receivable ", -1, creditAmount, TransactionType.Transfer,
											voucherForIntPost);
									populateCurrentTransactionRecord(lineOfCredit, currentTransactionList, TransactionCode.PENAL_INTEREST_RECEIVED,
											"Penal Interest Accrued posted Received", 1, creditAmount, TransactionType.Transfer, voucherForIntPost);
									transactionAmount = netMoney;
								}
							}
						}
					} else if (RecoveryOrder.CHARGES.getValue().intValue() == recoverySequence[i]) {
					} else {
					}
				}
			}
			if (transactionAmount.isZero() || transactionAmount.isDebit()) {
				break;
			}
		}
		/*//for subsidy amount cash in transit/pacs bank account number transaction
		if(!totalSubsidy.isZero()){
			if (isStandaloneMode) {
					if (pacs.getIsBorrwingTransRequiredInGLExtract().equals("Y")) {
						populateCurrentTransactionRecord(null, cashInTransitGl, currentTransactionList, TransactionCode.CASH_IN_TRANSIT,
								"Reocovery:Cash in transit GL", 1, master.getTransactionAmount().add(totalSubsidy), true, master, voucherNumber);
					}
			} else {
					populateCurrentTransactionRecord(null, pacsBankAccount, currentTransactionList, TransactionCode.PACS_BANK_ACC,
							"Reocovery:Pacs bank account", 1, master.getTransactionAmount().add(totalSubsidy), true, master, voucherNumber);
			}
		}*/
		creditMap.put(ServiceConstants.CREDIT_CURRENT_TRANSACTION_LIST, currentTransactionList);
		creditMap.put(ServiceConstants.CREDIT_ACCOUNT_MAP, creditAccountMap);
		value = true;
		logger.info("End : Processing credit transaction in doCreditTransaction() method.");
		logger.info(" return value in doCreditTransaction() method : " + value);
		return value;
	}

	public void populateCurrentTransactionRecord(LineOfCredit loanLoc, String accountNumber, List<CurrentTransaction> currentTransactionList,
			TransactionCode transactionCode, String remarks, Integer crdr, AccountingMoney transactionAmount, boolean isCashGl,
			CurrentTransaction master, Integer voucherNumber) {
		logger.info("papulating currentTransaction");
		IPacsDAO pacsDao=KLSDataAccessFactory.getPacsDAO();
		CurrentTransaction currentTransaction = new CurrentTransaction();
		currentTransaction.setAccount(master.getAccount());
		currentTransaction.setAccountNumber(accountNumber);
		Pacs pacs=pacsDao.getPacs(master.getPacs().getId());
		logger.info("pacs type::"+pacs.getPacsStatus());
	
		if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
			String businessDate="";
			businessDate=RestClientUtil.getPacsBusinessDate(master.getPacs().getId(),pacs.getBranch().getId());
			//businessDate="2017-05-01";
			currentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(businessDate));
		}
		else
		currentTransaction.setBusinessDate(LoanServiceUtil.getBusinessDate());
		currentTransaction.setChannelType(master.getChannelType());
		currentTransaction.setCrDr(crdr);
		if (isCashGl) {
			currentTransaction.setLineOfCredit(null);
			currentTransaction.setOpeningBalance(null);
		} else {
			currentTransaction.setLineOfCredit(loanLoc);
			currentTransaction.setOpeningBalance(loanLoc.getCurrentBalance());
		}
		currentTransaction.setPacs(master.getPacs());
		currentTransaction.setPostedStatus(1);
		currentTransaction.setRemarks(remarks);
		currentTransaction.setTerminalId(master.getTerminalId());
		currentTransaction.setTransactionAmount(transactionAmount);
		currentTransaction.setTransactionCode(transactionCode);
		currentTransaction.setTransactionType(master.getTransactionType());
		currentTransaction.setVoucherNumber(master.getTransactionType().getValue() + "-" + voucherNumber);
		currentTransactionList.add(currentTransaction);
	}

	/**
	 * 
	 * @param master
	 * @param currentTransactionList
	 * @param transactionAmount
	 */
	private void populateCreditCurrentTransactionList(CurrentTransaction currentTransaction, List<CurrentTransaction> currentTransactionList,
			AccountingMoney transactionAmount) {

		logger.info("Start : Populating the current transaction in populateCreditCurrentTransactionList() method.");
		for (int i = 1; i <= 3; i++) {
			CurrentTransaction copyObject = new CurrentTransaction();
			copyObject.setAccount(currentTransaction.getAccount());
			copyObject.setAccountNumber(currentTransaction.getAccountNumber());
			copyObject.setBusinessDate(currentTransaction.getBusinessDate());
			copyObject.setChannelType(ChannelType.SYSTEM);
			copyObject.setLineOfCredit(currentTransaction.getLineOfCredit());
			copyObject.setOpeningBalance(AccountingMoney.ZERO);
			copyObject.setPacs(currentTransaction.getPacs());
			copyObject.setPostedStatus(currentTransaction.getPostedStatus());
			copyObject.setRemarks(currentTransaction.getRemarks());
			copyObject.setTransactionAmount(transactionAmount);
			copyObject.setTransactionCode(TransactionCode.PRINCIPAL_BAL);
			copyObject.setVoucherNumber(currentTransaction.getVoucherNumber());
			if (i % 2 == 0) {
				copyObject.setCrDr(1);
				copyObject.setTransactionType(TransactionType.Deposit);
			} else {
				copyObject.setCrDr(-1);
				copyObject.setTransactionType(TransactionType.Withdrawal);
			}
			currentTransactionList.add(copyObject);
		}
		logger.info("End : Populating the current transaction in populateCreditCurrentTransactionList() method.");
	}

	/**
	 * 
	 * @param master
	 * @return
	 */
	private boolean calculateLineOfCreditAccountsBalance(CurrentTransaction master, Map<String, Object> creditMap) {

		logger.info("Start : Fetching the sum of all the available balances from the line of "
				+ "credit accounts in getLineOfCreditAccountsBalance() method.");
		ILineOfCreditDAO dao = KLSDataAccessFactory.getLineOfCreditDAO();
		boolean value = false;
		String query = PropertiesUtil.getProperty(StringConstant.LINE_Of_CREDIT_CREDIT_QUERY);
		logger.info(" query is : " + query);
		try {
			List lineOfCreditAccountsList = dao.getLineOfCreditAccountsList(master.getAccount().getId(), false, query);
			logger.info(" line of credits account list size : " + lineOfCreditAccountsList.size());
			AccountingMoney sumBalance = AccountingMoney.ZERO;
			if (!lineOfCreditAccountsList.isEmpty()) {
				for (Object object : lineOfCreditAccountsList) {
					LineOfCredit lineOfCredit = (LineOfCredit) object;
					AccountingMoney currentBalance = lineOfCredit.getCurrentBalance();
					logger.info("line of credit id : " + lineOfCredit.getId() + " currentBalance : " + currentBalance);
					BigDecimal interestReceivable = lineOfCredit.getInterestReceivable();
					AccountingMoney interestReceivableMoney = MoneyUtil.getAccountingMoney(interestReceivable);
					logger.info(" interestReceivable : " + interestReceivable);
					BigDecimal penalInterestReceivable = lineOfCredit.getPenalInterestReceivable();
					AccountingMoney penalInterestReceivableMoney = MoneyUtil.getAccountingMoney(penalInterestReceivable);
					logger.info(" penalInterestReceivable : " + penalInterestReceivable);
					AccountingMoney sum = currentBalance.add(interestReceivableMoney).add(penalInterestReceivableMoney);
					if (lineOfCredit.getProduct().getAsAndWhenImplemented()) {
						BigDecimal intrestAccrued = lineOfCredit.getInterestAccrued().setScale(0, RoundingMode.HALF_UP);
						logger.info("interest accrued: " + intrestAccrued);
						AccountingMoney interestAccruedMoney = MoneyUtil.getAccountingMoney(interestReceivable);
						BigDecimal overdueInterest = lineOfCredit.getOverdueInterest().setScale(0, RoundingMode.HALF_UP);
						logger.info("overdue interest:" + overdueInterest);
						AccountingMoney overdueInterestMoney = MoneyUtil.getAccountingMoney(interestReceivable);
						sum = sum.add(overdueInterestMoney).add(interestAccruedMoney);
					}
					logger.info(" sum : " + sum);
					sumBalance = sumBalance.add(sum);
					AccountProperty accountProperty = master.getAccount().getAccountProperty();
					int pacsId = accountProperty.getPacs().getId();
					int productId = lineOfCredit.getProduct().getId();
					BorrowingsAccountProperty br  = KLSDataAccessFactory.getBorrowingsAccountPropertyDAO().getAccountPropertyByLoanProductIdPacsId(pacsId, productId);
					creditMap.put("CCB_ACCOUNT", br.getCcbAccountNumber());
					creditMap.put("Borrowing_Product", br.getBorrowingProduct().getId());
				}
				creditMap.put(ServiceConstants.SUM, sumBalance);
				logger.info(" sumBalance : " + sumBalance);
				value = true;
				creditMap.put(ServiceConstants.LINE_OF_CREDIT_LIST, lineOfCreditAccountsList);
			} else {
				logger.error("Transaction date is invalid.");
				Object object = creditMap.get(ServiceConstants.KLS_RESPONSE);
				if (object instanceof KLSResponse) {
					KLSResponse klsResponse = (KLSResponse) object;
					String message = PropertiesUtil.getProperty(ISOResponseCodes.TRANSACTION_NOT_ALLOWED);
					klsResponse.setResponseStatus(ISOResponseCodes.TRANSACTION_NOT_ALLOWED);
					klsResponse.setErrorMessage(message);
				}
			}
		} catch (Exception excp) {
			logger.error(" Cannot fetch line of credit accounts.");
			Object object = creditMap.get(ServiceConstants.KLS_RESPONSE);
			if (object instanceof KLSResponse) {
				KLSResponse klsResponse = (KLSResponse) object;
				String message = PropertiesUtil.getProperty(ISOResponseCodes.UNABLE_TO_PROCESS_INVALID_ACCOUNT);
				klsResponse.setResponseStatus(ISOResponseCodes.UNABLE_TO_PROCESS_INVALID_ACCOUNT);
				klsResponse.setErrorMessage(message);
			}
		}
		logger.info("End : Fetching the sum of all the available balances from the line of "
				+ "credit accounts in getLineOfCreditAccountsBalance() method.");
		logger.info(" return value in getLineOfCreditAccountsBalance() method : " + value);
		return value;
	}

	/**
	 * 
	 * @param lineOfCredit
	 * @param creditMap
	 * @return
	 */
	private int[] getRecoverySequence(LineOfCredit lineOfCredit, Map<String, Object> creditMap) {

		logger.info("Start : Fetching the recovery sequence for the line of " + "credit id in getRecoverySequence() method.");
		int[] intArray = {};
		try {
			Integer recoverySequenceId = lineOfCredit.getProduct().getRecoverySequence().getId();
			IRecoveryOrderDao recoveryOrderDao = KLSDataAccessFactory.getRecoveryOrderDAO();
			List<EventType> eventTypeList = recoveryOrderDao.getAllEventTypeBasedOnEventDefinition(recoverySequenceId, false);
			intArray = new int[eventTypeList.size()];
			int count = 0;
			for (EventType eventType : eventTypeList) {
				intArray[count] = eventType.getRecoverySequence();
				count++;
			}
		} catch (Exception excp) {
			logger.error("Cannot fetch recovery sequence.");
			Object object = creditMap.get(ServiceConstants.KLS_RESPONSE);
			if (object instanceof KLSResponse) {
				KLSResponse klsResponse = (KLSResponse) object;
				String message = PropertiesUtil.getProperty(ISOResponseCodes.SYSTEM_ERROR);
				klsResponse.setResponseStatus(ISOResponseCodes.SYSTEM_ERROR);
				klsResponse.setErrorMessage(message);
			}
		}
		logger.info(" recovery sequence : " + Arrays.toString(intArray));
		logger.info("End : Fetching the recovery sequence for the line of " + "credit id in getRecoverySequence() method.");
		return intArray;
	}

	/**
	 * 
	 */
	public KLSResponse handleKindTransaction(CurrentTransactionData currentTransactionData) {

		logger.info("Start : Calling current transaction dao in handleKindTransaction() method.");
		CurrentTransaction master = CurrentTransactionHelper.getCurrentTransaction(currentTransactionData);
		KLSResponse klsResponse = new KLSResponse();
		// check whether the savings account number exists.
		logger.info("currentTransactionData.isStandAloneStatus() in handle kind transaction method."+currentTransactionData.isStandAloneStatus());
		
		
		boolean isSavingsAccountNumberValid = isSavingsAccountNumberValid(currentTransactionData.getSavingsAccountNumber(), master, klsResponse,
				currentTransactionData.isStandAloneStatus());
		
		logger.info("isSavingsAccountNumberValid in handle kind transaction method."+isSavingsAccountNumberValid);
		if (isSavingsAccountNumberValid) {
			List<CurrentTransaction> currentTransactionList = new ArrayList<CurrentTransaction>();
			// check line of credit accounts balance.
			ReturnStatus returnStatus = checkLineOfCreditKindAmountBalance(master, klsResponse, currentTransactionList);
			if (returnStatus != null && returnStatus.isReturnValue()) {
				// get the current transaction dao.
				ICurrentTransactionDAO dao = KLSDataAccessFactory.getCurrentTransactionDAO();
				// TransactionHistoryHelper.getTransactionHistoryList(currentTransactionList);
				try {
					if (master.getId() == null) {
						dao.saveKindCurrentTransaction(currentTransactionList, returnStatus.getDebitAccountMap());

						Map<String, String> recoveryTransMap = new<String, String> HashMap();
						recoveryTransMap.put("TOTAL_KIND", "0");

						for (CurrentTransaction currentTransaction : currentTransactionList) {
							BigDecimal amount = BigDecimal.ZERO;
							BigDecimal transAmount = BigDecimal.ZERO;
							switch (currentTransaction.getTransactionCode()) {
							case KIND:
								amount = new BigDecimal(recoveryTransMap.get("TOTAL_KIND"));
								transAmount = currentTransaction.getTransactionAmount().getMoney().getAmount();
								amount = amount.add(transAmount);
								recoveryTransMap.put("TOTAL_KIND", amount.toString());
								break;
							}
						}

					} else {
						logger.error("Current Transaction id already exists.");
						klsResponse.setResponseStatus(ISOResponseCodes.DUPLICATE_TRANSACTION);
						klsResponse.setErrorMessage("Current Transaction id already exists.");
					}
				} catch (Exception excp) {
					logger.error("Current Transaction data cannot be saved.");
					klsResponse.setResponseStatus(ISOResponseCodes.SYSTEM_ERROR);
					klsResponse.setErrorMessage("Current Transaction data cannot be saved.");
				}
			}
		}
		logger.info("End : Calling current transaction dao in handleKindTransaction() method.");
		return klsResponse;
	}

	private ReturnStatus checkLineOfCreditKindAmountBalance(CurrentTransaction master, KLSResponse klsResponse,
			List<CurrentTransaction> currentTransactionList) {

		logger.info("Start : Checking the kind amount against the line of " + "credit accounts in checkLineOfCreditKindAmountBalance() method.");
		ReturnStatus returnStatus = null;
		ILineOfCreditDAO dao = KLSDataAccessFactory.getLineOfCreditDAO();
		String query = PropertiesUtil.getProperty(StringConstant.LINE_Of_CREDIT_KIND_QUERY);
		logger.info(" query is : " + query);
		List lineOfCreditAccountsList = dao.getLineOfCreditAccountsList(master.getAccount().getId(), false, query);
		logger.info(" line of credits account list size : " + lineOfCreditAccountsList.size());
		checkTransactionDateValidity(master, lineOfCreditAccountsList);
		logger.info(" line of credits account list size after transaction date validity : " + lineOfCreditAccountsList.size());
		populateAllDefaultValues();
		AccountingMoney sumBalance = AccountingMoney.ZERO;
		if (!lineOfCreditAccountsList.isEmpty()) {
			for (Object object : lineOfCreditAccountsList) {
				LineOfCredit lineOfCredit = (LineOfCredit) object;
				master.setLineOfCredit(lineOfCredit);
				Money kindLimit = lineOfCredit.getKindLimit();
				logger.info(" line of credit id : " + lineOfCredit.getId() + " kindLimit : " + kindLimit);
				AccountingMoney kindLimitMoney = MoneyUtil.getAccountingMoney(kindLimit);
				AccountingMoney kindBalance = lineOfCredit.getKindBalance();
				logger.info(" kindBalance : " + kindBalance);
				AccountingMoney balance = kindLimitMoney.add(kindBalance);
				logger.info(" balance : " + balance);
				sumBalance = sumBalance.add(balance);
			}
			moneyMap.put(ServiceConstants.SUM, sumBalance);
			logger.info(" sumBalance : " + sumBalance);
			returnStatus = checkForKindBalance(master, lineOfCreditAccountsList, klsResponse, currentTransactionList);
			if (returnStatus != null) {
				IBorrowingsAccountDAO borrowingsAccDao = KLSDataAccessFactory.getBorrowingsAccountDAO();
				AccountProperty accountProperty = master.getAccount().getAccountProperty();
				int branchId = accountProperty.getBranch().getId();
				int pacsId = accountProperty.getPacs().getId();
				String bankCode = accountProperty.getBranch().getCode();

				List<LineOfCredit> locList = (ArrayList<LineOfCredit>) returnStatus.getDebitAccountMap().get(ServiceConstants.ACCOUNTS_LIST);
				List<AccountingMoney> moneyList = (ArrayList<AccountingMoney>) returnStatus.getDebitAccountMap().get(ServiceConstants.AMOUNTS_LIST);
				HashMap<String, AccountingMoney> pacsBorrowingsMap = new HashMap<String, AccountingMoney>();

				if (!locList.isEmpty()) {
					LineOfCredit loc;
					for (int i = 0; i < locList.size(); i++) {
						loc = locList.get(i);
						loc = dao.getLineOfCreditById(loc.getId(), false);
						int productId = loc.getProduct().getId();
						String borrowingsAcc = borrowingsAccDao.getBorrowingsAccNo(bankCode, branchId, pacsId, productId);
						if (borrowingsAcc != null)
							pacsBorrowingsMap.put(borrowingsAcc, moneyList.get(i));
					}
				}
				klsResponse.setPacsBorrowingsMap(pacsBorrowingsMap);
			}
		} else {
			logger.error("Transaction date is invalid.");
			klsResponse.setErrorMessage("Transaction date is invalid.");
			klsResponse.setResponseStatus(ISOResponseCodes.INVALID_BUSINESS_DATE);
		}
		logger.info("End : Checking the kind amount against the line of " + "credit accounts in checkLineOfCreditKindAmountBalance() method.");
		return returnStatus;
	}

	/**
	 * 
	 * @param master
	 * @param lineOfCreditAccountsList
	 * @param klsResponse
	 * @param currentTransactionList
	 * @return
	 */
	private ReturnStatus checkForKindBalance(CurrentTransaction master, List lineOfCreditAccountsList, KLSResponse klsResponse,
			List<CurrentTransaction> currentTransactionList) {

		logger.info("Start : Checking the kind amount against the line of " + "credit accounts sum in checkForKindBalance() method.");
		ReturnStatus returnStatus = null;
		AccountingMoney transactionAmount = master.getTransactionAmount();
		logger.info(" transactionAmount : " + transactionAmount);
		AccountingMoney sum = moneyMap.get(ServiceConstants.SUM);
		logger.info(" sum kind balance : " + sum);
		BigDecimal bdTransactionAmount = transactionAmount.getMoney().getAmount();
		if ((sum.isZero() || sum.isCredit()) && transactionAmount.isCredit()) {
			BigDecimal bdSum = sum.getMoney().getAmount();
			int result = bdSum.compareTo(bdTransactionAmount);
			logger.info(" compareTo result in sum credit if block : " + result);
			if (result >= 0) {
				returnStatus = debitTheKindAccount(master, lineOfCreditAccountsList, currentTransactionList);
				klsResponse.setAvailableBalance(sum.getMoney().getAmount().toString());
				klsResponse.setResponseStatus(ISOResponseCodes.APPROVED);
			} else {
				logger.info(" NO FUNDS ");
				klsResponse.setResponseStatus(ISOResponseCodes.NO_FUNDS);
			}
		} else {
			logger.info(" NO FUNDS ");
			klsResponse.setResponseStatus(ISOResponseCodes.NO_FUNDS);
		}
		logger.info("End : Checking the kind amount against the line of " + "credit accounts sum in checkForKindBalance() method.");
		return returnStatus;
	}

	private ReturnStatus debitTheKindAccount(CurrentTransaction master, List lineOfCreditAccountsList, List<CurrentTransaction> currentTransactionList) {

		logger.info("Start : Debit the kind amount in line of " + "credit accounts in debitTheKindAccount() method.");
		ReturnStatus returnStatus = this.new ReturnStatus();
		Map<String, List<?>> debitMap = new HashMap<String, List<?>>();
		List<LineOfCredit> debitAccountList = new ArrayList<LineOfCredit>();
		List<AccountingMoney> amountsList = new ArrayList<AccountingMoney>();
		AccountingMoney transactionAmount = master.getTransactionAmount();
		Integer voucherNumber = VoucherNumberUtil.getVoucherNumber(master.getPacs().getId().toString(), master.getTransactionType().getValue());
		for (Object object : lineOfCreditAccountsList) {
			LineOfCredit lineOfCredit = (LineOfCredit) object;
			Money kindLimit = lineOfCredit.getKindLimit();
			master.setLineOfCredit(lineOfCredit);
			AccountingMoney kindBalance = lineOfCredit.getKindBalance();
			AccountingMoney kindLimitMoney = MoneyUtil.getAccountingMoney(kindLimit);
			AccountingMoney balance = kindLimitMoney.add(kindBalance);
			logger.info(" balance : " + balance);
			if (balance.isZero()) {
				continue;
			}
			AccountingMoney netMoney = transactionAmount.subtract(balance);
			logger.info(" net money : " + netMoney);
			if (netMoney.isZero() || netMoney.isDebit()) {
				debitAccountList.add(lineOfCredit);
				master.setOpeningBalance(AccountingMoney.ZERO);
				populateRemarks(master, lineOfCredit, "by kind:");
				kindBalance = AccountingMoney.valueOf(transactionAmount.getMoney(), DebitOrCredit.DEBIT);
				amountsList.add(kindBalance);
				populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.KIND, transactionAmount, voucherNumber);
				break;
			} else {
				transactionAmount = netMoney;
				debitAccountList.add(lineOfCredit);
				AccountingMoney kindAmount = AccountingMoney.valueOf(balance.getMoney(), DebitOrCredit.DEBIT);
				logger.info(" kind amount : " + kindAmount);
				amountsList.add(kindAmount);
				master.setOpeningBalance(AccountingMoney.ZERO);
				populateRemarks(master, lineOfCredit, "by kind:");
				AccountingMoney transAmount = AccountingMoney.valueOf(kindAmount.getMoney(), DebitOrCredit.CREDIT);
				logger.info(" trans amount : " + transAmount);
				populateCurrentTransactionList(master, currentTransactionList, null, TransactionCode.KIND, transAmount, voucherNumber);
			}
		}
		debitMap.put(ServiceConstants.ACCOUNTS_LIST, debitAccountList);
		debitMap.put(ServiceConstants.AMOUNTS_LIST, amountsList);
		returnStatus.setReturnValue(true);
		returnStatus.setDebitAccountMap(debitMap);
		logger.info("End : Debit the kind amount in line of " + "credit accounts in debitTheKindAccount() method.");
		return returnStatus;
	}

	@Override
	public KLSResponse handleBalanceEnquiry(CurrentTransactionData currentTransactionData) {
		logger.info("Start : Computing balance enquiry. Calling handleBalanceEnquiry()");
		KLSResponse klsResponse = new KLSResponse();
		AccountingMoney totalCashLimit = AccountingMoney.ZERO;
		AccountingMoney totalKindLimit = AccountingMoney.ZERO;
		AccountingMoney totalCashBalance = AccountingMoney.ZERO;
		AccountingMoney totalKindBalance = AccountingMoney.ZERO;
		String query = PropertiesUtil.getProperty(StringConstant.LINE_Of_CREDIT_DEBIT_QUERY);

		ILineOfCreditDAO dao = KLSDataAccessFactory.getLineOfCreditDAO();

		CurrentTransaction master = CurrentTransactionHelper.getCurrentTransaction(currentTransactionData);
		logger.info("currentTransactionData.isStandAloneStatus() in handle balance enquiry method."+currentTransactionData.isStandAloneStatus());
		
		
		boolean isSavingsAccountNumberValid = isSavingsAccountNumberValid(currentTransactionData.getSavingsAccountNumber(), master, klsResponse,
				currentTransactionData.isStandAloneStatus());
		
		logger.info("isSavingsAccountNumberValid in handle balance enquiry method."+isSavingsAccountNumberValid);
		

		if (isSavingsAccountNumberValid) {
			logger.info("Query : " + query);
			List lineOfCreditAccountsList = dao.getLineOfCreditAccountsList(master.getAccount().getId(), false, query);

			if (!lineOfCreditAccountsList.isEmpty()) {
				for (Object object : lineOfCreditAccountsList) {
					LineOfCredit lineOfCredit = (LineOfCredit) object;
					master.setLineOfCredit(lineOfCredit);

					AccountingMoney cashLimit = AccountingMoney.ZERO;
					AccountingMoney kindLimit = AccountingMoney.ZERO;
					AccountingMoney cashBalance = AccountingMoney.ZERO;
					AccountingMoney kindBalance = AccountingMoney.ZERO;

					if (lineOfCredit.getOperatingCashLimit() != null)
						cashLimit = MoneyUtil.getAccountingMoney(lineOfCredit.getOperatingCashLimit());
					if (lineOfCredit.getKindLimit() != null)
						kindLimit = MoneyUtil.getAccountingMoney(lineOfCredit.getKindLimit());
					cashBalance = lineOfCredit.getCurrentBalance();
					kindBalance = lineOfCredit.getKindBalance();

					logger.info("\nLine of credit id : " + lineOfCredit.getId());
					logger.info("Operating Cash Limit : " + cashLimit + "  Current Balance : " + cashBalance + "  Kind Limit : " + kindLimit
							+ "  Kind balance  : " + kindBalance);

					totalCashLimit = totalCashLimit.add(cashLimit != null ? cashLimit : AccountingMoney.ZERO);
					totalKindLimit = totalKindLimit.add(kindLimit != null ? kindLimit : AccountingMoney.ZERO);
					totalCashBalance = totalCashBalance.add(cashBalance != null ? cashBalance : AccountingMoney.ZERO);
					totalKindBalance = totalKindBalance.add(kindBalance != null ? kindBalance : AccountingMoney.ZERO);

				}
			}
			/*klsResponse.setAvailableBalance((totalCashLimit.add(totalKindLimit).add(totalCashBalance).add(totalKindBalance)).getMoney().getAmount()
					.toString());*/
			klsResponse.setAvailableBalance((totalCashLimit.add(totalCashBalance.subtract(totalKindBalance))).getMoney().getAmount()
					.toString());
			klsResponse.setResponseStatus(ISOResponseCodes.APPROVED);
			logger.info("Balance Amount computed: " + klsResponse.getAvailableBalance());
		} else {
			klsResponse.setResponseStatus(ISOResponseCodes.DUPLICATE_TRANSACTION);
			klsResponse.setErrorMessage("Current Transaction id already exists.");
		}
		logger.info("End : Computing balance enquiry. Calling handleBalanceEnquiry()");
		return klsResponse;
	}

	@Override
	public void populateCurrentTransactionRecord(LineOfCredit loanLoc, List<CurrentTransaction> currentTransactionList,
			TransactionCode transactionCode, String remarks, Integer crdr, AccountingMoney transactionAmount, TransactionType transactionType,
			String voucherNumber) {

		logger.info("Start: Populating the recovery current transaction record in populateCurrentTransactionRecord()");
		IPacsDAO pacsDao=KLSDataAccessFactory.getPacsDAO();
		CurrentTransaction currentTransaction = new CurrentTransaction();
		currentTransaction.setAccount(loanLoc.getAccount());
		currentTransaction.setAccountNumber(loanLoc.getAccount().getAccountNumber());
		Pacs pacs=pacsDao.getPacs(loanLoc.getAccount().getAccountProperty().getPacs().getId());
		logger.info("pacs type::"+pacs.getPacsStatus());
		if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
			String businessDate="";
			businessDate=RestClientUtil.getPacsBusinessDate(loanLoc.getAccount().getAccountProperty().getPacs().getId(),pacs.getBranch().getId());
			//businessDate="2017-05-01";
			currentTransaction.setBusinessDate(DateUtil.getVSoftDateByString(businessDate));
		}
		else
		currentTransaction.setBusinessDate(LoanServiceUtil.getBusinessDate());
		currentTransaction.setChannelType(ChannelType.SYSTEM);
		currentTransaction.setCrDr(crdr);
		currentTransaction.setLineOfCredit(loanLoc);
		currentTransaction.setOpeningBalance(loanLoc.getCurrentBalance());
		currentTransaction.setPacs(loanLoc.getAccount().getAccountProperty().getPacs());
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

	@Override
	public void checkTransactionDateValidity(CurrentTransaction master, List lineOfCreditAccountsList) {

		logger.info("Start : Checking whether the transaction date is valid in isTransactionDateValid() method.");
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		Date transactionDate = master.getBusinessDate();
		if (transactionDate != null) {
			List copyList = new ArrayList();
			copyList.addAll(lineOfCreditAccountsList);
			for (Object object : copyList) {
				if (object instanceof LineOfCredit) {
					LineOfCredit lineOfCredit = (LineOfCredit) object;
					logger.info(" Loan Type : " + lineOfCredit.getLoanType());
					String businessDate="";
					Pacs pacsMaster=lineOfCredit.getAccount().getAccountProperty().getPacs();
					Pacs pacs=pacsDao.getPacs(pacsMaster.getId());
					logger.info("pacs type::"+pacs.getPacsStatus());
					if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
						businessDate=RestClientUtil.getPacsBusinessDate(pacsMaster.getId(),pacs.getBranch().getId());
						//businessDate="2017-05-01";
						transactionDate=DateUtil.getVSoftDateByString(businessDate);
					}
					Date seasonStartDate = lineOfCredit.getSeason().getDrawalStartDate();
					Date seasonEndDate = lineOfCredit.getSeason().getDrawalEndDate();
					if (!((transactionDate.getTimeInMillis() >= seasonStartDate.getTimeInMillis()) && (transactionDate.getTimeInMillis() <= seasonEndDate
							.getTimeInMillis()))) {
						lineOfCreditAccountsList.remove(lineOfCredit);
					}
				}
			}
		} else {
			lineOfCreditAccountsList = new ArrayList();
		}
		logger.info("End : Checking whether the transaction date is valid in isTransactionDateValid() method.");
	}
	
	
	@Override
	public String checkTransactionDateValid(CurrentTransaction master, List lineOfCreditAccountsList) {

		logger.info("Start : Checking whether the transaction date is valid in isTransactionDateValid() method.");
		Date transactionDate = master.getBusinessDate();
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		String message="";
		if (transactionDate != null) {
			List copyList = new ArrayList();
			copyList.addAll(lineOfCreditAccountsList);
			for (Object object : copyList) {
				if (object instanceof LineOfCredit) {
					LineOfCredit lineOfCredit = (LineOfCredit) object;
					logger.info(" Loan Type : " + lineOfCredit.getLoanType());
					Pacs pacsMaster=lineOfCredit.getAccount().getAccountProperty().getPacs();
					String businessDate="";
					Pacs pacs=pacsDao.getPacs(pacsMaster.getId());
					logger.info("pacs type::"+pacs.getPacsStatus());
					if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
						businessDate=RestClientUtil.getPacsBusinessDate(pacsMaster.getId(),pacs.getBranch().getId());
						//businessDate="2017-05-01";
						transactionDate=DateUtil.getVSoftDateByString(businessDate);
					}
					Date seasonStartDate = lineOfCredit.getSeason().getDrawalStartDate();
					Date seasonEndDate = lineOfCredit.getSeason().getDrawalEndDate();
					logger.info("comparison==="+lineOfCredit.getId());
					if (!((transactionDate.getTimeInMillis() >= seasonStartDate.getTimeInMillis()) && (transactionDate.getTimeInMillis() <= seasonEndDate
							.getTimeInMillis()))) {
						lineOfCreditAccountsList.remove(lineOfCredit);
					}
					if(seasonStartDate.isAfter(transactionDate))
						message="Drawl period not started";
					if(seasonEndDate.isBefore(transactionDate))
						message="Drawl period completed";
				}
			}
		} else {
			lineOfCreditAccountsList = new ArrayList();
		}
		logger.info("End : Checking whether the transaction date is valid in isTransactionDateValid() method.");
		return message;
	}

	public void postDebitCreditTransaction(CurrentTransaction master, String voucherNumber){
		List<CurrentTransaction> currentTransactionList = new ArrayList<CurrentTransaction>();
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		master.setTransactionCode(TransactionCode.COLLECTION);
		master.setRemarks("Credit Total Recovery Amount to Loan Account");
		master.setVoucherNumber(voucherNumber);
		currentTransactionList.add(master);
		CurrentTransaction creditTransaction = new CurrentTransaction();
		creditTransaction .setAccount(master.getAccount());
		creditTransaction.setAccountNumber(master.getAccountNumber());
		String businessDate="";
		Pacs pacs=pacsDao.getPacs(master.getPacs().getId());
		logger.info("pacs type::"+pacs.getPacsStatus());
		if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
			businessDate=RestClientUtil.getPacsBusinessDate(master.getPacs().getId(),pacs.getBranch().getId());
			//businessDate="2017-05-01";
			creditTransaction.setBusinessDate(DateUtil.getVSoftDateByString(businessDate));
		}
		else
		creditTransaction.setBusinessDate(master.getBusinessDate());
		creditTransaction.setChannelType(master.getChannelType());
		creditTransaction.setCrDr(-1);
		creditTransaction.setLineOfCredit(master.getLineOfCredit());
		creditTransaction.setOpeningBalance(master.getOpeningBalance());
		creditTransaction.setPacs(master.getPacs());
		creditTransaction.setPostedStatus(master.getPostedStatus());
		creditTransaction.setTerminalId(master.getTerminalId());
		creditTransaction.setTransactionAmount(master.getTransactionAmount());
		creditTransaction.setTransactionCode(TransactionCode.COLLECTION);
		creditTransaction.setTransactionType(master.getTransactionType());
		creditTransaction.setVoucherNumber(voucherNumber);
		creditTransaction.setRemarks("Debit Total Recovery Amount From Loan Account");
		currentTransactionList.add(creditTransaction);
		ICurrentTransactionDAO dao = KLSDataAccessFactory.getCurrentTransactionDAO();
		dao.saveCurrentTransactionsList(currentTransactionList);
	}
	public CurrentTransaction copyCurrentTransaction(CurrentTransaction master){
		
		CurrentTransaction creditTransaction = new CurrentTransaction();
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		creditTransaction .setAccount(master.getAccount());
		creditTransaction.setAccountNumber(master.getAccountNumber());
		String businessDate="";
		Pacs pacs=pacsDao.getPacs(master.getPacs().getId());
		logger.info("pacs type::"+pacs.getPacsStatus());
		if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
			businessDate=RestClientUtil.getPacsBusinessDate(master.getPacs().getId(),pacs.getBranch().getId());
			//businessDate="2017-05-01";
			creditTransaction.setBusinessDate(DateUtil.getVSoftDateByString(businessDate));
		}
		else
		creditTransaction.setBusinessDate(master.getBusinessDate());
		creditTransaction.setChannelType(master.getChannelType());
		creditTransaction.setCrDr(master.getCrDr());
		creditTransaction.setLineOfCredit(master.getLineOfCredit());
		creditTransaction.setOpeningBalance(master.getOpeningBalance());
		creditTransaction.setPacs(master.getPacs());
		creditTransaction.setPostedStatus(master.getPostedStatus());
		creditTransaction.setTerminalId(master.getTerminalId());
		creditTransaction.setTransactionAmount(master.getTransactionAmount());
		creditTransaction.setTransactionCode(TransactionCode.COLLECTION);
		creditTransaction.setTransactionType(master.getTransactionType());
		creditTransaction.setRemarks("Debit Total Recovery Amount From Loan Account");
		
		return creditTransaction;
	}
	
	/**
	 * 
	 * @param lineOfCredit
	 * @param currentTransactionList
	 * @param master
	 * @param voucherNumber
	 * @return remaining interest
	 */
	private AccountingMoney doSubsidyCalculationAtRecovery(
			LineOfCredit lineOfCredit,
			List<CurrentTransaction> currentTransactionList,
			CurrentTransaction master, Integer voucherNumber) {
		ISubsidyInterestAmountsDAO isubDao = KLSDataAccessFactory
				.getSubsidyInterestAmountsDAO();
		List<Map> schemeGlList = isubDao.getSubsidySchemeAndGlMap(
				lineOfCredit.getId(), "SS");
		AccountingMoney subsidyReceivable = AccountingMoney.ZERO;
		AccountingMoney tmpInterestReceivableMoney = MoneyUtil.getAccountingMoney(lineOfCredit.getInterestReceivable().setScale(0,
				RoundingMode.HALF_UP));
		boolean asAndWhenImplemented = lineOfCredit.getProduct()
				.getAsAndWhenImplemented();
		
		//get subsidyReceivable from map
		if(asAndWhenImplemented){
			tmpInterestReceivableMoney = tmpInterestReceivableMoney.add(
					MoneyUtil.getAccountingMoney(lineOfCredit.getInterestAccrued().setScale(0,
							RoundingMode.HALF_UP)));
		}
		
		for (int a = 0; a < schemeGlList.size(); a++) {
			subsidyReceivable = MoneyUtil
					.getAccountingMoney((BigDecimal) schemeGlList.get(a).get(
							"subsidyReceivable"));
			logger.info("As and when ====="+asAndWhenImplemented);
			// updating subsidy receivable amount
			if (asAndWhenImplemented) {
				AccountingMoney subInterestAccrued = MoneyUtil
						.getAccountingMoney((BigDecimal) schemeGlList.get(a)
								.get("subsidyAccrued"));
				logger.info("subsidyAccrued before : " + subInterestAccrued);
				if (!(subInterestAccrued.isZero()) || subInterestAccrued
						.isCredit()) {
					logger.info("inside if checking:::");
					subsidyReceivable = subsidyReceivable
							.add(subInterestAccrued);
					logger.info("subsidy receivable inside if===="+subsidyReceivable);
				}
			}
			String intSubsidyReceivableGl = (String) schemeGlList.get(a).get(
					"subsidyReceievableGl");
			logger.info("subsidyReceivable before : " + subsidyReceivable);
			
			AccountingMoney diffAmount = tmpInterestReceivableMoney
					.subtract(subsidyReceivable);
			if (diffAmount.isDebit() || diffAmount.isZero()) {
				// tempSubsidyReceivable = subsidyReceivable;
				tmpInterestReceivableMoney = diffAmount; // after applaying
															// subsidy interest
															// receivable
			} else {
				subsidyReceivable = tmpInterestReceivableMoney; // Here subsidy
																// is more so we
																// are
																// considering
																// only interest
																// amount
				tmpInterestReceivableMoney = AccountingMoney.ZERO;
			}
			if (!subsidyReceivable.isZero()) {
				// Interest SubsidyReceivableGl transaction
				AccountingMoney creditAmount = MoneyUtil.getAccountingMoney(subsidyReceivable.getMoney().getAmount().setScale(0,
						RoundingMode.HALF_UP),
						DebitOrCredit.CREDIT);
				populateCurrentTransactionRecord(lineOfCredit,
						intSubsidyReceivableGl, currentTransactionList,
						TransactionCode.INTEREST_SUBSIDY,
						"Reocovery:Interest Subsidy Receivable Gl ", -1,
						creditAmount, false, master, voucherNumber);
			}

			logger.info("tmpInterestReceivableMoney : "
					+ tmpInterestReceivableMoney);
			logger.info("subsidyReceivable after: " + subsidyReceivable);
			List<SubsidyInterestAmounts> subsidyAmountsLst = new ArrayList<>();
			subsidyAmountsLst = isubDao.getSubsidyInterestAmtObj(
					lineOfCredit.getId(),
					(Long) schemeGlList.get(a).get("subsidySchemeId"));
			SubsidyInterestAmounts subsidyAmounts = new SubsidyInterestAmounts();
			if (subsidyAmountsLst.size() > 0)
				subsidyAmounts = subsidyAmountsLst.get(0);
			if(asAndWhenImplemented) subsidyAmounts.setSubsidyAccrued(BigDecimal.ZERO);
			subsidyAmounts.setSubsidyReceivable(BigDecimal.ZERO);
			subsidyAmounts.setSubsidyPaid(subsidyAmounts.getSubsidyPaid().add(
					subsidyReceivable.getMoney().getAmount()
							.multiply(BigDecimal.valueOf(-1l))));

			isubDao.updateSubsidyInterestAmounts(subsidyAmounts);
			// subsidyReceivable =
			// MoneyUtil.getAccountingMoney(subsidyReceivable.getDebitAmount());//
			// here subsidy recvable +ve
			logger.info("subsidyReceivable afetr credit: " + subsidyReceivable);

			//tmpInterestReceivableMoney = tmpInterestReceivableMoney.subtract(subsidyReceivable);
			logger.info("tmpInterestReceivableMoney fin=="+tmpInterestReceivableMoney);
		}
		
		return tmpInterestReceivableMoney;// remaining interest

	}


	@Override
	public List<VoucherData> getTransactionsByVoucherNumber(
			String voucherNumber) {

		logger.info("Start: Fetching Transactions with voucher number");
		List<VoucherData> voucherDataList =  new ArrayList<VoucherData>();
		ICurrentTransactionDAO dao = KLSDataAccessFactory.getCurrentTransactionDAO();
		List<CurrentTransaction> currentTransactionList = dao.getTransactionsByVoucherNumber(voucherNumber);
		try{
			if(currentTransactionList != null && !currentTransactionList.isEmpty()){
				for(CurrentTransaction currentTransaction : currentTransactionList){
					VoucherData voucherData = new VoucherData();
					voucherData.setVoucherNumber(currentTransaction.getVoucherNumber());
					voucherData.setBusinessDate(DateUtil.getDateString(currentTransaction.getBusinessDate()));
					voucherData.setCrdr(currentTransaction.getCrDr());
					voucherData.setAccountNumber(currentTransaction.getAccountNumber());
					voucherData.setDescription(currentTransaction.getRemarks());
					voucherData.setAmount(currentTransaction.getTransactionAmount().getMoney().getAmount().toString());
					
					voucherDataList.add(voucherData);
				}
			}
			
		}catch(Exception e){
			logger.info("Error: Error while Fetching Transactions with voucher number");
			e.printStackTrace();
		}
		logger.info("End: Fetching Transactions with voucher number");
		return voucherDataList;
	}
	
	/**
	 * 
	 * @param accountNumber
	 * @return
	 */
	private boolean isSavingsAccountNumberValidForBulk(String savingsAccountNumber, CurrentTransaction master, KLSResponse klsResponse,
			boolean isStandaloneMode,String mode) {

		logger.info("Start : Checking whether the savings account number is valid in isSavingsAccountValid() method.");
		logger.info(" Savings account number : " + savingsAccountNumber);
		boolean returnValue = false;
		// get the account property dao.
		IAccountPropertyDAO dao = KLSDataAccessFactory.getAccountPropertyDAO();
		AccountProperty accountProperty = null;
		Account account = null;
		IAccountDAO accountdao=KLSDataAccessFactory.getAccountDAO();
		logger.info(" isStandaloneMode in issaving method : " + isStandaloneMode);
		
			try {
				if(mode.equals("C") || mode.equals("D")){
					logger.info(" in StandaloneMode in accountnumber method : " + master.getAccountNumber());
					account=accountdao.getAccount(savingsAccountNumber);
					if(account.getAccountProperty()!=null)
					{
						logger.info(" in StandaloneMode inside accountproperty : " );
						accountProperty=account.getAccountProperty();
					}
					
				}
				if (mode.equals("T") && savingsAccountNumber != null && !isStandaloneMode) {
				accountProperty = dao.getAccountProperty(savingsAccountNumber, false);
				if (accountProperty == null) {
					logger.error("Savings account number does not exists in the database.");
					String message = PropertiesUtil.getProperty(ISOResponseCodes.NO_SAVINGS_ACCOUNT);
					klsResponse.setResponseStatus(ISOResponseCodes.NO_SAVINGS_ACCOUNT);
					klsResponse.setErrorMessage(message);
				}
				}
				if (accountProperty != null) {
					returnValue = isValidTransactionForBulk(accountProperty, isStandaloneMode, klsResponse);
					if (returnValue)
						returnValue = isAccountValid(accountProperty, master, klsResponse);
				}
			} catch (Exception excp) {
				if (accountProperty == null) {
					logger.error("Savings/Account  account number does not exists in the database.");
					String message = PropertiesUtil.getProperty(ISOResponseCodes.NO_SAVINGS_ACCOUNT);
					klsResponse.setResponseStatus(ISOResponseCodes.NO_SAVINGS_ACCOUNT);
					klsResponse.setErrorMessage(message);
				}
			}

		
		logger.info("End : Checking whether the savings account number is valid in isSavingsAccountValid() method.");
		return returnValue;
	}

	private boolean isValidTransactionForBulk(AccountProperty accountProperty, boolean isStandaloneMode, KLSResponse klsResponse) {
		boolean returnValue = true;
		String cardStatus = "";
		PersonData personData = RestClientUtil.getAllCustomerDataByCusomterId(accountProperty.getCustomerId());
		List<PartyBankInfoData> bankDetailList = personData.getBankDetailList();
		if (bankDetailList != null && !bankDetailList.isEmpty()) {
			PartyBankInfoData bankData = bankDetailList.get(0);
			cardStatus = bankData.getCardStatus();
		}
		/*if (!isStandaloneMode && !cardStatus.equalsIgnoreCase("I")) {
			logger.info("Transactions not allowed as Card not not issued");
			klsResponse.setResponseStatus(ISOResponseCodes.TRANSACTION_NOT_ALLOWED);
			klsResponse.setErrorMessage("Transactions are not allowed");
			returnValue = false;
		} else if (isStandaloneMode && cardStatus.equalsIgnoreCase("I")) {
			logger.info("Transactions not allowed as Card not not issued");
			klsResponse.setResponseStatus(ISOResponseCodes.TRANSACTION_NOT_ALLOWED);
			klsResponse.setErrorMessage("Transactions are not allowed");
			returnValue = false;
		}*/
		return returnValue;
	}
	
		
	private boolean isSBNumberValid(String savingsAccountNumber, CurrentTransaction master, KLSResponse klsResponse,
			boolean isStandaloneMode) {

		logger.info("Start : Checking whether the savings account number is valid in isSBNumberValid() method.");
		logger.info(" Savings account number : " + savingsAccountNumber);
		boolean returnValue = false;
		// get the account property dao.
		IAccountPropertyDAO dao = KLSDataAccessFactory.getAccountPropertyDAO();
		AccountProperty accountProperty = null;
		Account account = null;
		IAccountDAO accountdao=KLSDataAccessFactory.getAccountDAO();
		logger.info(" isStandaloneMode in issaving method : " + isStandaloneMode);
		
			try {
				if(isStandaloneMode){
					logger.info(" in StandaloneMode in accountnumber method : " + master.getAccountNumber());
					account=accountdao.getAccount(savingsAccountNumber);
					if(account.getAccountProperty()!=null)
					{
						logger.info(" in StandaloneMode inside accountproperty : " );
						accountProperty=account.getAccountProperty();
					}
					
				}
				if (savingsAccountNumber != null && !isStandaloneMode) {
				accountProperty = dao.getAccountProperty(savingsAccountNumber, false);
				if (accountProperty == null) {
					logger.error("Savings account number does not exists in the database.");
					String message = PropertiesUtil.getProperty(ISOResponseCodes.NO_SAVINGS_ACCOUNT);
					klsResponse.setResponseStatus(ISOResponseCodes.NO_SAVINGS_ACCOUNT);
					klsResponse.setErrorMessage(message);
				}
				}
				if (accountProperty != null) {
					returnValue = isValidTransaction(accountProperty, isStandaloneMode, klsResponse);
					if (returnValue)
						returnValue = isAccountValid(accountProperty, master, klsResponse);
				}
			} catch (Exception excp) {
				if (accountProperty == null) {
					logger.error("Savings/Account  account number does not exists in the database.");
					String message = PropertiesUtil.getProperty(ISOResponseCodes.NO_SAVINGS_ACCOUNT);
					klsResponse.setResponseStatus(ISOResponseCodes.NO_SAVINGS_ACCOUNT);
					klsResponse.setErrorMessage(message);
				}
			}

		
		logger.info("End : Checking whether the savings account number is valid in isSBNumberValid() method.");
		return returnValue;
	}

	@Override
	public Map<String,String> checkLoanLimitsService(KLSRequest klsRequest) {
		
		logger.info("Start : Checking Loan limits are available or not in checkLoanLimitsService() method.");
		CurrentTransactionData data = new CurrentTransactionData();
		KLSResponse klsResponse = new KLSResponse();
		BankParameter bankParameter = KLSDataAccessFactory.getBankParameter();
		String transactionMode=klsRequest.getTransactionMode();
		logger.info("klsRequest==" + klsRequest);
		Map<String, String> isoDataElements = klsRequest.getIsoDataElements();
		logger.info("Printing all iso nodes  : " + isoDataElements.size());
		for (String key : isoDataElements.keySet()) {
			logger.info(" iso data elements key is : " + key);
			logger.info(" iso data elements value is : " + isoDataElements.get(key));
		}
		data.setVoucherNumber(null);
		data.setSavingsAccountNumber(klsRequest.getAccountNumber());
		data.setBusinessDate(isoDataElements.get(ISODataElements.TRANSACTION_DATE_AND_TIME));
		if (TransactionType.Withdrawal.getValue().equals(klsRequest.getTransactionType())) {
			data.setCrDr("-1");
			//Change the transaction Type from W to T for Normal Mode Withdrawl.
			data.setTransactionType("T");
		} 
		data.setTransactionAmount(isoDataElements.get(ISODataElements.TRANSACTION_AMOUNT));
		data.setTransactionTowards("1");
		data.setChannelType(klsRequest.getChannelType());
		data.setRemarks(isoDataElements.get(ISODataElements.CARD_ACCEPTOR_NAME_LOCATION));
		data.setTerminalId(isoDataElements.get(ISODataElements.CARD_ACCEPTOR_TERMINAL_IDENTIFICATION));
		data.setStandAloneStatus(false);
		try {
			CurrentTransaction master = CurrentTransactionHelper.getCurrentTransaction(data);
			if(isSBNumberValid(klsRequest.getAccountNumber(), master,klsResponse, false)){
				
				//BankParameter bankParameter = KLSDataAccessFactory.getBankParameter();
				AccountProperty accountProperty = KLSDataAccessFactory.getAccountPropertyDAO().getAccountProperty(klsRequest.getAccountNumber(), false);
				Account account = KLSDataAccessFactory.getAccountDAO().getAccount(accountProperty.getId(), false);
				Map<String,Object> loanInfo = KLSServiceFactory.getLoanDisbursementEntryService().getLoanInfo(account.getId(), "C", "D");
				BigDecimal drawableAmount= new BigDecimal((String)loanInfo.get("drawableAmount"));
				BigDecimal transactionAmount= master.getTransactionAmount().getMoney().getAmount();
				if(transactionAmount.compareTo(drawableAmount)==1){
					logger.error("Insufficient funds in account");
					isoDataElements.put(ISODataElements.RESPONSE_CODE, ISOResponseCodes.NO_FUNDS);
				}else{
					isoDataElements.put(ISODataElements.RESPONSE_CODE, ISOResponseCodes.APPROVED);
					isoDataElements.put(ISODataElements.RESERVED_FOR_PRIVATE_USE_120, bankParameter.getSuspenseAccount());//here we are using bit 120 for sharing suspense account
				}
			}else{
				logger.error("Savings account number does not exists in the database.");
				isoDataElements.put(ISODataElements.RESPONSE_CODE, ISOResponseCodes.NO_SAVINGS_ACCOUNT);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			isoDataElements.put(ISODataElements.RESPONSE_CODE, ISOResponseCodes.SYSTEM_ERROR);
		}
		logger.info("End : Checking Loan limits are available or not in checkLoanLimitsService() method.");
		return isoDataElements;
	}
	@Override
	public KLSResponse reversalTransaction(Map<String,String> isoDataElements){
		logger.info("Start : reversalTransaction method");
		String returnMsg="";
		ICurrentTransactionDAO currentTranDao=KLSDataAccessFactory.getCurrentTransactionDAO();
		CurrentTransaction master=new CurrentTransaction();
		ILineOfCreditDAO locDao=KLSDataAccessFactory.getLineOfCreditDAO();
		Long locId=new Long(0);
		AccountingMoney transAmt=AccountingMoney.ZERO;
		KLSResponse klsResponse = new KLSResponse();
		try{
			logger.info("inside try block");
			master=CurrentTransactionHelper.getReversalTransaction(isoDataElements);
			logger.info("after helper");
			locId=master.getLineOfCredit().getId();
			logger.info("testing locId==="+locId);
			transAmt=master.getTransactionAmount();
	    returnMsg=currentTranDao.saveReversalTransaction(master);
	    if(returnMsg!=""){
	    	LineOfCredit loc=locDao.getLocId(locId);
	    	AccountingMoney currentBalance=loc.getCurrentBalance().add(transAmt);
	    	locDao.updateCurrentBalence(locId, currentBalance);
	    	klsResponse.setResponseStatus(ISOResponseCodes.APPROVED);
	    }
	   
		}catch(Exception ex){
			ex.printStackTrace();
			klsResponse.setResponseStatus(ISOResponseCodes.SYSTEM_ERROR);
		}
		logger.info("End : reversalTransaction method");
		return klsResponse;
	}
@Override
public List<MiniStatementData> getMiniStatement(String accountNum){
	logger.info("Start : Calling from getMiniStatement method .");
	List<MiniStatementData> miniStatementList=new ArrayList<MiniStatementData>();
	try{
		IAccountPropertyDAO accountPropDao=KLSDataAccessFactory.getAccountPropertyDAO();
		AccountProperty accPorper=new AccountProperty();
		accPorper=accountPropDao.getAccountProperty(accountNum, false);
		IAccountDAO accDao=KLSDataAccessFactory.getAccountDAO();
		Account acc=accDao.getAccount(accPorper.getId(), false);
		ICurrentTransactionDAO dao=KLSDataAccessFactory.getCurrentTransactionDAO();
		ITransactionHistoryDAO tranHistdao=KLSDataAccessFactory.getTransactionHistoryDAO();
		BankParameter parameter=KLSDataAccessFactory.getBankParameter();
		List<CurrentTransaction> currentTransactionList=dao.getMiniStatement(acc.getAccountNumber(), parameter.getBusinessDate());
		List<TransactionHistory> transactionHistory=new ArrayList<TransactionHistory>();
		Integer noOfRecords=new Integer(0);
		if(currentTransactionList.size()<10){
			noOfRecords=10-currentTransactionList.size();
			transactionHistory=tranHistdao.getMiniStatement(acc.getAccountNumber(), parameter.getBusinessDate(),noOfRecords);	
		}
		miniStatementList=CurrentTransactionHelper.getMiniStatementData(currentTransactionList,transactionHistory);
	}catch(Exception e){
		e.printStackTrace();
	}
	return miniStatementList;
}
private CamelRequest retrieveCamelRequest(Integer pacsId,Integer productId,CurrentTransactionData currentTransactionData)
{
	
	IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO(); 
	CamelRequest camelRequest=new CamelRequest();
	BorrowingsAccountProperty bAccountProperty  = KLSDataAccessFactory.getBorrowingsAccountPropertyDAO().getAccountPropertyByLoanProductIdPacsId(pacsId, productId);
	BorrowingProductGlMapping bglMapping = KLSDataAccessFactory.getBorrowingProductGlMappingDAO()
			.getBorrowingProductGlMapping(bAccountProperty.getBorrowingProduct().getId(), pacsId);
	
	camelRequest.setLoanIntReceivableGL(bglMapping.getInterestpayableGl());
	camelRequest.setLoanIntReceivedGL(bglMapping.getInterestpaidGl());
	camelRequest.setLoanPenalIntReceivableGL(bglMapping.getPenalInterestPayableGlCode());
	camelRequest.setLoanPenalIntReceivedGL(bglMapping.getPenalInterestPaidGlCode());
	Pacs pacs=pacsDao.getPacs(pacsId);
	camelRequest.setPacsSBAccount(pacs.getBankPacsAccNumber());
	camelRequest.setBankCashInTransitGL(pacs.getCashInTransitAccNo());
	camelRequest.setLoanOperativeGL(bAccountProperty.getCcbAccountNumber());
	//camelRequest.setInterestRecovered(glMap.getIntReceivedGL());
	//camelRequest.setLoanOperativeGL(glMap.getGlCode());
	//camelRequest.setPrincipalRecovred(glMap.getGlCode());
	String tranDate=currentTransactionData.getBusinessDate();
	Date dt=DateUtil.getVSoftDateByString(currentTransactionData.getBusinessDate());
	java.util.Date dt1=new java.util.Date();
	Integer hr=dt1.getHours();
	Integer min=dt1.getMinutes();
	Integer sec=dt1.getSeconds();
	String minutes=min.toString();
	if(minutes.length()==1)
		minutes="0"+minutes;
	String hours=hr.toString();
	if(hours.length()==1)
		hours="0"+hours;
	String seconds=sec.toString();
	if(seconds.length()==1)
		seconds="0"+seconds;
	String time=hours+minutes+seconds;
	logger.info("current business date::"+currentTransactionData.getBusinessDate().substring(0, 6)+time);
	camelRequest.setTransactionDate(currentTransactionData.getBusinessDate().substring(0, 6)+time);
	String transactionAmt=currentTransactionData.getTransactionAmount();
	int amtLen=transactionAmt.length();
	String tranAmt="000000000000";
	camelRequest.setTransactionAmount(String.format("%0"+ (12 - transactionAmt.length() )+"d%s",0 ,transactionAmt));
//	logger.info("amount after format::"+String.format("%012d",transactionAmt));
	logger.info("amount after format::"+String.format("%0"+ (12 - transactionAmt.length() )+"d%s",0 ,transactionAmt)); 
	return camelRequest;
}
private CamelRequest createBorrowingRecoveryTransactions(BigDecimal amount, Integer transactionCode,
		List<BorrowingsLineOfCredit> borrowingLocDetailsList,CamelRequest request) {
	logger.info("Start: Creating borrowings recovery transactions in createBorrowingRecoveryTrasactions()"+request);
	IBorrowingsLineOfCreditDAO bDao = KLSDataAccessFactory.getBorrowingsLineOfCreditDAO();
	IProductDAO productDAO = KLSDataAccessFactory.getProductMasterDAO();
	BigDecimal amountPaid = amount;
	Integer voucherNumber = null;
	//CamelRequest request=new CamelRequest();
	for (BorrowingsLineOfCredit bLoc : borrowingLocDetailsList) {
		voucherNumber = VoucherNumberUtil.getVoucherNumber(bLoc.getLoanAccountProperty().getAccount()
				.getAccountProperty().getPacs().getId().toString(), TransactionType.Borrowings.getValue());
		
		
		Product product1 = productDAO.getProduct(bLoc.getProduct(), false);
		IRecoveryOrderDao rDao = KLSDataAccessFactory.getRecoveryOrderDAO();

		int a = 0;
		if (transactionCode == TransactionCode.PRINCIPAL_BAL.getValue()) {
			// principal credit
			a = amountPaid.compareTo(BigDecimal.ZERO);
			if (a == 1) {
				a = bLoc.getCurrentBalance().compareTo(AccountingMoney.ZERO);
				if (a == 1) {
					a = amountPaid.compareTo(bLoc.getCurrentBalance().getMoney().getAmount());
					AccountingMoney payingAmt = MoneyUtil.getAccountingMoney(amountPaid);
					if (a == 1 || a == 0) {
						request.setPrincipalRecovred(bLoc.getCurrentBalance().getMoney().getAmount().toString());
						logger.info("checking principle amount::"+request.getPrincipalRecovred());
						amountPaid = amountPaid.subtract(bLoc.getCurrentBalance().getMoney().getAmount());
						bLoc.setCurrentBalance(AccountingMoney.ZERO);

					} else {
						request.setPrincipalRecovred(payingAmt.getMoney().getAmount().toString());
						logger.info("checking principle amount::"+request.getPrincipalRecovred());
						amountPaid = BigDecimal.ZERO;
						bLoc.setCurrentBalance(bLoc.getCurrentBalance().subtract(payingAmt));
					}

				//	bDao.saveBorrowingsLOCAndPostTransaction(bLoc, currTrans);
				}
			}
		} else if (transactionCode == TransactionCode.INTEREST.getValue()) {

			// interest credit
			logger.info("inside else if  interest part::");
			a = amountPaid.compareTo(BigDecimal.ZERO);
			if (a == 1) {
				a = bLoc.getInterestReceivable().compareTo(BigDecimal.ZERO);
				AccountingMoney payingAmt = MoneyUtil.getAccountingMoney(amountPaid);
				if (a == 1) {
					a = amountPaid.compareTo(bLoc.getInterestReceivable());
					if (a == 1 || a == 0) {
						String principleAmt=MoneyUtil.getAccountingMoney(bLoc.getInterestReceivable()).getMoney().getAmount().toString();
						request.setInterestRecovered(principleAmt);
						logger.info("checking interest amount::***"+request.getPrincipalRecovred());
						amountPaid = amountPaid.subtract(bLoc.getInterestReceivable());
						bLoc.setInterestReceivable(BigDecimal.ZERO);
						

					} else {
						request.setInterestRecovered(payingAmt.getMoney().getAmount().toString());
						logger.info("checking interest amount::"+request.getPrincipalRecovred());
						amountPaid = BigDecimal.ZERO;
						bLoc.setInterestReceivable(bLoc.getInterestReceivable().subtract(amountPaid));
						
					}
				//	bDao.saveBorrowingsLOCAndPostTransaction(bLoc, currTrans);
				}
			}
		} else if (transactionCode == TransactionCode.PENAL_INTEREST.getValue()) {

			// penal interest credit
			//currTrans.setRemarks("For Borrowing Recovery Penal Interest Credit.");
			//currTrans.setTransactionCode(TransactionCode.PENAL_INTEREST);
			logger.info("inside else if penal interest part::"+amountPaid);
			a = amountPaid.compareTo(BigDecimal.ZERO);
			logger.info("a value is==="+a);
			if (a == 1) {
				logger.info("borrowing penal int::"+bLoc.getPenalInterestReceivable());
				a = bLoc.getPenalInterestReceivable().compareTo(BigDecimal.ZERO);
				if (a == 1) {
					logger.info("inside first if");
					AccountingMoney payingAmt = MoneyUtil.getAccountingMoney(amountPaid);
					logger.info("inside first if"+bLoc.getPenalInterestReceivable());
					a = amountPaid.compareTo(bLoc.getPenalInterestReceivable());
                     logger.info("checking a value==="+a);
					if (a == 1 || a == 0) {
						request.setPenalInterestRecovered(MoneyUtil.getAccountingMoney(bLoc
								.getPenalInterestReceivable()).getMoney().getAmount().toString());
						logger.info("penal interest::"+request.getPenalInterestRecovered());
						amountPaid = amountPaid.subtract(bLoc.getPenalInterestReceivable());
						bLoc.setPenalInterestReceivable(BigDecimal.ZERO);
						

					} else {
						request.setPenalInterestRecovered(payingAmt.getMoney().getAmount().toString());
						logger.info("penal interest in else::"+request.getPenalInterestRecovered());
						amountPaid = BigDecimal.ZERO;
						bLoc.setPenalInterestReceivable(bLoc.getPenalInterestReceivable().subtract(amountPaid));
						
					}
					//bDao.saveBorrowingsLOCAndPostTransaction(bLoc, currTrans);
				}
			}
		}
		// Charges received amount wont be adjusted for borrowing account

		// Boolean flag = bDao.isAllAmountsClear(bLoc.getId());
		//if (amountPaid.compareTo(BigDecimal.ZERO) == 0) { // !flag
		//	return null;
		//}
	}
	// TODO: Need to adjust remaining amount against additional borrowing
	// account
	logger.info("amount paid::"+amountPaid);
	if (amountPaid.compareTo(BigDecimal.ZERO) == 1)
		logger.info("Remaining amount after borrowing recovery :" + amountPaid);
	logger.info("End: Creating borrowings recovery transactions in createBorrowingRecoveryTrasactions()"+request);
	return request;
}
private ISOTransactionResponse getISOResponse(List<CurrentTransaction> currentTransactionList,CurrentTransactionData currentTransactionData){
	IBorrowingsLineOfCreditDAO borrLOCDAO = KLSDataAccessFactory.getBorrowingsLineOfCreditDAO();
	IBorrowingsAccountPropertyDAO borrAccountPropertyDAO = KLSDataAccessFactory.getBorrowingsAccountPropertyDAO();	
	BankParameter bankParam=KLSDataAccessFactory.getBankParameter();	
	Map<Integer,CamelRequest> reqestMap=new HashMap<Integer,CamelRequest>();
	for(CurrentTransaction transaction:currentTransactionList){
		LineOfCredit loc = transaction.getLineOfCredit();
		if(bankParam.getCbsIntegrationMethod().equals(CBSMethodEnum.ISO8583)){
		if (transaction.getCrDr() == 1 && loc != null
				&& !TransactionType.Borrowings.equals(transaction.getTransactionType())) {
			//BorrowingsAccountProperty borrowingsAccountProperty = borrAccountPropertyDAO
			//		.getAccountPropertyByProductId(loc.getProduct().getId());
			List<BorrowingsLineOfCredit> borrowingLocDetailsList=new ArrayList<BorrowingsLineOfCredit>();
			if (bankParam.getBorrowingTransactionMethod().getValue().equals(BorrowingTransactionMethod.Grouping.getValue())){
			BorrowingsAccountProperty borrowingsAccountProperty = borrAccountPropertyDAO.getAccountPropertyByProductIdPacsId(
					loc.getProduct().getId(), transaction.getPacs().getId());
			borrowingLocDetailsList = borrLOCDAO
					.getOrderedBorrowingLocListByAccountId(borrowingsAccountProperty.getAccount().getId());
			}
			else if (bankParam.getBorrowingTransactionMethod().getValue().equals(BorrowingTransactionMethod.OneToOneStright.getValue())){
			borrowingLocDetailsList = borrLOCDAO.getBlocsPerLoanLoc(
					loc.getId(), loc.getProduct().getId(), transaction.getPacs().getId());
			}
			else if (bankParam.getBorrowingTransactionMethod().getValue().equals(BorrowingTransactionMethod.OneToOneEarly.getValue())){
			BorrowingsAccountProperty borrowingsAccountProperty = borrAccountPropertyDAO
					.getAccountPropertyByProductId(loc.getProduct().getId());
			borrowingLocDetailsList = borrLOCDAO
					.getOrderedBorrowingLocListByAccountId(borrowingsAccountProperty.getAccount().getId());
			}
			CamelRequest camelRequest=retrieveCamelRequest(transaction.getPacs().getId(),loc.getProduct().getId(),currentTransactionData);
			logger.info("checking request object=====:"+transaction.getTransactionCode().getValue());
			CamelRequest request=createBorrowingRecoveryTransactions(transaction.getTransactionAmount().getMoney().getAmount(),  transaction.getTransactionCode().getValue(),borrowingLocDetailsList,camelRequest);
			logger.info("checking request object::"+request.getPenalInterestRecovered());
			if(reqestMap.containsKey(loc.getProduct().getId())){
				CamelRequest requestTemp=(CamelRequest)reqestMap.get(loc.getProduct().getId());
				BigDecimal principle=BigDecimal.ZERO;
				BigDecimal interest=BigDecimal.ZERO;
				BigDecimal penal=BigDecimal.ZERO;
				//Principle Calculation
				if(requestTemp.getPrincipalRecovred()!=null && request.getPrincipalRecovred()!=null)
				 principle=new BigDecimal(requestTemp.getPrincipalRecovred()).add(new BigDecimal(request.getPrincipalRecovred()));
				else if(request.getPrincipalRecovred()!=null && requestTemp.getPrincipalRecovred()==null)
					 principle=BigDecimal.ZERO.add(new BigDecimal(request.getPrincipalRecovred()));
				else if(request.getPrincipalRecovred()==null && requestTemp.getPrincipalRecovred()!=null)
					principle=new BigDecimal(requestTemp.getPrincipalRecovred()).add(BigDecimal.ZERO);
				else
					principle=principle;
				// interest recovery
				if(requestTemp.getInterestRecovered()!=null && request.getInterestRecovered()!=null)
				  interest=new BigDecimal(requestTemp.getInterestRecovered()).add(new BigDecimal(request.getInterestRecovered()));
				else if(request.getInterestRecovered()!=null)
					 interest=BigDecimal.ZERO.add(new BigDecimal(request.getInterestRecovered()));
				else if(requestTemp.getInterestRecovered()!=null && request.getInterestRecovered()==null)
					interest=new BigDecimal(requestTemp.getInterestRecovered()).add(BigDecimal.ZERO);
				else
					interest=interest;
				//Penal interest calculation
				if(requestTemp.getPenalInterestRecovered()!=null && request.getPenalInterestRecovered()!=null)
				  penal=new BigDecimal(requestTemp.getPenalInterestRecovered()).add(new BigDecimal(request.getPenalInterestRecovered()));
				else if(request.getPenalInterestRecovered()!=null)
					 penal=BigDecimal.ZERO.add(new BigDecimal(request.getPenalInterestRecovered()));
				else if(requestTemp.getPenalInterestRecovered()!=null &&request.getPenalInterestRecovered()==null)
					penal=new BigDecimal(requestTemp.getPenalInterestRecovered()).add(BigDecimal.ZERO);
				else
					penal=penal;
				requestTemp.setPrincipalRecovred(principle.toString());
				requestTemp.setInterestRecovered(interest.toString());
				requestTemp.setPenalInterestRecovered(penal.toString());
			}
			else
			reqestMap.put(loc.getProduct().getId(), request);
		}
	}
		
	}	
		
		
	
		Gson gson = new GsonBuilder().create();
		Iterator iterator=reqestMap.entrySet().iterator();
		//camelRequest.setBankCashInHandGL(bankParam.getCashGl());
		String amount="000000000000";
		ISOTransactionResponse response=new ISOTransactionResponse();
		for(Map.Entry<Integer,CamelRequest> obj:reqestMap.entrySet()){
			
		CamelRequest camelRequest=(CamelRequest)obj.getValue();
		logger.info("cash gl ==="+camelRequest.getPenalInterestRecovered());
		camelRequest.setBankCashInHandGL(bankParam.getCashGl());
		if(camelRequest.getInterestRecovered()==null || camelRequest.getInterestRecovered().equals("0"))
			camelRequest.setInterestRecovered(amount);
		else{
			String interest=camelRequest.getInterestRecovered();
		    interest=interest.substring(0,interest.indexOf("."))+interest.substring((interest.indexOf(".")+1),interest.length());
			camelRequest.setInterestRecovered(String.format("%0"+ (12 - interest.length() )+"d%s",0 ,interest));
		}
		if(camelRequest.getPenalInterestRecovered()==null || camelRequest.getPenalInterestRecovered().equals("0"))
			camelRequest.setPenalInterestRecovered(amount);
		else
		{
			String penalInterest=camelRequest.getPenalInterestRecovered();
			logger.info("penal interest::"+penalInterest);
			penalInterest=penalInterest.substring(0,penalInterest.indexOf("."))+penalInterest.substring((penalInterest.indexOf(".")+1),penalInterest.length());
			camelRequest.setPenalInterestRecovered(String.format("%0"+ (12 - penalInterest.length() )+"d%s",0 ,penalInterest));
		}
		if(camelRequest.getPrincipalRecovred()==null || camelRequest.getPrincipalRecovred().equals("0"))
			camelRequest.setPrincipalRecovred(amount);
		else{
		String principleRecovered=camelRequest.getPrincipalRecovred();
		//logger.info("principle amount==="+principleRecovered);
		//principleRecovered=principleRecovered.replaceAll(".", "");
		principleRecovered=principleRecovered.substring(0,principleRecovered.indexOf("."))+principleRecovered.substring((principleRecovered.indexOf(".")+1),principleRecovered.length());
		logger.info("checkinhg principle recovered:::"+principleRecovered);
		camelRequest.setPrincipalRecovred(String.format("%0"+ (12 - principleRecovered.length() )+"d%s",0 ,principleRecovered));
		}
		if((!camelRequest.getPrincipalRecovred().equals(amount))|| (!camelRequest.getPenalInterestRecovered().equals(amount)) || (!camelRequest.getInterestRecovered().equals(amount))){
		String camelResponse=RestClientUtil.getTransactionStatus(camelRequest,"recovery");
		response=gson.fromJson(camelResponse, ISOTransactionResponse.class);
		}
		if(response==null || response.getStatus()==null || !response.getStatus()){
			response=null;
			break;
		}
}
		return response;
}
}
