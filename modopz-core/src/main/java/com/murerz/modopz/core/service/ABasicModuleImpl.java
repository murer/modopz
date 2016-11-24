package com.murerz.modopz.core.service;

public class ABasicModuleImpl implements ABasicModule {

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

	public AEcho echo(AEcho echo) {
		return echo;
	}

}
