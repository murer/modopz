package com.murerz.modopz.core.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ServerSocketFactory;

import com.murerz.modopz.core.module.Module;
import com.murerz.modopz.core.module.SocketFowardModule;
import com.murerz.modopz.core.service.Service;
import com.murerz.modopz.core.util.Util;

public class SocketFowardModuleImpl implements SocketFowardModule {

	private Map<String, SocketForwardListen> servers = new HashMap<String, SocketForwardListen>();
	private Service service;

	public Class<?> spec() {
		return SocketFowardModule.class;
	}

	public void start() {

	}

	public synchronized void close() {
		for (SocketForwardListen server : servers.values()) {
			server.stop();
		}
		for (SocketForwardListen server : servers.values()) {
			server.waitFor();
		}
		servers.clear();
	}

	public synchronized String bind(SocketForward forward) {
		try {
			if (forward.getSourceHost() == null) {
				forward.setSourceHost("127.0.0.1");
			}
			InetAddress sourceAddress = InetAddress.getByName(forward.getSourceHost());
			ServerSocket sck = ServerSocketFactory.getDefault().createServerSocket(forward.getSourcePort(), 50,
					sourceAddress);
			String id = Util.format("%s:%s", forward.getSourceHost(), sck.getLocalPort());
			SocketForwardListen server = new SocketForwardListen().setId(id).setForward(forward).setServer(sck).setService(service);
			servers.put(id, server);
			server.start();
			return id;
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Module setService(Service service) {
		this.service = service;
		return this;
	}

	public static SocketFowardModuleImpl create() {
		return new SocketFowardModuleImpl();
	}

}
