package com.murerz.modopz.core.client;

import java.util.Arrays;
import java.util.List;

import com.murerz.modopz.core.json.JSON;

public class ClientConfig {

	private static final ClientConfig ME = new ClientConfig();

	public static ClientConfig me() {
		return ME;
	}

	public String require(String name) {
		String ret = prop(name);
		if (ret == null) {
			throw new RuntimeException("config required: " + name);
		}
		return ret;
	}

	public String prop(String name) {
		return prop(name, null);
	}

	public List<String> list(String name) {
		String str = prop(name, "");
		return Arrays.asList(str.split(","));
	}

	public String prop(String name, String def) {
		String ret = System.getProperty(name);
		return ret == null ? def : ret;
	}

	public <T> T json(String json, Class<T> clazz) {
		return JSON.parse(prop(json), clazz);
	}

	public boolean propBoolean(String name) {
		return "true".equals(prop(name));
	}

	public void config(String name, String value) {
		if (prop(name) == null) {
			System.setProperty(name, value);
		}
	}

}
