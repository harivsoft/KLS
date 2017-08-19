/**
 * 
 */
package com.vsoftcorp.kls.data;

import com.vsoftcorp.kls.data.CalendarData;
/**
 * @author a1621
 * 
 */
public class CalendarSetupData {

	public CalendarSetupData() {

	}

	private Long id;
	
	private String startDate;
	
	private String lastDate;

	private Integer weeklyOff;
	
	private CalendarData calendarData;
	
	private LoggedInUserDetailsData loggedInUserDetailsData;
	/**
	 * @return the lastDate
	 */
	public String getLastDate() {
		return lastDate;
	}

	/**
	 * @param lastDate the lastDate to set
	 */
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	/**
	 * @return the weeklyOff
	 */
	public Integer getWeeklyOff() {
		return weeklyOff;
	}

	/**
	 * @param weeklyOff the weeklyOff to set
	 */
	public void setWeeklyOff(Integer weeklyOff) {
		this.weeklyOff = weeklyOff;
	}

	/**
	 * @return the calendarData
	 */
	public CalendarData getCalendarData() {
		return calendarData;
	}

	/**
	 * @param calendarData the calendarData to set
	 */
	public void setCalendarData(CalendarData calendarData) {
		this.calendarData = calendarData;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the loggedInUserDetailsData
	 */
	public LoggedInUserDetailsData getLoggedInUserDetailsData() {
		return loggedInUserDetailsData;
	}

	/**
	 * @param loggedInUserDetailsData the loggedInUserDetailsData to set
	 */
	public void setLoggedInUserDetailsData(LoggedInUserDetailsData loggedInUserDetailsData) {
		this.loggedInUserDetailsData = loggedInUserDetailsData;
	}

}
