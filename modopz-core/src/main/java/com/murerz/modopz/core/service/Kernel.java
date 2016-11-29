package com.murerz.modopz.core.service;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;

import com.murerz.modopz.core.module.Module;
import com.murerz.modopz.core.util.Util;

public class Kernel implements Service, Closeable {

	private Map<String, Module> modules = new HashMap<String, Module>();

	public void close() {
		for (Module module : modules.values()) {
			Util.close(module);
		}
	}

	public void start() {
		for (Module module : modules.values()) {
			module.start();
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T module(Class<T> spec) {
		return (T) module(spec.getSimpleName());
	}

	public Module module(String simpleName) {
		Module ret = modules.get(simpleName);
		if (ret == null) {
			throw new RuntimeException("module not found: " + simpleName);
		}
		return ret;
	}

	public Kernel load(Module module) {
		modules.put(module.spec().getSimpleName(), module);
		return this;
	}

}
