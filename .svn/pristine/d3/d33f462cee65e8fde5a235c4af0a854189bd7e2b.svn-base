package com.vsoftcorp.kls.business.util;

import com.vsoftcorp.IParameterizedEnum;

public enum Period implements IParameterizedEnum<Period, String> {
	DAYS("D"), MONTHS("M"), YEAR("Y");

	private String value;

	private Period(String theValue) {
		value = theValue;
	}

	public String getValue() {
		return value;
	}

	public String toString() {
		return this.name();
	}

	public String getName() {
		return name();
	}

	public static Period toPeriod(String thePeriodIn) {

		Period returnedPeriod = null;
		if (thePeriodIn.equalsIgnoreCase("D")) {
			returnedPeriod = Period.DAYS;
		}
		if (thePeriodIn.equalsIgnoreCase("M")) {
			returnedPeriod = Period.MONTHS;
		}
		if (thePeriodIn.equalsIgnoreCase("Y")) {
			returnedPeriod = Period.YEAR;
		}
		return returnedPeriod;

	}

}
