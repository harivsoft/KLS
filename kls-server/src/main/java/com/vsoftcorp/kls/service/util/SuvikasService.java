/**
 * 
 */
package com.vsoftcorp.kls.service.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.service.constants.ServiceConstants;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.time.Date;
import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.core.banking.data.CoreBankingRequest;
import com.vsoftcorp.core.banking.data.CoreBankingResponse;
import com.vsoftcorp.core.banking.data.TransactionData;
import com.vsoftcorp.core.banking.data.TransactionRequestData;
import com.vsoftcorp.core.banking.data.values.RequestType;
import com.vsoftcorp.core.banking.data.values.BalanceType;
import com.vsoftcorp.core.banking.data.ChannelInfo;

//import com.vsoftcorp.camelintg.service.SuvikasService;
import com.vsoftcorp.camelintg.util.DBUtil;
import com.vsoftcorp.camelintg.util.JPOSUtil;

import com.vsoftcorp.core.banking.data.values.BalanceType;
import com.vsoftcorp.core.banking.data.values.RequestType;
import com.vsoftcorp.core.banking.data.values.AccountStatus;
/**
 * @author a9152
 * 
 */
public class SuvikasService {

	private static final Logger logger = Logger.getLogger(SuvikasService.class);

	public static CoreBankingResponse checkAccountAndBalance(String accountNumber, RequestType requestType) {

		logger.info("Start: Inside checkAccountAndBalance() method.");
		CoreBankingResponse coreBankingResponse = null;
		try {
			String CBSAppln = PropertiesUtil.getDocumentProperty(StringConstant.CBS_APPLICATION);
			if ("FINACLE".equalsIgnoreCase(CBSAppln)){
				return checkAccountAndBalanceISO(accountNumber,requestType);						
			}
			CoreBankingRequest coreBankingRequest = getCoreBankingRequest(accountNumber, requestType);
			Gson gson = new GsonBuilder().create();
			String suvikasCoreBankingRequest = gson.toJson(coreBankingRequest);
			String suvikasUrl = PropertiesUtil.getSuvikasProperty(ServiceConstants.SUVIKAS_BANKING_URL);
			logger.info(" Suvikas url is : " + suvikasUrl);
			long startTime = System.currentTimeMillis();
			String response = getSuvikasResponse("application/json", suvikasUrl, suvikasCoreBankingRequest);
			coreBankingResponse = (CoreBankingResponse) gson.fromJson(response.toString(), CoreBankingResponse.class);
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			logger.info(" Suvikas response time for checkAccountAndBalance : " + elapsedTime);
			logger.info(" Suvikas savings bank account status : " + coreBankingResponse.getAccountStatus());
			logger.info(" Suvikas savings bank balance : " + coreBankingResponse.getBalances().get(BalanceType.Avail));
			logger.info("End: Inside checkAccountAndBalance() method.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error occured while checking account and balance :" + e.getMessage());
			throw new KlsRuntimeException("Error occured while checking account and balance.", e.getCause());
		}
		return coreBankingResponse;
	}

	public static CoreBankingResponse checkAccountAndBalanceISO(String accountNumber, RequestType requestType) {

		logger.info("Start: Inside checkAccountAndBalanceISO() method.");
		CoreBankingResponse coreBankingResponse = null;
		try {
			HashMap<String, String> isoElements = new HashMap<String, String>();

//			Balance enquiry
//			Input:
//				3 Processing code Valid value : 31
//				102 Account number Account for which balance inquiry is required.
//
//			Output:
//				39 Response code Valid ISO response codes (Refer section 4.4.76.5)
//				48 Additional data private Balances of account as given below:
//					17 char ledger balance (Source account)
//					17 char available balance
//					17 char float balance
//					17 char FFD balance
//					17 char user defined balance
//					3 char balance currency code
//					14 char Fallback time in YYYYMMDDhhmmss format, (Blank if data center is not in fallback mode)
//					All the balance fields will have sign (“+” or “-”) followed by sixteen character 
//					amount with implied decimal based on balance currency code.
	

		isoElements.put(ISODataElements.ACCOUNT_IDENTIFICATION_1,accountNumber);
		isoElements.put(ISODataElements.PROCESSING_CODE,"311000"); 

		String coreISOMssg = JPOSUtil.buildResponseISOMssg(isoElements);
		String response = com.vsoftcorp.camelintg.service.SuvikasService.routeTheRequestToSuvikas(coreISOMssg, "");

		HashMap<String, String> isoElementsResponse = (HashMap<String, String>) JPOSUtil.parseISOMssg(response);
		String pvtField48 = isoElementsResponse.get(ISODataElements.ADDITIONAL_DATA_PRIVATE);
		String responseCode = isoElementsResponse.get(ISODataElements.RESPONSE_CODE);
		
		if ( "000".equals(responseCode)	){
			String availBal = pvtField48.substring(16 ,33 );
			HashMap<BalanceType, AccountingMoney> balances = new HashMap<BalanceType, AccountingMoney>();
			AccountingMoney availableBalance = MoneyUtil.getAccountingMoney(new BigDecimal(availBal));
			
			balances.put(BalanceType.Avail, availableBalance);
			
			coreBankingResponse = new CoreBankingResponse(balances,	AccountStatus.Active, "0", "0");
		}		
		
		logger.info("End: Inside checkAccountAndBalanceISO() method.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error occured while checking account and balance through ISO :" + e.getMessage());
			throw new KlsRuntimeException("Error occured while checking account and balance through ISO.", e.getCause());
		}
		return coreBankingResponse;
	}
	
	/**
	 * 
	 * @param accountNumber
	 * @param requestType
	 */
	private static CoreBankingRequest getCoreBankingRequest(String accountNumber, RequestType requestType) {

		logger.info("Start: Get Core Banking Request in getCoreBankingRequest() method");
		CoreBankingRequest coreBankingRequest = new CoreBankingRequest();
		coreBankingRequest.setAccountNumber(accountNumber);
		ChannelInfo channelInfo = new ChannelInfo();
		coreBankingRequest.setChannelInfo(channelInfo);
		coreBankingRequest.setRequestType(requestType);
		logger.info("End: Get Core Banking Request in getCoreBankingRequest() method");
		return coreBankingRequest;
	}

	/**
	 * 
	 * @param url
	 * @param acceptType
	 * @param bodyType
	 * @param request
	 * @return
	 */
	private static ClientRequest getClientRequest(String url, String acceptType, String bodyType, String request) {

		ClientRequest clientRequest = new ClientRequest(url);
		clientRequest.accept(acceptType);
		clientRequest.body(bodyType, request);
		logger.info(" Suvikas core banking request for balance enquiry : " + request);
		return clientRequest;
	}

	private static String getSuvikasResponse(String contentType, String suvikasUrl, String suvikasRequest) {

		logger.info("Start: Get Suvikas response for account and balance verification in getSuvikasResponse() method");
		StringBuilder responseStr = new StringBuilder("");
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(suvikasUrl);
			postRequest.addHeader("accept", contentType);
			StringEntity input = new StringEntity(suvikasRequest);
			input.setContentType(contentType);
			postRequest.setEntity(input);
			HttpResponse response = httpClient.execute(postRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException(
						"Suvikas Service check account and balance verification failed : HTTP error code : "
								+ response.getStatusLine().getStatusCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String out = "";
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error occured while checking account and balance :" + excp.getMessage());
			throw new KlsRuntimeException("Error occured while checking account and balance.", excp.getCause());
		}
		logger.info("Response from server :" + responseStr.toString());
		logger.info("End: Get Suvikas response for account and balance verification in getSuvikasResponse() method");
		return responseStr.toString();
	}

	public static String postSuvikasTransaction(String accountNumber, RequestType requestType, Integer branchId,
			AccountingMoney transactionAmount, String remarks) {

		logger.info("Start: Inside postSuvikasTransaction() method.");
		String transactionResponse = "";
		try {
			TransactionRequestData transRequestData = getTransactionRequest(accountNumber, requestType, branchId,
					transactionAmount, remarks);
			Gson gson = new GsonBuilder().create();
			String suvikasTransactionBankingRequest = gson.toJson(transRequestData);
			String suvikasTransactionUrl = PropertiesUtil.getSuvikasProperty(ServiceConstants.SUVIKAS_TRANSACTION_URL);
			logger.info(" suvikasTransactionUrl : " + suvikasTransactionUrl);
			long startTime = System.currentTimeMillis();
			transactionResponse = getSuvikasResponse("application/json", suvikasTransactionUrl,
					suvikasTransactionBankingRequest);
			transactionResponse = (String) gson.fromJson(transactionResponse, String.class);
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			logger.info(" Suvikas response time for postSuvikasTransaction : " + elapsedTime);
			logger.info(" response : " + transactionResponse);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error occured while checking account and balance :" + e.getMessage());
			throw new KlsRuntimeException("Error occured while checking account and balance.", e.getCause());
		}
		logger.info("End: Inside postSuvikasTransaction() method.");
		return transactionResponse;
	}

	/**
	 * 
	 * @param accountNumber
	 * @param requestType
	 */
	private static TransactionRequestData getTransactionRequest(String accountNumber, RequestType requestType,
			Integer branchId, AccountingMoney transactionAmount, String remarks) {

		logger.info("Start: Get Transaction Banking Request in getTransactionRequest() method");
		TransactionRequestData transRequestData = new TransactionRequestData();
		List<TransactionData> transactionDataList = new ArrayList<TransactionData>();
		ChannelInfo channelInfo = new ChannelInfo();
		String transactionId = new Long(System.currentTimeMillis()).toString();
		logger.info("transactionId : " + transactionId);
		if (RequestType.CreditTxn.equals(requestType)) {
			TransactionData transactionData = new TransactionData(transactionId, "0", accountNumber, transactionAmount,
					remarks, false, false, false, false);
			transactionDataList.add(transactionData);
		} else if (RequestType.DebitTxn.equals(requestType)) {
			TransactionData transactionData = new TransactionData(transactionId, accountNumber, "0", transactionAmount,
					remarks, false, false, false, false);
			transactionDataList.add(transactionData);
		}

		transRequestData.setChannelInfo(channelInfo);
		transRequestData.setIsReversalTxn(false);
		transRequestData.setBranchId(branchId.toString());
		transRequestData.setTransactionDataList(transactionDataList);
		transRequestData.setTransactionType(com.vsoftcorp.core.banking.data.values.TransactionType.MATM_KWDRL);
		logger.info("End: Get Transaction Banking Request in getTransactionRequest() method");
		return transRequestData;
	}
}
