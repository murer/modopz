package com.murerz.dsopz.core.exec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.murerz.dsopz.core.MOUtil;

public class MOProcessTest {

	@Test
	public void testProcessStdout() {
		MOProcess process = MOProcess.create(Arrays.asList("echo", "abc"));
		assertTrue(process.getCreatedAt() <= System.currentTimeMillis());
		assertTrue(process.getCreatedAt() >= System.currentTimeMillis() - 5000);
		assertTrue(process.getId() > 0);

		ProcessStatus status = process.waitFor(500L);
		assertEquals("abc", MOUtil.toString(status.getStdout(), "UTF-8").trim());
		assertEquals("", MOUtil.toString(status.getStderr(), "UTF-8"));
		assertEquals(new Integer(0), status.getCode());
	}

	@Test
	public void testProcessComplete() {
		MOProcess process = MOProcess.create(Arrays.asList("/bin/bash"));
		assertTrue(process.getCreatedAt() <= System.currentTimeMillis());
		assertTrue(process.getCreatedAt() >= System.currentTimeMillis() - 5000);
		assertTrue(process.getId() > 0);
		
		process.stdin("#!/bin/bash -xe\necho stderr 1>&2; echo stdout; exit 5;\n".getBytes());

		ProcessStatus status = process.waitFor(500L);
		assertEquals("stdout", MOUtil.toString(status.getStdout(), "UTF-8").trim());
		assertEquals("stderr", MOUtil.toString(status.getStderr(), "UTF-8").trim());
		assertEquals(new Integer(5), status.getCode());
	}
	
	@Test
	public void testProcessWait() {
		MOProcess process = MOProcess.create(Arrays.asList("/bin/bash"));
		assertTrue(process.getCreatedAt() <= System.currentTimeMillis());
		assertTrue(process.getCreatedAt() >= System.currentTimeMillis() - 5000);
		assertTrue(process.getId() > 0);
		
		process.stdin("#!/bin/bash -xe\nsleep 1; echo stderr 1>&2; echo stdout; exit 5;\n".getBytes());

		ProcessStatus status = process.waitFor(1500L);
		assertEquals("stdout", MOUtil.toString(status.getStdout(), "UTF-8").trim());
		assertEquals("stderr", MOUtil.toString(status.getStderr(), "UTF-8").trim());
		assertEquals(new Integer(5), status.getCode());
	}

}
