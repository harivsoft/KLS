package com.vsoftcorp.kls.valuetypes;

/**
 * @author sponnam
 *
 */
public enum PeriodType implements
ParameterizedEnum<PeriodType, String>{
	Days("D"),Months("M"),Years("Y");
	
	
	public static PeriodType getType(String theValue) {
		PeriodType[] productType = values();
		try{
		for (PeriodType type : productType ) {
			if (type.getValue().equals(theValue))
				return type;
		}
		}
		catch (UnsupportedOperationException e) {
			return Days;
		}
		return Days;
	}

	@Override
	public String getValue() {
		return value;
	}

	private String value;

	

	private PeriodType(String theValue) {
		value = theValue;
	}
	@Override
	public String toString() {

		return name();
	}

	public String getName() {
		return name();
	}


}
