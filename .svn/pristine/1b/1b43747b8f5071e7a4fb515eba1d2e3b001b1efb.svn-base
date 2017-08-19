package com.vsoftcorp.kls.valuetypes;

public enum DayStatus  implements ParameterizedEnum<String, String> {
	
	OPEN("O"),CLOSED("C");

		public static DayStatus getType(String theValue) {
			DayStatus[] dayStaus = values();
			try {
				for (DayStatus status : dayStaus) {
					if (status.getValue().equals(theValue))
						return status;
				}
			} catch (UnsupportedOperationException e) {
				return OPEN;
			}
			return OPEN;
		}

		@Override
		public String getValue() {
			return value;
		}

		private String value;

		private DayStatus(String theValue) {
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



