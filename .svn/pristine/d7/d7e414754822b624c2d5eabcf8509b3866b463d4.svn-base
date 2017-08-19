package com.vsoftcorp.kls.valuetypes.transaction;

import com.vsoftcorp.kls.valuetypes.ParameterizedEnum;

/**
 * @author a1605
 * 
 */
public enum PacsGLExtractUpload implements ParameterizedEnum<PacsGLExtractUpload, String> {
	MANUAL("M"), AUTOMATIC("A");

	private String value;

	private PacsGLExtractUpload(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static PacsGLExtractUpload getType(String value) {
		PacsGLExtractUpload[] gLExtractUpload = values();
		for (PacsGLExtractUpload type : gLExtractUpload) {
			if (type.getValue().equals(value))
				return type;
		}
		throw new UnsupportedOperationException("Invalid value for GL extact upload enum");
	}

}