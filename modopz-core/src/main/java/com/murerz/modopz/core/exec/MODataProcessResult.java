package com.murerz.modopz.core.exec;

import com.murerz.modopz.core.kernel.MOResult;

public class MODataProcessResult implements MOResult {

	private Long id;
	private Long createdAt;
	private Integer code;
	private byte[] stdout;
	private byte[] stderr;

	public Long getId() {
		return id;
	}

	public MODataProcessResult setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public MODataProcessResult setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public Integer getCode() {
		return code;
	}

	public MODataProcessResult setCode(Integer code) {
		this.code = code;
		return this;
	}

	public byte[] getStdout() {
		return stdout;
	}

	public MODataProcessResult setStdout(byte[] stdout) {
		this.stdout = stdout;
		return this;
	}

	public byte[] getStderr() {
		return stderr;
	}

	public MODataProcessResult setStderr(byte[] stderr) {
		this.stderr = stderr;
		return this;
	}

}
