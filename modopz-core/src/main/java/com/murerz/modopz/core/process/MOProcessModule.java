package com.murerz.modopz.core.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.murerz.modopz.core.kernel.MOModule;
import com.murerz.modopz.core.util.MOUtil;

public class MOProcessModule extends MOModule {

	private Map<Long, MOProcess> prcs = new HashMap<Long, MOProcess>();

	public synchronized Collection<Long> processIds() {
		return prcs.keySet();
	}

	public synchronized MOProcess getProcess(Long id) {
		return prcs.get(id);
	}

	public synchronized void destroy(Long id) {
		MOProcess process = prcs.remove(id);
		MOUtil.close(process);
	}

	public synchronized void addProcess(MOProcess process) {
		prcs.put(process.getId(), process);
	}

	@Override
	public synchronized void close() {
		List<Long> ids = new ArrayList<Long>(prcs.keySet());
		Collections.sort(ids);
		for (Long id : ids) {
			destroy(id);
		}
	}

	@Override
	public List<Class<?>> getCommands() {
		List<Class<?>> ret = new ArrayList<Class<?>>();
		ret.add(MOStartProcessCommand.class);
		ret.add(MOCloseProcessCommand.class);
		ret.add(MOStatusProcessCommand.class);
		ret.add(MOListProcessCommand.class);
		return ret;
	}

}
