package com.vsoftcorp.kls.rest.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vsoftcorp.camelintg.TcpMsgHandler;
import com.vsoftcorp.camelintg.service.SuvikasService;
import com.vsoftcorp.camelintg.util.JPOSUtil;
import com.vsoftcorp.kls.business.entities.BankParameter;
import com.vsoftcorp.kls.business.entity.account.AccountProperty;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.BankParameterData;
import com.vsoftcorp.kls.data.BulkCustomerData;
import com.vsoftcorp.kls.data.BulkLoanRecoveryResponse;
import com.vsoftcorp.kls.data.BulkRecoveryData;
import com.vsoftcorp.kls.data.BulkSTRecoveryResponse;
import com.vsoftcorp.kls.data.ChequeTransactionData;
import com.vsoftcorp.kls.data.CurrentTransactionData;
import com.vsoftcorp.kls.data.CustXLData;
import com.vsoftcorp.kls.data.MiniStatementData;
import com.vsoftcorp.kls.data.SmsData;
import com.vsoftcorp.kls.data.TransactionHistoryData;
import com.vsoftcorp.kls.data.VoucherData;
import com.vsoftcorp.kls.data.gateway.datahelpers.KLSRequest;
import com.vsoftcorp.kls.data.gateway.datahelpers.KLSResponse;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.data.gateway.datahelpers.STLoanRecoveryData;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.impl.BankParameterDAO;
import com.vsoftcorp.kls.dataaccess.loan.IAccountPropertyDAO;
import com.vsoftcorp.kls.service.account.IAccountPropertyService;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.BankParameterHelper;
import com.vsoftcorp.kls.service.loan.ILoanRecoveryService;
import com.vsoftcorp.kls.service.transaction.ICurrentTransactionService;
import com.vsoftcorp.kls.service.transaction.ISTLoanRecoveryTransactionService;
import com.vsoftcorp.kls.service.transaction.ITransactionHistoryService;
import com.vsoftcorp.kls.service.util.FileUtil;
import com.vsoftcorp.kls.service.util.ISODataElements;
import com.vsoftcorp.kls.service.util.ISOResponseCodes;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.service.util.PropertiesUtil;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.kls.service.util.StringConstant;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;
import com.vsoftcorp.time.Date;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

@Path("/transaction")
public class KLSTransactionRestService {

	private static final Logger logger = Logger.getLogger(KLSTransactionRestService.class);

	public KLSTransactionRestService() {

	}

	private static final KLSTransactionRestService INSTANCE = new KLSTransactionRestService();

	public static KLSTransactionRestService getInstance() {
		return INSTANCE;
	}

	@POST
	@Path("/debit")
	@Consumes("application/json")
	@Produces("application/json")
	public String debitTransaction(String jsonKlsRequest) {

		logger.info("Start : Calling CurrentTransaction master service in debitTransaction() method." + jsonKlsRequest);
		String responseJSON = null;
		KLSResponse klsResponse = null;
		Gson gson = new GsonBuilder().create();
		BankParameter bankParameter = KLSDataAccessFactory.getBankParameter();
		/*EntityManager em = EntityManagerUtil.getEntityManager();
		 if(!"O".equalsIgnoreCase(bankParameter.getCbsIntegrationMethod().getValue()) && "V".equalsIgnoreCase(bankParameter.getOther_cbs())){
			 em.getTransaction().begin();
		 }*/
		try {
			ICurrentTransactionService currentTransactionService = KLSServiceFactory.getCurrentTransactionService();
			CurrentTransactionData data = new CurrentTransactionData();
			KLSRequest klsRequest = gson.fromJson(jsonKlsRequest, KLSRequest.class);
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
			data.setBusinessDate(DateUtil.getDateString(bankParameter.getBusinessDate()));
			if (TransactionType.Withdrawal.getValue().equals(klsRequest.getTransactionType())) {
				data.setCrDr("-1");
				//Change the transaction Type from W to T for Normal Mode Withdrawl.
				data.setTransactionType("T");
			} /*else if (TransactionType.Deposit.getValue().equals(klsRequest.getTransactionType())) {
				data.setCrDr("1");
				data.setTransactionType("D");
			}*/
			data.setTransactionAmount(isoDataElements.get(ISODataElements.TRANSACTION_AMOUNT));
			data.setTransactionTowards("1");
			data.setChannelType(klsRequest.getChannelType());
			data.setRemarks(isoDataElements.get(ISODataElements.CARD_ACCEPTOR_NAME_LOCATION));
			data.setTerminalId(isoDataElements.get(ISODataElements.CARD_ACCEPTOR_TERMINAL_IDENTIFICATION));
			String suvikasBalance = klsRequest.getSuvikasBalance();
			data.setStandAloneStatus(false);
			klsResponse = currentTransactionService.handleDebitTransaction(data, suvikasBalance);
			
			/*//For virmati CBS integration
			if(!"O".equalsIgnoreCase(bankParameter.getCbsIntegrationMethod().getValue()) && "V".equalsIgnoreCase(bankParameter.getOther_cbs())){
				
				IPostVirmatiCBSTransactions postVirmatiCBSTransactions = KLSServiceFactory.getPostVirmatiCBSTransaction();
				boolean status=postVirmatiCBSTransactions.postVirmatiCBSDebitTransactions(klsResponse, klsRequest.getAccountNumber(),false,transactionMode);
				if(status){
					em.getTransaction().commit();
				}
				
			 }*/
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception thrown in debit transaction method.");
			klsResponse = new KLSResponse();
			klsResponse.setResponseStatus(ISOResponseCodes.SYSTEM_ERROR);
			klsResponse.setErrorMessage("Exception thrown in debit transaction");
		} finally {
			EntityManagerUtil.closeSession();
		}
		responseJSON = gson.toJson(klsResponse);
		logger.info("End : Calling CurrentTransaction master service in debitTransaction() method.");
		return responseJSON;
	}

	@POST
	@Path("/credit")
	@Consumes("application/json")
	@Produces("application/json")
	public String creditTransaction(String jsonKlsRequest) {

		logger.info("Start : Calling CurrentTransaction service in creditTransaction() method.");
		String responseJSON = null;
		KLSResponse klsResponse = null;
		Gson gson = new GsonBuilder().create();
		try {
			BankParameter bankParameter = KLSDataAccessFactory.getBankParameter();
			/*EntityManager em = EntityManagerUtil.getEntityManager();
			 if(!"O".equalsIgnoreCase(bankParameter.getCbsIntegrationMethod().getValue()) && "V".equalsIgnoreCase(bankParameter.getOther_cbs())){
				 em.getTransaction().begin();
			 }*/
			ICurrentTransactionService currentTransactionService = KLSServiceFactory.getCurrentTransactionService();
			CurrentTransactionData data = new CurrentTransactionData();
			KLSRequest klsRequest = gson.fromJson(jsonKlsRequest, KLSRequest.class);
			Map<String, String> isoDataElements = klsRequest.getIsoDataElements();
			String transactionMode=klsRequest.getTransactionMode();
			logger.info("Printing all iso nodes  : ");
			for (String key : isoDataElements.keySet()) {
				logger.info(" iso data elements key is : " + key);
				logger.info(" iso data elements value is : " + isoDataElements.get(key));
			}
			data.setVoucherNumber(null);
			data.setSavingsAccountNumber(isoDataElements.get(ISODataElements.ACCOUNT_IDENTIFICATION_1));
			data.setBusinessDate(DateUtil.getDateString(bankParameter.getBusinessDate()));
		/*	if (TransactionType.Withdrawal.getValue().equals(klsRequest.getTransactionType())) {
				data.setCrDr("-1");
				data.setTransactionType("W");
			} else*/ 
			if (TransactionType.Deposit.getValue().equals(klsRequest.getTransactionType())) {
				data.setCrDr("1");
				data.setTransactionType("D");
			}
			data.setTransactionAmount(isoDataElements.get(ISODataElements.TRANSACTION_AMOUNT));
			data.setTransactionTowards("1");
			data.setChannelType(klsRequest.getChannelType());
			data.setTerminalId(isoDataElements.get(ISODataElements.CARD_ACCEPTOR_TERMINAL_IDENTIFICATION));
			data.setStandAloneStatus(false);
			klsResponse = currentTransactionService.handleCreditTransaction(data);
			
		/*	//For virmati CBS integration
			if(!"O".equalsIgnoreCase(bankParameter.getCbsIntegrationMethod().getValue()) && "V".equalsIgnoreCase(bankParameter.getOther_cbs())){
				
				IPostVirmatiCBSTransactions postVirmatiCBSTransactions = KLSServiceFactory.getPostVirmatiCBSTransaction();
				boolean status=postVirmatiCBSTransactions.postVirmatiCBSCebitTransactions(klsResponse, klsRequest.getAccountNumber(),false,transactionMode);
				if(status){
					em.getTransaction().commit();
				}
				
			 }*/
			
		} catch (Exception e) {
			logger.error("Exception thrown in credit transaction method.");
			klsResponse = new KLSResponse();
			klsResponse.setResponseStatus(ISOResponseCodes.SYSTEM_ERROR);
			klsResponse.setErrorMessage("Exception thrown in credit transaction");
		} finally {
			if (!(ISOResponseCodes.APPROVED.equals(klsResponse.getResponseStatus()))) {
				String responseStatus=klsResponse.getResponseStatus();
				String responseDes=klsResponse.getErrorMessage();
				klsResponse = new KLSResponse();
				klsResponse.setResponseStatus(responseStatus);
				klsResponse.setErrorMessage(responseDes);
			}
			EntityManagerUtil.closeSession();
		}
		responseJSON = gson.toJson(klsResponse);
		logger.info("End : Calling CurrentTransaction service in creditTransaction() method.");
		return responseJSON;
	}

	@POST
	@Path("/kind")
	@Consumes("application/json")
	@Produces("application/json")
	public String kindTransaction(String jsonKlsRequest) {

		logger.info("Start : Calling CurrentTransaction service in kindTransaction() method.");
		String responseJSON = null;
		KLSResponse klsResponse = null;
		Gson gson = new GsonBuilder().create();
		try {
			ICurrentTransactionService currentTransactionService = KLSServiceFactory.getCurrentTransactionService();
			CurrentTransactionData data = new CurrentTransactionData();
			KLSRequest klsRequest = gson.fromJson(jsonKlsRequest, KLSRequest.class);
			Map<String, String> isoDataElements = klsRequest.getIsoDataElements();
			logger.info("Printing all iso nodes  : ");
			for (String key : isoDataElements.keySet()) {
				logger.info(" iso data elements key is : " + key);
				logger.info(" iso data elements value is : " + isoDataElements.get(key));
			}
			data.setVoucherNumber(null);
			data.setSavingsAccountNumber(isoDataElements.get(ISODataElements.ACCOUNT_IDENTIFICATION_1));
			data.setBusinessDate(isoDataElements.get(ISODataElements.TRANSACTION_DATE_AND_TIME));
			data.setStandAloneStatus(false);
			if (TransactionType.Withdrawal.getValue().equals(klsRequest.getTransactionType())) {
				data.setCrDr("-1");
				data.setTransactionType("T");
			} /*else if (TransactionType.Deposit.getValue().equals(klsRequest.getTransactionType())) {
				data.setCrDr("1");
				data.setTransactionType("D");
			} else if (TransactionType.Transfer.getValue().equals(klsRequest.getTransactionType())) {
				data.setCrDr("-1");
				data.setTransactionType("W");
			}*/
			data.setTransactionAmount(isoDataElements.get(ISODataElements.TRANSACTION_AMOUNT));
			data.setTransactionTowards("1");
			data.setChannelType(klsRequest.getChannelType());
			data.setTerminalId(isoDataElements.get(ISODataElements.CARD_ACCEPTOR_TERMINAL_IDENTIFICATION));
			klsResponse = currentTransactionService.handleKindTransaction(data);
		} catch (Exception e) {
			logger.error("Exception thrown in kind transaction method.");
			klsResponse = new KLSResponse();
			klsResponse.setResponseStatus(ISOResponseCodes.SYSTEM_ERROR);
			klsResponse.setErrorMessage("Exception thrown in kind transaction");
		} finally {
			EntityManagerUtil.closeSession();
		}
		responseJSON = gson.toJson(klsResponse);
		logger.info("End : Calling CurrentTransaction service in kindTransaction() method.");
		return responseJSON;
	}

	@POST
	@Path("/balance")
	@Consumes("*/*")
	@Produces("application/json")
	public String balanceEnquiry(String jsonKlsRequest) {

		logger.info("Start : Calling CurrentTransaction master service in balanceEnquiry() method.");
		String responseJSON = null;
		KLSResponse klsResponse = null;
		Gson gson = new GsonBuilder().create();
		try {
			IAccountPropertyDAO ap = KLSDataAccessFactory.getAccountPropertyDAO();
			ICurrentTransactionService currentTransactionService = KLSServiceFactory.getCurrentTransactionService();
			BankParameter bankParameter = KLSDataAccessFactory.getBankParameter();
			CurrentTransactionData data = new CurrentTransactionData();
			KLSRequest klsRequest = gson.fromJson(jsonKlsRequest, KLSRequest.class);
			Map<String, String> isoDataElements = klsRequest.getIsoDataElements();
			logger.info("Printing all iso nodes  : ");
			for (String key : isoDataElements.keySet()) {
				logger.info(" iso data elements key is : " + key);
				logger.info(" iso data elements value is : " + isoDataElements.get(key));
			}
			AccountProperty account = ap.getAccountProperty(klsRequest.getAccountNumber(), false);
			System.out.println("request SB="+klsRequest.getAccountNumber());
			System.out.println("actual SB="+account.getAtmLoanAccountNumber());
			String loanaccount=account.getAtmLoanAccountNumber();
			String traceNumber = new StringBuffer(isoDataElements.get(ISODataElements.TRACE_NUMBER)).reverse().toString();
			System.out.println("trac rev"+traceNumber);
			isoDataElements.put(ISODataElements.ACCOUNT_IDENTIFICATION_1, loanaccount);
			isoDataElements.put(ISODataElements.AUTHORIZATION_IDENTIFICATION_RESPONSE, traceNumber);
			
			data.setVoucherNumber(null);
			data.setSavingsAccountNumber(klsRequest.getAccountNumber());
			data.setBusinessDate(DateUtil.getDateString(bankParameter.getBusinessDate()));

			data.setCrDr("1"); // Not persisted as it is balance enquiry only. 
			data.setTransactionType("D"); // Not persisted as it is balance enquiry only. 

			data.setTransactionAmount(isoDataElements.get(ISODataElements.TRANSACTION_AMOUNT));
			data.setTransactionTowards("1");
			data.setChannelType(klsRequest.getChannelType());
			data.setRemarks(isoDataElements.get(ISODataElements.CARD_ACCEPTOR_NAME_LOCATION));
			data.setTerminalId(isoDataElements.get(ISODataElements.CARD_ACCEPTOR_TERMINAL_IDENTIFICATION));

			klsResponse = currentTransactionService.handleBalanceEnquiry(data);
		} catch (Exception e) {
			logger.error("Exception while balance enquiry");
			klsResponse = new KLSResponse();
			klsResponse.setResponseStatus(ISOResponseCodes.SYSTEM_ERROR);
			klsResponse.setErrorMessage("Exception while balance enquiry");
		} finally {
			EntityManagerUtil.closeSession();
		}
		responseJSON = gson.toJson(klsResponse);
		logger.info("End : Calling CurrentTransaction master service in balanceEnquiry() method.");
		return responseJSON;
	}

	/*** This service also used for standalone mode disbursement and collection ***/
	@POST
	@Path("/fromApplication")
	@Consumes("application/json")
	@Produces("application/json")
	public String demoTransactions(String jsonKlsRequest) {

		logger.info("Start : Calling CurrentTransaction master service in demoTransactions() method.");
		String responseJSON = null;
		KLSResponse klsResponse = null;
		KLSRequest klsRequest = null;
		Gson gson = new GsonBuilder().create();
		TcpMsgHandler tcpHandler = new TcpMsgHandler();
		/*BankParameter bankParameter = KLSDataAccessFactory.getBankParameter();
		EntityManager em = EntityManagerUtil.getEntityManager();
		if(!"O".equalsIgnoreCase(bankParameter.getCbsIntegrationMethod().getValue()) && "V".equalsIgnoreCase(bankParameter.getOther_cbs())){
			 em.getTransaction().begin();
		 }*/
		try {
			ICurrentTransactionService currentTransactionService = KLSServiceFactory.getCurrentTransactionService();
			CurrentTransactionData data = new CurrentTransactionData();
			klsRequest = gson.fromJson(jsonKlsRequest, KLSRequest.class);
			String transactionMode=klsRequest.getTransactionMode();
			Map<String, String> isoDataElements = klsRequest.getIsoDataElements();
			isoDataElements.put(ISODataElements.PAN, "800025150004367");
			logger.info("Printing all iso nodes  : ");
			for (String key : isoDataElements.keySet()) {
				logger.info(" iso data elements key is : " + key);
				logger.info(" iso data elements value is : " + isoDataElements.get(key));
			}
			data.setVoucherNumber(null);
			data.setSavingsAccountNumber(klsRequest.getAccountNumber());
			data.setStandAloneStatus(klsRequest.isStandAloneStatus());
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

			isoDataElements.put(ISODataElements.TRANSACTION_DATE_AND_TIME, dateAndTime);
			String amount[] = isoDataElements.get(ISODataElements.TRANSACTION_AMOUNT).split("\\.", 2);

			data.setTransactionAmount(amount[0]);
			data.setTransactionTowards("1");

			//data.setChannelType(klsRequest.getChannelType());
			data.setChannelType("S");
			//			data.setRemarks(isoDataElements.get(ISODataElements.CARD_ACCEPTOR_NAME_LOCATION));
			//			data.setTerminalId(isoDataElements.get(ISODataElements.CARD_ACCEPTOR_TERMINAL_IDENTIFICATION));

			data.setTerminalId(null);

			String suvikasBalance = "0";//klsRequest.getSuvikasBalance();

			
			SmsData smsData = new SmsData();
			
			smsData.setAccountNumber(klsRequest.getAccountNumber());
			
			smsData.setAccType("LN");
			smsData.setStatus("R");
		
			
			
			if (TransactionType.Withdrawal.getValue().equals(klsRequest.getTransactionType())) {
				data.setCrDr("-1");
				data.setTransactionType("T");
				//	data.setRemarks("Drawal demo");

				klsResponse = currentTransactionService.handleDebitTransaction(data, suvikasBalance);
				smsData.setTransMode("DI");
				smsData.setTransType("W");
				smsData.setAccTransType("D");
				smsData.setTransAmt(new BigDecimal(amount[0]).divide(BigDecimal.valueOf(100d)));
				smsData.setWithdrawBalance(BigDecimal.ZERO);
				smsData.setRemarks("Amount Disbursed Successfully");
				
				/*if(!"O".equalsIgnoreCase(bankParameter.getCbsIntegrationMethod().getValue()) && "V".equalsIgnoreCase(bankParameter.getOther_cbs())){
					
					IPostVirmatiCBSTransactions postVirmatiCBSTransactions = KLSServiceFactory.getPostVirmatiCBSTransaction();
					boolean status=postVirmatiCBSTransactions.postVirmatiCBSDebitTransactions(klsResponse, klsRequest.getAccountNumber(),true,transactionMode);
					if(status){
						em.getTransaction().commit();
					}
					
				 }*/
			} else if (TransactionType.Deposit.getValue().equals(klsRequest.getTransactionType())) {
				data.setCrDr("1");
				data.setTransactionType("D");
				//	data.setRemarks("Collection demo");

				STLoanRecoveryData loanRecoveryData = klsRequest.getStLoanRecoveryData();
				if (!loanRecoveryData.getInterestPaidEditable())
					klsResponse = currentTransactionService.handleCreditTransaction(data);
				else
					klsResponse = currentTransactionService.handleCreditTransaction(data, loanRecoveryData);
				smsData.setTransMode("RE");
				smsData.setTransType("D");
				smsData.setAccTransType("C");
				smsData.setTransAmt(loanRecoveryData.getAmountPaid());
				smsData.setWithdrawBalance(BigDecimal.ZERO);
				smsData.setRemarks("Amount Recovered Successfully");
				//For virmati CBS integration
				/*if(!"O".equalsIgnoreCase(bankParameter.getCbsIntegrationMethod().getValue()) && "V".equalsIgnoreCase(bankParameter.getOther_cbs())){
					
					IPostVirmatiCBSTransactions postVirmatiCBSTransactions = KLSServiceFactory.getPostVirmatiCBSTransaction();
					boolean status=postVirmatiCBSTransactions.postVirmatiCBSCebitTransactions(klsResponse, klsRequest.getAccountNumber(),true,transactionMode);
					if(status){
						em.getTransaction().commit();
					}
					
				 }*/
			
			} else if (TransactionType.Transfer.getValue().equals(klsRequest.getTransactionType())) {
				data.setCrDr("-1");
				data.setTransactionType("W");
				//	data.setRemarks("Kind drawal demo");
				klsResponse = currentTransactionService.handleKindTransaction(data);

			}
			
			
			

			// For StandAlone calls core side transaction should not be posted hence commenting below code
			/*			if (ISOResponseCodes.APPROVED.equals(klsResponse.getResponseStatus())){
							klsResponse.setErrorMessage("Transaction Successfull");

							isoDataElements.put(ISODataElements.MTI, "0200");
							Long traceNo = (new java.util.Date().getTime());
							String traceNoStr = traceNo.toString();
							traceNoStr = traceNoStr.substring(traceNoStr.length() - 7, traceNoStr.length()-1);
							isoDataElements.put(ISODataElements.TRACE_NUMBER, traceNoStr);
							isoDataElements.put(ISODataElements.RETRIEVAL_REFERENCE_NUMBER, isoDataElements.get(ISODataElements.TRANSACTION_AMOUNT));
							isoDataElements.put(ISODataElements.ACCOUNT_IDENTIFICATION_1, klsRequest.getAccountNumber());
							klsRequest.setBranchId("040");
							klsRequest.setChannelType("M");
							klsRequest.setTransactionMode("O");

							klsRequest.setIsoDataElements(isoDataElements);
							tcpHandler.postTheTransactionToSuvikas(klsRequest, klsResponse);
						}*/
			
			
			
			
			/*PersonData personData = RestClientUtil.getCustomerById(master.getLineOfCredit().getCustomerId());
			smsData.setCustomerNumber(personData.getMemberNumber());*/
			//smsData.setTransDate(LoanServiceUtil.getBusinessDate().toString());
			
				//RestClientUtil.postSMSAlertData(smsData);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception caught in demoTransactions method. " + e.getMessage());
			klsResponse = new KLSResponse();
			klsResponse.setResponseStatus(ISOResponseCodes.SYSTEM_ERROR);
			klsResponse.setErrorMessage("Exception occurred while transaction :" + e.getMessage());
		} finally {
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
			default:
				if (!(ISOResponseCodes.APPROVED.equals(klsResponse.getResponseStatus()))) {
					String responseStatus=klsResponse.getResponseStatus();
					String responseDes=klsResponse.getErrorMessage();
					klsResponse = new KLSResponse();
					klsResponse.setResponseStatus(responseStatus);
					klsResponse.setErrorMessage(responseDes);
				}
				break;
				
			}
			EntityManagerUtil.closeSession();
		}
		responseJSON = gson.toJson(klsResponse);
		logger.info("End : Calling CurrentTransaction master service in demoTransactions() method.");
		return responseJSON;
	}

	/*** Below service is only implemented for demo purposes ***/
	@POST
	@Path("/atmTransactionsFromApplication")
	@Consumes("application/json")
	@Produces("application/json")
	public String doATMTransactionsFromApplication(String jsonKlsRequest) {

		logger.info("Start : Calling CurrentTransaction master service in demoTransactions() method.");
		String responseJSON = null;
		KLSResponse klsResponse = null;
		KLSRequest klsRequest = null;
		Gson gson = new GsonBuilder().create();
		TcpMsgHandler tcpHandler = new TcpMsgHandler();
		try {
			ICurrentTransactionService currentTransactionService = KLSServiceFactory.getCurrentTransactionService();
			CurrentTransactionData data = new CurrentTransactionData();
			klsRequest = gson.fromJson(jsonKlsRequest, KLSRequest.class);
			Map<String, String> isoDataElements = klsRequest.getIsoDataElements();
			isoDataElements.put(ISODataElements.PAN, "800025150004367");
			logger.info("Printing all iso nodes  : ");
			for (String key : isoDataElements.keySet()) {
				logger.info(" iso data elements key is : " + key);
				logger.info(" iso data elements value is : " + isoDataElements.get(key));
			}
			data.setVoucherNumber(null);
			data.setSavingsAccountNumber(klsRequest.getAccountNumber());
			data.setStandAloneStatus(klsRequest.isStandAloneStatus());
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

			isoDataElements.put(ISODataElements.TRANSACTION_DATE_AND_TIME, dateAndTime);
			String amount[] = isoDataElements.get(ISODataElements.TRANSACTION_AMOUNT).split("\\.", 2);

			data.setTransactionAmount(amount[0]);
			data.setTransactionTowards("1");

			//data.setChannelType(klsRequest.getChannelType());
			data.setChannelType("SYSTEM");
			//			data.setRemarks(isoDataElements.get(ISODataElements.CARD_ACCEPTOR_NAME_LOCATION));
			//			data.setTerminalId(isoDataElements.get(ISODataElements.CARD_ACCEPTOR_TERMINAL_IDENTIFICATION));

			data.setTerminalId("Demo terminal");

			String suvikasBalance = "0";//klsRequest.getSuvikasBalance();

			if (TransactionType.Withdrawal.getValue().equals(klsRequest.getTransactionType())) {
				data.setCrDr("-1");
				data.setTransactionType("W");
				data.setRemarks("Drawal demo");

				klsResponse = currentTransactionService.handleDebitTransaction(data, suvikasBalance);

			} else if (TransactionType.Deposit.getValue().equals(klsRequest.getTransactionType())) {
				data.setCrDr("1");
				data.setTransactionType("D");
				data.setRemarks("Collection demo");
				STLoanRecoveryData loanRecoveryData=klsRequest.getStLoanRecoveryData();
				if (!loanRecoveryData.getInterestPaidEditable())
					klsResponse = currentTransactionService.handleCreditTransaction(data);
				else
					klsResponse = currentTransactionService.handleCreditTransaction(data, loanRecoveryData);
				} else if (TransactionType.Transfer.getValue().equals(klsRequest.getTransactionType())) {
				data.setCrDr("-1");
				data.setTransactionType("W");
				data.setRemarks("Kind drawal demo");
				klsResponse = currentTransactionService.handleKindTransaction(data);

			}
			if (ISOResponseCodes.APPROVED.equals(klsResponse.getResponseStatus())) {
				klsResponse.setErrorMessage("Transaction Successfull");

				isoDataElements.put(ISODataElements.MTI, "0200");
				Long traceNo = (new java.util.Date().getTime());
				String traceNoStr = traceNo.toString();
				traceNoStr = traceNoStr.substring(traceNoStr.length() - 7, traceNoStr.length() - 1);
				isoDataElements.put(ISODataElements.TRACE_NUMBER, traceNoStr);
				isoDataElements.put(ISODataElements.RETRIEVAL_REFERENCE_NUMBER, isoDataElements.get(ISODataElements.TRANSACTION_AMOUNT));
				isoDataElements.put(ISODataElements.ACCOUNT_IDENTIFICATION_1, klsRequest.getAccountNumber());
				klsRequest.setBranchId(klsRequest.getAccountNumber().substring(0, 3));
				klsRequest.setChannelType("M");
				klsRequest.setTransactionMode("O");

				klsRequest.setIsoDataElements(isoDataElements);
				tcpHandler.postTheTransactionToSuvikas(klsRequest, klsResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception caught in demoTransactions method. " + e.getMessage());
			klsResponse = new KLSResponse();
			klsResponse.setResponseStatus(ISOResponseCodes.SYSTEM_ERROR);
			klsResponse.setErrorMessage("Exception occurred while transaction :" + e.getMessage());
		} finally {
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
			EntityManagerUtil.closeSession();
		}
		responseJSON = gson.toJson(klsResponse);
		logger.info("End : Calling CurrentTransaction master service in demoTransactions() method.");
		return responseJSON;
	}

	@POST
	@Path("/getbifurcationamts")
	@Consumes("application/json")
	@Produces("application/json")
	public String getBufurcationAmounts(String stLoanRecoveryJsonString) {

		logger.info("Start : Calling  master service in getBufurcationAmounts() method.");
		com.vsoftcorp.kls.data.gateway.datahelpers.STLoanRecoveryData stLoanRecoveryData = new com.vsoftcorp.kls.data.gateway.datahelpers.STLoanRecoveryData();
		Gson gson = new GsonBuilder().create();
		try {
			stLoanRecoveryData = gson.fromJson(stLoanRecoveryJsonString, com.vsoftcorp.kls.data.gateway.datahelpers.STLoanRecoveryData.class);
			ISTLoanRecoveryTransactionService service = KLSServiceFactory.getStRecoveryTransactionService();
			stLoanRecoveryData = service.getBifurcationAmountsToBePaid(stLoanRecoveryData);
			stLoanRecoveryJsonString = gson.toJson(stLoanRecoveryData);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling  master service in getBufurcationAmounts() method.");
		return stLoanRecoveryJsonString;
	}
	
	@POST
	@Path("/chequeValidation")
	@Consumes("*/*")
	@Produces("application/json")
	public String validateCheque(String chequeTransactionJSON) {

		logger.info("Start : Calling  master service in validateCheque() method.");
		Gson gson = new GsonBuilder().create();
		String retMssg = "";
		try {

			ChequeTransactionData chequeTransactionData = (ChequeTransactionData) gson.fromJson(chequeTransactionJSON,
					ChequeTransactionData.class);
			String CBSAppln = PropertiesUtil.getDocumentProperty(StringConstant.CBS_APPLICATION);

			if ("FINACLE".equalsIgnoreCase(CBSAppln)){
				return RestClientUtil.isSuvikasCheckValid(chequeTransactionData);						
			}
			
			HashMap<String, String> isoElements = new HashMap<String, String>();

				//		Input
				//			3 Processing code Valid value : 94
				//			62 Cheque Details 1.       Start Cheque Number N16 2.       No of leaves N4
				//			102 Account number Account for which cheque status inquiry is required..
				//		Output
				//			39 Response code Valid ISO response codes (Refer section 4.6.5)
				//			125 Reserved field For Cheque status inquiry, it will return one character cheque status as follows:
				//				1.       Paid i.e. Cheque used (‘P’)
				//				2.       Cautioned i.e. a warning set up on the cheque (‘C’)
				//				3.       Destroyed when account is being closed (‘D’)
				//				4.       Stopped (‘S’)
				//				5.       Paid cheque returned (‘R’)
				//				6.       Unused (‘U’)
				//				7.       Sent to user but not acknowledged by user (‘I’)
				//		000 Financial transaction has been approved (DCC should go ahead and complete the transaction)
				//		163 Invalid Cheque Status
				//		181 Cheques are in different books
				//		182 Not all cheques could be stopped
				//		183 Cheque not issued to this account
				//		187 Cheque Stopped		
			

			String chequeNo = chequeTransactionData.getChequeNo();
			chequeNo = JPOSUtil.leftPad(chequeNo,'0',12);
			 isoElements.put(ISODataElements.ACCOUNT_IDENTIFICATION_1,chequeTransactionData.getDebitAccountNumber());
			 isoElements.put(ISODataElements.RESERVED_PRIVATE_62,chequeNo);
			 isoElements.put(ISODataElements.PROCESSING_CODE,"921000"); 

			String coreISOMssg = JPOSUtil.buildResponseISOMssg(isoElements);
			String response = response = SuvikasService.routeTheRequestToSuvikas(coreISOMssg, "");

			HashMap<String, String> isoElementsResponse = (HashMap<String, String>) JPOSUtil.parseISOMssg(response);
			String pvtField125 = isoElementsResponse.get(ISODataElements.NETWORK_MANAGEMENT_INFORMATION);
			String responseCode = isoElementsResponse.get(ISODataElements.RESPONSE_CODE);
			
			switch (pvtField125){
				case "P":
					retMssg = "1";
					break;
				case "C":
				case "D":
				case "S":
				case "R":
				case "U":
				case "I":
					retMssg = "-1";
					break;
			}

			if ("163".equals(responseCode)){
				retMssg = "-1";
			}
			
		}catch (Exception e) {
			logger.error("Error occurred while handling cheque validation call :" + e.getMessage());
			retMssg = "-1";
			//e.printStackTrace();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling  master service in validateCheque() method.");
		return retMssg;
	}

	
	@GET
	@Path("/transactionHistory/{memberId}")
	@Produces("application/json")
	public String getTractionHistory(@PathParam("memberId") String memberId, @QueryParam("locId") Long locId,  
			@QueryParam("fromDate") String fromDate, @QueryParam("toDate") String toDate) {

		logger.info("Start : getTractionHistory in getgetTractionHistory() method.");
		List<TransactionHistoryData> list = new ArrayList<TransactionHistoryData>();
		String jsonString = "";
		try {
			ITransactionHistoryService service = KLSServiceFactory.getTransactionHistoryService();
			PersonData personData=RestClientUtil.getCustomerByMemberNumber(memberId);
			list = service.getAllTransactionHistoryRecordsById(personData.getCustomerId(), locId, fromDate.substring(4,15), toDate.substring(4,15));
			Map<String, List<TransactionHistoryData>> transactionDataMap = new HashMap<String, List<TransactionHistoryData>>();
			transactionDataMap.put("history", list);
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(transactionDataMap);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End :getTractionHistory in getgetTractionHistory() method");
		return jsonString;
	}
	
	@GET
	@Path("/byvouchernumber/{voucherNumber}")
	@Produces("application/json")
	public String getTractionsByVoucherNumber(@PathParam("voucherNumber") String voucherNumber) {

		logger.info("Start : Fetching data in getTractionsByVoucherNumber() method.");
		List<VoucherData> list = new ArrayList<VoucherData>();
		String jsonString = "";
		try {
			ICurrentTransactionService service =  KLSServiceFactory.getCurrentTransactionService();
			list=service.getTransactionsByVoucherNumber(voucherNumber);
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(list);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End :Fetching data  in getTractionsByVoucherNumber() method");
		return jsonString;
	}
	@POST
	@Path("/bulkrecovery")
	@Consumes("application/json")
	@Produces("application/json")
	public String doBulkRecovery(String bulkRecoveryData) {
		
		logger.info("Start: Reading data from Excel File in doBulkRecovery() method");
		List<CustXLData> rejectedList = new ArrayList<CustXLData>();
		List<CustXLData> rejected = new ArrayList<CustXLData>();
		ISTLoanRecoveryTransactionService sevice = KLSServiceFactory.getStRecoveryTransactionService();
		String jsonString="";
		Gson gson = new GsonBuilder().create();
		try {
			
			BulkRecoveryData bulkData = gson.fromJson(bulkRecoveryData,BulkRecoveryData.class);
			/*File excelFile = new File(fileName);
			 excelFile.createNewFile();
			byte[] custdata = DatatypeConverter.parseBase64Binary(bulkData.getCustFile());
			 BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(excelFile));
		        writer.write(custdata);
		        writer.flush();
		        writer.close();
			//ByteArrayInputStream inputStream = new ByteArrayInputStream(custdata);
*/	
			File excelFile = FileUtil.getEncodedFile(bulkData.getCustFile());
			Workbook w;
			w = Workbook.getWorkbook(excelFile);

			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			Cell cell = null;
			// Loop over first 10 column and lines
			List<CustXLData> custXLDataList = new ArrayList<CustXLData>();
			CustXLData excelData = null;
			if(sheet.getRows()!=0 && sheet.getCell(0,0).getContents()!=null){
			for (int i = 0; i < sheet.getRows(); i++) {
				try{
				excelData = new CustXLData();
				for (int j = 0; j < sheet.getColumns(); j++) {
					cell = sheet.getCell(j, i);
					if (cell.getContents() != null && cell.getContents().trim() != "" && !cell.getContents().trim().equalsIgnoreCase("null")) {
						if (j == 0) {
                            excelData.setMemberNumber(cell.getContents());
                        } else if (j == 1) {
                            excelData.setMemberName(cell.getContents());
                        } else if (j == 2) {
                            excelData.setRecoverdAmount(new BigDecimal(cell.getContents()));
                        }
					}
				}
				custXLDataList.add(excelData);
				}catch(NumberFormatException nfe){
					excelData.setRemarks("Invalid amount format");
					rejected.add(excelData);	
				}
			}
			rejectedList = sevice.validateAndProcessRecovery(custXLDataList, bulkData.getPacsId());
			if(rejected != null && rejected.size() > 0){
				rejectedList.addAll(rejected);
			}
			if(rejectedList != null && rejectedList.size()>0){
				BulkSTRecoveryResponse response = new BulkSTRecoveryResponse();
				response.setRejectedList(rejectedList);
				response.setStatus(false);
				response.setResponse("Transaction Successfull");
				jsonString = gson.toJson(response);
			}else{
				BulkSTRecoveryResponse response = new BulkSTRecoveryResponse();
				response.setStatus(true);
				response.setResponse("Transaction Successfull");
				jsonString = gson.toJson(response);
			}
			}else{
				BulkSTRecoveryResponse response = new BulkSTRecoveryResponse();
				response.setStatus(true);
				response.setResponse("Can not proceed. Empty Excel File Uploaded.");
				jsonString = gson.toJson(response);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(BiffException b){
			BulkSTRecoveryResponse response = new BulkSTRecoveryResponse();
			response.setStatus(true);
			response.setResponse("Invalid File Format");
			jsonString = gson.toJson(response);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("End: Reading data from Excel File in doBulkRecovery() method");
		return jsonString;

	}
	
	@POST
	@Path("/bulkloanrecovery")
	@Consumes("*/*")
	@Produces("application/json")
	public String doBulkLoanRecovery(String bulkLoanRecoveryData) {
		logger.info("Start: Reading data from bulkRecovery in doBulkLoanRecovery() method"+bulkLoanRecoveryData);
		logger.info("Start: Reading data from bulkRecovery in doBulkLoanRecovery() method");
		List<BulkCustomerData> transactionRejectedList = new ArrayList<BulkCustomerData>();
		List<BulkCustomerData> mtltTransactionRejectedList = new ArrayList<BulkCustomerData>();
		List<BulkCustomerData> stLoanRecoveryList = new ArrayList<BulkCustomerData>();
		List<BulkCustomerData> mtltLoanRecoveryList = new ArrayList<BulkCustomerData>();

		ISTLoanRecoveryTransactionService stLoanRecService = KLSServiceFactory.getStRecoveryTransactionService();
		ILoanRecoveryService loanRecService = KLSServiceFactory.getLoanRecoveryService();
		
		String jsonString="";
		Gson gson = new GsonBuilder().create();
		List<BulkCustomerData> bulkCustDataList = gson.fromJson(bulkLoanRecoveryData, new TypeToken<ArrayList<BulkCustomerData>>() {}.getType());
		logger.info("customer info: customer data List :"+bulkCustDataList );
		if(bulkCustDataList != null && !bulkCustDataList.isEmpty()) {
			for (BulkCustomerData bulkCustData : bulkCustDataList) {
				if("ST".equalsIgnoreCase(bulkCustData.getLoanType())) {
					stLoanRecoveryList.add(bulkCustData);
				}else if("MT".equalsIgnoreCase(bulkCustData.getLoanType())) {
					mtltLoanRecoveryList.add(bulkCustData);
				}
			}
		}
		if(stLoanRecoveryList != null && !stLoanRecoveryList.isEmpty()){
			transactionRejectedList = stLoanRecService.validateAndProcessBulkLoanRecovery(stLoanRecoveryList);	
		}
		if(mtltLoanRecoveryList != null && !mtltLoanRecoveryList.isEmpty()){
			mtltTransactionRejectedList = loanRecService.validateAndProcessBulkMtLtRecovery(mtltLoanRecoveryList); 
		}
		 
		if(transactionRejectedList != null && transactionRejectedList.size()>0 || mtltTransactionRejectedList != null && mtltTransactionRejectedList.size() > 0){
			BulkLoanRecoveryResponse response = new BulkLoanRecoveryResponse();
			response.setRejectedList(transactionRejectedList);
			response.getRejectedList().addAll(mtltTransactionRejectedList);
			response.setStatus(false);
			response.setResponse("Transaction  Successfull");
			jsonString = gson.toJson(response);
		}else{
			BulkLoanRecoveryResponse response = new BulkLoanRecoveryResponse();
			response.setStatus(true);
			response.setResponse("Transaction Successfull");
			jsonString = gson.toJson(response);
		} 
		 
		logger.info("End: Reading data from bulkRecovery in doBulkLoanRecovery() method");
		return jsonString;

	}
	
	@GET
	@Path("/checkLoanLimits")
	@Consumes("*/*")
	@Produces("application/json")
	public String checkLoanLimits(@QueryParam("klsRequestJson") String klsRequestJson){
		
		logger.info("Start : Calling checking loan limits in checkLoanLimits() method." + klsRequestJson);
		String responseJSON = null;
		Gson gson = new GsonBuilder().create();
		KLSRequest klsRequest = gson.fromJson(klsRequestJson, KLSRequest.class);
		ICurrentTransactionService currentTransactionService = KLSServiceFactory.getCurrentTransactionService();
		Map<String,String> resMap = currentTransactionService.checkLoanLimitsService(klsRequest);
		responseJSON=gson.toJson(resMap);
		
		return responseJSON;
	}
	
	@GET
	@Path("/getSBAccountByLoanAccount")
	@Consumes("*/*")
	@Produces("application/json")
	public String getSBAccountByATMLoanAccount(@QueryParam("atmLoanAccount") String atmLoanAccount){
		
		logger.info("Start : fetching SB account by ATMLoanAccount in getSBAccountByATMLoanAccount() method.");
		String responseJSON = null;
		IAccountPropertyService service = KLSServiceFactory.getAccountPropertyService();
		responseJSON=service.getSbByAtmLoanAccount(atmLoanAccount);
		logger.info("Start : fetching SB account by ATMLoanAccount in getSBAccountByATMLoanAccount() method.");
		return responseJSON;
	}
	
	@POST
	@Path("/reversal")
	@Consumes("application/json")
	@Produces("application/json")
	public String reversalTransaction(String jsonKlsRequest) {

		logger.info("Start : Calling CurrentTransaction service in creditTransaction() method."+jsonKlsRequest);
		String responseJSON = null;
		Gson gson = new GsonBuilder().create();
		KLSResponse klsResponse=new KLSResponse();
		try {
			ICurrentTransactionService currentTransactionService = KLSServiceFactory.getCurrentTransactionService();
			//CurrentTransactionData data = new CurrentTransactionData();
			KLSRequest klsRequest = gson.fromJson(jsonKlsRequest, KLSRequest.class);
			Map<String, String> isoDataElements = klsRequest.getIsoDataElements();
			klsResponse=currentTransactionService.reversalTransaction(isoDataElements);				
		} catch (Exception e) {
			e.printStackTrace();
			klsResponse.setResponseStatus(ISOResponseCodes.SYSTEM_ERROR);
			logger.error("Exception thrown in credit transaction method.");
			
		} 
		responseJSON = gson.toJson(klsResponse);
		logger.info("End : Calling CurrentTransaction service in creditTransaction() method.");
		return responseJSON;
	}
@GET
@Path("/suspenseaccount")
@Produces("*/*")
public String getSuspenseAccunt(){
logger.info("Start : Calling from getSuspenseAccunt method .");	
String returnJson="";
try{
	BankParameter bankParameter = KLSDataAccessFactory.getBankParameter();
	returnJson=bankParameter.getSuspenseAccount();
	
}catch(Exception ex){
	ex.printStackTrace();
}
return returnJson;
}
@GET
@Path("/miniStatement")
@Consumes("application/json")
@Produces("application/json")
public String miniStatement(@QueryParam("accountNum") String accountNum){
	logger.info("Start : Calling from miniStatement method .");
	String returnJson="";
	List<MiniStatementData> statementDataList=new ArrayList<MiniStatementData>();
	try{
		ICurrentTransactionService service=KLSServiceFactory.getCurrentTransactionService();
		Gson gson=new GsonBuilder().create();
		statementDataList=service.getMiniStatement(accountNum);
		returnJson=gson.toJson(statementDataList);
	}catch(Exception ex){
		ex.printStackTrace();
	}
	logger.info("End : Calling miniStatement");
	return returnJson;
}
	
}
