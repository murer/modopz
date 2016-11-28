package com.murerz.modopz.server;

import org.junit.After;
import org.junit.Before;

import com.murerz.modopz.core.service.HttpProxyService;
import com.murerz.modopz.core.service.Kernel;
import com.murerz.modopz.core.service.Service;
import com.murerz.modopz.core.socket.SocketFowardModuleImpl;
import com.murerz.modopz.core.util.Util;

public class AbstractTestCase {

	protected MOServer server;
	protected int port;
	protected Service service;
	protected Kernel local;

	@Before
	public void setUp() {
		server = new MOServer();
		server.boot();
		port = server.bind("127.0.0.1", 0);

		service = new HttpProxyService().prepare("http://localhost:" + port + "/s/modopz");

		local = new Kernel();
		local.load(SocketFowardModuleImpl.create().setService(service));
		local.start();
	}

	@After
	public void tearDown() {
		Util.close(local);
		Util.close(server);
	}
}
