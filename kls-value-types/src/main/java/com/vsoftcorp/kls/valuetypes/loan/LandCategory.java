package com.vsoftcorp.kls.valuetypes.loan;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

public enum LandCategory implements ParameterizedEnum<LandCategory, String> {
	
	TAMPLELAND("TL"), CLASS1("C1"),CLASS2("C2"), REWARD("RW");
	
	private String value;
	
	private LandCategory(final String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static LandCategory getType(String value) {
		
		LandCategory[] landCategory = values();
		
		for(LandCategory category : landCategory) {
			if(category.getValue().equals(value))
				return category;
		}
		throw new UnsupportedOperationException("Invalid value for Land Category Enum");
	}
	
	@Override
	public String toString() {

		return value;
	}
	
	public String getName() {
		return name();
	}

}
