package com.murerz.modopz.core.socket;

import com.murerz.modopz.core.kernel.MOResultMessage;
import com.murerz.modopz.core.socket.MODataSocketMessage.DataSocketResult;

public class MODataSocketMessage extends MOSocketMessage<DataSocketResult> {

	public static class DataSocketResult extends MOResultMessage {
		private Long id;
		private Long createdAt;
		private byte[] received;

		public Long getId() {
			return id;
		}

		public DataSocketResult setId(Long id) {
			this.id = id;
			return this;
		}

		public Long getCreatedAt() {
			return createdAt;
		}

		public DataSocketResult setCreatedAt(Long createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public byte[] getReceived() {
			return received;
		}

		public DataSocketResult setReceived(byte[] received) {
			this.received = received;
			return this;
		}

	}

	private Long id;
	private byte[] send;

	public Long getId() {
		return id;
	}

	public MODataSocketMessage setId(Long id) {
		this.id = id;
		return this;
	}

	public byte[] getSend() {
		return send;
	}

	public MODataSocketMessage setSend(byte[] send) {
		this.send = send;
		return this;

	}

	@Override
	public Class<DataSocketResult> respType() {
		return DataSocketResult.class;
	}

}
