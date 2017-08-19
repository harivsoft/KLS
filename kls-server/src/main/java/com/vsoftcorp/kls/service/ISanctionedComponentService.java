package com.vsoftcorp.kls.service;

import com.vsoftcorp.kls.data.SanctionedComponentData;

public interface ISanctionedComponentService {
	public void saveSanctionedComponent(SanctionedComponentData data);

	public SanctionedComponentData getSanctionedComponentsBySeasonId(
			Long seasonId);

}
