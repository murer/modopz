package com.murerz.modopz.core.exp;

import com.murerz.modopz.core.kernel.MOResult;

public class MOResultException extends MOException {

	private static final long serialVersionUID = 1L;

	private MOResult result;

	public MOResultException(String msg, MOResult result) {
		super(msg);
		this.result = result;
	}

	public MOResult getResult() {
		return result;
	}

	public MOResultException setResult(MOResult result) {
		this.result = result;
		return this;
	}

}
