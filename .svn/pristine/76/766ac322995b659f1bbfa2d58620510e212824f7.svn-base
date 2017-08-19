package com.vsoftcorp.kls.valuetypes.transaction;

/**
 * U Danaiah
 * 
 */

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;


public enum BorrowingTransactionMethod  implements ParameterizedEnum<BorrowingTransactionMethod, String> {
	
	OneToOneStright("S"),OneToOneEarly("E"),Grouping("G");
	
	
	private String value;
	
	private BorrowingTransactionMethod(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static BorrowingTransactionMethod getType(String value) {
		BorrowingTransactionMethod[] bTransactionMethod = values();
		for (BorrowingTransactionMethod type : bTransactionMethod) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for BorrowingTransactionMethod Enum");
	}
}
