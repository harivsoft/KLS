package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.business.entities.SeasonDefinition;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.SeasonData;
import com.vsoftcorp.kls.valuetypes.SeasonStatus;

public class SeasonHelper {

	public static SeasonData getSeasonData(Season theMaster) {

		SeasonData seasonData = new SeasonData();
		seasonData.setId(theMaster.getId().toString());
		seasonData.setName(theMaster.getName());

		seasonData.setOverdueDate(DateUtil.getDateString(theMaster
				.getOverdueDate()));
		seasonData.setDrawalEndDate(DateUtil.getDateString(theMaster
				.getDrawalEndDate()));
		seasonData.setDrawalStartDate(DateUtil.getDateString(theMaster
				.getDrawalStartDate()));
		seasonData.setProcessStatus(theMaster.getProcessStatus().toString());
		seasonData.setRecoveryPeriod(theMaster.getRecoveryPeriod());
		seasonData.setDueDateMethod(theMaster.getDueDateMethod());
		seasonData.setDueDateInMonths(theMaster.getDueDateInMonths());
		
		return seasonData;
	}

	public static Season getSeason(SeasonData data) {
		Season season = new Season();

		if (data.getId() != null)
			season.setId(Long.parseLong(data.getId()));
		season.setDrawalEndDate(DateUtil.getVSoftDateByString(data
				.getDrawalEndDate()));

		season.setDrawalStartDate(DateUtil.getVSoftDateByString(data
				.getDrawalStartDate()));
		season.setName(data.getName());
		season.setOverdueDate(DateUtil.getVSoftDateByString(data
				.getOverdueDate()));
		season.setProcessStatus(SeasonStatus.getType(data.getProcessStatus()));
		season.setRecoveryPeriod(data.getRecoveryPeriod());
		season.setDueDateMethod(data.getDueDateMethod());
		season.setDueDateInMonths(data.getDueDateInMonths());
		return season;
	}

	public static SeasonData getSeasonDataByDef(
			SeasonDefinition seasonDefinition, String seasonYear) {
		SeasonData seasonData = new SeasonData();
		String startDate = seasonDefinition.getDrawalStartDay()+"/"+seasonDefinition.getDrawalStartMonth()+"/"+seasonYear.substring(0,4);
		
		String endDate = DateUtil.getDateByAddingNoOfMonths(startDate, seasonDefinition.getDrawalEndDuration()); 
		
		String overdueDate = DateUtil.getLastFridayOfMonth(DateUtil.getDateByAddingNoOfMonths(startDate, seasonDefinition.getLoanOverdueDuration()));
		
		seasonData.setOverdueDate(overdueDate);
		seasonData.setDrawalEndDate(endDate);
		seasonData.setDrawalStartDate(startDate);
		seasonData.setDueDateMethod(seasonDefinition.getDueDateMethod());
		seasonData.setDueDateInMonths(seasonDefinition.getDueDateInMonths());

		return seasonData;
	}

}