package com.vsoftcorp.kls.valuetypes;

/**
 * @author sponnam
 * 
 */
public enum LoanApplicationState implements ParameterizedEnum<LoanApplicationState, Integer> {
	NEW(0), ENTERED(1), RECOMMENDED(2), INSPECTED(3), SANCTIONED(4), REJECTED(5), ACCOUNT_CREATED(6),ENTRY_PENDING(7),
	INSPECTION_PENDING(8),SANCTION_PENDING(9),APPROVED(10), AUTHORIZATION_PENDING(11),INSPECTION_RETURN(12),SANCTION_RETURN(13);

	// ENTERED - Entered Loan Application of Farmer
	// RECOMMENDED - Recommended for Sanction
	// INSPECTED - Inspected after Recommendation
	// SANCTIONED - sanctioned
	// REJECTED - Rejected
	// PENDING - Pending (not used as of now)
	// ACCOUNT_CREATED - Used for pacs MT/LT loans.
	private Integer value;

	private LoanApplicationState(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static LoanApplicationState getType(Integer value) {
		LoanApplicationState[] loanApplicationStates = values();
		for (LoanApplicationState type : loanApplicationStates) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for loanApplicationStates Enum");
	}
}