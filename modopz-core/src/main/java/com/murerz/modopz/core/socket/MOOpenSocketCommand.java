package com.murerz.modopz.core.socket;

import com.murerz.modopz.core.kernel.MOCommand;
import com.murerz.modopz.core.kernel.MOKernel;

public class MOOpenSocketCommand implements MOCommand {

	private String host;
	private Integer port;

	public String getHost() {
		return host;
	}

	public MOOpenSocketCommand setHost(String host) {
		this.host = host;
		return this;
	}

	public Integer getPort() {
		return port;
	}

	public MOOpenSocketCommand setPort(Integer port) {
		this.port = port;
		return this;
	}

	public MOSocketResult execute(MOKernel kernel) {
		MOSocketModule module = kernel.module(MOSocketModule.class);
		MOSocket socket = MOSocket.create(host, port);
		module.addSocket(socket);
		return new MOSocketResult().setId(socket.getId());
	}

}
