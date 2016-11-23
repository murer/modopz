package com.murerz.modopz.server;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.murerz.modopz.core.kernel.MOEchoCommand;

public class MOServerEchoTest extends MOServerTestCase {

	@Test
	public void testServer() {
		assertEquals("[MOEchoCommand msg=t1]",
				client.json(new MOEchoCommand().setMsg("t1"), MOEchoCommand.class).toString());
	}

}
