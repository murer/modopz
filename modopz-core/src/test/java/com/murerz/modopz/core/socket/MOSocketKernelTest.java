package com.murerz.modopz.core.socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.Test;

import com.murerz.modopz.core.exp.MOMessageException;
import com.murerz.modopz.core.kernel.MOAbstractKernelTest;
import com.murerz.modopz.core.util.MOUtil;

public class MOSocketKernelTest extends MOAbstractKernelTest {

	private ServerSocket server;
	private Socket client;

	@After
	public void tearDown() {
		MOUtil.close(client);
		MOUtil.close(server);
		super.tearDown();
	}

	@Test
	public void testSocket() throws IOException {
		server = new ServerSocket(0);

		Long id = new MOOpenSocketCommand().setHost("127.0.0.1").setPort(server.getLocalPort()).execute(kernel).getId();
		assertTrue(id.longValue() > 0L);

		MOSocketResult data = new MODataSocketCommand().setId(id).execute(kernel);
		assertTrue(data.getCreatedAt() <= System.currentTimeMillis());
		assertTrue(data.getCreatedAt() >= System.currentTimeMillis() - 5000);
		assertTrue(data.getId() > 0);
		assertEquals("", MOUtil.toString(data.getReceived(), "UTF-8"));

		client = server.accept();
		assertEquals("", MOUtil.toString(new MODataSocketCommand().setId(id).execute(kernel).getReceived(), "UTF-8"));
		assertEquals("", MOUtil.readAvailable(client.getInputStream(), 10, "UTF-8"));

		MOUtil.writeFlush(client.getOutputStream(), "t1", "UTF-8");
		assertEquals("t1", MOUtil.toString(new MODataSocketCommand().setId(id).execute(kernel).getReceived(), "UTF-8"));
		assertEquals("", MOUtil.readAvailable(client.getInputStream(), 10, "UTF-8"));

		assertEquals("", MOUtil.toString(new MODataSocketCommand().setId(id).setSend(MOUtil.toBytes("t2", "UTF-8"))
				.execute(kernel).getReceived(), "UTF-8"));
		assertEquals("t2", MOUtil.readAvailable(client.getInputStream(), 10, "UTF-8"));
	}

	@Test
	public void testSocketClose() throws IOException {
		server = new ServerSocket(0);
		Long id = new MOOpenSocketCommand().setHost("127.0.0.1").setPort(server.getLocalPort()).execute(kernel).getId();
		client = server.accept();
		MOUtil.writeFlush(client.getOutputStream(), "t1", "UTF-8");
		new MODataSocketCommand().setId(id).setSend(MOUtil.toBytes("t2", "UTF-8")).execute(kernel).getReceived();
		new MOCloseSocketCommand().setId(id).execute(kernel);
		assertEquals("t2", MOUtil.readAvailable(client.getInputStream(), 10, "UTF-8"));

		try {
			assertNull(new MODataSocketCommand().setId(id).execute(kernel).getReceived());
			fail("MOMessageException expected");
		} catch (MOMessageException e) {
			assertEquals(new Long(id), ((MOSocketNotFoundResult) e.getResult()).getId());
		}
	}

	@Test
	public void testSocketOtherPeerClose() throws IOException {
		server = new ServerSocket(0);
		Long id = new MOOpenSocketCommand().setHost("127.0.0.1").setPort(server.getLocalPort()).execute(kernel).getId();
		client = server.accept();
		MOUtil.writeFlush(client.getOutputStream(), "t1", "UTF-8");
		MOUtil.close(client);
		assertEquals("t1", MOUtil.toString(new MODataSocketCommand().setId(id).execute(kernel).getReceived(), "UTF-8"));
		try {
			assertNull(new MODataSocketCommand().setId(id).execute(kernel).getReceived());
			fail("MOMessageException expected");
		} catch (MOMessageException e) {
			assertEquals(new Long(id), ((MOSocketNotFoundResult) e.getResult()).getId());
		}
	}

}
