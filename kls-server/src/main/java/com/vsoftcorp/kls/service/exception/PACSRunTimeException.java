package com.vsoftcorp.kls.service.exception;

/**
 * This class throws custom runtime exception.
 * @author a1605
 *
 */
public class PACSRunTimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PACSRunTimeException() {
	}

	public PACSRunTimeException(String message) {
		super(message);
	}

	public PACSRunTimeException(String message, Throwable excp) {
		super(message, excp);
	}
}
