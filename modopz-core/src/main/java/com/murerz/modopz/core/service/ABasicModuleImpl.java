package com.murerz.modopz.core.service;

public class ABasicModuleImpl implements ABasicModule {

	public Object echo(Object param) {
		return param;
	}

	public String ping() {
		return "OK";
	}

}
