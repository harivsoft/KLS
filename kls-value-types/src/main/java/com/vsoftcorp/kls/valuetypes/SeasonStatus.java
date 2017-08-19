package com.vsoftcorp.kls.valuetypes;

/**
 * @author sponnam
 * 
 */
public enum SeasonStatus implements ParameterizedEnum<SeasonStatus, String> {
	NEW("N"), ENTRY_STARTED("E"), DRAWAL_STARTED("D");

	public static SeasonStatus getType(String theValue) {
		SeasonStatus[] seasonStatus = values();
		try {
			for (SeasonStatus status : seasonStatus) {
				if (status.getValue().equals(theValue))
					return status;
			}
		} catch (UnsupportedOperationException e) {
			return NEW;
		}
		return NEW;
	}

	@Override
	public String getValue() {
		return value;
	}

	private String value;

	private SeasonStatus(String theValue) {
		value = theValue;
	}

	@Override
	public String toString() {

		return value;
	}

	public String getName() {
		return name();
	}
}