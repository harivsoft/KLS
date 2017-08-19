package com.vsoftcorp.kls.valuetypes.transaction;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

public enum TransactionCode implements ParameterizedEnum<TransactionCode, Integer> {

	PRINCIPAL_BAL(1), INTEREST(2), PENAL_INTEREST(3), CHARGES(4), SHARE_TRANSFER(5), INSURANCE_DEDUCTION(6), INTEREST_RECEIVABLE(7), INTEREST_RECEIVED(
			8), PENAL_INTEREST_RECEIVABLE(9), PENAL_INTEREST_RECEIVED(10), KIND(11), SHARE_TRANSFER_ON_SHARE_ACC(12), COLLECTION(13), BORROWING_TRANSACTION(
			14), MARGINAL(15), CLOSURE(16), CHARGES_RECEIVABLE(17), CHARGES_RECEIVED(18), PROCESSING_FEE(19), CASH_TRANSFER(20), SHARE_CHARGES(21), CLOSING_CHARGES(
			22), SAVINGS_TRANSACTION(23), INSURANCE_DEDUCTION_GL(24), PROCESSING_FEE_GL(25), MARGINAL_GL(26),CASH_IN_TRANSIT(27),PACS_BANK_ACC(28),INTEREST_SUBSIDY(29),
			INTEREST_SUBSIDY_ON_LOAN(30),BANK_CHARGES_RECEIVED(31),BANK_CHARGES_RECEIVABLE(32),MEMBER_SAVINGS_ACC(33),PACS_BANK_ACC_BORROWING(34);

	private Integer value;

	private TransactionCode(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static TransactionCode getType(Integer value) {
		TransactionCode[] transactionCodes = values();
		for (TransactionCode type : transactionCodes) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for transactionCode Enum");
	}

}
