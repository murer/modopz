package com.murerz.modopz.client;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import org.junit.Test;

import com.murerz.modopz.client.process.MOProcessClient;
import com.murerz.modopz.core.process.MOStartProcessCommand;
import com.murerz.modopz.core.util.MOUtil;
import com.murerz.modopz.server.MOServerTestCase;

public class MOHttpClientTest extends MOServerTestCase {

	@Test
	public void testExec() throws Exception {
		MOProcessClient client = new MOProcessClient();
		client.setIn(new ByteArrayInputStream(MOUtil.toBytes(
				"#!/bin/bash -xe\nsleep 0.3;\necho stderr 1>&2;\nsleep 0.3; echo stdout;\nsleep 0.3; exit 5;\n",
				"UTF-8")));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream err = new ByteArrayOutputStream();
		client.setOut(out);
		client.setErr(err);
		client.setClient(this.client);

		MOStartProcessCommand command = new MOStartProcessCommand().setCmds(Arrays.asList("/bin/bash"));
		Integer code = client.execute(command);
		assertEquals("stdout", out.toString("UTF-8").trim());
		assertEquals("stderr", err.toString("UTF-8").trim());
		assertEquals(new Integer(5), code);
	}

}
