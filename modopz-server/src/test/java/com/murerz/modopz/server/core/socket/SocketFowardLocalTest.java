package com.murerz.modopz.server.core.socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.Test;

import com.murerz.modopz.core.module.SocketFowardModule;
import com.murerz.modopz.core.socket.SocketForward;
import com.murerz.modopz.core.util.Util;
import com.murerz.modopz.server.AbstractTestCase;

public class SocketFowardLocalTest extends AbstractTestCase {

	private ServerSocket server;
	private Socket socket;
	private Socket client;

	@After
	public void tearDown() {
		Util.close(client);
		Util.close(socket);
		Util.close(server);
	}

	@Test
	public void testFoward() throws Exception {
		server = new ServerSocket(0);

		SocketFowardModule module = local.module(SocketFowardModule.class);
		SocketForward forward = new SocketForward().setSourcePort(0).setDestHost("127.0.0.1")
				.setDestPort(server.getLocalPort());
		String id = module.bind(forward);
		int port = Integer.parseInt(id.replaceAll("^.+:", ""));
		assertTrue(port > 0);

		client = new Socket(forward.getSourceHost(), port);
		socket = server.accept();
		socket.setSoTimeout(1000);
		
		Util.writeFlush(client.getOutputStream(), "t1", "UTF-8");
		Util.writeFlush(socket.getOutputStream(), "t2", "UTF-8");
		Util.sleep(150);
		assertEquals("t2", Util.readAvailable(client.getInputStream(), 10, "UTF-8"));
		assertEquals("t1", Util.readAvailable(socket.getInputStream(), 10, "UTF-8"));
		
		Util.writeFlush(socket.getOutputStream(), "t4", "UTF-8");
		Util.writeFlush(client.getOutputStream(), "t3", "UTF-8");
		Util.sleep(150);
		assertEquals("t3", Util.readAvailable(socket.getInputStream(), 10, "UTF-8"));
		assertEquals("t4", Util.readAvailable(client.getInputStream(), 10, "UTF-8"));
	}

	@Test
	public void testFowardLocalClose() throws Exception {
		server = new ServerSocket(0);

		SocketFowardModule module = local.module(SocketFowardModule.class);
		SocketForward forward = new SocketForward().setSourcePort(0).setDestHost("127.0.0.1")
				.setDestPort(server.getLocalPort());
		int port = Integer.parseInt(module.bind(forward).replaceAll("^.+:", ""));
		
		client = new Socket(forward.getSourceHost(), port);
		socket = server.accept();
		socket.setSoTimeout(1000);
		
		Util.writeFlush(client.getOutputStream(), "t1", "UTF-8");
		
		Util.sleep(100);
		client.close();
		assertEquals("t1", Util.readAll(socket.getInputStream(), "UTF-8"));
	}
	
	@Test
	public void testFowardRemoteClose() throws Exception {
		server = new ServerSocket(0);

		SocketFowardModule module = local.module(SocketFowardModule.class);
		SocketForward forward = new SocketForward().setSourcePort(0).setDestHost("127.0.0.1")
				.setDestPort(server.getLocalPort());
		int port = Integer.parseInt(module.bind(forward).replaceAll("^.+:", ""));
		
		client = new Socket(forward.getSourceHost(), port);
		socket = server.accept();
		socket.setSoTimeout(1000);
		
		Util.writeFlush(client.getOutputStream(), "t1", "UTF-8");
		Util.writeFlush(socket.getOutputStream(), "t2", "UTF-8");
		
		Util.sleep(100);
		socket.close();
		assertEquals("t2", Util.readAll(client.getInputStream(), "UTF-8"));
	}
	
}
