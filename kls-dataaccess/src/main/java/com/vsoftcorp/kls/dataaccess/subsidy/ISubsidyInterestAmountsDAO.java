package com.vsoftcorp.kls.dataaccess.subsidy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.vsoftcorp.kls.business.entity.subsidy.SubsidyInterestAmounts;

public interface ISubsidyInterestAmountsDAO {

	public void saveSubsidyInterestAmounts(SubsidyInterestAmounts master);
	
	public SubsidyInterestAmounts getSubsidyInterestAmountsbyLocId(Long locId);
	
	public List<Map> getSubsidySchemeAndGlMap(Long locId, String schemeType);
	
	public void updateSubsidyInterestAmounts(SubsidyInterestAmounts master);

	public List<SubsidyInterestAmounts> getSubsidyListByLocId(Long locId);

	public List<SubsidyInterestAmounts> getSubsidyInterestAmtObj(Long id,
			Long subSchemeId);

	public BigDecimal getSubventionAmount(Long id);
}
