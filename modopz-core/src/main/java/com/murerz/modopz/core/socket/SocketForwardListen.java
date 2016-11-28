package com.murerz.modopz.core.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.murerz.modopz.core.service.Service;

public class SocketForwardListen implements Runnable {

	private String id;
	private SocketForward forward;
	private ServerSocket server;

	private Thread thread;
	private Service service;

	public String getId() {
		return id;
	}

	public SocketForwardListen setId(String id) {
		this.id = id;
		return this;
	}

	public SocketForward getForward() {
		return forward;
	}

	public SocketForwardListen setForward(SocketForward forward) {
		this.forward = forward;
		return this;
	}

	public ServerSocket getServer() {
		return server;
	}

	public SocketForwardListen setServer(ServerSocket server) {
		this.server = server;
		return this;
	}

	public void start() {
		thread = new Thread(this, "SFListen-" + id);
		thread.start();
	}

	public void run() {
		try {
			while (true) {
				Socket source = server.accept();
				source.setSoTimeout(500);
				SocketFowardSource forwardSource = new SocketFowardSource().setForward(forward).setSource(source)
						.setService(service);
				forwardSource.start();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void stop() {
		if (thread != null) {
			thread.interrupt();
		}
	}

	public void waitFor() {
		if (thread != null) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public SocketForwardListen setService(Service service) {
		this.service = service;
		return this;
	}

}
