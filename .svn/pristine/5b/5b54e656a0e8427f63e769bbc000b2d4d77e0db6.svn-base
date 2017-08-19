package com.vsoftcorp.kls.valuetypes.transaction;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author sponnam
 * 
 */
public enum TransactionType implements
		ParameterizedEnum<TransactionType, String> {
	Withdrawal("W"), Deposit("D"), Transfer("T"), Borrowings("B"),Reversal("R"),BalanceEnquiry("B");

	public static TransactionType getType(String theValue) {
		TransactionType[] transactionType = values();
		try {
			for (TransactionType type : transactionType) {
				if (type.getValue().equals(theValue))
					return type;
			}
		} catch (UnsupportedOperationException e) {
			return Withdrawal;
		}
		return Withdrawal;
	}

	@Override
	public String getValue() {
		return value;
	}

	private String value;

	private TransactionType(String theValue) {
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