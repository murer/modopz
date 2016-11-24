package com.murerz.modopz.core.service;

public class AEcho {

	private String name;

	public String getName() {
		return name;
	}

	public AEcho setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String toString() {
		return "[AEcho " + name + "]";
	}

}
