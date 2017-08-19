package com.vsoftcorp.kls.service.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vsoftcorp.kls.data.gateway.datahelpers.ShareAccountData;
import com.vsoftcorp.kls.data.gateway.datahelpers.ShareTransactionData;
//import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.time.Date;

public class SampleMain {

	 public static void main1(String[] args) {
		System.out.println("Start: Calling getMemberShareAccountByCustomerId() service ===");
		ShareAccountData shareAccountData = null;
		String memberShipRestURL = "http://192.168.2.186:8080/customer-1.0/rest/";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			String url = memberShipRestURL + "shareAccountService/bymemberid?customerId=1321";
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");
			
	/*		HttpParams params = new BasicHttpParams();
			params.setParameter("customerId", id);
			getRequest.setParams(params );*/
			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				System.out.println("RestClientUtil.getMemberShareAccountByCustomerId() Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			
			String out = "";
			StringBuilder responseStr = new StringBuilder("");
			while ( (out = br.readLine()) != null){
				responseStr.append(out);
			}
			System.out.println("Response from server :" + responseStr.toString());
			//shareAccountData = (ShareAccountData) gson.fromJson(responseStr.toString(), ShareAccountData.class);
		} catch (Exception e) {
			//logger.error("Error while calling rest service to fetch share account by customer Id :" + id);
			//throw new KlsRuntimeException("Error while calling rest service to fetch share account by customer Id", e);
		}
		//logger.info("Start: Calling getMemberShareAccountByCustomerId() service ");
		//return shareAccountData;
	} 
	 
	 public static void main (String args[]){
		 
		 Map <String, Integer> trans = new  <String, Integer> HashMap();
		 trans.put("1", 30);
		 int value = trans.get("1");
		 
		 BigDecimal d1 = new BigDecimal("5");
		 BigDecimal d2 = new BigDecimal("10");
		 System.out.println(d1.toString() + d2.toString());
		 
		 d1=d1.add(d2);
		 System.out.println(d1.toString() + d2.toString());

		 
	 }

}
