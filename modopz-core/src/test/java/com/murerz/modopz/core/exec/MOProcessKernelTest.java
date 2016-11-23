package com.murerz.modopz.core.exec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import com.murerz.modopz.core.exp.MOException;
import com.murerz.modopz.core.kernel.MOAbstractKernelTest;
import com.murerz.modopz.core.util.MOUtil;

public class MOProcessKernelTest extends MOAbstractKernelTest {

	@Test
	public void testProcessComplete() {
		long id = new MOStartProcessCommand().setCmds(Arrays.asList("/bin/bash")).execute(kernel).getId();
		assertTrue(id > 0);

		MODataProcessResult status = new MOStatusProcessCommand().setId(id).execute(kernel);
		assertTrue(status.getCreatedAt() <= System.currentTimeMillis());
		assertTrue(status.getCreatedAt() >= System.currentTimeMillis() - 5000);
		assertEquals("", MOUtil.toString(status.getStdout(), "UTF-8").trim());
		assertEquals("", MOUtil.toString(status.getStderr(), "UTF-8").trim());
		assertTrue(status.getId() > 0);

		status = new MOStatusProcessCommand().setId(id)
				.setStdin("#!/bin/bash -xe\necho stderr 1>&2; echo stdout; exit 5;\n".getBytes()).setWaitFor(500L)
				.execute(kernel);
		assertEquals("stdout", MOUtil.toString(status.getStdout(), "UTF-8").trim());
		assertEquals("stderr", MOUtil.toString(status.getStderr(), "UTF-8").trim());
		assertEquals(new Integer(5), status.getCode());
	}

	@Test
	public void testProcessDestroy() {
		long id = new MOStartProcessCommand().setCmds(Arrays.asList("/bin/bash")).execute(kernel).getId();
		assertTrue(id > 0);

		MODataProcessResult status = new MOStatusProcessCommand().setId(id)
				.setStdin("#!/bin/bash -xe\nsleep 1; echo stdout;\n".getBytes()).execute(kernel);
		assertTrue(status.getCreatedAt() <= System.currentTimeMillis());
		assertTrue(status.getCreatedAt() >= System.currentTimeMillis() - 5000);
		assertEquals("", MOUtil.toString(status.getStdout(), "UTF-8").trim());
		assertEquals("", MOUtil.toString(status.getStderr(), "UTF-8").trim());
		assertTrue(status.getId() > 0);

		MOUtil.sleep(500L);
		new MOCloseProcessCommand().setId(id).execute(kernel);

		try {
			new MOStatusProcessCommand().setId(id)
					.setStdin("#!/bin/bash -xe\necho stderr 1>&2; echo stdout; exit 5;\n".getBytes()).setWaitFor(500L)
					.execute(kernel);
			fail("MOMessageException expected");
		} catch (MOException e) {
			assertEquals(new Long(id), ((MODataProcessResult) e.getResult()).getId());
		}
	}

	@Test
	public void testProcessList() {
		long id1 = new MOStartProcessCommand().setCmds(Arrays.asList("/bin/bash")).execute(kernel).getId();
		long id2 = new MOStartProcessCommand().setCmds(Arrays.asList("echo", "test")).execute(kernel).getId();
		long id3 = new MOStartProcessCommand().setCmds(Arrays.asList("/bin/bash")).execute(kernel).getId();

		assertTrue(id1 > 0);
		assertTrue(id2 > 0);
		assertTrue(id3 > 0);

		new MOStatusProcessCommand().setId(id1).setWaitFor(500L).execute(kernel);
		new MOStatusProcessCommand().setId(id2).setWaitFor(1L).execute(kernel);
		new MOStatusProcessCommand().setId(id3).setWaitFor(1L).execute(kernel);

		assertEquals("[1, 3]", new MOListProcessCommand().execute(kernel).getPrcs().toString());
	}

}
