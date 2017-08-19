package com.vsoftcorp.kls.service.helper.subsidy;

import com.vsoftcorp.kls.business.entity.subsidy.InterestSubsidy;
import com.vsoftcorp.kls.business.entity.subsidy.SubsidySchemeGlMapping;
import com.vsoftcorp.kls.subsidy.data.SubsidySchemeGlMappingData;

public class SubsidySchemeGlMappingHelper {

	public static SubsidySchemeGlMapping getSubsidySchemeGlMapping(
			SubsidySchemeGlMappingData data) {
		SubsidySchemeGlMapping schemeGlMapping = new SubsidySchemeGlMapping();
		schemeGlMapping.setId(data.getId());
		schemeGlMapping.setPacsId(data.getPacsId());
		schemeGlMapping.setSubsidyReceievableGl(data.getSubsidyReceievableGl());
		if(data.getSubsidySchemeId()!=null){
			InterestSubsidy interestSubsidy = new InterestSubsidy();
			interestSubsidy.setId(data.getSubsidySchemeId());
			schemeGlMapping.setSubsidySchemeId(interestSubsidy);
		}
		
		return schemeGlMapping;
	}

	public static SubsidySchemeGlMappingData getSubsidySchemeGlMappingData(
			SubsidySchemeGlMapping master) {
		SubsidySchemeGlMappingData data = new SubsidySchemeGlMappingData();
		data.setId(master.getId());
		data.setPacsId(master.getPacsId());
		data.setSubsidyReceievableGl(master.getSubsidyReceievableGl());
		data.setSubsidySchemeId(master.getSubsidySchemeId().getId());
		return data;
	}

}
