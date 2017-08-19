/**
 * 
 */
package com.vsoftcorp.kls.valuetypes.transaction;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author a9152
 * 
 */
public enum TransactionMode implements ParameterizedEnum<TransactionMode, String> {

	Cash("C"), SB_Account_Of_Branch_Of_Bank("B"), Demand_Draft("D"), Cheque("Q"), Transfer("T"), 
	TRANSFER_FROM_PACS_SB_ACCOUNT("P"), TRANSFER_FROM_MEMBER_SB_ACCOUNT("M");

	private String value;

	private TransactionMode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static TransactionMode getType(String value) {
		TransactionMode[] transactionModes = values();
		for (TransactionMode type : transactionModes) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for transaction mode enum");
	}

}