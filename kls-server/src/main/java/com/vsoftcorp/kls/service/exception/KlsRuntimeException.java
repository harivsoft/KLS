package com.vsoftcorp.kls.service.exception;
/**
 * This class throws custom runtime exception.
 * @author a9152
 *
 */
public class KlsRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KlsRuntimeException() {
	}

	public KlsRuntimeException(String message) {
		super(message);
	}

	public KlsRuntimeException(String message, Throwable excp) {
		super(message, excp);
	}
}
