package com.vsoftcorp.kls.valuetypes.subsidy;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

public enum InterestSubsidyPostingOn implements
		ParameterizedEnum<TypeOfInstitute, String> {
	ONRECEIPT("R"), ONACCOUNT("A");

	public static InterestSubsidyPostingOn getType(String theValue) {
		InterestSubsidyPostingOn[] intSubsidyPostingOn = values();
		try {
			for (InterestSubsidyPostingOn type : intSubsidyPostingOn) {
				if (type.getValue().equals(theValue))
					return type;
			}
		} catch (UnsupportedOperationException e) {
			return ONRECEIPT;
		}
		return ONRECEIPT;
	}

	@Override
	public String getValue() {
		return value;
	}

	private String value;

	private InterestSubsidyPostingOn(String theValue) {
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
