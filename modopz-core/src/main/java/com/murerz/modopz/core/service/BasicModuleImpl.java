package com.murerz.modopz.core.service;

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

}
