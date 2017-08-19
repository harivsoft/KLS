package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.Taluka;
import com.vsoftcorp.kls.business.entities.Village;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.AccountProperty;
import com.vsoftcorp.kls.data.KccCardMappingData;
import com.vsoftcorp.kls.data.gateway.datahelpers.AddressData;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;

public class KccCardMappingHelper {
	
	public static KccCardMappingData getKccCardMappingData(Account account,PersonData personData){
		
		KccCardMappingData kccCardMappingData = new KccCardMappingData();
		try {
			AccountProperty ap = account.getAccountProperty();
			kccCardMappingData.setActualLoan(account.getAccountNumber());
			kccCardMappingData.setActualSB(ap.getSavingsAccountNumber());
			kccCardMappingData.setCustomerId(personData.getCustomerId());
			AddressData address = personData.getContactDetailList().get(0);
			Village village = KLSDataAccessFactory.getVillageDAO().getVillageById(address.getVillageId());
			kccCardMappingData.setVillage(village.getName());
			Taluka taluka = KLSDataAccessFactory.getTalukaDAO().getTaluka(village.getTaluka());
			kccCardMappingData.setTaluka(taluka.getName());
			kccCardMappingData.setDistict(KLSDataAccessFactory.getDistrictDAO().getDistrict(taluka.getDistrict()).getName());
			kccCardMappingData.setFatherName(personData.getFatherName());
			kccCardMappingData.setKccCardNumber(ap.getCardNumber());
			kccCardMappingData.setLinkedLoan(ap.getAtmLoanAccountNumber());
			kccCardMappingData.setLinkedSB(ap.getDummySBAccountNumber());
			kccCardMappingData.setMemberName(personData.getDisplayName());
			kccCardMappingData.setMemberNumber(personData.getMemberNumber());
			kccCardMappingData.setCardStatus(personData.getBankDetailList().get(0).getCardStatus());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return kccCardMappingData;
		
	}

}
