package com.github.murer.modopz.core.process;

public class MOProcessStatus {

	private Integer code;

	private byte[] stdout = new byte[0];

	private byte[] stderr = new byte[0];

	public Integer getCode() {
		return code;
	}

	public MOProcessStatus setCode(Integer code) {
		this.code = code;
		return this;
	}

	public byte[] getStdout() {
		return stdout;
	}

	public MOProcessStatus setStdout(byte[] stdout) {
		this.stdout = stdout;
		return this;
	}

	public byte[] getStderr() {
		return stderr;
	}

	public MOProcessStatus setStderr(byte[] stderr) {
		this.stderr = stderr;
		return this;
	}

}
