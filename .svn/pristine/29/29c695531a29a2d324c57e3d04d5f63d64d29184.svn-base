package com.vsoftcorp.kls.valuetypes.subsidy;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

public enum EligibilityCriteria implements
		ParameterizedEnum<TypeOfInstitute, String> {
	ONTIMELYREPAYMENT("O"), ONLOANOUTSTANDINGUPTODUEDATE("D");

	public static EligibilityCriteria getType(String theValue) {
		EligibilityCriteria[] eligibilityCriteria = values();
		try {
			for (EligibilityCriteria type : eligibilityCriteria) {
				if (type.getValue().equals(theValue))
					return type;
			}
		} catch (UnsupportedOperationException e) {
			return ONTIMELYREPAYMENT;
		}
		return ONTIMELYREPAYMENT;
	}

	@Override
	public String getValue() {
		return value;
	}

	private String value;

	private EligibilityCriteria(String theValue) {
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
