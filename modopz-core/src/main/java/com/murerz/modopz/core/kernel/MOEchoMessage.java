package com.murerz.modopz.core.kernel;

public class MOEchoMessage extends MOMessage<MOEchoMessage> {

	private String msg;

	public String getMsg() {
		return msg;
	}

	public MOEchoMessage setMsg(String message) {
		this.msg = message;
		return this;
	}

	@Override
	public Class<MOEchoMessage> respType() {
		return MOEchoMessage.class;
	}

}
