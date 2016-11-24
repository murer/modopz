package com.murerz.modopz.core.service;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;

import com.murerz.modopz.core.util.MOUtil;

public class AKernel implements AService, Closeable {

	private Map<String, AModule> modules = new HashMap<String, AModule>();

	public void close() {
		for (AModule module : modules.values()) {
			MOUtil.close(module);
		}
	}

	public AKernel start() {
		for (AModule module : modules.values()) {
			module.start();
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T module(Class<T> spec) {
		return (T) module(spec.getName());
	}

	public AModule module(String simpleName) {
		return modules.get(simpleName);
	}

	public AKernel load(AModule module) {
		modules.put(module.spec().getSimpleName(), module);
		return this;
	}

}
