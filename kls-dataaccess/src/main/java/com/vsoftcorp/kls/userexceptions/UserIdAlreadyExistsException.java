package com.vsoftcorp.kls.userexceptions;

public class UserIdAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserIdAlreadyExistsException() {
		super();
	}

	public UserIdAlreadyExistsException(String message) {

		super(message);

	}

}
