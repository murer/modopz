package com.murerz.modopz.core.exec;

import com.murerz.modopz.core.exec.MOCloseProcessMessage.CloseStatusProcessResult;
import com.murerz.modopz.core.kernel.MOResultMessage;

public class MOCloseProcessMessage extends MOProcessMessage<CloseStatusProcessResult> {

	public static class CloseStatusProcessResult extends MOResultMessage {
		
	}

	private Long id;

	public Long getId() {
		return id;
	}

	public MOCloseProcessMessage setId(Long id) {
		this.id = id;
		return this;
	}

	@Override
	public Class<CloseStatusProcessResult> respType() {
		return CloseStatusProcessResult.class;
	}

}
