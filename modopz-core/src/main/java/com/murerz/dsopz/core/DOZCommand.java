package com.murerz.dsopz.core;

public abstract class DOZCommand {

	private String command;

	public String getCommand() {
		return command;
	}

	public DOZCommand setCommand(String command) {
		this.command = command;
		return this;
	}

	public abstract Object result();

}
