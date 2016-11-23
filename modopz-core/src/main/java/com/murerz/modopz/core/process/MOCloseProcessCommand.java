package com.murerz.modopz.core.process;

import com.murerz.modopz.core.kernel.MOCommand;
import com.murerz.modopz.core.kernel.MOKernel;

public class MOCloseProcessCommand implements MOCommand {

	private Long id;

	public Long getId() {
		return id;
	}

	public MOCloseProcessCommand setId(Long id) {
		this.id = id;
		return this;
	}

	public MODataProcessResult execute(MOKernel kernel) {
		MOProcessModule module = kernel.module(MOProcessModule.class);
		module.destroy(id);
		return new MODataProcessResult().setId(id);
	}

}
