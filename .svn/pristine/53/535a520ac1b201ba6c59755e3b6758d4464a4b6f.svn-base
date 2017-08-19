/**
 * 
 */
package com.vsoftcorp.kls.valuetypes.calendar;

import java.util.HashMap;
import java.util.Map;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author a1621
 * 
 */
public enum EventType implements ParameterizedEnum<EventType, String> {
	WeeklyOff("Weekly Off"), Holiday("Holiday"), ForceHoliday("Force Holiday"), DayEnd(
			"Day End"), MonthEnd("Month End"), QuarterEnd("Quarter End"), HalfYearEnd(
			"Half Year End"), YearEnd("Year End");
	private String value;

	private EventType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static EventType getType(String value) {
		EventType[] types = values();
		for (EventType type : types) {
			if (type.toString().equalsIgnoreCase(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for day enum");
	}

	private static final Map<String, EventType> lookup = new HashMap<String, EventType>();
	static {
		for (EventType d : EventType.values())
			lookup.put(d.getValue(), d);
	}

	public static EventType get(String abbreviation) {
		return lookup.get(abbreviation);
	}

}
