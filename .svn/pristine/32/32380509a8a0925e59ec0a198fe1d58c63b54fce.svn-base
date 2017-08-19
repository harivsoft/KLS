/**
 * 
 */
package com.vsoftcorp.kls.valuetypes.loan;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author a9152
 * 
 */
public enum LoanType implements ParameterizedEnum<LoanType, String> {

	SHORT_TERM("ST"), MEDIUM_TERM("MT"), LONG_TERM("LT");

	private String value;

	private LoanType(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static LoanType getType(String value) {
		LoanType[] loanTypes = values();
		for (LoanType type : loanTypes) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for LoanType Enum");
	}
	
	@Override
	public String toString() {

		return value;
	}
	
	public String getName() {
		return name();
	}
}
