/**
 * 
 */
package com.vsoftcorp.kls.valuetypes.loanproduct;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author a9152
 * 
 */
public enum RepaymentType implements ParameterizedEnum<RepaymentType, String> {

	EQUATED_INSTALLMENTS("E"), NON_EQUATED_INSTALLMENTS("O"), NOT_APPLICABLE("N");

	private String value;

	private RepaymentType(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static RepaymentType getType(String value) {
		RepaymentType[] repaymentTypes = values();
		for (RepaymentType type : repaymentTypes) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for RepaymentType Enum");
	}
}
