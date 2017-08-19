package com.vsoftcorp.kls.valuetypes;


	public enum PacsLandUnits implements ParameterizedEnum<PacsLandUnits, String> {
		Acre_Gunta("AG"),Acre_Cent("AC"), Hectare_Gunta("HG"),Hectare_Cent("HC");

		public static PacsLandUnits getType(String theValue) {
			PacsLandUnits[] pacsLandUnits = values();
			try {
				for (PacsLandUnits units : pacsLandUnits) {
					if (units.getValue().equals(theValue))
						return units;
				}
			} catch (UnsupportedOperationException e) {
				return Acre_Gunta;
			}
			return Acre_Gunta;
		}

		@Override
		public String getValue() {
			return value;
		}

		private String value;

		private PacsLandUnits(String theValue) {
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
