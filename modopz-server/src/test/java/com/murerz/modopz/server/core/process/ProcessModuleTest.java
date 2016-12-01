package com.murerz.modopz.server.core.process;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.murerz.modopz.core.log.Log;
import com.murerz.modopz.core.log.LogFactory;
import com.murerz.modopz.core.module.ProcessModule;
import com.murerz.modopz.core.process.MOProcessStatus;
import com.murerz.modopz.core.process.ProcessCommand;
import com.murerz.modopz.core.util.Util;
import com.murerz.modopz.server.AbstractTestCase;

public class ProcessModuleTest extends AbstractTestCase {

	private static final Log LOG = LogFactory.me().create(ProcessModuleTest.class);

	private Long pid;

	@Override
	public void tearDown() {
		if (pid != null) {
			try {
				ProcessModule module = service.module(ProcessModule.class);
				module.destroy(pid);
			} catch (Exception e) {
				LOG.error("Error closing", e);
			}
		}
		super.tearDown();
	}

	@Test
	public void testProcess() {
		ProcessModule module = service.module(ProcessModule.class);
		pid = module.create(new ProcessCommand().setCmds(Arrays.asList("/bin/bash", "-e")));
		assertTrue(pid.longValue() > 0);

		MOProcessStatus status = module.status(pid, null);
		assertEquals("", Util.toString(status.getStdout(), "UTF-8"));
		assertEquals("", Util.toString(status.getStderr(), "UTF-8"));
		assertNull(status.getCode());

		module.write(pid, Util.toBytes("echo out; echo err 1>&2; exit 7;\n", "UTF-8"));

		status = module.status(pid, 50L);
		assertEquals("out", Util.toString(status.getStdout(), "UTF-8").trim());
		assertEquals("err", Util.toString(status.getStderr(), "UTF-8").trim());
		assertEquals(new Integer(7), status.getCode());

		module.destroy(pid);
		assertNull(module.status(pid, null));
		pid = null;
	}

	@Test
	public void testDestroy() {
		ProcessModule module = service.module(ProcessModule.class);
		pid = module.create(new ProcessCommand().setCmds(Arrays.asList("/bin/bash", "-e")));
		assertTrue(pid.longValue() > 0);

		MOProcessStatus status = module.status(pid, null);
		assertEquals("", Util.toString(status.getStdout(), "UTF-8"));
		assertEquals("", Util.toString(status.getStderr(), "UTF-8"));
		assertNull(status.getCode());

		module.destroy(pid);
		assertNull(module.status(pid, null));
		pid = null;
	}

}
