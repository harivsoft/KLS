/**
 * 
 */
package com.vsoftcorp.kls.valuetypes.loanproduct;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author a9152
 * 
 */
public enum InterestPostingFrequency implements ParameterizedEnum<InterestPostingFrequency, String> {

	MONTHLY("M"), QUATERLY("Q"), HALF_YEARLY("H"), YEARLY("Y"), NOT_APPLICABLE("N"),UNDEFINED("U");

	private String value;

	private InterestPostingFrequency(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static InterestPostingFrequency getType(String value) {
		InterestPostingFrequency[] intPostingFreqTypes = values();
		for (InterestPostingFrequency type : intPostingFreqTypes) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for InterestPostingFrequency Enum");
	}
}
