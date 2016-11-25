package com.murerz.modopz.server;

import com.murerz.modopz.core.exp.AException;

public class MOEntityTooLargeHttpException extends AException {

	private static final long serialVersionUID = 1L;

	public MOEntityTooLargeHttpException(int max) {
		super("entity too large, max: " + max);
	}

}
