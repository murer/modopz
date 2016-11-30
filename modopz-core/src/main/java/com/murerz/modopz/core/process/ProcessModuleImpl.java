package com.murerz.modopz.core.process;

import com.murerz.modopz.core.module.ProcessModule;

public class ProcessModuleImpl implements ProcessModule {

	public Class<?> spec() {
		return ProcessModule.class;
	}

	public void start() {

	}

	public void close() {

	}

	public Long create(ProcessCommand setCmds) {
		return null;
	}

	public MOProcessStatus status(long l) {
		return null;
	}

}
