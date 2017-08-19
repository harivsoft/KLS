package com.vsoftcorp.kls.userexceptions;

public class CustomerDoesntExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomerDoesntExistsException() {
		super();
	}

	public CustomerDoesntExistsException(String message) {

		super(message);

	}

}
