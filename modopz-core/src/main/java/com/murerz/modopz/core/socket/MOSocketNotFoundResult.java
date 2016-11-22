package com.murerz.modopz.core.socket;

import com.murerz.modopz.core.kernel.MOResultMessage;

public class MOSocketNotFoundResult extends MOResultMessage {

	private Long id;

	public Long getId() {
		return id;
	}

	public MOSocketNotFoundResult setId(Long id) {
		this.id = id;
		return this;
	}

}
