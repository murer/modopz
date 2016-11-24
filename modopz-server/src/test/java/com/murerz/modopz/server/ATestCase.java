package com.murerz.modopz.server;

import org.junit.After;
import org.junit.Before;

import com.murerz.modopz.core.client.AHttpClient;
import com.murerz.modopz.core.service.AService;
import com.murerz.modopz.core.util.MOUtil;

public class ATestCase {

	protected AServer server;
	protected int port;
	protected AService service;

	@Before
	public void setUp() {
		server = new AServer();
		server.boot();
		port = server.bind("127.0.0.1", 0);

		service = new AHttpClient().prepare("http://localhost:" + port + "/s/modopz");
	}

	@After
	public void tearDown() {
		MOUtil.close(server);
	}
}
