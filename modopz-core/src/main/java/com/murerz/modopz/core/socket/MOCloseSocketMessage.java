package com.murerz.modopz.core.socket;

import com.murerz.modopz.core.kernel.MOResultMessage;
import com.murerz.modopz.core.socket.MOCloseSocketMessage.CloseSocketResult;

public class MOCloseSocketMessage extends MOSocketMessage<CloseSocketResult> {

	public static class CloseSocketResult extends MOResultMessage {
	}

	private Long id;

	public Long getId() {
		return id;
	}

	public MOCloseSocketMessage setId(Long id) {
		this.id = id;
		return this;
	}

	@Override
	public Class<CloseSocketResult> respType() {
		return CloseSocketResult.class;
	}

}