/**
 * 
 */
package com.vsoftcorp.kls.valuetypes.loanproduct;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author a9152
 * 
 */
public enum InterestType implements ParameterizedEnum<InterestType, String> {

	SIMPLE("S"), COMPOUND("C"), NOT_APPLICABLE("N");

	private String value;

	private InterestType(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static InterestType getType(String value) {
		InterestType[] interestTypes = values();
		for (InterestType type : interestTypes) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for InterestType Enum");
	}
}
