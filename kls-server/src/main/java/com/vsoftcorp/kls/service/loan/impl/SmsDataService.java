package com.vsoftcorp.kls.service.loan.impl;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.data.SmsData;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.service.util.RestClientUtil;

public class SmsDataService {
	
	public static SmsData getSmsData(LoanLineOfCredit lineOfCredit, AccountingMoney transAmount, 
			String transType, String accTransType, String transMode, Money withdrawlBal, String remarks) {
		SmsData smsData = new SmsData();
		
		smsData.setAccountNumber(lineOfCredit.getAccount().getAccountNumber());
		smsData.setAccTransType(accTransType);
		smsData.setAccType("LN");
		smsData.setStatus("R");
		smsData.setTransMode(transMode);
		smsData.setTransType(transType);
		smsData.setTransAmt(transAmount.getMoney().getAmount());
		
		smsData.setWithdrawBalance(withdrawlBal.getAmount());
		smsData.setRemarks(remarks);
		/*PersonData personData = RestClientUtil.getCustomerById(lineOfCredit.getCustomerId());
		smsData.setCustomerNumber(personData.getMemberNumber());*/
		smsData.setTransDate(LoanServiceUtil.getBusinessDate().toString());
		return smsData;
	}
	
	
	public static SmsData getLocSmsData(LineOfCredit lineOfCredit, AccountingMoney transAmount, 
			String transType, String accTransType, String transMode, Money withdrawlBal, String remarks) {
		SmsData smsData = new SmsData();
		
		smsData.setAccountNumber(lineOfCredit.getAccount().getAccountNumber());
		smsData.setAccTransType(accTransType);
		smsData.setAccType("LN");
		smsData.setStatus("R");
		smsData.setTransMode(transMode);
		smsData.setTransType(transType);
		smsData.setTransAmt(transAmount.getMoney().getAmount());
		
		smsData.setWithdrawBalance(withdrawlBal.getAmount());
		smsData.setRemarks(remarks);
		/*PersonData personData = RestClientUtil.getCustomerById(lineOfCredit.getCustomerId());
		smsData.setCustomerNumber(personData.getMemberNumber());*/
		smsData.setTransDate(LoanServiceUtil.getBusinessDate().toString());
		return smsData;
	}

		
}
