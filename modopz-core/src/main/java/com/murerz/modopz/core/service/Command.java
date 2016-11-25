package com.murerz.modopz.core.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Command {

	private String module;

	private String action;

	private Map<String, Object> params = new HashMap<String, Object>();

	public String module() {
		return module;
	}

	public Command module(String module) {
		this.module = module;
		return this;
	}

	public String action() {
		return action;
	}

	public Command action(String action) {
		this.action = action;
		return this;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public Command setParams(Map<String, Object> params) {
		this.params = params;
		return this;
	}

	public Command setCmd(String cmd) {
		String[] array = cmd.split("\\.");
		module(array[0]);
		action(array[1]);
		return this;
	}

	@Override
	public String toString() {
		return String.format("[Command %s.%s %s]", module, action, keys());
	}

	public Set<String> keys() {
		return params == null ? null : params.keySet();
	}

	public String getCmd() {
		if (module == null || action == null) {
			return null;
		}
		return String.format("%s.%s", module, action);
	}

}
