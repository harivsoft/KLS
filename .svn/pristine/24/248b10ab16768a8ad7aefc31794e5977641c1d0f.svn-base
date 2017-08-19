/**
 * 
 */
package com.vsoftcorp.kls.valuetypes.account;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author a9152
 * 
 */
public enum OperatingInstructionsType implements ParameterizedEnum<OperatingInstructionsType, String> {

	SELF("S"), ANYONE("A"), ALL_MUST("M");

	private String value;

	private OperatingInstructionsType(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static OperatingInstructionsType getType(String value) {
		OperatingInstructionsType[] accountTypes = values();
		for (OperatingInstructionsType type : accountTypes) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for OperatingInstructionsType Enum");
	}
}
