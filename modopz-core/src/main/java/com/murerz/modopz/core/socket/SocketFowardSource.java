package com.murerz.modopz.core.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

import com.murerz.modopz.core.log.Log;
import com.murerz.modopz.core.log.LogFactory;
import com.murerz.modopz.core.util.Util;

public class SocketFowardSource implements Runnable {

	private static final Log LOG = LogFactory.me().create(Util.class);

	private Socket source;
	private SocketForward forward;
	private Socket dest;
	private boolean running;

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
			dest = SocketFactory.getDefault().createSocket(forward.getDestHost(), forward.getDestPort());
			dest.setSoTimeout(1000);
			running = true;
			thread = new Thread("SFP-" + getId()) {
				public void run() {
					pipe(source, dest);
				}
			};
			thread.start();
			pipe(dest, source);
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				thread.join();
			} catch (InterruptedException e) {
				LOG.error("error on thread.join", e);
			}
			close();
		}

	}

	private void pipe(Socket in, Socket out) {
		try {
			InputStream sin = in.getInputStream();
			OutputStream sout = out.getOutputStream();
			while (running || sin.available() > 0) {
				try {
					Util.copyExp(sin, sout);
					running = false;
				} catch (SocketTimeoutException e) {
					// ok
				}
			}
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		} finally {
			running = false;
		}
	}

	private void close() {
		Util.close(dest);
		Util.close(source);
	}

}
