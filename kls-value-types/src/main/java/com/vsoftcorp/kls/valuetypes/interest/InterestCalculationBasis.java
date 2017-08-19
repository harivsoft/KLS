package com.vsoftcorp.kls.valuetypes.interest;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author sponnam
 *
 */
public enum InterestCalculationBasis implements
		ParameterizedEnum<InterestCalculationBasis, String> {
	Daily("D"), Monthly("M"), Quarterly("Q"), Yearly("Y"), Halfyearly("H");

	public static InterestCalculationBasis getType(String theValue) {
		InterestCalculationBasis[] intposting = values();
		try {
			for (InterestCalculationBasis type : intposting) {
				if (type.getValue().equals(theValue))
					return type;
			}
		} catch (UnsupportedOperationException e) {
			return Daily;
		}
		return Monthly;

	}

	@Override
	public String getValue() {
		return value;
	}

	private String value;

	private InterestCalculationBasis(String theValue) {
		value = theValue;
	}

	@Override
	public String toString() {

		return name();
	}

	public String getName() {
		return name();
	}

}
