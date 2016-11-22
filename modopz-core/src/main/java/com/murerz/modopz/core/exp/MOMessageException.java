package com.murerz.modopz.core.exp;

import com.murerz.modopz.core.kernel.MOMessage;

public class MOMessageException extends MOException {

	private static final long serialVersionUID = 1L;

	private MOMessage<?> result;

	public MOMessageException(String msg, MOMessage<?> result) {
		super(msg);
		this.result = result;
	}

	public MOMessage<?> getResult() {
		return result;
	}

	public MOMessageException setResult(MOMessage<?> msg) {
		this.result = msg;
		return this;
	}

}
