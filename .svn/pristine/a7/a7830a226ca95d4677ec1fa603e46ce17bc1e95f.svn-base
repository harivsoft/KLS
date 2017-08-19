/**
 * 
 */
package com.vsoftcorp.kls.valuetypes.calendar;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author a1621
 *
 */
public enum CalendarType implements ParameterizedEnum<CalendarType, Integer> {
	WeeklyOff(0),Event(1),Holiday(2);
	
	private Integer value;
	
	private CalendarType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static CalendarType getType(Integer value) {
		CalendarType[] types = values();
		for (CalendarType type : types) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for day enum");
	}
	
	public static CalendarType getType(String value) {
		CalendarType[] types = values();
		for (CalendarType type : types) {
			if (type.toString().equalsIgnoreCase(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for day enum");
	}
	
	
}
