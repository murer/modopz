package com.murerz.modopz.core.exp;

import com.murerz.modopz.core.kernel.MOMessage;

public class MOMessageException extends MOException {

	private static final long serialVersionUID = 1L;

	private MOMessage msg;

	public MOMessageException(String msg, MOMessage momessage) {
		super(msg);
		this.msg = momessage;
	}

}
