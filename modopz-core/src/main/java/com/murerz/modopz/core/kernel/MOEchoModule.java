package com.murerz.modopz.core.kernel;

public class MOEchoModule extends MOModule {

	@SuppressWarnings("unchecked")
	@Override
	public <R> R command(MOMessage<R> cmd) {
		if (cmd instanceof MOEchoMessage) {
			return (R) cmd;
		}
		return null;
	}

}