package com.vsoftcorp.kls.dataaccess;

import java.math.BigDecimal;
import java.util.List;

import com.vsoftcorp.kls.business.entities.SanctionedComponentDetail;

public interface ISanctionedComponentDAO {
	public void saveSanctionedComponent(SanctionedComponentDetail master);

	public void updateSanctionedComponent(SanctionedComponentDetail master);
	
	public List<SanctionedComponentDetail> getSanctionedComponentsDetailsBySeasonId(Long seasonId);

	public void deleteComponentDetail(String id);
	
	public BigDecimal getTotalKindPercentage(Long seasonId);
}
