package com.vsoftcorp.kls.valuetypes.subsidy;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

public enum GeographicalArea implements ParameterizedEnum<GeographicalArea, String> {
	Nation_Wide("N"), State("S");

	public static GeographicalArea getType(String theValue) {
		GeographicalArea[] geographicalArea = values();
		try {
			for (GeographicalArea type : geographicalArea) {
				if (type.getValue().equals(theValue))
					return type;
			}
		} catch (UnsupportedOperationException e) {
			return Nation_Wide;
		}
		return Nation_Wide;
	}

	@Override
	public String getValue() {
		return value;
	}

	private String value;

	private GeographicalArea(String theValue) {
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
