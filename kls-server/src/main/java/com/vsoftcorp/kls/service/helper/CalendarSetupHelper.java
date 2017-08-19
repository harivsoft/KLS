package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.CalendarSetup;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.CalendarSetupData;

public class CalendarSetupHelper {

	public static CalendarSetup getCalendarSetup(CalendarSetupData data) {
		CalendarSetup calendarSetup = new CalendarSetup();
		calendarSetup.setBranchId(Integer.parseInt(data.getLoggedInUserDetailsData().getBranchId()));
		calendarSetup.setLastDate(DateUtil.getVSoftDateByString(data.getLastDate()));
		if(data.getLoggedInUserDetailsData().getIsBankUser()){
			data.getLoggedInUserDetailsData().setPacsId("0");
		}
		calendarSetup.setPacsId(Integer.parseInt(data.getLoggedInUserDetailsData().getPacsId()));
		calendarSetup.setWeeklyOff(data.getWeeklyOff());
		if(data.getId()!=null){
			calendarSetup.setId(Integer.valueOf(data.getId().toString()));
		}
		return calendarSetup;
	}

	public static CalendarSetupData getCalendarSetup(CalendarSetup calendarSetup) {
		CalendarSetupData data  = new CalendarSetupData();
		if(calendarSetup.getLastDate() != null){
			data.setLastDate(DateUtil.getDateByAddingNoOfDays(DateUtil.getDateString(calendarSetup.getLastDate()),1));
		}
		data.setWeeklyOff(calendarSetup.getWeeklyOff());
		if(calendarSetup.getId()!=null){
			data.setId(Long.valueOf(calendarSetup.getId().toString()));
		}

		return data;
	}

}
