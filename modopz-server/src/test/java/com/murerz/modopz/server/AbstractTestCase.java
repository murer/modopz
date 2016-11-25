package com.murerz.modopz.server;

import org.junit.After;
import org.junit.Before;

import com.murerz.modopz.core.client.HttpClient;
import com.murerz.modopz.core.service.Service;
import com.murerz.modopz.core.util.Util;

public class AbstractTestCase {

	protected MOServer server;
	protected int port;
	protected Service service;

	@Before
	public void setUp() {
		server = new MOServer();
		server.boot();
		port = server.bind("127.0.0.1", 0);

		service = new HttpClient().prepare("http://localhost:" + port + "/s/modopz");
	}

	@After
	public void tearDown() {
		Util.close(server);
	}
}
