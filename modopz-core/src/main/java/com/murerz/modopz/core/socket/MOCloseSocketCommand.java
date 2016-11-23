package com.murerz.modopz.core.socket;

import com.murerz.modopz.core.kernel.MOCommand;
import com.murerz.modopz.core.kernel.MOKernel;

public class MOCloseSocketCommand implements MOCommand {

	private Long id;

	public Long getId() {
		return id;
	}

	public MOCloseSocketCommand setId(Long id) {
		this.id = id;
		return this;
	}

	public MOSocketResult execute(MOKernel kernel) {
		MOSocketModule module = kernel.module(MOSocketModule.class);
		module.closeSocket(id);
		return new MOSocketResult().setId(id);
	}

}
