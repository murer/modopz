package com.murerz.modopz.core.exp;

public class MOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MOException(String message, Throwable cause) {
		super(message, cause);
	}

	public MOException(String message) {
		super(message);
	}

	public MOException(Throwable cause) {
		super(cause);
	}

}
