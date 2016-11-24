package com.murerz.modopz.core.service;

public class ACommand<A> {

	private String module;

	private String action;

	private A params;

	public String getModule() {
		return module;
	}

	public ACommand<A> setModule(String module) {
		this.module = module;
		return this;
	}

	public String getAction() {
		return action;
	}

	public ACommand<A> setAction(String action) {
		this.action = action;
		return this;
	}

	public A getParams() {
		return params;
	}

	public ACommand<A> setParams(A params) {
		this.params = params;
		return this;
	}

}
