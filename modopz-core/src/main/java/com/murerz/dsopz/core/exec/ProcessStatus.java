package com.murerz.dsopz.core.exec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProcessStatus {

	private Integer code;

	private ByteArrayOutputStream stdout = new ByteArrayOutputStream();

	private ByteArrayOutputStream stderr = new ByteArrayOutputStream();

	public Integer getCode() {
		return code;
	}

	public ProcessStatus setCode(Integer code) {
		this.code = code;
		return this;
	}

	public ByteArrayOutputStream getStdout() {
		return stdout;
	}

	public ProcessStatus setStdout(ByteArrayOutputStream stdout) {
		this.stdout = stdout;
		return this;
	}

	public ByteArrayOutputStream getStderr() {
		return stderr;
	}

	public ProcessStatus setStderr(ByteArrayOutputStream stderr) {
		this.stderr = stderr;
		return this;
	}

	public void append(ProcessStatus status) {
		try {
			code = status.getCode();
			status.stdout.writeTo(stdout);
			status.stderr.writeTo(stderr);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
