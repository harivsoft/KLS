package com.vsoftcorp.kls.valuetypes.loanproduct;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

public enum InterestFrequency implements ParameterizedEnum<InterestFrequency, String> {

	MONTHLY("M"), QUATERLY("Q"), HALF_YEARLY("H"), YEARLY("Y"), NOT_APPLICABLE("N");

	private String value;

	private InterestFrequency(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static InterestFrequency getType(String value) {
		InterestFrequency[] intFreqequency = values();
		for (InterestFrequency type : intFreqequency) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for InterestPostingFrequency Enum");
	}


}

