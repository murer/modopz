package com.murerz.modopz.core.socket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.murerz.modopz.core.kernel.MOModule;
import com.murerz.modopz.core.util.MOUtil;

public class MOSocketModule extends MOModule {

	private Map<Long, MOSocket> scks = new HashMap<Long, MOSocket>();

	public synchronized void closeSocket(Long id) {
		MOSocket socket = scks.remove(id);
		MOUtil.close(socket);
	}

	public synchronized MOSocket getSocket(Long id) {
		return scks.get(id);
	}

	public synchronized void addSocket(MOSocket socket) {
		scks.put(socket.getId(), socket);
	}

	@Override
	public synchronized void close() {
		List<Long> ids = new ArrayList<Long>(scks.keySet());
		Collections.sort(ids);
		for (Long id : ids) {
			closeSocket(id);
		}
	}

	@Override
	public List<Class<?>> getCommands() {
		List<Class<?>> ret = new ArrayList<Class<?>>();
		ret.add(MOOpenSocketCommand.class);
		ret.add(MOCloseSocketCommand.class);
		ret.add(MODataSocketCommand.class);
		return ret;	}

}
