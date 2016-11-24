package com.murerz.modopz.core.service;

import java.io.Closeable;

public class AKernel implements AService, Closeable {

	public void close() {

	}

	public AKernel start() {
		return this;
	}

	public <T> T module(Class<T> spec) {
		return null;
	}

	public AKernel load(Class<?> spec, AModule module) {
		return this;
	}

	public AModule module(String simpleName) {
		return null;
	}

	public AKernel load(AModule module) {
		return load(module.spec(), module);
	}

}
