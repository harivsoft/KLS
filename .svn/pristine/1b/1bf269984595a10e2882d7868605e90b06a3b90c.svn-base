package com.vsoftcorp.kls.service.subsidy;

import java.util.List;

import com.vsoftcorp.kls.subsidy.data.SubsidySchemeGlMappingData;

public interface ISubsidySchemeGlService {

	void saveSubsidySchemeGlMapping(SubsidySchemeGlMappingData subsidyGlData);
	public void modifySubsidySchemeGlMapping(SubsidySchemeGlMappingData data);
	public List<SubsidySchemeGlMappingData> getAllSubsidySchemeGlMapping(Integer pacsId);
	public SubsidySchemeGlMappingData getSubsidySchemeGlMapping(
			Long subsidySchemeId, Integer pacsId);
	boolean isTransactionExist(String subsidyReceievableGl, Integer pacsId);

}
