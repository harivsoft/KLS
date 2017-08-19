package com.vsoftcorp.kls.valuetypes.account;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author a1623
 * 
 */

public enum SanctionAuthority implements ParameterizedEnum<SanctionAuthority, String> {

	Board_Of_Director("BD"), Chairman("CM"), Managing_Director("MD"), Loan_Committee("LC"), Branch_Manager("BM");

	private String value;

	private SanctionAuthority(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static SanctionAuthority getType(String value) {
		SanctionAuthority[] sanctionAuthorities = values();
		for (SanctionAuthority type : sanctionAuthorities) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for SanctionAuthority Enum");
	}
}