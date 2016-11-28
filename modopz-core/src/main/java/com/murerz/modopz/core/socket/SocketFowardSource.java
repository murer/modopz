package com.murerz.modopz.core.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.murerz.modopz.core.module.SocketModule;
import com.murerz.modopz.core.service.Service;
import com.murerz.modopz.core.util.Util;

public class SocketFowardSource implements Runnable {

	private Socket source;
	private SocketForward forward;
	private String dest;
	private boolean running;

	private Service service;

	public Socket getSource() {
		return source;
	}

	public SocketFowardSource setSource(Socket source) {
		this.source = source;
		return this;
	}

	public SocketForward getForward() {
		return forward;
	}

	public SocketFowardSource setForward(SocketForward forward) {
		this.forward = forward;
		return this;
	}

	public void start() {
		Thread thread = new Thread(this, "SFS-" + getId());
		thread.start();
	}

	private String getId() {
		return Util.format("%s:%s-%s:%s", forward.getSourceHost(), source.getLocalPort(), forward.getDestHost(),
				source.getPort());
	}

	public void run() {
		Thread thread = null;
		try {
			dest = service.module(SocketModule.class).connect(forward.getDestHost(), forward.getDestPort());
			running = true;
			thread = new Thread("SFP-" + getId()) {
				public void run() {
					pipe(source, dest);
				}
			};
			thread.start();
			pipe(dest, source);
		} finally {
			Util.join(thread);
			close();
		}

	}

	protected void pipe(Socket source, String dest) {
		try {
			InputStream in = source.getInputStream();
			byte[] buffer = new byte[10 * 1024];
			while (running) {
				int read = in.read(buffer);
				if (read > 0) {
					byte[] n = Util.cut(buffer, 0, read);
					service.module(SocketModule.class).write(dest, n);
				}
				if (read < 0) {
					return;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			running = false;
		}
	}

	private void pipe(String dest, Socket source) {
		try {
			OutputStream out = source.getOutputStream();
			while (running) {
				byte[] data = service.module(SocketModule.class).read(dest);
				if (data == null) {
					return;
				}
				if (data.length > 0) {
					out.write(data);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			running = false;
		}

	}

	private void close() {
		closeDest();
		Util.close(source);
	}

	private void closeDest() {
		service.module(SocketModule.class).destroy(dest);
	}

	public SocketFowardSource setService(Service service) {
		this.service = service;
		return this;
	}

}
