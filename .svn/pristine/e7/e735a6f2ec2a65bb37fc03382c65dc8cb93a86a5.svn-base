package com.vsoftcorp.kls.valuetypes.subsidy;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

public enum TypeOfScheme implements ParameterizedEnum<TypeOfInstitute, String> {
	SUBVENTION("SB"), SUBSIDY("SS");

	public static TypeOfScheme getType(String theValue) {
		TypeOfScheme[] typeOfScheme = values();
		try {
			for (TypeOfScheme type : typeOfScheme) {
				if (type.getValue().equals(theValue))
					return type;
			}
		} catch (UnsupportedOperationException e) {
			return SUBVENTION;
		}
		return SUBVENTION;
	}

	@Override
	public String getValue() {
		return value;
	}

	private String value;

	private TypeOfScheme(String theValue) {
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
