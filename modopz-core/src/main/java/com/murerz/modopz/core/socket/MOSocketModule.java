package com.murerz.modopz.core.socket;

import java.util.HashMap;
import java.util.Map;

import com.murerz.modopz.core.kernel.MOMessage;
import com.murerz.modopz.core.kernel.MOModule;
import com.murerz.modopz.core.socket.MODataSocketMessage.DataSocketResult;
import com.murerz.modopz.core.socket.MOOpenSocketMessage.OpenSocketResult;

public class MOSocketModule extends MOModule {

	private Map<Long, MOSocket> scks = new HashMap<Long, MOSocket>();

	@SuppressWarnings("unchecked")
	@Override
	public <R> R command(MOMessage<R> cmd) {
		if (cmd instanceof MOOpenSocketMessage) {
			return (R) openSocket((MOOpenSocketMessage) cmd);
		}
		if (cmd instanceof MODataSocketMessage) {
			return (R) dataSocket((MODataSocketMessage) cmd);
		}
		return null;
	}

	private DataSocketResult dataSocket(MODataSocketMessage cmd) {
		return null;
	}

	private OpenSocketResult openSocket(MOOpenSocketMessage cmd) {
		MOSocket socket = MOSocket.create(cmd.getHost(), cmd.getPort());
		put(socket.getId(), socket);
		return new OpenSocketResult().setId(socket.getId());
	}

	private synchronized void put(long id, MOSocket socket) {
		scks.put(id, socket);
	}

}
