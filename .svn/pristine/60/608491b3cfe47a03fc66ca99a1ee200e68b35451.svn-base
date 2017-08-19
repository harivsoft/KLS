package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.SeasonDefinition;
import com.vsoftcorp.kls.data.SeasonMasterData;

public class SeasonMasterHelper {

	public static SeasonMasterData getSeasonData(SeasonDefinition theMaster) {
		SeasonMasterData data = new SeasonMasterData(); 
		data.setId(theMaster.getId().toString());
		data.setName(theMaster.getName()); 
		data.setDrawalStartDay(theMaster.getDrawalStartDay());
		data.setDrawalStartMonth(theMaster.getDrawalStartMonth());
		data.setDrawalEndDuration(theMaster.getDrawalEndDuration());
		data.setDueDuration(theMaster.getLoanOverdueDuration());
		data.setDueDateMethod(theMaster.getDueDateMethod());
		data.setDueDateInMonths(theMaster.getDueDateInMonths());
		return data;
	}

	public static SeasonDefinition getSeason(SeasonMasterData data) {
		SeasonDefinition master = new SeasonDefinition();
		if (data.getId() != null)
			master.setId(Integer.parseInt(data.getId()));
		master.setName(data.getName());
		master.setDrawalStartDay(data.getDrawalStartDay());
		master.setDrawalStartMonth(data.getDrawalStartMonth());
		master.setDrawalEndDuration(data.getDrawalEndDuration());
		master.setLoanOverdueDuration(data.getDueDuration());
		master.setDueDateInMonths(data.getDueDateInMonths());
		master.setDueDateMethod(data.getDueDateMethod());
		return master;
	}
}
