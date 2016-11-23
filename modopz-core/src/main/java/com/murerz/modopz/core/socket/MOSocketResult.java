package com.murerz.modopz.core.socket;

import com.murerz.modopz.core.kernel.MOResult;

public class MOSocketResult implements MOResult {

	private Long id;
	private Long createdAt;
	private byte[] received;

	public Long getId() {
		return id;
	}

	public MOSocketResult setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public MOSocketResult setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public byte[] getReceived() {
		return received;
	}

	public MOSocketResult setReceived(byte[] received) {
		this.received = received;
		return this;
	}

}
