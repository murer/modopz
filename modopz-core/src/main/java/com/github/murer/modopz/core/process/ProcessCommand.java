package com.github.murer.modopz.core.process;

import java.util.ArrayList;
import java.util.List;

public class ProcessCommand {

	private List<String> cmds = new ArrayList<String>();

	public List<String> getCmds() {
		return cmds;
	}

	public ProcessCommand setCmds(List<String> cmds) {
		this.cmds = cmds;
		return this;
	}

}
