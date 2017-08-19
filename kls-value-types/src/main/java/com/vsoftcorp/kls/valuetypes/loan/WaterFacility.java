package com.vsoftcorp.kls.valuetypes.loan;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

public enum WaterFacility implements ParameterizedEnum<WaterFacility, String> {
	
	RIVER("R"), CANAL("C"), BOREWELL("B"), WELL("W");
	
	private String value;
	
	private WaterFacility(final String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static WaterFacility getType(String value) {
		WaterFacility[] waterFacility = values();
		for(WaterFacility facility : waterFacility) {
			if(facility.getValue().equals(value))
				return facility;
		}
		throw new UnsupportedOperationException("Invalid value for Water Facility Enum");
	}
	
	@Override
	public String toString() {

		return value;
	}
	
	public String getName() {
		return name();
	}
}
