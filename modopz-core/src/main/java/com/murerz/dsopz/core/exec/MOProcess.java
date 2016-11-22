package com.murerz.dsopz.core.exec;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import com.murerz.dsopz.core.MOID;
import com.murerz.dsopz.core.MOUtil;

public class MOProcess {

	private static final int MAX = 512 * 1024;

	private long id;

	private long createdAt;

	private Process process;

	private BufferedInputStream stderr;

	private BufferedInputStream stdout;

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
			ret.id = MOID.next();
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

	public ProcessStatus status() {
		try {
			ProcessStatus ret = new ProcessStatus();
			MOUtil.copyAvailable(this.stdout, ret.getStdout(), MAX - ret.getStdout().size());
			MOUtil.copyAvailable(this.stderr, ret.getStderr(), MAX - ret.getStderr().size());
			Integer code = code();
			if (code != null) {
				if (this.stdout.available() == 0 && this.stderr.available() == 0) {
					ret.setCode(code);
				}
			}
			return ret;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public ProcessStatus waitFor(long timeout) {
		long before = System.currentTimeMillis();
		ProcessStatus ret = status();
		while (true) {
			if (ret.getCode() != null) {
				return ret;
			}
			if (before + timeout < System.currentTimeMillis()) {
				return ret;
			}
			MOUtil.sleep(20L);
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

	public void stdin(byte[] bytes) {
		try {
			this.stdin.write(bytes);
			this.stdin.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
