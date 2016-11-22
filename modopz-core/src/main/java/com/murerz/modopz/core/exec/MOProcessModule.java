package com.murerz.modopz.core.exec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.murerz.modopz.core.exec.MOCloseProcessMessage.CloseStatusProcessResult;
import com.murerz.modopz.core.exec.MOListProcessMessage.ListProcessResult;
import com.murerz.modopz.core.exec.MOStartProcessMessage.StartProcessResult;
import com.murerz.modopz.core.exec.MOStatusProcessMessage.StatusProcessResult;
import com.murerz.modopz.core.exp.MOMessageException;
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
		if (cmd instanceof MOListProcessMessage) {
			return (R) listProcess((MOListProcessMessage) cmd);
		}
		return null;
	}

	private ListProcessResult listProcess(MOListProcessMessage cmd) {
		List<Long> ids = new ArrayList<Long>();
		ids.addAll(processIds());
		Collections.sort(ids);
		return new ListProcessResult().setPrcs(ids);
	}

	private synchronized Collection<Long> processIds() {
		return prcs.keySet();
	}

	private CloseStatusProcessResult closeProcess(MOCloseProcessMessage cmd) {
		destroy(cmd.getId());
		return new CloseStatusProcessResult();
	}

	public synchronized MOProcess getProcess(Long id) {
		return prcs.get(id);
	}

	private StatusProcessResult statusProcess(MOStatusProcessMessage cmd) {
		MOProcess process = getProcess(cmd.getId());
		if (process == null) {
			throw new MOMessageException("not found", new MOProcessNotFoundResult().setId(cmd.getId()));
		}
		if (cmd.getStdin() != null && cmd.getStdin().length > 0) {
			process.stdin(cmd.getStdin());
		}
		Long waitFor = cmd.getWaitFor();
		MOProcessStatus status = waitFor == null ? process.status() : process.waitFor(waitFor);
		StatusProcessResult ret = new StatusProcessResult().setCode(status.getCode())
				.setCreatedAt(process.getCreatedAt()).setId(process.getId()).setStderr(status.getStderr().toByteArray())
				.setStdout(status.getStdout().toByteArray());
		if (ret.getCode() != null) {
			destroy(cmd.getId());
		}
		return ret;
	}

	private synchronized void destroy(Long id) {
		MOProcess process = prcs.remove(id);
		MOUtil.close(process);
	}

	private StartProcessResult startProcess(MOStartProcessMessage cmd) {
		MOProcess process = MOProcess.create(cmd.getCmds());
		StartProcessResult ret = new MOStartProcessMessage.StartProcessResult().setId(process.getId());
		putProcess(process.getId(), process);
		return ret;
	}

	private synchronized void putProcess(long id, MOProcess process) {
		prcs.put(id, process);
	}

	@Override
	public synchronized void close() {
		List<Long> ids = new ArrayList<Long>(prcs.keySet());
		Collections.sort(ids);
		for (Long id : ids) {
			destroy(id);
		}
	}

}
