package com.murerz.modopz.core.socket;

import com.murerz.modopz.core.kernel.MOResultMessage;
import com.murerz.modopz.core.socket.MOOpenSocketMessage.OpenSocketResult;

public class MOOpenSocketMessage extends MOSocketMessage<OpenSocketResult> {

	public static class OpenSocketResult extends MOResultMessage {
		private Long id;

		public Long getId() {
			return id;
		}

		public OpenSocketResult setId(Long id) {
			this.id = id;
			return this;
		}

	}

	private String host;
	private Integer port;

	public String getHost() {
		return host;
	}

	public MOOpenSocketMessage setHost(String host) {
		this.host = host;
		return this;
	}

	public Integer getPort() {
		return port;
	}

	public MOOpenSocketMessage setPort(Integer port) {
		this.port = port;
		return this;
	}

	@Override
	public Class<OpenSocketResult> respType() {
		return OpenSocketResult.class;
	}

}
