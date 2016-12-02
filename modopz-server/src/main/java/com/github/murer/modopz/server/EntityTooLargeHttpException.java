package com.github.murer.modopz.server;

import com.github.murer.modopz.core.exp.MOException;

public class EntityTooLargeHttpException extends MOException {

	private static final long serialVersionUID = 1L;

	public EntityTooLargeHttpException(int max) {
		super("entity too large, max: " + max);
		setCode(413);
	}

}
