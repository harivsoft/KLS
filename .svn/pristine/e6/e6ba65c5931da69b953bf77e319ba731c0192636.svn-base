/**
 * 
 */
package com.vsoftcorp.kls.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Calendar;
import com.vsoftcorp.kls.business.entities.CalendarSetup;
import com.vsoftcorp.kls.data.CalendarSetupData;
import com.vsoftcorp.kls.dataaccess.ICalendarSetupDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.ICalendarSetupService;
import com.vsoftcorp.kls.service.helper.CalendarHelper;
import com.vsoftcorp.kls.service.helper.CalendarSetupHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

/**
 * @author a1621
 *
 */
public class CalendarSetupService implements ICalendarSetupService{

	private static final Logger logger = Logger.getLogger(CalendarSetupService.class);
	
	@Override
	public void setupCalendar(CalendarSetupData data) {
		logger.info("Start : Calling setupCalendar() in CalendarSetupService method.");
		ICalendarSetupDAO calendarSetupDAO = KLSDataAccessFactory.getCalendarSetupDAO();
		
		try{ 
			CalendarSetup master = CalendarSetupHelper.getCalendarSetup(data);
			List<Calendar> calendars = CalendarHelper.getCalendar(data);
			calendarSetupDAO.setupCalendar(master,calendars);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error While setupCalendar() method..");
			throw new DataAccessException("Error While setupCalendar() method ", e.getCause());
		}
		logger.info("Start : Calling setupCalendar() in CalendarSetupService method.");
	}

	@Override
	public CalendarSetupData getCalendarSetup(Integer pacsId) {
		logger.info("Start: Calling  getCalendarSetup() method .");
		CalendarSetupData data = new CalendarSetupData();
		ICalendarSetupDAO calendarSetupDAO = KLSDataAccessFactory.getCalendarSetupDAO();
		try{ 
			data = CalendarSetupHelper.getCalendarSetup(calendarSetupDAO.getCalendarSetup(pacsId));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error While setupCalendar() method..");
			throw new DataAccessException("Error While updating setupCalendar data", e.getCause());
		}
		logger.info("End: Calling  getCalendarSetup() method .");
		return data;
	}

}
