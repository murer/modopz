package com.murerz.modopz.core.process;

import java.util.ArrayList;
import java.util.List;

import com.murerz.modopz.core.kernel.MOCommand;
import com.murerz.modopz.core.kernel.MOKernel;
import com.murerz.modopz.core.kernel.MOResult;

public class MOStartProcessCommand implements MOCommand {

	private List<String> cmds = new ArrayList<String>();

	public List<String> getCmds() {
		return cmds;
	}

	public MOStartProcessCommand setCmds(List<String> cmds) {
		this.cmds = cmds;
		return this;
	}

	public MODataProcessResult execute(MOKernel kernel) {
		MOProcessModule module = kernel.module(MOProcessModule.class);
		MOProcess process = MOProcess.create(cmds);
		module.addProcess(process);
		return new MODataProcessResult().setId(process.getId());
	}

}
