package com.murerz.dsopz.core;

public class DOZBuildable {

	private DOZLog log;

	public void setLog(DOZLog log) {
		this.log = log;
	}

	protected DOZLog log() {
		return log;
	}
}
