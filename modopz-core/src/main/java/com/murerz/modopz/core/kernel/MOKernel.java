package com.murerz.modopz.core.kernel;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.murerz.modopz.core.util.MOUtil;

public class MOKernel implements Closeable {

	private List<MOModule> modules = new ArrayList<MOModule>();
	private Map<String, Class<?>> commands;

	public Class<?> command(String name) {
		Class<?> ret = commands.get(name);
		if (ret == null) {
			throw new RuntimeException("command not found: " + name);
		}
		return ret;
	}

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
		Map<String, Class<?>> commands = new HashMap<String, Class<?>>();
		for (MOModule module : modules) {
			List<Class<?>> cmds = module.getCommands();
			for (Class<?> cmd : cmds) {
				Class<?> old = commands.put(cmd.getSimpleName(), cmd);
				if (old != null) {
					throw new RuntimeException("command registered twice: " + old.getName() + ", " + cmd.getName());
				}
			}
		}
		for (MOModule module : modules) {
			module.start();
		}
		this.commands = commands;
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
