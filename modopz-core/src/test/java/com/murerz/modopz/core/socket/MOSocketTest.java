package com.murerz.modopz.core.socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.murerz.modopz.core.socket.MOSocket;
import com.murerz.modopz.core.util.MOUtil;

public class MOSocketTest {

	private MOSocket socket;
	private ServerSocket server;
	private Socket client;

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
		MOUtil.close(client);
		MOUtil.close(socket);
		MOUtil.close(server);
	}

	@Test
	public void testSocket() throws IOException {
		server = new ServerSocket(0);

		socket = MOSocket.create("127.0.0.1", server.getLocalPort());
		assertTrue(socket.getCreatedAt() <= System.currentTimeMillis());
		assertTrue(socket.getCreatedAt() >= System.currentTimeMillis() - 5000);
		assertTrue(socket.getId() > 0);
		assertEquals("", MOUtil.toString(socket.read(), "UTF-8"));

		client = server.accept();
		assertEquals("", MOUtil.toString(socket.read(), "UTF-8"));
		assertEquals("", MOUtil.readAvailable(client.getInputStream(), 10, "UTF-8"));

		MOUtil.writeFlush(client.getOutputStream(), "t1", "UTF-8");
		assertEquals("t1", MOUtil.toString(socket.read(), "UTF-8"));
		assertEquals("", MOUtil.readAvailable(client.getInputStream(), 10, "UTF-8"));

		socket.write(MOUtil.toBytes("t2", "UTF-8"));
		assertEquals("", MOUtil.toString(socket.read(), "UTF-8"));
		assertEquals("t2", MOUtil.readAvailable(client.getInputStream(), 10, "UTF-8"));
	}

	@Test
	public void testSocketClose() throws IOException {
		server = new ServerSocket(0);
		socket = MOSocket.create("127.0.0.1", server.getLocalPort());
		client = server.accept();
		MOUtil.writeFlush(client.getOutputStream(), "t1", "UTF-8");
		MOUtil.close(socket);
		assertNull(socket.read());
	}

	@Test
	public void testSocketOtherPeerClose() throws IOException {
		server = new ServerSocket(0);
		socket = MOSocket.create("127.0.0.1", server.getLocalPort());
		client = server.accept();
		MOUtil.writeFlush(client.getOutputStream(), "t1", "UTF-8");
		MOUtil.close(client);
		assertEquals("t1", MOUtil.toString(socket.read(), "UTF-8"));
		assertNull(socket.read());
	}
}
