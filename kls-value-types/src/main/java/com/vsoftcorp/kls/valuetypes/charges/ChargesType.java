package com.vsoftcorp.kls.valuetypes.charges;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

public enum ChargesType implements ParameterizedEnum<ChargesType, Integer> {
	Fixed(0), Variable(1);

	public static ChargesType getType(Integer theValue) {
		ChargesType[] chargesType = values();
		try {
			for (ChargesType chargeType : chargesType) {
				if (chargeType.getValue().equals(theValue))
					return chargeType;
			}
		} catch (UnsupportedOperationException e) {
			return Fixed;
		}
		return Fixed;
	}

	private Integer value;

	@Override
	public Integer getValue() {
		return value;
	}

	private ChargesType(Integer theValue) {
		value = theValue;
	}

	public String getName() {
		return name();
	}
}
