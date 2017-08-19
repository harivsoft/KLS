/**
 * 
 */
package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.Calendar;

/**
 * @author a1621
 *
 */
public interface ICalendarDAO {

	List<Calendar> getCalendar(Integer pacsId, String businessDate);

	void newCalendar(List<Long> deleteList, List<Calendar> calendars, Integer pacsId);

	Calendar getCalendarForDate(Integer pacsId, String date);

}
