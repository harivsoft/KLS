package com.vsoftcorp.kls.service.helper.subsidy;

import com.vsoftcorp.kls.business.entities.Scheme;
import com.vsoftcorp.kls.business.entity.subsidy.InstituteMaster;
import com.vsoftcorp.kls.business.entity.subsidy.InterestSubsidy;
import com.vsoftcorp.kls.subsidy.data.InterestSubsidyData;
import com.vsoftcorp.kls.valuetypes.subsidy.EligibilityCriteria;
import com.vsoftcorp.kls.valuetypes.subsidy.InterestSubsidyPostingOn;
import com.vsoftcorp.kls.valuetypes.subsidy.TypeOfScheme;

public class InterestSubsidyHelper {

	public static InterestSubsidy getInterestSubsidy(InterestSubsidyData data) {

		InterestSubsidy master = new InterestSubsidy();

		if (data.getId() != null) {
			master.setId(data.getId());
		}
		if (data.getEligibilityCriteria() != null) {
			master.setEligibilityCriteria(EligibilityCriteria.getType(data
					.getEligibilityCriteria()));
		}
		if (data.getInstituteMasterId() != null) {
			InstituteMaster instituteMaster = new InstituteMaster();
			instituteMaster.setId(data.getInstituteMasterId());
			master.setInstituteMaster(instituteMaster);
		}
		master.setMaxPeriodForSubsidy(data.getMaxPeriodForSubsidy());
		master.setNameOfScheme(data.getNameOfScheme());
		if (data.getSchemeId() != null) {
			Scheme scheme = new Scheme();
			scheme.setId(data.getSchemeId());
			master.setScheme(scheme);
		}
		master.setSeasonId(data.getSeasonId());
		if (data.getTypeOfScheme() != null) {
			master.setTypeOfScheme(TypeOfScheme.getType(data.getTypeOfScheme()));
		}
		return master;
	}

	public static InterestSubsidyData getInterestSubsidyData(
			InterestSubsidy master) {

		InterestSubsidyData data = new InterestSubsidyData();

		if (master.getId() != null) {
			data.setId(master.getId());
		}
		if (master.getEligibilityCriteria() != null) {
			data.setEligibilityCriteria(master.getEligibilityCriteria()
					.getValue());
		}
		if (master.getInstituteMaster() != null) {
			data.setInstituteMasterId(master.getInstituteMaster().getId());
		}
		data.setMaxPeriodForSubsidy(master.getMaxPeriodForSubsidy());
		data.setNameOfScheme(master.getNameOfScheme());
		if (data.getSchemeId() != null) {
			data.setSchemeId(master.getScheme().getId());
		}
		data.setSeasonId(master.getSeasonId());
		data.setSchemeId(master.getScheme().getId());
		if (master.getTypeOfScheme() != null) {
			data.setTypeOfScheme(master.getTypeOfScheme().getValue());
		}
		return data;
	}

}
