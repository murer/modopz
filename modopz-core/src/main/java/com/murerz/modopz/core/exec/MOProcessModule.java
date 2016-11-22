package com.murerz.modopz.core.exec;

import java.util.HashMap;
import java.util.Map;

import com.murerz.modopz.core.kernel.MOMessage;
import com.murerz.modopz.core.kernel.MOModule;

public class MOProcessModule extends MOModule {

	private Map<Long, MOProcess> prcs = new HashMap<Long, MOProcess>();

	@SuppressWarnings("unchecked")
	@Override
	public <R> R command(MOMessage<R> cmd) {
		if (cmd instanceof MOStartProcessMessage) {
			return (R) startProcess((MOStartProcessMessage) cmd);
		}
		return null;
	}

	private MOStartProcessMessage.StartProcessResult startProcess(MOStartProcessMessage cmd) {
		MOProcess process = MOProcess.create(cmd.getCmds());
		prcs.put(process.getId(), process);
		return new MOStartProcessMessage.StartProcessResult().setId(process.getId());
	}

}
