package com.murerz.modopz.server;

import org.junit.After;
import org.junit.Before;

import com.murerz.modopz.client.util.MOHttpClient;
import com.murerz.modopz.core.util.MOUtil;

public class MOServerTestCase {

	protected MOHttpClient client;
	private MOServer server;
	private int port;

	@Before
	public void setUp() {
		server = new MOServer();
		server.boot();
		port = server.bind("127.0.0.1", 0);
		client = new MOHttpClient().setUrl("http://localhost:" + port + "/s/modopz");
	}

	@After
	public void tearDown() {
		MOUtil.close(server);
	}
}
