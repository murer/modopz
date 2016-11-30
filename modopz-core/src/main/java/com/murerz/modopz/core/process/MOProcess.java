package com.murerz.modopz.core.process;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import com.murerz.modopz.core.util.ID;
import com.murerz.modopz.core.util.Util;

public class MOProcess implements Closeable {

	private static final int MAX = 512 * 1024;

	private long id;

	private long createdAt;

	private Process process;

	private InputStream stderr;

	private InputStream stdout;

	private OutputStream stdin;

	public long getId() {
		return id;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public static MOProcess create(ProcessCommand command) {
		return create(command.getCmds());
	}

	public static MOProcess create(List<String> cmds) {
		try {
			MOProcess ret = new MOProcess();
			ret.id = ID.next();
			ret.createdAt = System.currentTimeMillis();
			ProcessBuilder builder = new ProcessBuilder(cmds);
			builder.redirectOutput(Redirect.PIPE).redirectError(Redirect.PIPE).redirectInput(Redirect.PIPE);
			ret.process = builder.start();
			ret.stdout = new BufferedInputStream(ret.process.getInputStream(), MAX * 2);
			ret.stderr = new BufferedInputStream(ret.process.getErrorStream(), MAX * 2);
			ret.stdin = ret.process.getOutputStream();
			return ret;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized MOProcessStatus read() {
		MOProcessStatus ret = new MOProcessStatus();
		ret.setStdout(Util.readAvailable(this.stdout, MAX - ret.getStdout().length));
		ret.setStderr(Util.readAvailable(this.stderr, MAX - ret.getStderr().length));
		Integer code = code();
		if (code != null) {
			if (Util.available(this.stdout) <= 0 && Util.available(this.stderr) <= 0) {
				ret.setCode(code);
			}
		}
		return ret;
	}

	public MOProcessStatus waitFor(long timeout) {
		long before = System.currentTimeMillis();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream err = new ByteArrayOutputStream();
		Integer code = null;
		while (true) {
			MOProcessStatus status = read();
			Util.writeFlush(out, status.getStdout());
			Util.writeFlush(err, status.getStderr());
			code = status.getCode();
			if (code != null) {
				return new MOProcessStatus().setCode(code).setStdout(out.toByteArray()).setStderr(err.toByteArray());
			}
			if (before + timeout < System.currentTimeMillis()) {
				return new MOProcessStatus().setCode(code).setStdout(out.toByteArray()).setStderr(err.toByteArray());
			}
			Util.sleep(20L);
		}
	}

	private Integer code() {
		try {
			return process.exitValue();
		} catch (IllegalThreadStateException e) {
			return null;
		}
	}

	public synchronized void write(byte[] buffer) {
		Util.writeFlush(stdin, buffer);
	}

	public synchronized void close() {
		process.destroy();
	}

}
