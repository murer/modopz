package com.murerz.modopz.core.kernel;

public abstract class MOModule {

	public abstract <R> R command(MOMessage<R> cmd);
}
