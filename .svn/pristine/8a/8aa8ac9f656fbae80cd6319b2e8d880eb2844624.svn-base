package com.vsoftcorp.kls.valuetypes;

/**
 * @author sponnam
 * 
 */
public enum BankProcessStatus implements
		ParameterizedEnum<BankProcessStatus, Integer> {
	DAY_BEGIN(1), PRE_DAY_END(2), DAY_END(3), DAY_BEGIN_IN_PROGRESS(4), DAY_END_IN_PROGRESS(
			5);

	public static BankProcessStatus getType(Integer theValue) {
		BankProcessStatus[] bankProcessStatus = values();
		try {
			for (BankProcessStatus status : bankProcessStatus) {
				if (status.getValue().equals(theValue))
					return status;
			}
		} catch (UnsupportedOperationException e) {
			return DAY_BEGIN;
		}
		return DAY_BEGIN;
	}

	@Override
	public Integer getValue() {
		return value;
	}

	private Integer value;

	private BankProcessStatus(Integer theValue) {
		value = theValue;
	}

	public String getName() {
		return name();
	}
}
