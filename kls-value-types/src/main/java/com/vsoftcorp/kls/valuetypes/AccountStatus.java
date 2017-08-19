package com.vsoftcorp.kls.valuetypes;

/**
 * @author sponnam
 *
 */
public enum AccountStatus implements ParameterizedEnum<AccountStatus, String> {
	Active("A"), DELETED("D"), INOPERATIVE("I"), CLOSED("C");

	public static AccountStatus getType(String theValue) {
		AccountStatus[] accountStatus = values();
		try {
			for (AccountStatus status : accountStatus) {
				if (status.getValue().equals(theValue))
					return status;
			}
		} catch (UnsupportedOperationException e) {
			return Active;
		}
		return Active;
	}

	@Override
	public String getValue() {
		return value;
	}

	private String value;

	private AccountStatus(String theValue) {
		value = theValue;
	}

	@Override
	public String toString() {

		return value;
	}

	public String getName() {
		return name();
	}
}
