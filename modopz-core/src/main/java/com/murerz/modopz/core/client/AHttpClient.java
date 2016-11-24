package com.murerz.modopz.core.client;

import java.lang.reflect.Method;

import com.murerz.modopz.core.service.AService;

public class AHttpClient extends AClient {

	private String url;

	public AService prepare(String url) {
		this.url = url;
		return this;
	}

	@Override
	protected Object proxy(Invoker invoker, Object proxy, Class<?> spec, Method method, Object[] args) {
		return null;
	}

}
