/**
 * 
 */
package com.vsoftcorp.kls.valuetypes.loanproduct;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author a9152
 * 
 */
public enum PenalInterestApplicable implements ParameterizedEnum<PenalInterestApplicable, String> {

	OUTSTANDING("O"), OVERDUE("V"), NOT_APPLICABLE("N");

	private String value;

	private PenalInterestApplicable(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static PenalInterestApplicable getType(String value) {
		PenalInterestApplicable[] penalIntApplicableTypes = values();
		for (PenalInterestApplicable type : penalIntApplicableTypes) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for PenalInterestApplicable Enum");
	}
}
