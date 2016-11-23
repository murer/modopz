package com.murerz.modopz.core.exp;

import com.murerz.modopz.core.kernel.MOResult;

public class MOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int code;

	private MOResult result;

	public MOException(String msg, MOResult result) {
		this(500, msg, result);
	}

	public MOException(int code, String msg, MOResult result) {
		super("code: " + code + ", msg: " + msg);
		this.result = result;
	}

	public MOResult getResult() {
		return result;
	}

	public int getCode() {
		return code;
	}

}
