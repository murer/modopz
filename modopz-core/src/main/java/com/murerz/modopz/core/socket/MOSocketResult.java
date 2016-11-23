package com.murerz.modopz.core.socket;

import com.murerz.modopz.core.kernel.MOResult;

public class MOSocketResult implements MOResult {

	private Long id;

	public Long getId() {
		return id;
	}

	public MOSocketResult setId(Long id) {
		this.id = id;
		return this;
	}

}
