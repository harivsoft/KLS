/**
 * 
 */
package com.vsoftcorp.kls.valuetypes.account;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author a9152
 * 
 */
public enum AccountType implements ParameterizedEnum<AccountType, String> {

	SINGLE("S"), EITHER_OR_SURVIVOR("E"), FORMER_OR_SURVIVOR("F"), JOINT("J"), ORGANIZATIONAL("O");

	private String value;

	private AccountType(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static AccountType getType(String value) {
		AccountType[] accountTypes = values();
		for (AccountType type : accountTypes) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for AccountType Enum");
	}
}
