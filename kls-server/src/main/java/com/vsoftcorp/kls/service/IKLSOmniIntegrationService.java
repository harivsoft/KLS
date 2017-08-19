package com.vsoftcorp.kls.service;

import com.vsoftcorp.kls.data.LandDetailData;
import com.vsoftcorp.kls.data.MemberSummaryData;

public interface IKLSOmniIntegrationService {
	
	public MemberSummaryData getMemberSummaryService(String memberNumber);
	
	public LandDetailData getLandDetailsByMemberNumber(String memberNumber);
	
	public String getData(String inputParam);

}
