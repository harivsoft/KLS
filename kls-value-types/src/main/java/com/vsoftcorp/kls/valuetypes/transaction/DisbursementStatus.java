package com.vsoftcorp.kls.valuetypes.transaction;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;
/**
 * 
 * @author a1623
 *
 */
public enum DisbursementStatus implements ParameterizedEnum<DisbursementStatus, Integer> {
	Entry(1), Passed(2), Fail(3), SUCCESS(4);



	private Integer value;

	private DisbursementStatus(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static DisbursementStatus getType(Integer value) {
		DisbursementStatus[] statusArray = values();
		for (DisbursementStatus status : statusArray) {
			if (status.getValue().equals(value))
				return status;
		}
		throw new UnsupportedOperationException("Invalid value for order Enum");
	}

	public static DisbursementStatus valueOf(Integer theValue) {
		if (theValue.equals(Integer.valueOf(1))) {
			return DisbursementStatus.Entry;
		} else if (theValue.equals(Integer.valueOf(2))) {
			return DisbursementStatus.Passed;
		} else if (theValue.equals(Integer.valueOf(3))) {
			return DisbursementStatus.Fail;
		} else if (theValue.equals(Integer.valueOf(4))) {
			return DisbursementStatus.SUCCESS;
		} else {
			throw new RuntimeException(DisbursementStatus.class.getName()
					+ " Enum does not support this value: " + theValue);
		}
	}

	public String getAsString(Integer theValue) {
		if (theValue.equals(Integer.valueOf(1))) {
			return "Entry";
		} else if (theValue.equals(Integer.valueOf(2))) {
			return "Passed";
		} else if (theValue.equals(Integer.valueOf(3))) {
			return "Fail";
		} else if (theValue.equals(Integer.valueOf(4))) {
			return "SUCCESS";
		} else {
			throw new RuntimeException(DisbursementStatus.class.getName()
					+ " Enum does not support this value: " + theValue);
		}
	}

}
