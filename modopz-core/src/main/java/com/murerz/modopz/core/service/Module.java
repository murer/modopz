package com.murerz.modopz.core.service;

import java.io.Closeable;

public interface Module extends Closeable {

	public Class<?> spec();

	public void start();

	public void close();

}
