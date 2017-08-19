package com.vsoftcorp.kls.valuetypes;

public enum PacsStatus  implements ParameterizedEnum<String, String> {
	
	Syncd("S"),Offline("O");

		public static PacsStatus getType(String theValue) {
			PacsStatus[] pacsStatus = values();
			try {
				for (PacsStatus status : pacsStatus) {
					if (status.getValue().equals(theValue))
						return status;
				}
			} catch (UnsupportedOperationException e) {
				return Offline;
			}
			return Offline;
		}

		@Override
		public String getValue() {
			return value;
		}

		private String value;

		private PacsStatus(String theValue) {
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



