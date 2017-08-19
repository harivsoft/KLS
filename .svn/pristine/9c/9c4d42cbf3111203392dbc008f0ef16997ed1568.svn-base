/**
 * 
 */
package com.vsoftcorp.kls.valuetypes.loan.repayment;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author a9152
 * 
 */
public enum LoanRepaymentEditType implements ParameterizedEnum<LoanRepaymentEditType, Integer> {

	PRINCIPAL(1), EQUATED(2), NON_EQUATED(3);

	/** This enum used only for UI editing of the loan repayment schedule list. **/
	private Integer value;

	private LoanRepaymentEditType(final Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static LoanRepaymentEditType getType(Integer value) {
		LoanRepaymentEditType[] repaymentScheduleTypes = values();
		for (LoanRepaymentEditType type : repaymentScheduleTypes) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for LoanRepaymentEditType Enum");
	}
}