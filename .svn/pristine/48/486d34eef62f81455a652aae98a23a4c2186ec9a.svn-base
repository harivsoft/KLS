/**
 * 
 */
package com.vsoftcorp.kls.valuetypes.loanapplication;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author a9152
 * 
 */
public enum InstallmentFrequency implements ParameterizedEnum<InstallmentFrequency, Integer> {

	MONTHLY(1), QUATERLY(3), HALF_YEARLY(6), YEARLY(12);

	private Integer value;

	private InstallmentFrequency(final Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static InstallmentFrequency getType(Integer value) {
		InstallmentFrequency[] installmentFreqTypes = values();
		for (InstallmentFrequency type : installmentFreqTypes) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for InstallmentFrequency Enum");
	}
}
