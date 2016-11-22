package com.murerz.modopz.core.exec;

import java.util.HashMap;
import java.util.Map;

import com.murerz.modopz.core.exec.MOCloseProcessMessage.CloseStatusProcessResult;
import com.murerz.modopz.core.exec.MOStartProcessMessage.StartProcessResult;
import com.murerz.modopz.core.exec.MOStatusProcessMessage.StatusProcessResult;
import com.murerz.modopz.core.kernel.MOMessage;
import com.murerz.modopz.core.kernel.MOModule;
import com.murerz.modopz.core.util.MOUtil;

public class MOProcessModule extends MOModule {

	private Map<Long, MOProcess> prcs = new HashMap<Long, MOProcess>();

	@SuppressWarnings("unchecked")
	@Override
	public <R> R command(MOMessage<R> cmd) {
		if (cmd instanceof MOStartProcessMessage) {
			return (R) startProcess((MOStartProcessMessage) cmd);
		}
		if (cmd instanceof MOStatusProcessMessage) {
			return (R) statusProcess((MOStatusProcessMessage) cmd);
		}
		if (cmd instanceof MOCloseProcessMessage) {
			return (R) closeProcess((MOCloseProcessMessage) cmd);
		}
		return null;
	}

	private CloseStatusProcessResult closeProcess(MOCloseProcessMessage cmd) {
		MOProcess process = prcs.remove(cmd.getId());
		MOUtil.close(process);
		return new CloseStatusProcessResult();
	}

	private StatusProcessResult statusProcess(MOStatusProcessMessage cmd) {
		MOProcess process = prcs.get(cmd.getId());
		if (cmd.getStdin() != null && cmd.getStdin().length > 0) {
			process.stdin(cmd.getStdin());
		}
		Long waitFor = cmd.getWaitFor();
		MOProcessStatus status = waitFor == null ? process.status() : process.waitFor(waitFor);
		return new StatusProcessResult().setCode(status.getCode()).setCreatedAt(process.getCreatedAt())
				.setId(process.getId()).setStderr(status.getStderr().toByteArray())
				.setStdout(status.getStdout().toByteArray());
	}

	private StartProcessResult startProcess(MOStartProcessMessage cmd) {
		MOProcess process = MOProcess.create(cmd.getCmds());
		prcs.put(process.getId(), process);
		return new MOStartProcessMessage.StartProcessResult().setId(process.getId());
	}

}
