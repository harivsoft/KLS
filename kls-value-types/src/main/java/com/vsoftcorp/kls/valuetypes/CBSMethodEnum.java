package com.vsoftcorp.kls.valuetypes;

public enum CBSMethodEnum implements ParameterizedEnum<CBSMethodEnum, String> {
	
	PROPRIETARY("P"), ISO8583("I"), OFFLINE("O");
	
	public static CBSMethodEnum getType(String theValue) {
		CBSMethodEnum[] cbsMethodEnum = values();
		try{
			for(CBSMethodEnum cbsMethod : cbsMethodEnum) {
				if (cbsMethod.getValue().equals(theValue))
					return cbsMethod;
			}
		}catch (UnsupportedOperationException e) {
			return PROPRIETARY;
		}
		return PROPRIETARY;
	}

	@Override
	public String getValue() {
		return value;
	}

	private String value;
	
	private CBSMethodEnum(String theValue) {
		value = theValue;
	}
	
	@Override
	public String toString() {

		return value;
	}

	public String getName() {
		return name();
	}

}
