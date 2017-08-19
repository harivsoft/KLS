package com.vsoftcorp.kls.service.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.vsoftcorp.kls.business.entities.Calendar;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.CalendarData;
import com.vsoftcorp.kls.data.CalendarSetupData;
import com.vsoftcorp.kls.data.NewCalendarData;
import com.vsoftcorp.kls.valuetypes.calendar.CalendarType;
import com.vsoftcorp.kls.valuetypes.calendar.EventType;

public class CalendarHelper {

	public static List<Calendar> getCalendar(CalendarSetupData data) {
		List<Calendar> calendars = new ArrayList<Calendar>();
		Integer day = DateUtil.getDayOfWeek(data.getStartDate());
		while(day != data.getWeeklyOff()){
			data.setStartDate(DateUtil.getDateByAddingNoOfDays(data.getStartDate(), 1));;
			day = (day+1)%7;
		}
		while(DateUtil.getVSoftDateByString(data.getStartDate()).compareTo(DateUtil.getVSoftDateByString(data.getLastDate())) <= 0){
			Calendar calendar = new Calendar();
			calendar.setDate(DateUtil.getVSoftDateByString(data.getStartDate()));
			calendar.setDescription(null);
			calendar.setTitle("WeeklyOff");
			calendar.setType(0);
			calendars.add(calendar);
			data.setStartDate(DateUtil.getDateByAddingNoOfDays(data.getStartDate(), 7));
			
		}
		return calendars;
	}

	public static List<CalendarData> getCalendar(List<Calendar> calendar) {
		List<CalendarData> calendarDatas = new ArrayList<CalendarData>();
		for(Calendar data:calendar){
			CalendarData calendarData = new CalendarData();
			List<String> className = new LinkedList<String>(Arrays.asList(CalendarType.getType(data.getType()).toString()));
			if(data.getType()==0)
				className.add("bg-color-red");
			else if(data.getType()==1)
				className.add("bg-color-greenLight");
			else if(data.getType()==2)
				className.add("bg-color-blue");
			calendarData.setClassName(className);
			calendarData.setDate(DateUtil.getDateString(data.getDate()));
			calendarData.setDescription(data.getDescription());
			calendarData.setId(data.getId());
			calendarData.setTitle(EventType.getType(data.getTitle()).getValue());
			calendarData.setTypeId(data.getType());
			calendarData.setTypeName(CalendarType.getType(data.getType()).toString());
			
			calendarDatas.add(calendarData);
		}
		return calendarDatas;
	}

	public static List<Long> getDeleteList(List<Long> deleteList) {
		List<Long> list = new ArrayList<Long>();
		for(Long long1: deleteList){
			list.add(long1);
		}
		return list;
	}

	public static List<Calendar> getCalendar(NewCalendarData data) {
		List<Calendar> calendars = new ArrayList<Calendar>();
		for(CalendarData calendar: data.getCalendar()){
			Calendar calendar2 = new Calendar();
			calendar2.setDate(DateUtil.getVSoftDateByString(data.getSelectedDate()));
			calendar2.setDescription(calendar.getDescription());
			calendar2.setTitle(EventType.get(calendar.getTitle()).toString());
			String className = calendar.getClassName().get(0);
			CalendarType type = CalendarType.getType(className);
			calendar2.setType(type.getValue());
			calendars.add(calendar2);
		}
		return calendars;
	}

	public static CalendarData getCalendarForDate(Calendar data) {
			CalendarData calendarData = new CalendarData();
			if(data.getType() != null){
				List<String> className = new LinkedList<String>(Arrays.asList(CalendarType.getType(data.getType()).toString()));
				if(data.getType()==0)
					className.add("bg-color-red");
				else if(data.getType()==1)
					className.add("bg-color-greenLight");
				else if(data.getType()==2)
					className.add("bg-color-blue");
				calendarData.setClassName(className);
				calendarData.setTypeId(data.getType());
				calendarData.setTypeName(CalendarType.getType(data.getType()).toString());
			
			}
			if(data.getDate() != null){
				calendarData.setDate(DateUtil.getDateString(data.getDate()));
			}
			calendarData.setDescription(data.getDescription());
			calendarData.setId(data.getId());
			if(data.getTitle() != null){
				calendarData.setTitle(EventType.getType(data.getTitle()).getValue());
			}			
			return calendarData;
	}


}
