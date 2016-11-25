package com.murerz.modopz.core.service;

public class Echo {

	private String name;

	public String getName() {
		return name;
	}

	public Echo setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String toString() {
		return "[Echo " + name + "]";
	}

}
