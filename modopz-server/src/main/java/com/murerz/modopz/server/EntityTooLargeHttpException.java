package com.murerz.modopz.server;

import com.murerz.modopz.core.exp.MOException;

public class EntityTooLargeHttpException extends MOException {

	private static final long serialVersionUID = 1L;

	public EntityTooLargeHttpException(int max) {
		super("entity too large, max: " + max);
	}

}
