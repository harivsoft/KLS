/**
 * 
 */
package com.vsoftcorp.kls.service;

import com.vsoftcorp.kls.data.CalendarSetupData;

/**
 * @author a1621
 * 
 */
public interface ICalendarSetupService {

	public void setupCalendar(CalendarSetupData calendarSetupData);

	public CalendarSetupData getCalendarSetup(Integer pacsId);

}
