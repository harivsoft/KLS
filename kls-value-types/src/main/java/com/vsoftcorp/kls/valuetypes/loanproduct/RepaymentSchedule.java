/**
 * 
 */
package com.vsoftcorp.kls.valuetypes.loanproduct;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author a9152
 * 
 */
public enum RepaymentSchedule implements ParameterizedEnum<RepaymentSchedule, String> {

	PRINCIPAL("P"), PRINCIPAL_AND_INTEREST("I"), NOT_APPLICABLE("N");

	private String value;

	private RepaymentSchedule(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static RepaymentSchedule getType(String value) {
		RepaymentSchedule[] repaymentScheduleTypes = values();
		for (RepaymentSchedule type : repaymentScheduleTypes) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for RepaymentSchedule Enum");
	}
}
