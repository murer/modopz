package com.murerz.modopz.core.socket;

import java.util.HashMap;
import java.util.Map;

import com.murerz.modopz.core.kernel.MOMessage;
import com.murerz.modopz.core.kernel.MOModule;
import com.murerz.modopz.core.util.MOUtil;

public class MOSocketModule extends MOModule {

	private Map<Long, MOSocket> scks = new HashMap<Long, MOSocket>();

	@Override
	public <R> R command(MOMessage<R> cmd) {
		return null;
	}

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

}
