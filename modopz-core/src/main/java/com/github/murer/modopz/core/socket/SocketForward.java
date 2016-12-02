package com.github.murer.modopz.core.socket;

public class SocketForward {

	private String sourceHost;
	private Integer sourcePort;
	private String destHost;
	private Integer destPort;

	public String getSourceHost() {
		return sourceHost;
	}

	public SocketForward setSourceHost(String sourceHost) {
		this.sourceHost = sourceHost;
		return this;
	}

	public Integer getSourcePort() {
		return sourcePort;
	}

	public SocketForward setSourcePort(Integer sourcePort) {
		this.sourcePort = sourcePort;
		return this;
	}

	public String getDestHost() {
		return destHost;
	}

	public SocketForward setDestHost(String destHost) {
		this.destHost = destHost;
		return this;
	}

	public Integer getDestPort() {
		return destPort;
	}

	public SocketForward setDestPort(Integer destPort) {
		this.destPort = destPort;
		return this;
	}

}
