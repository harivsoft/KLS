/**
 * 
 */
package com.vsoftcorp.kls.valuetypes.loanproduct;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author a9152
 * 
 */
public enum InterestCalculationMethod implements ParameterizedEnum<InterestCalculationMethod, String> {

	SANCTION_LIMIT("S"), OUTSTANDING_BALANCE("O"), NOT_APPLICABLE("N");

	private String value;

	private InterestCalculationMethod(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static InterestCalculationMethod getType(String value) {
		InterestCalculationMethod[] intCalcMethod = values();
		for (InterestCalculationMethod type : intCalcMethod) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for InterestCalculationMethod Enum");
	}
}
