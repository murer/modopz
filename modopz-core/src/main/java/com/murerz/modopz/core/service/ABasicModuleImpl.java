package com.murerz.modopz.core.service;

public class ABasicModuleImpl implements ABasicModule {

	public Object echo(Object param) {
		return param;
	}

	public String ping() {
		return "OK";
	}

	public Class<?> spec() {
		return ABasicModule.class;
	}

	public void start() {

	}

	public void close() {

	}

}
