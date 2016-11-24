package com.murerz.modopz.core.client;

import com.murerz.modopz.core.service.AService;

public class AHttpClient extends AClient {

	private String url;

	public AService prepare(String url) {
		this.url = url;
		return this;
	}

}
