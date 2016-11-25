package com.murerz.modopz.core.module;

import java.io.Closeable;

public interface Module extends Closeable {

	public Class<?> spec();

	public void start();

	public void close();

}
