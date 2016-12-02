package com.github.murer.modopz.core.process;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;

import com.github.murer.modopz.core.module.ProcessModule;
import com.github.murer.modopz.core.util.Util;

public class ProcessConsole implements Closeable {

	private ProcessModule module;
	private ProcessCommand command;
	private InputStream in;
	private OutputStream out;
	private OutputStream err;
	private Long id;
	private Integer code;

	public ProcessConsole module(ProcessModule module) {
		this.module = module;
		return this;
	}

	public void close() {

	}

	public void exec() {
		try {
			id = module.create(command);
			while (true) {
				receive();
				send();
				if (code != null) {
					System.exit(code);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void receive() {
		MOProcessStatus status = module.status(id, 300L);
		if (status == null) {
			this.code = 1;
			return;
		}
		this.code = status.getCode();
		Util.writeFlush(out, status.getStdout());
		Util.writeFlush(err, status.getStderr());
	}

	private void send() {
		byte[] send = Util.readAvailable(in, 10 * 1024);
		module.write(id, send);
	}

	public ProcessConsole command(ProcessCommand command) {
		this.command = command;
		return this;
	}

	public ProcessConsole in(InputStream in) {
		this.in = in;
		return this;
	}

	public ProcessConsole out(OutputStream out) {
		this.out = out;
		return this;
	}

	public ProcessConsole err(OutputStream err) {
		this.err = err;
		return this;
	}

}
