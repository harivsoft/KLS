package com.vsoftcorp.kls.valuetypes;

/**
 * @author sponnam
 *
 */
public enum LoanApplicationAction implements
		ParameterizedEnum<LoanApplicationAction, String> {
	 ENTER("ENTER"), RECOMMEND("RECOMMEND"),INSPECT("INSPECT"), SANCTION(
			"SANCTION"), REJECT("REJECT"), DELETE("DELETE");
	private String value;

	private LoanApplicationAction(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

	public static LoanApplicationAction getType(String value) {
		LoanApplicationAction[] loanApplicationActions = values();
		for (LoanApplicationAction type : loanApplicationActions) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException(
				"Invalid value for LoanApplicationAction Enum");
	}
}
