package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Calendar;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.CalendarData;
import com.vsoftcorp.kls.data.CalendarSetupData;
import com.vsoftcorp.kls.data.LoggedInUserDetailsData;
import com.vsoftcorp.kls.data.NewCalendarData;
import com.vsoftcorp.kls.dataaccess.ICalendarDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.ICalendarService;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.CalendarHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.valuetypes.calendar.EventType;

public class CalendarService implements ICalendarService {

	private static final Logger logger = Logger
			.getLogger(CalendarSetupService.class);

	@Override
	public List<CalendarData> getCalendar(
			LoggedInUserDetailsData loggedInUserDetailsData) {
		logger.info("Start: Calling  getCalendar() method .");
		List<CalendarData> data = new ArrayList<CalendarData>();
		ICalendarDAO calendarDAO = KLSDataAccessFactory.getCalendarDAO();
		try {
			data = CalendarHelper.getCalendar(calendarDAO.getCalendar(
					Integer.parseInt(loggedInUserDetailsData.getPacsId()),
					loggedInUserDetailsData.getBusinessDate()));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error While setupCalendar() method..");
			throw new DataAccessException(
					"Error While updating setupCalendar data", e.getCause());
		}
		logger.info("End: Calling  getCalendarSetup() method .");
		return data;
	}

	@Override
	public void newCalendar(NewCalendarData data) {
		logger.info("Start : Calling setupCalendar() in CalendarSetupService method.");
		ICalendarDAO calendarDAO = KLSDataAccessFactory.getCalendarDAO();

		try {
			List<Long> deleteList = CalendarHelper.getDeleteList(data
					.getDeleteList());
			List<Calendar> calendars = CalendarHelper.getCalendar(data);
			if(data.getUserDetailsData().getIsBankUser()){
				data.getUserDetailsData().setPacsId("0");
			}
			calendarDAO.newCalendar(deleteList, calendars, Integer.parseInt(data
					.getUserDetailsData().getPacsId()));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error While setupCalendar() method..");
			throw new DataAccessException(
					"Error While setupCalendar() method ", e.getCause());
		}
		logger.info("Start : Calling setupCalendar() in CalendarSetupService method.");

	}

	@Override
	public Map getNextBusinessDate(String date, Integer pacsId) {
		logger.info("date:"+date);
		Map<String, String> map = new HashMap<>();
		try {
			CalendarSetupData calendarSetupData = KLSServiceFactory
					.getCalendarSetupService().getCalendarSetup(pacsId);
			CalendarData calendarCurrentData = new CalendarData();
			calendarCurrentData = getCalendarForDate(pacsId, date);
			if(calendarCurrentData.getId() == null){
				calendarCurrentData.setTitle(EventType.DayEnd.getValue());
			}
			if (DateUtil.compareDate(date, calendarSetupData.getLastDate()) < 0) {
				map.put("isValid", "false");
				boolean b = false;
				while (DateUtil.compareDate(
						date,
						DateUtil.getDateBySubtractingNoOfDays(
								calendarSetupData.getLastDate(), 1)) < 0
						&& !b) {
					date = DateUtil.getDateByAddingNoOfDays(date, 1);
					CalendarData calendarData = new CalendarData();
					calendarData = getCalendarForDate(pacsId, date);
					if (calendarData.getId() == null) {
						map.put("nextBusinessDate", date);
						map.put("isValid", "true");
						map.put("type", EventType.get(calendarCurrentData.getTitle()).toString());
						return map;
					}
					if (EventType.get(calendarData.getTitle()) != EventType.WeeklyOff
							&& EventType.get(calendarData.getTitle()) != EventType.ForceHoliday
							&& EventType.get(calendarData.getTitle()) != EventType.Holiday) {
						map.put("nextBusinessDate", date);
						map.put("isValid", "true");
						map.put("type", EventType.get(calendarCurrentData.getTitle())
								.toString());
						b = true;
					}

				}
			} else {
				map.put("isValid", "false");
			}
		} catch (Exception exception) {
			map.put("isValid", "false");
			exception.printStackTrace();
		}
		return map;
	}

	@Override
	public CalendarData getCalendarForDate(Integer pacsId, String date) {
		logger.info("Start: Calling  getCalendarForDate() method .");
		CalendarData data = new CalendarData();
		ICalendarDAO calendarDAO = KLSDataAccessFactory.getCalendarDAO();
		try {
			data = CalendarHelper.getCalendarForDate(calendarDAO
					.getCalendarForDate(pacsId, date));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error While getCalendarForDate() method..");
			throw new DataAccessException(
					"Error While updating setupCalendar data", e.getCause());
		}
		logger.info("End: Calling  getCalendarForDate() method .");
		return data;
	}

}
