package com.murerz.modopz.core.exec;

import com.murerz.modopz.core.exp.MOException;
import com.murerz.modopz.core.kernel.MOCommand;
import com.murerz.modopz.core.kernel.MOKernel;

public class MOStatusProcessCommand implements MOCommand {

	private Long id;

	private byte[] stdin;

	private Long waitFor;

	public Long getId() {
		return id;
	}

	public MOStatusProcessCommand setId(Long id) {
		this.id = id;
		return this;
	}

	public byte[] getStdin() {
		return stdin;
	}

	public MOStatusProcessCommand setStdin(byte[] stdin) {
		this.stdin = stdin;
		return this;
	}

	public Long getWaitFor() {
		return waitFor;
	}

	public MOStatusProcessCommand setWaitFor(Long waitFor) {
		this.waitFor = waitFor;
		return this;
	}

	public MODataProcessResult execute(MOKernel kernel) {
		MOProcessModule module = kernel.module(MOProcessModule.class);
		MOProcess process = module.getProcess(id);
		if (process == null) {
			throw new MOException("not found", new MODataProcessResult().setId(id));
		}
		if (stdin != null && stdin.length > 0) {
			process.stdin(stdin);
		}
		MOProcessStatus status = waitFor == null ? process.status() : process.waitFor(waitFor);

		MODataProcessResult ret = new MODataProcessResult().setCode(status.getCode())
				.setCreatedAt(process.getCreatedAt()).setId(process.getId()).setStderr(status.getStderr().toByteArray())
				.setStdout(status.getStdout().toByteArray());

		if (ret.getCode() != null) {
			module.destroy(id);
		}
		return ret;
	}

}
