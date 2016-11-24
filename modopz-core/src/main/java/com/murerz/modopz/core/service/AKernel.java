package com.murerz.modopz.core.service;

import java.io.Closeable;

public class AKernel implements AService, Closeable {

	public void close() {

	}

	public AKernel load(AModule module) {
		return this;
	}

	public AKernel start() {
		return this;
	}

	public AModule module(String module) {
		return null;
	}

}
