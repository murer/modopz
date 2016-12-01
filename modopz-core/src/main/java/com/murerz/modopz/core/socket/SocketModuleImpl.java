package com.murerz.modopz.core.socket;

import java.util.HashMap;
import java.util.Map;

import com.murerz.modopz.core.module.SocketModule;
import com.murerz.modopz.core.util.Status;
import com.murerz.modopz.core.util.Util;

public class SocketModuleImpl implements SocketModule {

	private Map<String, MOSocket> scks = new HashMap<String, MOSocket>();

	public Class<?> spec() {
		return SocketModule.class;
	}

	public void start() {

	}

	public synchronized void close() {
		for (MOSocket sck : scks.values()) {
			Util.close(sck);
		}
		scks.clear();
	}

	private synchronized MOSocket removeSocket(String dest) {
		return scks.remove(dest);
	}

	private synchronized void addSocket(MOSocket socket) {
		scks.put(socket.getId(), socket);
	}

	private synchronized MOSocket getSocket(String dest) {
		return scks.get(dest);
	}

	public String connect(String host, Integer port) {
		MOSocket socket = MOSocket.create(host, port);
		addSocket(socket);
		return socket.getId();
	}

	public byte[] read(String dest) {
		MOSocket socket = getSocket(dest);
		return socket.read();
	}

	public Status write(String dest, byte[] data) {
		MOSocket socket = getSocket(dest);
		socket.write(data);
		return new Status();
	}

	public Status destroy(String dest) {
		MOSocket socket = removeSocket(dest);
		Util.close(socket);
		return new Status();
	}

}
