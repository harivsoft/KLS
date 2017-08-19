package com.vsoftcorp.kls.dataaccess.subsidy;

import java.util.List;

import com.vsoftcorp.kls.business.entity.subsidy.SubsidySchemeGlMapping;

public interface ISubsidySchemeGlMappingDAO {
	
	public void saveSubsidySchemeGlMapping(SubsidySchemeGlMapping master);
	
	public void modifySubsidySchemeGlMapping(SubsidySchemeGlMapping master);
	
	public SubsidySchemeGlMapping getSubsidySchemeGlMapping(Integer pacId, Long id);
	
	public List<SubsidySchemeGlMapping> getAllSubsidySchemeGlMapping(Integer pacId);

	public boolean isTransactionExist(String subsidyReceievableGl, Integer pacsId);

}
