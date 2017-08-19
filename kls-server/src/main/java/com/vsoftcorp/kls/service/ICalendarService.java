package com.vsoftcorp.kls.service;

import java.util.List;
import java.util.Map;

import com.vsoftcorp.kls.data.CalendarData;
import com.vsoftcorp.kls.data.LoggedInUserDetailsData;
import com.vsoftcorp.kls.data.NewCalendarData;

public interface ICalendarService {

	List<CalendarData> getCalendar(LoggedInUserDetailsData userDetailsData);
	
	CalendarData getCalendarForDate(Integer pacsId, String date);

	void newCalendar(NewCalendarData data);

	public Map getNextBusinessDate(String date, Integer pacsId);

}
