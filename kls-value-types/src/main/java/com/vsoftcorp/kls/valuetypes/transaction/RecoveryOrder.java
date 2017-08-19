package com.vsoftcorp.kls.valuetypes.transaction;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author sponnam
 * 
 */
public enum RecoveryOrder implements ParameterizedEnum<RecoveryOrder, Integer> {

	PRINCIPAL(1), INTEREST(2), PENAL_INTEREST(3), CHARGES(4);

	private Integer value;

	private RecoveryOrder(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static RecoveryOrder getType(Integer value) {
		RecoveryOrder[] orders = values();
		for (RecoveryOrder order : orders) {
			if (order.getValue().equals(value))
				return order;
		}
		throw new UnsupportedOperationException("Invalid value for order Enum");
	}

	public static RecoveryOrder valueOf(Integer theValue) {
		if (theValue.equals(Integer.valueOf(1))) {
			return RecoveryOrder.PRINCIPAL;
		} else if (theValue.equals(Integer.valueOf(2))) {
			return RecoveryOrder.INTEREST;
		} else if (theValue.equals(Integer.valueOf(3))) {
			return RecoveryOrder.PENAL_INTEREST;
		} else if (theValue.equals(Integer.valueOf(4))) {
			return RecoveryOrder.CHARGES;
		} else {
			throw new RuntimeException(RecoveryOrder.class.getName()
					+ " Enum does not support this value: " + theValue);
		}
	}

	public String getAsString(Integer theValue) {
		if (theValue.equals(Integer.valueOf(1))) {
			return "Principal";
		} else if (theValue.equals(Integer.valueOf(2))) {
			return "Interest";
		} else if (theValue.equals(Integer.valueOf(3))) {
			return "Penal Interest";
		} else if (theValue.equals(Integer.valueOf(4))) {
			return "Charges";
		} else {
			throw new RuntimeException(RecoveryOrder.class.getName()
					+ " Enum does not support this value: " + theValue);
		}
	}
}
