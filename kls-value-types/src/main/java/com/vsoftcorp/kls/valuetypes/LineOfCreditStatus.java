package com.vsoftcorp.kls.valuetypes;

/**
 * @author sponnam
 * 
 */
public enum LineOfCreditStatus implements
		ParameterizedEnum<LineOfCreditStatus, Integer> {
	Active(1), Closed(0);

	public static LineOfCreditStatus getType(Integer theValue) {
		LineOfCreditStatus[] lineOfCreditStatus = values();
		try {
			for (LineOfCreditStatus status : lineOfCreditStatus) {
				if (status.getValue().equals(theValue))
					return status;
			}
		} catch (UnsupportedOperationException e) {
			return Active;
		}
		return Active;
	}

	private Integer value;

	@Override
	public Integer getValue() {
		return value;
	}

	private LineOfCreditStatus(Integer theValue) {
		value = theValue;
	}

	public String getName() {
		return name();
	}
}
