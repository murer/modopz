package com.murerz.modopz.server.core.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.murerz.modopz.core.socket.SocketFowardLocal;
import com.murerz.modopz.core.util.Util;
import com.murerz.modopz.server.AbstractTestCase;

public class SocketFowardLocalTest extends AbstractTestCase {

	private ServerSocket server;
	private Socket socket;

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
		Util.close(socket);
		Util.close(server);
	}

	@Test
	public void testSocket() throws IOException {
		server = new ServerSocket(0);

		new SocketFowardLocal();
		
		socket = server.accept();
	}

	@Test
	public void testFoward() {

	}

}
