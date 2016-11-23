package com.murerz.modopz.core.kernel;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

import com.murerz.modopz.core.util.MOUtil;

public class MOKernel implements Closeable {

	private List<MOModule> modules = new ArrayList<MOModule>();

	public static MOKernel create() {
		return new MOKernel();
	}

	public void close() {
		for (MOModule module : modules) {
			MOUtil.close(module);
		}
	}

	public MOKernel load(MOModule module) {
		modules.add(module);
		return this;
	}

	public void start() {
		for (MOModule module : modules) {
			module.start();
		}
	}

	public <R, T> R command(MOMessage<R> cmd) {
		R ret = null;
		for (MOModule module : modules) {
			R result = module.command(cmd);
			if (result != null) {
				if (ret != null) {
					throw new RuntimeException("multiple responses: " + cmd);
				}
				ret = result;
			}
		}
		if (ret == null) {
			throw new RuntimeException("unknown command: " + cmd);
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public <T> T module(Class<T> clazz) {
		for (MOModule module : modules) {
			if (clazz.isInstance(module)) {
				return (T) module;
			}
		}
		throw new RuntimeException("module not found: " + clazz);
	}

}
