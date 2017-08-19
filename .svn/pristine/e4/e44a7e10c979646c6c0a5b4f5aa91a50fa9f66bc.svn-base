package com.vsoftcorp.kls.valuetypes.transaction;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author sponnam
 * 
 */
public enum ChannelType implements ParameterizedEnum<ChannelType, String> {
	ATM("A"), POS("P"), MATM("M"), SYSTEM("S"), BRANCH("B"), BRANCH_PACS_SB("Z"),BRANCH_MEM_SB("Y"); 
	// Z & Y are added for differentiating transactions done at branch while bulk loan recovery at branch.(IRKLS-404).
	public static ChannelType getType(String theValue) {
		ChannelType[] channelType = values();
		try {
			for (ChannelType type : channelType) {
				if (type.getValue().equals(theValue))
					return type;
			}
		} catch (UnsupportedOperationException e) {
			return ATM;
		}
		return ATM;
	}

	@Override
	public String getValue() {
		return value;
	}

	private String value;

	private ChannelType(String theValue) {
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