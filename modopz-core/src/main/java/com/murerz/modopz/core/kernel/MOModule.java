package com.murerz.modopz.core.kernel;

import java.io.Closeable;

public abstract class MOModule implements Closeable {

	public abstract <R> R command(MOMessage<R> cmd);

	public void start() {

	}

	public void close() {

	}

}
