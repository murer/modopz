package com.murerz.modopz.core.exec;

import com.murerz.modopz.core.exec.MOStatusProcessMessage.StatusProcessResult;

public class MOStatusProcessMessage extends MOProcessMessage<StatusProcessResult> {

	public static class StatusProcessResult {
		private Long id;
		private Long createdAt;
		private Long code;
		private byte[] stdout;
		private byte[] stderr;

		public Long getId() {
			return id;
		}

		public StatusProcessResult setId(Long id) {
			this.id = id;
			return this;
		}

		public Long getCreatedAt() {
			return createdAt;
		}

		public StatusProcessResult setCreatedAt(Long createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public Long getCode() {
			return code;
		}

		public StatusProcessResult setCode(Long code) {
			this.code = code;
			return this;
		}

		public byte[] getStdout() {
			return stdout;
		}

		public StatusProcessResult setStdout(byte[] stdout) {
			this.stdout = stdout;
			return this;
		}

		public byte[] getStderr() {
			return stderr;
		}

		public StatusProcessResult setStderr(byte[] stdin) {
			this.stderr = stdin;
			return this;
		}

	}

	private Long id;

	private byte[] stdin;

	private Long waitFor;

	public Long getId() {
		return id;
	}

	public MOStatusProcessMessage setId(Long id) {
		this.id = id;
		return this;
	}

	@Override
	public Class<StatusProcessResult> respType() {
		return StatusProcessResult.class;
	}

	public byte[] getStdin() {
		return stdin;
	}

	public MOStatusProcessMessage setStdin(byte[] stdin) {
		this.stdin = stdin;
		return this;
	}

	public Long getWaitFor() {
		return waitFor;
	}

	public MOStatusProcessMessage setWaitFor(Long waitFor) {
		this.waitFor = waitFor;
		return this;
	}

}
