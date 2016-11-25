package com.murerz.modopz.core.process;

import java.io.BufferedInputStream;
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

	public synchronized MOProcessStatus status() {
		MOProcessStatus ret = new MOProcessStatus();
		Util.copyAvailable(this.stdout, ret.getStdout(), MAX - ret.getStdout().size());
		Util.copyAvailable(this.stderr, ret.getStderr(), MAX - ret.getStderr().size());
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
		MOProcessStatus ret = status();
		while (true) {
			if (ret.getCode() != null) {
				return ret;
			}
			if (before + timeout < System.currentTimeMillis()) {
				return ret;
			}
			Util.sleep(20L);
			ret.append(status());
		}
	}

	private Integer code() {
		try {
			return process.exitValue();
		} catch (IllegalThreadStateException e) {
			return null;
		}
	}

	public synchronized void stdin(byte[] buffer) {
		Util.writeFlush(stdin, buffer);
	}

	public synchronized void close() {
		process.destroy();
	}

}
