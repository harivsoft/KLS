/**
 * 
 */
package com.vsoftcorp.kls.valuetypes.account;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author a9152
 * 
 */
public enum OperatorType implements ParameterizedEnum<OperatorType, String> {

	SELF("S"), SELF_AND_ATTORNEY("A"), GUARDIAN("G");

	private String value;

	private OperatorType(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static OperatorType getType(String value) {
		OperatorType[] accountTypes = values();
		for (OperatorType type : accountTypes) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for OperatorType Enum");
	}
}