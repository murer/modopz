package com.murerz.modopz.server;

import com.murerz.modopz.core.util.MOUtil;

public class MOBootstrap {

	public static void main(String[] args) {
		MOServer server = new MOServer();
		try {
			server.boot();
			server.bind("127.0.0.1", 8765);
			server.waitFor();
		} finally {
			MOUtil.close(server);
		}
	}

}
