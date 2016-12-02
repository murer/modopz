package com.github.murer.modopz.core.process;

import java.util.HashMap;
import java.util.Map;

import com.github.murer.modopz.core.module.ProcessModule;
import com.github.murer.modopz.core.util.Status;
import com.github.murer.modopz.core.util.Util;

public class ProcessModuleImpl implements ProcessModule {

	private Map<Long, MOProcess> prcs = new HashMap<Long, MOProcess>();

	public Class<?> spec() {
		return ProcessModule.class;
	}

	public void start() {

	}

	public synchronized void close() {
		for (MOProcess sck : prcs.values()) {
			Util.close(sck);
		}
		prcs.clear();
	}

	private synchronized MOProcess removeProcess(Long id) {
		return prcs.remove(id);
	}

	private synchronized void addProcess(MOProcess process) {
		prcs.put(process.getId(), process);
	}

	private synchronized MOProcess getProcess(Long id) {
		return prcs.get(id);
	}

	public Long create(ProcessCommand cmd) {
		MOProcess ret = MOProcess.create(cmd);
		addProcess(ret);
		return ret.getId();
	}

	public MOProcessStatus status(Long id, Long timeout) {
		MOProcess process = getProcess(id);
		if (process == null) {
			return null;
		}
		MOProcessStatus ret = timeout == null || timeout.longValue() < 0 ? process.read() : process.waitFor(timeout);
		return ret;
	}

	public Status write(Long id, byte[] data) {
		MOProcess process = getProcess(id);
		process.write(data);
		return new Status();
	}

	public Status destroy(Long id) {
		MOProcess process = removeProcess(id);
		if (process != null) {
			Util.close(process);
		}
		return new Status();
	}

}
