/**
 * 
 */
package com.vsoftcorp.kls.valuetypes.loanproduct;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author a9152
 * 
 */
public enum DisbursementType implements ParameterizedEnum<DisbursementType, String> {

	SINGLE("S"), MULTIPLE("M"), NOT_APPLICABLE("N");

	private String value;

	private DisbursementType(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static DisbursementType getType(String value) {
		DisbursementType[] disbursementTypes = values();
		for (DisbursementType type : disbursementTypes) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for DisbursementType Enum");
	}

}
