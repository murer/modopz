package com.murerz.modopz.core.socket;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import com.murerz.modopz.core.util.Util;

public class MOSocket implements Closeable {

	private static final int MAX = 512 * 1024;

	private long createdAt;
	private Socket socket;

	private InputStream in;

	private OutputStream out;

	public static MOSocket create(String host, int port) {
		try {
			MOSocket ret = new MOSocket();
			ret.createdAt = System.currentTimeMillis();
			ret.socket = new Socket(host, port);
			ret.socket.setSoTimeout(100);
			ret.in = new BufferedInputStream(ret.socket.getInputStream(), MAX * 2);
			ret.out = ret.socket.getOutputStream();
			return ret;
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized void close() {
		Util.close(socket);
	}

	public synchronized byte[] read() {
		if (socket.isClosed() && Util.available(in) <= 0) {
			return null;
		}
		try {
			byte[] buffer = new byte[MAX];
			int read = in.read(buffer);
			if (read < 0) {
				return null;
			}
			if (read == 0) {
				return new byte[0];
			}
			return Util.cut(buffer, 0, read);
		} catch (SocketTimeoutException e) {
			return new byte[0];
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized void write(byte[] buffer) {
		Util.writeFlush(out, buffer);
	}

	public String getId() {
		return Util.format("%s:%s-%s:%s", socket.getLocalAddress().getHostAddress(), socket.getLocalPort(),
				socket.getInetAddress().getHostAddress(), socket.getPort());
	}

	public long getCreatedAt() {
		return createdAt;
	}

}
