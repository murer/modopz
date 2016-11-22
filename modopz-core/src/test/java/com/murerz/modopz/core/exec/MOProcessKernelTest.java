package com.murerz.modopz.core.exec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.murerz.modopz.core.exec.MOStatusProcessMessage.StatusProcessResult;
import com.murerz.modopz.core.kernel.MOAbstractKernelTest;
import com.murerz.modopz.core.util.MOUtil;

public class MOProcessKernelTest extends MOAbstractKernelTest {

	private MOProcess process;

	@Before
	public void setUp() {
		super.setUp();
		if (process != null) {
			throw new RuntimeException("wrong: " + process);
		}
	}

	@After
	public void tearDown() {
		MOUtil.close(process);
		process = null;
		super.tearDown();
	}

	@Test
	public void testProcessComplete() {
		long id = kernel.command(new MOStartProcessMessage().setCmds(Arrays.asList("/bin/bash"))).getId();
		assertTrue(id > 0);

		StatusProcessResult status = kernel.command(new MOStatusProcessMessage().setId(id));
		assertTrue(status.getCreatedAt() <= System.currentTimeMillis());
		assertTrue(status.getCreatedAt() >= System.currentTimeMillis() - 5000);
		assertTrue(status.getId() > 0);

		status = kernel.command(new MOStatusProcessMessage().setId(id).setWaitFor(500L)
				.setStdin("#!/bin/bash -xe\necho stderr 1>&2; echo stdout; exit 5;\n".getBytes()));
		assertEquals("stdout", MOUtil.toString(status.getStdout(), "UTF-8").trim());
		assertEquals("stderr", MOUtil.toString(status.getStderr(), "UTF-8").trim());
		assertEquals(new Integer(5), status.getCode());
	}

	@Test
	public void testProcessDestroy() {
		long id = kernel.command(new MOStartProcessMessage().setCmds(Arrays.asList("/bin/bash"))).getId();
		assertTrue(id > 0);

		StatusProcessResult status = kernel.command(
				new MOStatusProcessMessage().setId(id).setStdin("#!/bin/bash -xe\nsleep 1; echo stdout;\n".getBytes()));

		MOUtil.sleep(500L);
		kernel.command(new MOCloseProcessMessage().setId(id));

		status = kernel.command(new MOStatusProcessMessage().setId(id).setWaitFor(500L)
				.setStdin("#!/bin/bash -xe\necho stderr 1>&2; echo stdout; exit 5;\n".getBytes()));
		assertEquals("", MOUtil.toString(status.getStdout(), "UTF-8").trim());
		assertEquals("", MOUtil.toString(status.getStderr(), "UTF-8").trim());
		assertEquals(new Integer(143), status.getCode());
	}

}
