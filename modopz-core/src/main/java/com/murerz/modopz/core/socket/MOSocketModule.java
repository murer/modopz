package com.murerz.modopz.core.socket;

import java.util.HashMap;
import java.util.Map;

import com.murerz.modopz.core.exp.MOMessageException;
import com.murerz.modopz.core.kernel.MOMessage;
import com.murerz.modopz.core.kernel.MOModule;
import com.murerz.modopz.core.socket.MOCloseSocketMessage.CloseSocketResult;
import com.murerz.modopz.core.socket.MODataSocketMessage.DataSocketResult;
import com.murerz.modopz.core.socket.MOOpenSocketMessage.OpenSocketResult;
import com.murerz.modopz.core.util.MOUtil;

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
		if (cmd instanceof MOCloseSocketMessage) {
			return (R) closeSocket((MOCloseSocketMessage) cmd);
		}
		return null;
	}

	private CloseSocketResult closeSocket(MOCloseSocketMessage cmd) {
		closeSocket(cmd.getId());
		return new CloseSocketResult();
	}

	private synchronized void closeSocket(Long id) {
		MOSocket socket = scks.remove(id);
		MOUtil.close(socket);
	}

	private DataSocketResult dataSocket(MODataSocketMessage cmd) {
		MOSocket socket = getSocket(cmd.getId());
		if (socket == null) {
			throw new MOMessageException("not found", new MOSocketNotFoundResult().setId(cmd.getId()));
		}
		if (cmd.getSend() != null && cmd.getSend().length > 0) {
			socket.write(cmd.getSend());
		}
		byte[] read = socket.read();
		return new DataSocketResult().setId(socket.getId()).setCreatedAt(socket.getCreatedAt()).setReceived(read);
	}

	private synchronized MOSocket getSocket(Long id) {
		return scks.get(id);
	}

	private OpenSocketResult openSocket(MOOpenSocketMessage cmd) {
		MOSocket socket = MOSocket.create(cmd.getHost(), cmd.getPort());
		putSocket(socket.getId(), socket);
		return new OpenSocketResult().setId(socket.getId());
	}

	private synchronized void putSocket(long id, MOSocket socket) {
		scks.put(id, socket);
	}

}
