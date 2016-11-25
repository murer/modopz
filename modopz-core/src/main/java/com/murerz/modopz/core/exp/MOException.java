package com.murerz.modopz.core.exp;

public class MOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Integer code = 500;

	public MOException(String message, Throwable cause) {
		super(message, cause);
	}

	public MOException(String message) {
		super(message);
	}

	public MOException(Throwable cause) {
		super(cause);
	}

	public Integer getCode() {
		return code;
	}

	public MOException setCode(Integer code) {
		this.code = code;
		return this;
	}

}
