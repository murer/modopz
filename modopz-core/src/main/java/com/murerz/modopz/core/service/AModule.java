package com.murerz.modopz.core.service;

import java.io.Closeable;

public interface AModule extends Closeable {

	public Class<?> spec();

	public void start();

	public void close();

}
