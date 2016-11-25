package com.murerz.modopz.core.module;

import com.murerz.modopz.core.exp.MOException;
import com.murerz.modopz.core.service.Echo;

public class BasicModuleImpl implements BasicModule {

	public String ping() {
		return "OK";
	}

	public Class<?> spec() {
		return BasicModule.class;
	}

	public void start() {

	}

	public void close() {

	}

	public Echo echo(Echo echo) {
		return echo;
	}

	public Object none() {
		return null;
	}

	public void exp(Integer code) {
		if (code == null) {
			throw new RuntimeException("exp");
		}
		throw new MOException("exp").setCode(code);
	}

}
