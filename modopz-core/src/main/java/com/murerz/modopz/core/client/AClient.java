package com.murerz.modopz.core.client;

import com.murerz.modopz.core.service.AService;

public abstract class AClient implements AService {

	public <T> T module(Class<T> spec) {
		return null;
	}

}
