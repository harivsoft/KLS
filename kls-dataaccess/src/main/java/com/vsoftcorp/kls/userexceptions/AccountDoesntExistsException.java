package com.vsoftcorp.kls.userexceptions;

public class AccountDoesntExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountDoesntExistsException() {
		super();
	}

	public AccountDoesntExistsException(String message) {

		super(message);

	}

}
