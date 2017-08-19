package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entity.subsidy.InstituteMaster;
import com.vsoftcorp.kls.subsidy.data.InstituteMasterData;
import com.vsoftcorp.kls.valuetypes.subsidy.GeographicalArea;
import com.vsoftcorp.kls.valuetypes.subsidy.TypeOfInstitute;

public class InstituteMasterHelper {
	public static InstituteMasterData getInstituteMasterData(InstituteMaster master) {
		InstituteMasterData data = new InstituteMasterData();

		if (master.getId() != null) {
			data.setId(master.getId());
		}
		data.setInstituteName(master.getInstituteName());
		data.setTypeOfInstitute(master.getTypeOfInstitute().getValue());
		data.setGeographicalArea(master.getGeographicalArea().getValue());

		return data;
	}

	public static InstituteMaster getInstituteMaster(InstituteMasterData data) {

		InstituteMaster master = new InstituteMaster();
		if (data.getId() != null) {
			master.setId(data.getId());
		}
		master.setInstituteName(data.getInstituteName());
		master.setTypeOfInstitute(TypeOfInstitute.getType(data
				.getTypeOfInstitute()));
		master.setGeographicalArea(GeographicalArea.getType(data.getGeographicalArea()));
		return master;
	}
}
