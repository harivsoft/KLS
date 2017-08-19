package com.vsoftcorp.kls.valuetypes;


public enum SBAccountStatus implements ParameterizedEnum<SBAccountStatus, Integer>{

	Assign_Matched(1),Assign_Not_Matched(2), Not_Assigned_Not_Found_In_CBS(3),Assigned_Override(5),Unassign(6), Not_Assigned_No_Acccount_Exist_In_CBS(7);

	public static SBAccountStatus getType(Integer theValue) {
		SBAccountStatus[] sbAccountStatus = values();
		try {
			for (SBAccountStatus type : sbAccountStatus) {
				if (type.getValue().equals(theValue))
					return type;
			}
		} catch (UnsupportedOperationException e) {
			return Assign_Matched;
		}
		return Assign_Matched;
	}

	@Override
	public Integer getValue() {
		return value;
	}

	private Integer value;

	private SBAccountStatus(Integer theValue) {
		value = theValue;
	}
	public String getName() {
		return name();
	}
}
