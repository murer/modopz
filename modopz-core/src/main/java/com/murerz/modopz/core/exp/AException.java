package com.murerz.modopz.core.exp;

public class AException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Integer code = 500;

	public AException(String message, Throwable cause) {
		super(message, cause);
	}

	public AException(String message) {
		super(message);
	}

	public AException(Throwable cause) {
		super(cause);
	}

	public Integer getCode() {
		return code;
	}

	public AException setCode(Integer code) {
		this.code = code;
		return this;
	}

}
