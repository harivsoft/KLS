package com.vsoftcorp.kls.valuetypes.subsidy;
import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author skeleti
 * 
 */
public enum TypeOfInstitute implements ParameterizedEnum<TypeOfInstitute, Integer> {
	Government_Of_India(1), Government_Of_State(2), Self(3), Others(4);

	public static TypeOfInstitute getType(Integer theValue) {
		TypeOfInstitute[] typeOfInstitute = values();
		try {
			for (TypeOfInstitute type : typeOfInstitute) {
				if (type.getValue().equals(theValue))
					return type;
			}
		} catch (UnsupportedOperationException e) {
			return Government_Of_India;
		}
		return Government_Of_India;
	}

	@Override
	public Integer getValue() {
		return value;
	}

	private Integer value;

	private TypeOfInstitute(Integer theValue) {
		value = theValue;
	}

}