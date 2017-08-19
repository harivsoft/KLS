package com.vsoftcorp.kls.service.loan.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.camelintg.types.TransactionMode;
import com.vsoftcorp.camelintg.util.ISOResponseCodes;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.SeasonParameter;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.BorrowingsAccountProperty;
import com.vsoftcorp.kls.business.entity.account.Charges;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.LoanDisbursementEntry;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.AccountData;
import com.vsoftcorp.kls.data.BorrowingProductData;
import com.vsoftcorp.kls.data.CurrentTransactionData;
import com.vsoftcorp.kls.data.LoanDisbursementEntryData;
import com.vsoftcorp.kls.data.LoanDisbursementEntryList;
import com.vsoftcorp.kls.data.gateway.datahelpers.KLSResponse;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.IProductDAO;
import com.vsoftcorp.kls.dataaccess.ISeasonParameterDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IChargesDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanDisbursementEntryDAO;
import com.vsoftcorp.kls.dataaccess.subsidy.ISubsidyInterestAmountsDAO;
import com.vsoftcorp.kls.service.IBorrowingProductService;
import com.vsoftcorp.kls.service.account.IAccountPropertyService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.LoanDisbursementHelper;
import com.vsoftcorp.kls.service.loan.ILoanDisbursementEntryService;
import com.vsoftcorp.kls.service.transaction.ICurrentTransactionService;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.service.util.PropertiesUtil;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.kls.service.util.StringConstant;
import com.vsoftcorp.kls.valuetypes.PacsStatus;
import com.vsoftcorp.kls.valuetypes.charges.ChargeType;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1623
 * 
 */
public class LoanDisbursementEntryService implements ILoanDisbursementEntryService {
	private static final Logger logger = Logger.getLogger(LoanDisbursementEntryService.class);

	@Override
	public String saveLoanDisbursementEntry(LoanDisbursementEntryData passingData) {
		try {
			LoanDisbursementEntry pacsLoanPassing = LoanDisbursementHelper.getPacsLoanPassing(passingData);
			ILoanDisbursementEntryDAO dao = KLSDataAccessFactory.getLoanDisbursementEntryDAO();
			dao.saveLoanDisbursementEntry(pacsLoanPassing);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Loan Disbursement Entry Data Saved Successfully!";
	}
	
	// adding new method for saving bulk record
	@Override
	public String saveBulkLoanDisbursementEntry(LoanDisbursementEntryList passingData) {
		AccountData accData= new AccountData();
		KLSResponse klsResponse = null;
		String responseJSON = null;
		Gson gson = new GsonBuilder().create();
		try {
			for (LoanDisbursementEntryData list : passingData.getListLoanDisbursementEntryData()) {
			LoanDisbursementEntry pacsLoanPassing = LoanDisbursementHelper.getPacsLoanPassing(list);
			ILoanDisbursementEntryDAO dao = KLSDataAccessFactory.getLoanDisbursementEntryDAO();
			logger.info("instrument no::"+passingData.getInstrumentNumber());
			if(passingData.getInstrumentNumber()!=null){
				pacsLoanPassing.setInstrumentNum(passingData.getInstrumentNumber());
			}
			logger.info("additional information:::"+passingData.getAdditionalInformation());
			if(passingData.getAdditionalInformation()!=null){
				pacsLoanPassing.setAdditionalInfo(passingData.getAdditionalInformation());
			}
			logger.info("tot disbursement:::"+passingData.getTotDisburseAmt());
			if(passingData.getTotDisburseAmt()!=null){
				pacsLoanPassing.setTotalDisburseAmt(passingData.getTotDisburseAmt());
			}
			dao.saveLoanDisbursementEntry(pacsLoanPassing);
			
			//For Auto Passing
		/*	ICurrentTransactionService currentTransactionService = KLSServiceFactory.getCurrentTransactionService();
			CurrentTransactionData data = new CurrentTransactionData();
			IAccountPropertyService accPropService = KLSServiceFactory.getAccountPropertyService();
			accData = accPropService.getAccountInfoByCustId(pacsLoanPassing.getCustomerNumber());
			data.setVoucherNumber(null);
			data.setSavingsAccountNumber(accData.getAccountNo());
			data.setStandAloneStatus(true);
			pacsLoanPassing.getDisbursementAmount();
			
			Date businessDate = LoanServiceUtil.getBusinessDate();

			java.util.Date d = DateUtil.getFormattedDate(DateUtil.getDateString(businessDate));
			//java.util.Date d = new java.util.Date();//business date
			String date = "" + d.getDate();
			String month = "" + (d.getMonth() + 1);
			String year = "" + d.getYear();
			//String year = d.getYear();
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
			String disbAmt = pacsLoanPassing.getDisbursementAmount().getAmount().toString().trim().replace(".","");
			System.out.println("DisbAmt: "+  disbAmt);
			data.setTransactionAmount(disbAmt);
			
			data.setTransactionTowards("1");

			data.setChannelType("S");

			data.setTerminalId(null);

			String suvikasBalance = "0";//klsRequest.getSuvikasBalance();
			data.setCrDr("-1");
			data.setTransactionType("T");
			klsResponse = currentTransactionService.handleDebitTransaction(data, suvikasBalance);*/
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception caught in demoTransactions method. " + e.getMessage());
			klsResponse = new KLSResponse();
			klsResponse.setResponseStatus(ISOResponseCodes.SYSTEM_ERROR);
			klsResponse.setErrorMessage("Exception occurred while transaction :" + e.getMessage());
		} /*finally {
			switch (klsResponse.getResponseStatus()) {
			case ISOResponseCodes.SYSTEM_ERROR:
				klsResponse.setErrorMessage("System error");
				break;
			case ISOResponseCodes.DUPLICATE_TRANSACTION:
				klsResponse.setErrorMessage("Duplicate Transaction");
				break;
			case ISOResponseCodes.NO_SAVINGS_ACCOUNT:
				klsResponse.setErrorMessage("No savings account");
				break;
			case ISOResponseCodes.UNABLE_TO_PROCESS_INVALID_ACCOUNT:
				klsResponse.setErrorMessage("Unable to process invalid account");
				break;
			case ISOResponseCodes.INVALID_TRANSACTION_ACCOUNT_CLOSED:
				klsResponse.setErrorMessage("Invalid transaction account closed");
				break;
			case ISOResponseCodes.INVALID_ACCOUNT:
				klsResponse.setErrorMessage("Invalid account");
				break;
			case ISOResponseCodes.NO_CREDIT_ACCOUNT:
				klsResponse.setErrorMessage("No eligible line of credits found");
				break;
			case ISOResponseCodes.NO_FUNDS:
				klsResponse.setErrorMessage("No funds");
				break;
			case ISOResponseCodes.APPROVED:
				klsResponse.setErrorMessage("Transaction Successful");
				break;
			case ISOResponseCodes.TRANSACTION_NOT_ALLOWED:
				klsResponse.setErrorMessage("Transaction not allowed");
				break;
			case ISOResponseCodes.INVALID_BUSINESS_DATE:
				klsResponse.setErrorMessage("Invalid business date");
				break;
			}
			
			if("00".equals(klsResponse.getResponseStatus())) {
				responseJSON = "Transaction Successful"; 
			} else {
				responseJSON = "Transaction Failed";
			}
		}*/
		responseJSON = "Bulk Disbursement Entry data saved successfully";
		return responseJSON;
	}
	
	
	
	

	@Override
	public List<LoanDisbursementEntryData> getDisbursementEntriesList(long customerNumber, Integer status, String loanType) {

		logger.info("Start: Get the LoanDisbursementEntry list using customer id in getDisbursementEntriesList() method.");
		ILoanDisbursementEntryDAO dao = KLSDataAccessFactory.getLoanDisbursementEntryDAO();
		List<LoanDisbursementEntryData> dataList = new ArrayList<LoanDisbursementEntryData>();
		try {
			List<LoanDisbursementEntry> list = dao.getDisbursementEntriesList(customerNumber, status, loanType);

			if (list.size() > 0) {
				for (LoanDisbursementEntry loanDisbursementEntry : list) {
					dataList.add(LoanDisbursementHelper.getLoanDisbursementEntryData(loanDisbursementEntry));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving the LoanDisbursementEntry list based on cust id from the database");
			throw new KlsRuntimeException("Error while retriving the LoanDisbursementEntry list based on cust id from the database");
		}
		logger.info("Start: Get the LoanDisbursementEntry list using customer id in getDisbursementEntriesList() method.");
		return dataList;
	}

	@Override
	public void updateLoanDisbursementEntry(LoanDisbursementEntryData data) {

		logger.info("Start: Inside method updateLoanDisbursementEntry()");
		try {
			ILoanDisbursementEntryDAO dao = KLSDataAccessFactory.getLoanDisbursementEntryDAO();
			LoanDisbursementEntry master = LoanDisbursementHelper.getPacsLoanPassing(data);

			// master.setStatus(DisbursementStatus.getType(data.getStatus()));
			// master.setChequeDate(data.getChequeDate());
			dao.updateLoanDisbursementEntry(master);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception while updating LoanDisbursementEntry: " + e.getMessage());
			throw new KlsRuntimeException("Exception while updating LoanDisbursementEntry: ", e.getCause());
		}
		logger.info("End: Inside method updateLoanDisbursementEntry()");
	}

	@Override
	public List<LoanDisbursementEntryData> getDisbursementEntriesListbasedOnLocId(long customerNumber, Integer status, long locId, String loanType) {

		logger.info("Start: Get the LoanDisbursementEntry list using customer id in getDisbursementEntriesList() method.");
		ILoanDisbursementEntryDAO dao = KLSDataAccessFactory.getLoanDisbursementEntryDAO();
		List<LoanDisbursementEntryData> dataList = new ArrayList<LoanDisbursementEntryData>();
		try {
			List<LoanDisbursementEntry> list = dao.getDisbursementEntriesListByLocId(customerNumber, status, locId, loanType);

			if (list.size() > 0) {
				for (LoanDisbursementEntry loanDisbursementEntry : list) {
					dataList.add(LoanDisbursementHelper.getLoanDisbursementEntryData(loanDisbursementEntry));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving the LoanDisbursementEntry list based on cust id from the database");
			throw new KlsRuntimeException("Error while retriving the LoanDisbursementEntry list based on cust id from the database");
		}
		logger.info("Start: Get the LoanDisbursementEntry list using customer id in getDisbursementEntriesList() method.");
		return dataList;
	}

	@Override
	public List<Map> getDisbursedLOCIdsForPassing(Long customerId, String loanType) {

		logger.info("Start: Get the loan loc list using customer id in getDisbursedLineOfCreditList() method.");
		ILoanDisbursementEntryDAO dao = KLSDataAccessFactory.getLoanDisbursementEntryDAO();
		List<Map> listMap = new ArrayList<>();
		try {
			List<Long> list = dao.getDisbursementLocIdsEntriesList(customerId, 1, loanType);

			Map<String, String> map = null;
			for (Long ids : list) {
				map = new HashMap<>();
				if (ids != null) {
					map.put("id", ids.toString());
					listMap.add(map);
				}
				logger.info("listMap : " + listMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving the Disbursed LOC list based on cust id from the database");
			throw new KlsRuntimeException("Error while retriving the Disbursed LOC list based on cust id from the database");
		}
		logger.info("Start: Get the Disbursed LOC list using customer id in getDisbursedLineOfCreditList() method.");
		return listMap;
	}

	// @Override
	// public Map<String, String> getLoanInfo(Integer pacsId, Long
	// customerNumber, String loanType) {
	//
	// logger.info("Start: Get the loan loc list using customer id in getLoanInfo() method.");
	// ILoanDisbursementEntryDAO dao =
	// KLSDataAccessFactory.getLoanDisbursementEntryDAO();
	//
	// Map<String, String> infoMap = new HashMap<String, String>();
	// try {
	// List<Map> list = dao.getLineOfCredit(pacsId, customerNumber, loanType);
	//
	// if (list != null && list.isEmpty() == false) {
	// for (Map mp : list) {
	//
	// AccountingMoney currentBalance = (AccountingMoney)
	// mp.get("currentBalance");
	// Money operatingCashLimit = (Money) mp.get("operatingCashLimit");
	// BigDecimal currBalBigDecmal = currentBalance.getMoney().getAmount();
	// BigDecimal drawableAmount =
	// operatingCashLimit.getAmount().subtract(currBalBigDecmal);
	// infoMap.put("currentBalance", ((AccountingMoney)
	// mp.get("currentBalance")).getMoney().getAmount().setScale(2).toString());
	// infoMap.put("sanctionedAmount", ((Money)
	// mp.get("sanctionedAmount")).getAmount().setScale(2).toString());
	// infoMap.put("interestReceivable", ((BigDecimal)
	// mp.get("interestReceivable")).abs().setScale(2).toString());
	// infoMap.put("penalInterestReceivable", ((BigDecimal)
	// mp.get("penalInterestReceivable")).abs().setScale(2).toString());
	// infoMap.put("chargesReceivable", ((BigDecimal)
	// mp.get("chargesReceivable")).abs().setScale(2).toString());
	// infoMap.put("drawableAmount", drawableAmount.setScale(2).toString());
	// logger.info("sizeOfInfoMap : " + infoMap.size());
	// }
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// logger.error("Error while retriving the Disbursed LOC list based on cust id from the database");
	// throw new
	// KlsRuntimeException("Error while retriving the Disbursed LOC list based on cust id from the database");
	// }
	// logger.info("Start: Get the Disbursed LOC list using customer id in getLoanInfo() method.");
	// return infoMap;
	// }

	@Override
	public Map<String, Object> getLoanInfo(Long accountId, String loanType, String transType) {

		logger.info("Start: Get the loan loc list using customer id in getLoanInfo() method.");
		Map<String, Object> infoMap = new HashMap<String, Object>();

		ILineOfCreditDAO dao = KLSDataAccessFactory.getLineOfCreditDAO();
		List lineOfCreditAccountsList = null;
        List lineOfCreditList=new ArrayList();
        String msg="";
		try {
			String query = "";
			logger.info(" query is : " + query);
			/******* Fetch locs ***************/

			ICurrentTransactionService service = KLSServiceFactory.getCurrentTransactionService();
			CurrentTransaction master = new CurrentTransaction();
			master.setBusinessDate(LoanServiceUtil.getBusinessDate());

			if (transType.equalsIgnoreCase("D")) {
				query = PropertiesUtil.getProperty(StringConstant.LINE_Of_CREDIT_DEBIT_QUERY);
				lineOfCreditAccountsList = dao.getLineOfCreditAccountsList(accountId, false, query);
				logger.info("checking loc list==="+lineOfCreditAccountsList);
				/*for(int i=0;i<lineOfCreditAccountsList.size();i++){
					LineOfCredit loc=(LineOfCredit) lineOfCreditAccountsList.get(i);
					for(int j=i+1;j<lineOfCreditAccountsList.size();j++){
						LineOfCredit loc1=(LineOfCredit) lineOfCreditAccountsList.get(i);
						if(loc.getSeason().getDrawalStartDate().isBefore(loc1.getSeason().getDrawalStartDate()))
							lineOfCreditList.add(lineOfCreditAccountsList.get(i));
						else
							lineOfCreditList.add(lineOfCreditAccountsList.get(j));
					}
				}
				if(lineOfCreditList.size()!=0)
				lineOfCreditAccountsList=lineOfCreditList;
				logger.info("line of credit=="+lineOfCreditList);*/
				Collections.sort(lineOfCreditAccountsList,new Comparator<LineOfCredit>(){
					@Override
					public int compare(LineOfCredit loc,LineOfCredit loc2){
						return loc.getSeason().getDrawalStartDate().compareTo(loc.getSeason().getDrawalStartDate());
					}
				});
				msg=service.checkTransactionDateValid(master, lineOfCreditAccountsList);

			} else if (transType.equalsIgnoreCase("K")) {
				query = PropertiesUtil.getProperty(StringConstant.LINE_Of_CREDIT_KIND_QUERY);

				lineOfCreditAccountsList = dao.getLineOfCreditAccountsList(accountId, false, query);
				service.checkTransactionDateValidity(master, lineOfCreditAccountsList);

			} else if (transType.equalsIgnoreCase("C")) {
				query = PropertiesUtil.getProperty(StringConstant.LINE_Of_CREDIT_CREDIT_QUERY);
				lineOfCreditAccountsList = dao.getLineOfCreditAccountsList(accountId, false, query);
			}

			/******* Iterate and aggregate ***************/
			AccountingMoney currentBalance = AccountingMoney.ZERO;
			Money sanctionedAmount = Money.ZERO;
			BigDecimal interestReceivable = BigDecimal.ZERO;
			BigDecimal subsidyInterestReceivable = BigDecimal.ZERO;
			BigDecimal penalInterestReceivable = BigDecimal.ZERO;
			BigDecimal chargesReceivable = BigDecimal.ZERO;
			BigDecimal drawableAmount = BigDecimal.ZERO;
			BigDecimal currBalBigDecmal = BigDecimal.ZERO;
			BigDecimal insuranceAmount = BigDecimal.ZERO;
			BigDecimal shareAmount = BigDecimal.ZERO;
			Money operatingCashLimit = Money.ZERO;
			AccountingMoney kindBalance = AccountingMoney.ZERO;
			BigDecimal subsidyReceivable = BigDecimal.ZERO;
			Money kindLimit = Money.ZERO;
			BigDecimal kindDrawableAmount = BigDecimal.ZERO;
			List<String> seasonDueDatesList = new ArrayList<String>();
			List<Long> locList = new ArrayList<Long>();
			Set<Integer> productIdSet = new HashSet<Integer>();
			List<String> seasonDrawalStartDate = new ArrayList<String>(); 
			List<LineOfCredit> firstDrawlList = new ArrayList<LineOfCredit>();
			ISeasonParameterDAO seasonParamDao = KLSDataAccessFactory.getSeasonParameterDAO();
			String businessDate = DateUtil.getDateString(LoanServiceUtil.getBusinessDate());
			String startDate="";
			String interestCalDate="";
			BigDecimal interestReceivableTemp = BigDecimal.ZERO;
			// compute also kind balance
			if (lineOfCreditAccountsList != null) {
				logger.info("*************" +lineOfCreditAccountsList.size());
				for (int i = 0; i < lineOfCreditAccountsList.size(); i++) {
					interestReceivableTemp = BigDecimal.ZERO;
					LineOfCredit loc = (LineOfCredit) lineOfCreditAccountsList.get(i);
					/*
					 * calculating insurance and share amount for first drawal
					 */
					SeasonParameter seasonParameter = seasonParamDao.getSeasonParameter(loc.getSeason().getId(), loc.getCrop().getId(),loc.getAccount().getAccountProperty().getPacs().getId());
					startDate = DateUtil.getDateString(loc.getSeason().getDrawalStartDate());
					String insuranceDate = null;
					if(loanType.equals("C") || loanType.equalsIgnoreCase("ST"))
					if(loc.getLoanExpiryDate() != null){
						interestCalDate = DateUtil.getDateBySubtractingNoOfDays(DateUtil.getDateString(loc.getLoanExpiryDate()), loc.getSeason().getRecoveryPeriod());
						logger.info("interest calculation date is:::"+interestCalDate);
					}
					if(loc.getIsFirstDrawal()==0){
						firstDrawlList.add(loc);
						if(seasonParameter != null){
							insuranceDate = DateUtil.getDateByAddingNoOfDays(startDate, seasonParameter.getInsurancePeriod());
							IChargesDAO chargesDao = KLSDataAccessFactory.getChargesDAO();
							List<Charges> chargesList = chargesDao.getChargesByAccountIdAndLoc(loc.getId(), accountId, loc.getSeason().getId());
							if(chargesList != null && !chargesList.isEmpty()){
								for(Charges charge : chargesList){
									if(charge.getChargeType().compareTo(ChargeType.INSURANCE) == 0 && DateUtil.compareDate(businessDate,insuranceDate)<=0){
										insuranceAmount = insuranceAmount.add(charge.getChargeAmount().getAmount());
									}
									/*else if(charge.getChargeType().compareTo(ChargeType.SHARE)==0){
										shareAmount=shareAmount.add(charge.getChargeAmount().getAmount());
									}*/
								}
							}
						}
					}
					seasonDueDatesList.add(DateUtil.getDateString(loc.getSeason().getOverdueDate()));
					seasonDrawalStartDate.add(DateUtil.getDateString(loc.getSeason().getDrawalStartDate()));
					locList.add(loc.getId());
					if(loc.getCurrentBalance().isDebit())
					currentBalance = currentBalance.add(loc.getCurrentBalance());
					sanctionedAmount = sanctionedAmount.add(loc.getSanctionedAmount());
					
					
					//if(loc.getOverdueInterest().compareTo(BigDecimal.ZERO) == 0){
						
						/*ILoanInterestCalculationDAO iDao = KLSDataAccessFactory.getLoanInterestCalculationDAO();
						SlabwiseInterestRate interestRate = iDao.getRateOfInterest(loc.getInterestCategory().getId(), LoanServiceUtil.getBusinessDate(),
								loc.getSanctionedAmount());*/
						
						
						
					//}
					ISubsidyInterestAmountsDAO isubDao = KLSDataAccessFactory.getSubsidyInterestAmountsDAO();
					if(loc.getLoanExpiryDate() != null && loc.getLoanExpiryDate().isAfter(LoanServiceUtil.getBusinessDate())){
						
						
						List<Map> schemeGlList = isubDao.getSubsidySchemeAndGlMap(loc.getId(),"SS");
						
						
						for (int a = 0; a < schemeGlList.size(); a++) {
								subsidyReceivable = subsidyReceivable.add((BigDecimal)schemeGlList.get(a).get("subsidyReceivable"));
						}
						
						if(loc.getProduct().getAsAndWhenImplemented()){
							for (int a = 0; a < schemeGlList.size(); a++) {
								subsidyReceivable = subsidyReceivable.add((BigDecimal)schemeGlList.get(a).get("subsidyAccrued"));
							}	
						}
					
						logger.info("subsidyReceivable : "+subsidyReceivable);
						
						
						
					}		
					/*else{
						BigDecimal subventionAmount = isubDao.getSubventionAmount(loc.getId());
						logger.info("subventionAmount : "+subventionAmount);
						if(subventionAmount != null){
							ILineOfCreditDAO lDao = KLSDataAccessFactory.getLineOfCreditDAO();
							loc.setInterestReceivable(loc.getInterestReceivable().setScale(0, RoundingMode.HALF_UP).add(subventionAmount));
							lDao.updateLineOfCredit(loc);
						}
					}*/
					
					interestReceivableTemp = interestReceivableTemp.add(loc.getInterestReceivable().setScale(0, RoundingMode.HALF_UP));
					logger.info("interest receivable==="+interestReceivableTemp);
					penalInterestReceivable = penalInterestReceivable.add(loc.getPenalInterestReceivable());
					chargesReceivable = chargesReceivable.add(loc.getChargesReceivable());
					if(loc.getCurrentBalance().isDebit())
						currBalBigDecmal = loc.getCurrentBalance().getMoney().getAmount();
						else
						currBalBigDecmal = BigDecimal.ZERO;
					logger.info("currBalBigDecmal::::" +currBalBigDecmal);
					operatingCashLimit = loc.getOperatingCashLimit();
					logger.info("operatingCashLimit::::" +operatingCashLimit);
					drawableAmount = drawableAmount.add(operatingCashLimit.getAmount().subtract(currBalBigDecmal.abs()));
					logger.info("drawableAmount::::::" +drawableAmount);
					if (loc.getLoanType().equals("C")) {
												
						kindBalance = kindBalance.add(loc.getKindBalance());
						logger.info("kindBalance :::::" +kindBalance);
						kindLimit = kindLimit.add(loc.getKindLimit());
						logger.info("kindLimit:::::" +kindLimit);
					
						
						Integer productId = loc.getProduct().getId();
						productIdSet.add(productId);
					
					}
					if (loc.getProduct().getAsAndWhenImplemented()) {
						BigDecimal intrestAccrued = loc.getInterestAccrued().setScale(0, RoundingMode.HALF_UP);
						logger.info("interest accrued: " + intrestAccrued);
						BigDecimal overdueInterest = loc.getOverdueInterest().setScale(0, RoundingMode.HALF_UP);
						logger.info("overdue interest:" + overdueInterest);
						interestReceivableTemp = interestReceivableTemp.add(interestReceivable.add(intrestAccrued));
						penalInterestReceivable = penalInterestReceivable.add(overdueInterest);
					}
					infoMap.put("lineOfCreditId", loc.getId());
					
					if(loanType.equals("C") || loanType.equalsIgnoreCase("ST")){
						//logger.info("checking conditions::"+interestCalDate+"businessDate"+businessDate+"condition"+DateUtil.compareDate(interestCalDate,businessDate));
						if(interestCalDate!=null && interestCalDate!="" && DateUtil.compareDate(interestCalDate,businessDate)<=0){
							logger.info("inside if part");
						interestReceivable = interestReceivable.add(interestReceivableTemp);
						}
						else{
							logger.info("inside else part");
							interestReceivable = interestReceivable.add(BigDecimal.ZERO);
						}
					}
					
				}
				kindDrawableAmount = kindDrawableAmount.add(kindLimit.getAmount().subtract(kindBalance.getMoney().getAmount()).abs());
				logger.info("kindDrawableAmount:::::" +kindDrawableAmount);
				
				if( firstDrawlList != null && !firstDrawlList.isEmpty())
					shareAmount=LoanServiceUtil.getShareAmountWhileDisbursement(firstDrawlList).get(0l).getAmount();
				infoMap.put("subsidyInterestReceivable", subsidyReceivable.abs().setScale(0, RoundingMode.HALF_UP).setScale(2));
			
			}
			infoMap.put("seasonDueDates", seasonDueDatesList);
			infoMap.put("seasonDrawalStartDates", seasonDrawalStartDate);
			
			infoMap.put("locList", locList);
			//Checking drawal is happening whether one or two products
			if (loanType.equals("C")) {
				boolean flag = true;
				BorrowingsAccountProperty bAccProperty = new BorrowingsAccountProperty();
				//if (!productIdSet.isEmpty() && productIdSet.size() > 1)
				//if (!productIdSet.isEmpty())
					//flag = false;
				//else {
				String ccb="";
				IBorrowingProductService borrowservice = KLSServiceFactory.getBorrowingProductService();
					Account account = KLSDataAccessFactory.getAccountDAO().getAccountById(accountId, false);
					BorrowingProductData data=new BorrowingProductData();
					for (Integer productId : productIdSet) {
						IProductDAO prdDao = KLSDataAccessFactory.getProductMasterDAO();
						Product prd=prdDao.getProductById(productId);
						if(prd.getBorrowingRequired().equals("Y")){
						if(prd.getBorrowingProductId()!=null){
						Integer borrowingPrdId=prd.getBorrowingProductId();
						 data = borrowservice.getProductById(borrowingPrdId);
						 ccb=data.getGlCode();
						}
						else{
							bAccProperty = KLSDataAccessFactory.getBorrowingsAccountPropertyDAO().getAccountPropertyByProductIdPacsId(productId,
										account.getAccountProperty().getPacs().getId());
								ccb=bAccProperty.getCcbAccountNumber();
								 }
						}
					}
				//}
				infoMap.put("drawalEntryEligibility", flag);
				infoMap.put("ccbAccountNumber", ccb);
			}
			
				
			drawableAmount=drawableAmount.subtract(insuranceAmount).subtract(shareAmount);
			infoMap.put("currentBalance", currentBalance.getMoney().getAmount().setScale(2).toString());
			infoMap.put("sanctionedAmount", sanctionedAmount.getAmount().setScale(2).toString());
			infoMap.put("interestReceivable", interestReceivable.abs().setScale(2).toString());
			infoMap.put("penalInterestReceivable", penalInterestReceivable.abs().setScale(2).toString());
			infoMap.put("chargesReceivable", chargesReceivable.abs().setScale(2).toString());
			infoMap.put("drawableAmount", drawableAmount.setScale(2).toString());
			infoMap.put("kindBalance", kindBalance.getMoney().getAmount().setScale(2).toString());
			infoMap.put("kindLimit", kindLimit.getAmount().setScale(2).toString());
			infoMap.put("kindDrawableAmount", kindDrawableAmount.setScale(2).toString());
			infoMap.put("insuranceAmount",insuranceAmount.setScale(2));
			infoMap.put("shareAmount",shareAmount.setScale(2));
			if(sanctionedAmount.isZero())
				infoMap.put("message", msg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving the Disbursed LOC list based on cust id from the database");
			throw new KlsRuntimeException("Error while retriving the Disbursed LOC list based on cust id from the database");
		}
		logger.info("Start: Get the Disbursed LOC list using customer id in getLoanInfo() method.");
		return infoMap;
	}
	@Override
	public LoanDisbursementEntryData getDisbursementEntriesbasedOnLocId(long customerNumber, Integer status, long locId, String loanType,String transactionDate) {

		logger.info("Start: Get the LoanDisbursementEntry list using customer id in getDisbursementEntriesList() method.");
		ILoanDisbursementEntryDAO dao = KLSDataAccessFactory.getLoanDisbursementEntryDAO();
		LoanDisbursementEntryData data = new LoanDisbursementEntryData();
		try {
			LoanDisbursementEntry list = dao.getDisbursementEntries(customerNumber, status, locId, loanType,transactionDate);

			//if (list.size() > 0) {
			//	for (LoanDisbursementEntry loanDisbursementEntry : list) {
					data=LoanDisbursementHelper.getLoanDisbursementEntryData(list);
			//	}
			//}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving the LoanDisbursementEntry list based on cust id from the database");
			throw new KlsRuntimeException("Error while retriving the LoanDisbursementEntry list based on cust id from the database");
		}
		logger.info("Start: Get the LoanDisbursementEntry list using customer id in getDisbursementEntriesList() method.");
		return data;
	}

@Override
public String disbursementPassing(LoanDisbursementEntryList passingData){
	AccountData accData= new AccountData();
	KLSResponse klsResponse = null;
	String responseJSON = null;
	Gson gson = new GsonBuilder().create();
	IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
	int status=0;
	try{
		
	for(LoanDisbursementEntryData list : passingData.getListLoanDisbursementEntryData()){
		LoanDisbursementEntry pacsLoanPassing = LoanDisbursementHelper.getPacsLoanPassing(list);
	ICurrentTransactionService currentTransactionService = KLSServiceFactory.getCurrentTransactionService();
	CurrentTransactionData data = new CurrentTransactionData();
	IAccountPropertyService accPropService = KLSServiceFactory.getAccountPropertyService();
	accData = accPropService.getAccountInfoByCustId(pacsLoanPassing.getCustomerNumber());
	data.setVoucherNumber(null);
	logger.info("checking dd"+list.getModeOfDisbursement());
	if(list.getCardStatus().equals("I")){
		data.setSavingsAccountNumber(accData.getSavingBankAccNo());
	data.setStandAloneStatus(false);
	if(list.getModeOfDisbursement().equals("C")){
		data.setSavingsAccountNumber(accData.getAccountNo());
		data.setModeOfPayment("C");
	}
	else if(list.getModeOfDisbursement().equals("D")){
		logger.info("checking dd");
		data.setSavingsAccountNumber(accData.getAccountNo());
		data.setModeOfPayment("D");
	}
		else{
			data.setSavingsAccountNumber(accData.getSavingBankAccNo());
			data.setModeOfPayment("T");
		}
	}
	else{
		if(list.getModeOfDisbursement().equals("C")){
		 data.setModeOfPayment("C");
		}
		else if(list.getModeOfDisbursement().equals("D")){
			data.setModeOfPayment("D");
		}
			else{
				data.setModeOfPayment("T");
			}
		data.setSavingsAccountNumber(accData.getAccountNo());
		data.setStandAloneStatus(true);
	}
	pacsLoanPassing.getDisbursementAmount();
	
	Date businessDate = LoanServiceUtil.getBusinessDate();

	java.util.Date d = DateUtil.getFormattedDate(DateUtil.getDateString(businessDate));
	//java.util.Date d = new java.util.Date();//business date
	String date = "" + d.getDate();
	String month = "" + (d.getMonth() + 1);
	String year = "" + d.getYear();
	//String year = d.getYear();
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
	String disbAmt = pacsLoanPassing.getDisbursementAmount().getAmount().toString().trim().replace(".","");
	System.out.println("DisbAmt: "+  disbAmt);
	data.setTransactionAmount(disbAmt);
	
	data.setTransactionTowards("1");

	data.setChannelType("S");

	data.setTerminalId(null);
    
	data.setBulkDisbursement("bd");//bd-bulk disbursement
	
	String suvikasBalance = "0";//klsRequest.getSuvikasBalance();
	data.setCrDr("-1");
	data.setTransactionType("T");
	logger.info("status==="+list.getStatus());
	if(list.getStatus().toString().equals("4")){
		logger.info("checking transactions");
	klsResponse = currentTransactionService.handleDebitTransaction(data, suvikasBalance);
	if("00".equals(klsResponse.getResponseStatus())){
		list.setVoucherNumber(klsResponse.getVoucherNumber());
		String passingDate="";
		Pacs pacs=pacsDao.getPacs(list.getPacsId());
		logger.info("pacs type::"+pacs.getPacsStatus());
		if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
			passingDate=RestClientUtil.getPacsBusinessDate(list.getPacsId(),pacs.getBranch().getId());
			//businessDate="2017-05-01";
			list.setBusinessDate(passingDate);
		}
		else
		list.setBusinessDate(DateUtil.getDateString(businessDate));
		updateLoanDisbursementEntry(list);
	}
		}
		if(list.getStatus().toString().equals("3")){
			logger.info("inside reject status");
		/*  ILoanDisbursementEntryService service = KLSServiceFactory.getLoanDisbursementEntryService();
		  service.updateLoanDisbursementEntry(list);*/
			//updateBulkDisbursementEntry(list);
			updateLoanDisbursementEntry(list);
			status=1;
	}
	}
	
} catch (Exception e) {
	e.printStackTrace();
	logger.error("Exception caught in demoTransactions method. " + e.getMessage());
	klsResponse = new KLSResponse();
	klsResponse.setResponseStatus(ISOResponseCodes.SYSTEM_ERROR);
	klsResponse.setErrorMessage("Exception occurred while transaction :" + e.getMessage());
}
finally {
	if(klsResponse!=null){
	switch (klsResponse.getResponseStatus()) {
	case ISOResponseCodes.SYSTEM_ERROR:
		klsResponse.setErrorMessage("System error");
		break;
	case ISOResponseCodes.DUPLICATE_TRANSACTION:
		klsResponse.setErrorMessage("Duplicate Transaction");
		break;
	case ISOResponseCodes.NO_SAVINGS_ACCOUNT:
		klsResponse.setErrorMessage("No savings account");
		break;
	case ISOResponseCodes.UNABLE_TO_PROCESS_INVALID_ACCOUNT:
		klsResponse.setErrorMessage("Unable to process invalid account");
		break;
	case ISOResponseCodes.INVALID_TRANSACTION_ACCOUNT_CLOSED:
		klsResponse.setErrorMessage("Invalid transaction account closed");
		break;
	case ISOResponseCodes.INVALID_ACCOUNT:
		klsResponse.setErrorMessage("Invalid account");
		break;
	case ISOResponseCodes.NO_CREDIT_ACCOUNT:
		klsResponse.setErrorMessage("No eligible line of credits found");
		break;
	case ISOResponseCodes.NO_FUNDS:
		klsResponse.setErrorMessage("No funds");
		break;
	case ISOResponseCodes.APPROVED:
		klsResponse.setErrorMessage("Transaction Successful");
		break;
	case ISOResponseCodes.TRANSACTION_NOT_ALLOWED:
		klsResponse.setErrorMessage("Transaction not allowed");
		break;
	case ISOResponseCodes.INVALID_BUSINESS_DATE:
		klsResponse.setErrorMessage("Invalid business date");
		break;
	}
}
	
	if(klsResponse!=null && "00".equals(klsResponse.getResponseStatus())) {
		responseJSON = "Transaction Successful"; 
	} else {
		if(status==1)
		responseJSON = "Rejected Transaction Successfully";
		else
		responseJSON = "Transaction Failed";
	}
}
	return responseJSON;
	
}

@Override
public void updateBulkDisbursementEntry(LoanDisbursementEntryData data) {

	logger.info("Start: Inside method updateLoanDisbursementEntry()");
	try {
		ILoanDisbursementEntryDAO dao = KLSDataAccessFactory.getLoanDisbursementEntryDAO();
		//LoanDisbursementEntry master = LoanDisbursementHelper.getPacsLoanPassing(data);

		// master.setStatus(DisbursementStatus.getType(data.getStatus()));
		// master.setChequeDate(data.getChequeDate());
		dao.updateBulkDisbursementEntry(data.getPacsId(),data.getCustomerNumber(),data.getLoanType());
	} catch (Exception e) {
		e.printStackTrace();
		logger.error("Exception while updating LoanDisbursementEntry: " + e.getMessage());
		throw new KlsRuntimeException("Exception while updating LoanDisbursementEntry: ", e.getCause());
	}
	logger.info("End: Inside method updateLoanDisbursementEntry()");
}

@Override
public String removeDisbursementEntry(String id){
	logger.info("Start: Inside method removeLoanDisbursementEntry()");
	String returnMsg="";
	try{
		ILoanDisbursementEntryDAO dao = KLSDataAccessFactory.getLoanDisbursementEntryDAO();
		returnMsg=dao.removeBulkDisbursementEntry(id);
	}catch(Exception e){
		e.printStackTrace();
	}
	return returnMsg;
	
}

}
