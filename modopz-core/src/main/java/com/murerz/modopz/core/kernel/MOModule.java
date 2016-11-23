package com.murerz.modopz.core.kernel;

import java.io.Closeable;
import java.util.List;

public abstract class MOModule implements Closeable {

	public void start() {

	}

	public void close() {

	}

	public abstract List<Class<?>> getCommands();

}
