package com.murerz.modopz.core.process;

import com.murerz.modopz.core.client.CLI;
import com.murerz.modopz.core.client.ClientConfig;
import com.murerz.modopz.core.module.ProcessModule;
import com.murerz.modopz.core.util.Util;

public class ProcessCLI extends CLI {

	private ProcessCommand command;

	public static void main(String[] args) {
		ClientConfig.me().config("modopz.client", ProcessCLI.class.getName());
		ClientConfig.me().config("modopz.process.cmd", "{\"cmds\":[\"/bin/bash\",\"-e\"]}");
		CLI.cli();
	}

	@Override
	public void exec() {
		ProcessModule mod = service().module(ProcessModule.class);
		ProcessConsole console = new ProcessConsole();
		try {
			console.module(mod);
			console.command(command);
			console.in(System.in).out(System.out).err(System.err);
			console.exec();
		} finally {
			Util.close(console);
		}
	}

	public void config() {
		super.config();
		this.command = ClientConfig.me().json("modopz.process.cmd", ProcessCommand.class);
	}

}
