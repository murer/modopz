package com.github.murer.modopz.core.service;

import java.io.Closeable;

public interface Service extends Closeable {

	public void start();

	public <T> T module(Class<T> spec);
	
	public void close();

}
