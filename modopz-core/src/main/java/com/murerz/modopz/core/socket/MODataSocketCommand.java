package com.murerz.modopz.core.socket;

import com.murerz.modopz.core.exp.MOException;
import com.murerz.modopz.core.kernel.MOCommand;
import com.murerz.modopz.core.kernel.MOKernel;

public class MODataSocketCommand implements MOCommand {

	private Long id;
	private byte[] send;

	public byte[] getSend() {
		return send;
	}

	public MODataSocketCommand setSend(byte[] send) {
		this.send = send;
		return this;
	}

	public Long getId() {
		return id;
	}

	public MODataSocketCommand setId(Long id) {
		this.id = id;
		return this;
	}

	public MOSocketResult execute(MOKernel kernel) {
		MOSocketModule module = kernel.module(MOSocketModule.class);
		MOSocket socket = module.getSocket(id);
		if (socket == null) {
			throw new MOException("not found", new MOSocketResult().setId(id));
		}
		if (send != null && send.length > 0) {
			socket.write(send);
		}
		byte[] read = socket.read();
		if (read == null) {
			module.closeSocket(socket.getId());
			throw new MOException("not found", new MOSocketResult().setId(socket.getId()));
		}
		return new MOSocketResult().setId(socket.getId()).setCreatedAt(socket.getCreatedAt()).setReceived(read);
	}

}
