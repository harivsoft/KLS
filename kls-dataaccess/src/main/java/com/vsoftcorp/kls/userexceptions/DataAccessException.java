package com.vsoftcorp.kls.userexceptions;

public class DataAccessException extends RuntimeException {

	private static final long serialVersionUID = 1567535083631513091L;

	/**
     * Create a new exception.
     */
    public DataAccessException() {
        super();
    }

    /**
     * Create a new exception with a message.
     * @param message
     */
    public DataAccessException(String message) {
        super(message);
    }

    /**
     * 
     * @param message
     * @param excp
     */
    public DataAccessException(String message, Throwable excp) {
		super(message, excp);
	}
}

