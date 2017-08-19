package com.vsoftcorp.kls.service.impl;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.AccountProperty;
import com.vsoftcorp.kls.data.KccCardMappingData;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IAccountDAO;
import com.vsoftcorp.kls.service.IKccCardMappingService;
import com.vsoftcorp.kls.service.helper.KccCardMappingHelper;
import com.vsoftcorp.kls.service.util.RestClientUtil;

public class KccCardMappingService implements IKccCardMappingService {

	public Logger logger = Logger.getLogger(KccCardMappingService.class);

	@Override
	public String saveCardMappingData(KccCardMappingData kccCardMappingData) {
		
		try {
			
			IAccountDAO accDAO = KLSDataAccessFactory.getAccountDAO();
			Account account = accDAO.getAccountByCardNumber(kccCardMappingData.getKccCardNumber());
			AccountProperty ap = account.getAccountProperty();
			ap.setSavingsAccountNumber(kccCardMappingData.getActualSB());
			account.setAccountProperty(ap);
			accDAO.updateAccount(account);
			String status = RestClientUtil.updateCardStatusByPartyId(kccCardMappingData.getCustomerId(),kccCardMappingData.getCardStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return "Error: While Mapping Card Details";
		}
		return "KCC Card Mapping Saved Successfully";
	}

	@Override
	public KccCardMappingData getCardMappingDataByCardNumber(String cardNumber) {

		logger.info("Start: Fetching KCC Card mapping details using Card Number");
		KccCardMappingData kccCardMappingData = null;
		try {
			IAccountDAO accDAO = KLSDataAccessFactory.getAccountDAO();
			Account account = accDAO.getAccountByCardNumber(cardNumber);
			if (account != null) {
				PersonData personData = RestClientUtil
						.getAllCustomerDataByCusomterId(account.getAccountProperty().getCustomerId());
				kccCardMappingData = KccCardMappingHelper.getKccCardMappingData(account, personData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("End: Successfully Fetched KCC Card mapping details using Card Number");

		return kccCardMappingData;
	}

}
