package com.vsoftcorp.kls.service.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.CamelRequest;
import com.vsoftcorp.kls.data.ChequeTransactionData;
import com.vsoftcorp.kls.data.PacsLoanDisbursementData;
import com.vsoftcorp.kls.data.SmsData;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.data.gateway.datahelpers.ShareAccountData;
import com.vsoftcorp.kls.data.gateway.datahelpers.ShareProductData;
import com.vsoftcorp.kls.data.gateway.datahelpers.ShareProductGlMappingData;
import com.vsoftcorp.kls.data.gateway.datahelpers.ShareTransactionData;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.time.Date;

public class RestClientUtil {

	private static Logger logger = Logger.getLogger(RestClientUtil.class);
	private static Gson gson = null;
	private static String memberShipRestURL = null;
	private static String klsSuvikasRestURL = null;
	private static String pacsRestURL = null;
	private static String pacsCamelRestURL=null;
	static {

		try {
			logger.info("Start: RestUtil static initialization");

			gson = new GsonBuilder().create();
			memberShipRestURL = PropertiesUtil.getDocumentProperty(StringConstant.MEMBERSHIP_REST_URL);
			klsSuvikasRestURL = PropertiesUtil.getDocumentProperty(StringConstant.KLS_SUVIKAS_REST_URL);
			pacsRestURL = PropertiesUtil.getDocumentProperty(StringConstant.PACS_REST_URL);
            pacsCamelRestURL=PropertiesUtil.getDocumentProperty(StringConstant.PACS_CAMEL_REST_URL);
			logger.info("End: RestUtil static initialization");
		} catch (Exception excp) {
			excp.printStackTrace();
		}
	}

	public static PersonData getCustomerById(Long id) {
		logger.info("Start: Calling getCustomerById() service ");
		PersonData customer = null;

		try {
			HttpClient httpClient = new DefaultHttpClient();
			String url = memberShipRestURL + "customerService/customer/byid?customerId=" + id;
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");

			/*
			 * HttpParams params = new BasicHttpParams();
			 * params.setParameter("customerId", id);
			 * getRequest.setParams(params );
			 */
			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException("RestClientUtil.getCustomerById() Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			logger.info("Response from server :" + responseStr.toString());

			customer = (PersonData) gson.fromJson(responseStr.toString(), PersonData.class);
		} catch (Exception e) {
			logger.error("Error while calling rest service to fetch customer data by Id :" + id);
			throw new KlsRuntimeException("Error while calling rest service to fetch customer data by Id", e);
		}
		logger.info("Start: Calling getCustomerById() service ");
		return customer;

	}
	
	public static PersonData getAllCustomerDataByCusomterId(Long id) {
		logger.info("Start: Calling getAllCustomerDataByCusomterId() service ");
		PersonData customer = null;

		try {
			HttpClient httpClient = new DefaultHttpClient();
			String url = memberShipRestURL + "customerService/customer?customerId=" + id;
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");

			/*
			 * HttpParams params = new BasicHttpParams();
			 * params.setParameter("customerId", id);
			 * getRequest.setParams(params );
			 */
			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException("RestClientUtil.getAllCustomerDataByCusomterId() Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			logger.info("Response from server :" + responseStr.toString());

			customer = (PersonData) gson.fromJson(responseStr.toString(), PersonData.class);
		} catch (Exception e) {
			logger.error("Error while calling rest service to fetch customer data by Id :" + id);
			throw new KlsRuntimeException("Error while calling rest service to fetch customer data by Id", e);
		}
		logger.info("Start: Calling getAllCustomerDataByCusomterId() service ");
		return customer;

	}

	public static Money getShareBalanceByCustomerId(Long id) {
		logger.info("Start: Calling getShareBalanceByCustomerId() service ");
		Money shareBalance = null;

		try {
			Double balance = null;
			HttpClient httpClient = new DefaultHttpClient();
			String url = memberShipRestURL + "shareAccountService/getsharebalancebycustomerid?customerId=" + id;
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");

			/*
			 * HttpParams params = new BasicHttpParams();
			 * params.setParameter("customerId", id);
			 * getRequest.setParams(params );
			 */
			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException(
						"RestClientUtil.getShareBalanceByCustomerId() Failed : HTTP error code : "
								+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			logger.info("Response from server :" + responseStr.toString());

			balance = (Double) gson.fromJson(responseStr.toString(), Double.class);
			shareBalance = MoneyUtil.getMoney(balance);
		} catch (Exception e) {
			logger.error("Error while calling rest service to fetch share balace by customer Id :" + id);
			throw new KlsRuntimeException("Error while calling rest service to fetch share balance by customer Id", e);
		}
		logger.info("Start: Calling getShareBalanceByCustomerId() service ");
		return shareBalance;

	}

	public static ShareProductData getShareProductById(Long id) {
		logger.info("Start: Calling getShareProductById() service ");
		ShareProductData shareProductData = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			String url = memberShipRestURL + "shareProductService/byid?id=" + id;
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");

			/*
			 * HttpParams params = new BasicHttpParams();
			 * params.setParameter("id", id); getRequest.setParams(params );
			 */
			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException("RestClientUtil.getShareProductById() Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			logger.info("Response from server :" + responseStr.toString());
			shareProductData = (ShareProductData) gson.fromJson(responseStr.toString(), ShareProductData.class);
		} catch (Exception e) {
			logger.error("Error while calling rest service to fetch Share Product By id :" + id);
			throw new KlsRuntimeException("Error while fetching Share Product By id", e);
		}
		logger.info("Start: Calling getShareProductById() service ");
		return shareProductData;

	}
	public static List<ShareProductData> getShareProductList() {
		logger.info("Start: Calling getShareProductById() service ");
		List<ShareProductData> productList = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			String url = memberShipRestURL + "shareProductService/shareproduct";
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");

			/*
			 * HttpParams params = new BasicHttpParams();
			 * params.setParameter("id", id); getRequest.setParams(params );
			 */
			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException("RestClientUtil.getShareProductById() Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			logger.info("Response from server :" + responseStr.toString());
			productList =  (List<ShareProductData>) gson.fromJson(responseStr.toString(), ShareProductData.class);
		} catch (Exception e) {
			logger.error("Error while calling rest service to fetch Share Products");
			throw new KlsRuntimeException("Error while fetching Share Product By id", e);
		}
		logger.info("Start: Calling getShareProductById() service ");
		return productList;

	}
	
	
	public static ShareAccountData getShareAccountByAccountNo(String accountNo) {
		logger.info("Start: Calling getShareAccountByAccountNo() service ");
		ShareAccountData shareAccountData = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			String url = memberShipRestURL + "shareAccountService/sharebyaccno?accountNumber=" + accountNo;
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException("RestClientUtil.getShareAccountByAccountNo() Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			logger.info("Response from server :" + responseStr.toString());
			shareAccountData = (ShareAccountData) gson.fromJson(responseStr.toString(), ShareAccountData.class);
		} catch (Exception e) {
			logger.error("Error while calling rest service to fetch Share Account By accountNo :" + accountNo);
			throw new KlsRuntimeException("Error while fetching Share Product By accountNo", e);
		}
		logger.info("Start: Calling getShareAccountByAccountNo() service ");
		return shareAccountData;

	}

	public static ShareAccountData getMemberShareAccountByCustomerId(Long id) {
		logger.info("Start: Calling getMemberShareAccountByCustomerId() service ");
		ShareAccountData shareAccountData = null;

		try {
			HttpClient httpClient = new DefaultHttpClient();
			String url = memberShipRestURL + "shareAccountService/bymemberid?customerId=" + id;
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");

			/*
			 * HttpParams params = new BasicHttpParams();
			 * params.setParameter("customerId", id);
			 * getRequest.setParams(params );
			 */
			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException(
						"RestClientUtil.getMemberShareAccountByCustomerId() Failed : HTTP error code : "
								+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			logger.info("Response from server :" + responseStr.toString());
			shareAccountData = (ShareAccountData) gson.fromJson(responseStr.toString(), ShareAccountData.class);
		} catch (Exception e) {
			logger.error("Error while calling rest service to fetch share account by customer Id :" + id);
			throw new KlsRuntimeException("Error while calling rest service to fetch share account by customer Id", e);
		}
		logger.info("Start: Calling getMemberShareAccountByCustomerId() service ");
		return shareAccountData;
	}

	public static String modifyShareAccount(ShareAccountData data) {
		logger.info("Start: Calling modifyShareAccount() service ");
		String returnMssg = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			String url = memberShipRestURL + "shareAccountService/bymemberid";
			HttpPut putRequest = new HttpPut(url);
			putRequest.addHeader("accept", "application/json");

			StringEntity input = new StringEntity(gson.toJson(data));
			input.setContentType("application/json");
			putRequest.setEntity(input);

			HttpResponse response = httpClient.execute(putRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException("RestClientUtil.modifyShareAccount() Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			logger.info("Response from server :" + responseStr.toString());
			// returnMssg = (String) gson.fromJson(responseStr.toString(),
			// String.class);
		} catch (Exception e) {
			logger.error("Error while calling rest service to update share account");
			throw new KlsRuntimeException("Error while calling rest service to update share account ", e);
		}
		logger.info("Start: Calling modifyShareAccount() service ");
		return returnMssg;
	}

	public static ShareProductGlMappingData getShareProductGlMapping(Long productId, Integer pacsId) {
		logger.info("Start: Calling getShareProductGlMapping() service ");
		ShareProductGlMappingData shareProductGlMapping = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			String url = memberShipRestURL + "shareProductGlMappingService/getshareproductglbyid?productId="
					+ productId + "&pacsId=" + pacsId;
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException("RestClientUtil.getShareProductGlMapping() Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			logger.info("Response from server :" + responseStr.toString());
			shareProductGlMapping = (ShareProductGlMappingData) gson.fromJson(responseStr.toString(),
					ShareProductGlMappingData.class);
		} catch (Exception e) {
			logger.error("Error while calling rest service to fetch Share Product GlMapping :");
			throw new KlsRuntimeException("Error while fetching Share Product GlMapping", e);
		}
		logger.info("Start: Calling getShareProductGlMapping() service ");
		return shareProductGlMapping;

	}

	public static String addNewSharesToExistingAccountByMemberCode(ShareTransactionData data) {
		logger.info("Start: Calling addNewSharesToExistingAccountByMemberCode() service ");
		String returnMssg = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			String url = memberShipRestURL + "shareTransactionService/addNewSharesToExistingAccountByMemberCode";
			HttpPost postRequest = new HttpPost(url);
			postRequest.addHeader("accept", "application/json");

			StringEntity input = new StringEntity(gson.toJson(data));
			input.setContentType("application/json");
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException(
						"RestClientUtil.addNewSharesToExistingAccountByMemberCode() Failed : HTTP error code : "
								+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			logger.info("Response from server :" + responseStr.toString());
			returnMssg = (String) gson.fromJson(responseStr.toString(), String.class);
		} catch (Exception e) {
			e.printStackTrace();

			logger.error("Error while calling rest service to post share transaction");
			throw new KlsRuntimeException("Error while calling rest service to post share transaction ", e);
		}
		logger.info("Start: Calling addNewSharesToExistingAccountByMemberCode() service ");
		return returnMssg;
	}

	public static boolean checkIfShareAccountExists(Long customerId) {

		logger.info("Start: Calling checkIfShareAccountExists() rest client service ");
		boolean returnStatus = false;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			String url = memberShipRestURL + "shareAccountService/checkshareaccountexist?customerId=" + customerId;
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException("RestClientUtil.checkIfShareAccountExists() Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			logger.info("Response from server :" + responseStr.toString());
			returnStatus = (Boolean) gson.fromJson(responseStr.toString(), Boolean.class);

		} catch (Exception e) {
			logger.error("Error while calling rest service to check if share account exists for a customer Id :"
					+ customerId);
			throw new KlsRuntimeException(
					"Error while calling rest service to check if share account exists for a customer Id", e);
		}
		logger.info("End: Calling checkIfShareAccountExists() rest client service ");
		return returnStatus;
	}
	
	public static String checkifMargnAmountAvailable(PacsLoanDisbursementData data) {
		logger.info("Start: Calling checkifMargnAmountAvailable() service ");
		String returnMssg = null;
		try {
			HttpClient   httpClient = new DefaultHttpClient();
			String url = klsSuvikasRestURL + "marginservice/marginmgmt/margin?transAmt="+data.getPacsSuvikasVoucherAmount()+"&voucherNum="+data.getPacsSuvikasVoucherNumber()+"&transDate="+data.getPacsSuvikasVoucherDate()+"&pacsId="+data.getPacsId()+"&branchId="+data.getBranchId();
			logger.error("checkifMargnAmountAvailable Url: "+url);
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/text");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException("RestClientUtil.getShareAccountByAccountNo() Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			returnMssg = (String) gson.fromJson(responseStr.toString(), String.class);
			logger.info("Response is: "+returnMssg);
		} catch (Exception e) {
			e.printStackTrace();

			logger.error("Error while calling rest service to post margin gl transaction");
			throw new KlsRuntimeException("Error while calling rest service to post margin gl transaction ", e);
		}
		logger.info("Start: Calling checkifMargnAmountAvailable() service ");
		return returnMssg;
	}
	
	public static String getShareBalanceByCustomerId(Long customerId, String toDate){
		logger.info("Start: Calling checkifMargnAmountAvailable() service ");
		String returnMssg = null;
		try {
			HttpClient   httpClient = new DefaultHttpClient();
			Date date = DateUtil.getVSoftDateByString(toDate);
			String url = memberShipRestURL + "shareTransactionService/getsharebalancebycustomerid?customerId="+customerId+"&toDate="+date.toString();
			logger.error("getShareBlanceByUserId Url: "+url);
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException("RestClientUtil.getShareAccountByAccountNo() Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			returnMssg = (String) gson.fromJson(responseStr.toString(), String.class);
			logger.info("Response is: "+returnMssg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while calling rest service Get the Share Balance");
			throw new KlsRuntimeException("Error while calling rest service get the Share Balance By Customer Id. ", e);
		}
		logger.info("Start: Calling getShareBalanceByCustomerId() service ");
		return returnMssg;
	}
	
	public static String isSuvikasCheckValid(ChequeTransactionData data) {
		logger.info("Start: Calling isSuvikasCheckValid() service ");
		String returnMssg = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			String url = klsSuvikasRestURL + "transaction/chequeValidation";
			logger.info("Check validation URL: "+url) ;

			HttpPost postRequest = new HttpPost(url);
			postRequest.addHeader("accept", "application/json");

			StringEntity input = new StringEntity(gson.toJson(data));
			input.setContentType("application/json");
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException(
						"RestClientUtil.isSuvikasCheckValid() Failed : HTTP error code : "
								+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			logger.info("Response from server :" + responseStr.toString());
			returnMssg = (String) gson.fromJson(responseStr.toString(), String.class);
		} catch (Exception e) {
			e.printStackTrace();

			logger.error("Error while calling rest service for cheque validation");
			throw new KlsRuntimeException("Error while calling rest service for cheque validation ", e);
		}
		logger.info("Start: Calling isSuvikasCheckValid() service ");
		return returnMssg;
	}
	
	public static String updatePartyBankInfo(Long partyId,String sbAccountNo) {
		logger.info("START: Updating partybankinfo in updatePartyBankInfo() method of RestClientUtil service ");
		String returnMssg = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			if(sbAccountNo == null)
				sbAccountNo="";
			String url = memberShipRestURL + "customerService/customer/updatesbAccountNo?partyId="+partyId+"&sbAccountNo="+sbAccountNo;
			HttpPut putRequest = new HttpPut(url);
			putRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(putRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException("RestClientUtil.updatePartyBankInfo() Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			logger.info("Response from server :" + responseStr.toString());
			returnMssg=responseStr.toString();
		} catch (Exception e) {
			logger.error("Error while calling rest service to update party bank info");
			throw new KlsRuntimeException("Error while calling rest service to update party bank info", e);
		}
		logger.info("END: Updating partybankinfo in updatePartyBankInfo() method of RestClientUtil service ");
		return returnMssg;
	}
	
	
	public static String getHouseHoldCardName(Long customerId){
		logger.info("Start: Calling getHouseHoldCardName() service ");
		String returnMssg = null;
		try {
			HttpClient   httpClient = new DefaultHttpClient();
			String url = pacsRestURL + "pds/householdcardname?customerId="+customerId;
			logger.error("getHouseHoldCardName Url: "+url);
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException("RestClientUtil.getHouseHoldCardName() Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			returnMssg = responseStr.toString();
			logger.info("Response is: "+returnMssg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while calling rest service Get the HouseHoldCardName");
			throw new KlsRuntimeException("Error while calling rest service get getHouseHoldCardName Customer Id. ", e);
		}
		logger.info("Start: Calling getHouseHoldCardName() service ");
		return returnMssg;
	}
	
	public static PersonData getCustomerByMemberNumber(String memberNumber) {
		logger.info("Start: Calling getCustomerByMemberNumber() service ");
		PersonData customer = null;

		try {
			HttpClient httpClient = new DefaultHttpClient();
			String url = memberShipRestURL + "customerService/customers/" + memberNumber;
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");

			/*
			 * HttpParams params = new BasicHttpParams();
			 * params.setParameter("customerId", id);
			 * getRequest.setParams(params );
			 */
			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException("RestClientUtil.getCustomerByMemberNumber() Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			logger.info("Response from server :" + responseStr.toString());

			customer = (PersonData) gson.fromJson(responseStr.toString(), PersonData.class);
		} catch (Exception e) {
			logger.error("Error while calling rest service to fetch customer data by member number :" + memberNumber);
			throw new KlsRuntimeException("Error while calling rest service to fetch customer data by member number", e);
		}
		logger.info("Start: Calling getCustomerByMemberNumber() service ");
		return customer;

	}
	
	/*
	 * This method will post member account related data to suvikas 
	 * which will be used for sending SMS alert to Registered cuastomers.
	 */
	
	public static String  postSMSAlertData(SmsData data) throws Exception {
		logger.info("Start: Posting SMS data to Suvikas in postSMSAlertData() of  RestClientUtil class.");
		String returnMssg = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			String url = klsSuvikasRestURL + "productService/saveMobileTransInfo";
			HttpPost postRequest = new HttpPost(url);
			postRequest.addHeader("accept", "application/text");
			
			StringEntity input = new StringEntity(gson.toJson(data));
			input.setContentType("application/json");
			postRequest.setEntity(input);
			
			HttpResponse response = httpClient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new Exception("RestClientUtil.postSMSAlertData() Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			
			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ( (out = br.readLine()) != null){
				responseStr.append(out);
			}
			returnMssg=responseStr.toString();
		} catch (Exception e) {
			e.printStackTrace();

			logger.error("Error while calling rest service to post SMS data." );
			throw new Exception("Error while calling rest service to post SMS data. ", e);
		}
		logger.info("END: Posting SMS data to Suvikas in postSMSAlertData() of  RestClientUtil class.");
		return returnMssg;
	}
	
	
	
	public static String updateFarmerType(Long partyId,Long farmerType) {
		logger.info("START: Updating farmer type in updateFarmerType() method of RestClientUtil service ");
		String returnMssg = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			String url = memberShipRestURL + "customerService/customer/updatefarmertype?partyId="+partyId+"&farmerType="+farmerType;
			HttpPut putRequest = new HttpPut(url);
			putRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(putRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException("RestClientUtil.updatePartyBankInfo() Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			logger.info("Response from server :" + responseStr.toString());
			returnMssg=responseStr.toString();
		} catch (Exception e) {
			logger.error("Error while calling rest service to update farmer type");
			throw new KlsRuntimeException("Error while calling rest service to update farmer type", e);
		}
		logger.info("END: Updating farmer type in updateFarmerType() method of RestClientUtil service ");
		return returnMssg;
	}

	public static String updateCardStatusByPartyId(Long partyId, String cardStatus) {
		
		logger.info("START: Updating partybankinfo in updateCardStatusByPartyId() method of RestClientUtil service ");
		String returnMssg = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			String url = memberShipRestURL + "customerService/customer/updateCardStatus?partyId="+partyId+"&cardStatus="+cardStatus;
			HttpPut putRequest = new HttpPut(url);
			putRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(putRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException("RestClientUtil.updateCardStatusByPartyId() Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			logger.info("Response from server :" + responseStr.toString());
			returnMssg=responseStr.toString();
		} catch (Exception e) {
			logger.error("Error while calling rest service to update party bank info");
			throw new KlsRuntimeException("Error while calling rest service to update party bank info", e);
		}
		logger.info("END: Updating partybankinfo in updateCardStatusByPartyId() method of RestClientUtil service ");
		return returnMssg;
		
	}
	
	public static String getPacsBusinessDate(Integer pacsId,Integer branchId) {
		logger.info("Start: Calling getPacsBusinessDate() service ");
		String returnMssg = null;
		try {
			HttpClient   httpClient = new DefaultHttpClient();
			String url = klsSuvikasRestURL + "userservice/businessdate?pacId="+pacsId+"&branchId="+branchId;
			logger.info("getPacsBusinessDate Url: "+url);
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/text");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException("RestClientUtil.getPacsBusinessDate() Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			returnMssg = responseStr.toString();
			logger.info("Response is: "+returnMssg);
		} catch (Exception e) {
			e.printStackTrace();

			logger.error("Error while calling rest service to post margin gl transaction");
			throw new KlsRuntimeException("Error while calling rest service to post margin gl transaction ", e);
		}
		logger.info("Start: Calling checkifMargnAmountAvailable() service ");
		return returnMssg;
	}

	
	public static String dayEndValidation(String pacsDate,String branchId,String userName) {
		logger.info("Start: Calling dayEndValidation() service ");
		String returnMssg = null;
		try {
			HttpClient   httpClient = new DefaultHttpClient();
			String url = klsSuvikasRestURL + "dayEndRestService/dayEndValidation?date="+pacsDate+"&branchId="+branchId+"&loginUser="+userName;
			logger.info("getPacsBusinessDate Url: "+url);
			HttpPost getRequest = new HttpPost(url);
			getRequest.addHeader("accept", "application/text");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException("RestClientUtil.dayEndValidation() Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			returnMssg = responseStr.toString();
			logger.info("Response is: "+returnMssg);
		} catch (Exception e) {
			e.printStackTrace();

			logger.error("Error while calling rest service for dayEndValidation");
			throw new KlsRuntimeException("Error while calling rest service for dayEndValidation ", e);
		}
		logger.info("Start: Calling dayEndValidation() service ");
		return returnMssg;
	}
	
	public static String processDayEndAndDayBegin(String pacsDate,String branchId,String userName) {
		logger.info("Start: Calling processDayEndAndDayBegin() service ");
		String returnMssg = null;
		try {
			HttpClient   httpClient = new DefaultHttpClient();
			String url = klsSuvikasRestURL + "dayEndRestService/processDayEndAndDayBegin?date="+pacsDate+"&branchId="+branchId+"&loginUser="+userName;
			logger.info("getPacsBusinessDate Url: "+url);
			HttpPost getRequest = new HttpPost(url);
			getRequest.addHeader("accept", "application/text");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException("RestClientUtil.processDayEndAndDayBegin() Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			returnMssg = responseStr.toString();
			logger.info("Response is: "+returnMssg);
		} catch (Exception e) {
			e.printStackTrace();

			logger.error("Error while calling rest service to for processDayEndAndDayBegin");
			throw new KlsRuntimeException("Error while calling rest service for processDayEndAndDayBegin ", e);
		}
		logger.info("Start: Calling processDayEndAndDayBegin() service ");
		return returnMssg;
	}
	public static String getTransactionStatus(CamelRequest camelRequest,String type){
		logger.info("Start: Calling getTransactionStatus() service ");
		String returnMssg = null;
		try {
			HttpClient   httpClient = new DefaultHttpClient();
			String url ="";
			if(type.equals("disbursement"))
				url = pacsCamelRestURL + "withdraw";
			else if(type.equals("recovery"))
			url = pacsCamelRestURL + "recovery";
			else
				url = pacsCamelRestURL + "recoveryAtBranch";
			logger.info("getTransactionStatus Url: "+url);
			HttpPost postRequest = new HttpPost(url);
			postRequest.addHeader("accept", "application/text");
			logger.info("testing json::"+gson.toJson(camelRequest));
			StringEntity input = new StringEntity(gson.toJson(camelRequest));
			input.setContentType("application/json");
			postRequest.setEntity(input);
			HttpResponse response = httpClient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new KlsRuntimeException("RestClientUtil.processDayEndAndDayBegin() Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ((out = br.readLine()) != null) {
				responseStr.append(out);
			}
			returnMssg = responseStr.toString();
			logger.info("Response is: "+returnMssg);
		} catch (Exception e) {
			e.printStackTrace();

			logger.error("Error while calling rest service to for processDayEndAndDayBegin");
			throw new KlsRuntimeException("Error while calling rest service for processDayEndAndDayBegin ", e);
		}
		logger.info("Start: Calling processDayEndAndDayBegin() service ");
		return returnMssg;
	}
}