/**
 * 
 */
package com.vsoftcorp.kls.valuetypes.charges;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author a9152
 * 
 */
public enum ChargeType implements ParameterizedEnum<ChargeType, String> {
	SHARE("S"), INSURANCE("I"), SUBSIDY("U");

	public static ChargeType getType(String theValue) {
		ChargeType[] chargeTypeArray = values();
		try {
			for (ChargeType chargeType : chargeTypeArray) {
				if (chargeType.getValue().equals(theValue))
					return chargeType;
			}
		} catch (UnsupportedOperationException e) {
			return SHARE;
		}
		return SHARE;
	}

	private String value;

	@Override
	public String getValue() {
		return value;
	}

	private ChargeType(String theValue) {
		value = theValue;
	}

	public String getName() {
		return name();
	}
}