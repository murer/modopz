package com.github.murer.modopz.core.process;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.murer.modopz.core.process.MOProcess;
import com.github.murer.modopz.core.process.MOProcessStatus;
import com.github.murer.modopz.core.process.ProcessCommand;
import com.github.murer.modopz.core.util.Util;

public class MOProcessTest {

	private MOProcess process;

	@Before
	public void setUp() {
		if (process != null) {
			throw new RuntimeException("wrong: " + process);
		}
	}

	@After
	public void tearDown() {
		Util.close(process);
		process = null;
	}

	@Test
	public void testProcessStdout() {
		process = MOProcess.create(new ProcessCommand().setCmds(Arrays.asList("echo", "abc")));
		assertTrue(process.getCreatedAt() <= System.currentTimeMillis());
		assertTrue(process.getCreatedAt() >= System.currentTimeMillis() - 5000);
		assertTrue(process.getId() > 0);

		MOProcessStatus status = process.waitFor(500L);
		assertEquals("abc", Util.toString(status.getStdout(), "UTF-8").trim());
		assertEquals("", Util.toString(status.getStderr(), "UTF-8"));
		assertEquals(new Integer(0), status.getCode());
	}

	@Test
	public void testProcessComplete() {
		process = MOProcess.create(new ProcessCommand().setCmds(Arrays.asList("/bin/bash")));
		assertTrue(process.getCreatedAt() <= System.currentTimeMillis());
		assertTrue(process.getCreatedAt() >= System.currentTimeMillis() - 5000);
		assertTrue(process.getId() > 0);

		process.write("#!/bin/bash -xe\necho stderr 1>&2; echo stdout; exit 5;\n".getBytes());

		MOProcessStatus status = process.waitFor(500L);
		assertEquals("stdout", Util.toString(status.getStdout(), "UTF-8").trim());
		assertEquals("stderr", Util.toString(status.getStderr(), "UTF-8").trim());
		assertEquals(new Integer(5), status.getCode());
	}

	@Test
	public void testProcessWait() {
		process = MOProcess.create(new ProcessCommand().setCmds(Arrays.asList("/bin/bash")));
		assertTrue(process.getCreatedAt() <= System.currentTimeMillis());
		assertTrue(process.getCreatedAt() >= System.currentTimeMillis() - 5000);
		assertTrue(process.getId() > 0);

		process.write("#!/bin/bash -xe\nsleep 1; echo stderr 1>&2; echo stdout; exit 5;\n".getBytes());

		MOProcessStatus status = process.waitFor(1500L);
		assertEquals("stdout", Util.toString(status.getStdout(), "UTF-8").trim());
		assertEquals("stderr", Util.toString(status.getStderr(), "UTF-8").trim());
		assertEquals(new Integer(5), status.getCode());
	}

	@Test
	public void testProcessDestroy() {
		process = MOProcess.create(new ProcessCommand().setCmds(Arrays.asList("/bin/bash")));
		assertTrue(process.getCreatedAt() <= System.currentTimeMillis());
		assertTrue(process.getCreatedAt() >= System.currentTimeMillis() - 5000);
		assertTrue(process.getId() > 0);

		process.write("#!/bin/bash -xe\nsleep 1; echo stdout;\n".getBytes());
		Util.sleep(500L);
		Util.close(process);

		MOProcessStatus status = process.waitFor(1500l);
		assertEquals("", Util.toString(status.getStdout(), "UTF-8").trim());
		assertEquals("", Util.toString(status.getStderr(), "UTF-8").trim());
		assertEquals(new Integer(143), status.getCode());
	}

}
