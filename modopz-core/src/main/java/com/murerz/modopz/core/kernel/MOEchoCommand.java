package com.murerz.modopz.core.kernel;

public class MOEchoCommand implements MOCommand, MOResult {

	private String msg;

	public String getMsg() {
		return msg;
	}

	public MOEchoCommand setMsg(String message) {
		this.msg = message;
		return this;
	}

	public MOEchoCommand execute(MOKernel kernel) {
		return this;
	}

	@Override
	public String toString() {
		return "[MOEchoCommand msg=" + msg + "]";
	}

}
