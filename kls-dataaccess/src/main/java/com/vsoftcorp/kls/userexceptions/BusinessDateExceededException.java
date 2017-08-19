package com.vsoftcorp.kls.userexceptions;

public class BusinessDateExceededException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessDateExceededException() {
		super();
	}

	public BusinessDateExceededException(String message) {

		super(message);

	}

}
