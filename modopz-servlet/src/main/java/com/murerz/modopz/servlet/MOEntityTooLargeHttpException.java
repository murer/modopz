package com.murerz.modopz.servlet;

import com.murerz.modopz.core.exp.MOException;

public class MOEntityTooLargeHttpException extends MOException {

	private static final long serialVersionUID = 1L;

	public MOEntityTooLargeHttpException(int max) {
		super(413, "entity too large, max: " + max, null);
	}

}
