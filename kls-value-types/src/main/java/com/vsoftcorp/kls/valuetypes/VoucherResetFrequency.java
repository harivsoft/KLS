package com.vsoftcorp.kls.valuetypes;

public enum VoucherResetFrequency implements ParameterizedEnum<VoucherResetFrequency, String> {
	DAILY("D"),WEEKLY("W"),MONTHLY("M"),QUARTERLY("Q"),HALF_YEARLY("H"),YEARLY("Y");
	
	private String value;
	
	private VoucherResetFrequency(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static VoucherResetFrequency getType(String value) {
		VoucherResetFrequency[] voucherResetFrequency = values();
		for (VoucherResetFrequency type : voucherResetFrequency) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for VoucherResetFrequency Enum");
	}
}
