package com.murerz.modopz.core.exec;

import com.murerz.modopz.core.kernel.MOResultMessage;

public class MOProcessNotFoundResult extends MOResultMessage {

	private Long id;

	public Long getId() {
		return id;
	}

	public MOProcessNotFoundResult setId(Long id) {
		this.id = id;
		return this;
	}

}
