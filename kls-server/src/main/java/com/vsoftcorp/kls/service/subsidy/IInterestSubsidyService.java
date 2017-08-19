package com.vsoftcorp.kls.service.subsidy;

import java.util.List;

import com.vsoftcorp.kls.subsidy.data.InterestSubsidyData;

public interface IInterestSubsidyService {
	
	public void saveInterestSubsidyService(InterestSubsidyData data);
	
	public void modifyInterestSubsidyService(InterestSubsidyData data);
	
	public InterestSubsidyData getInterestSubsidyService(Long id);
	
	public List<InterestSubsidyData> getAllInterestSubsidyService();

	public List<InterestSubsidyData> getInterestSubsidySchemeByType(
			String subsidySchemeType);
	
	public boolean isLOCExistService(Integer schemeId, Long seasonId);

}
