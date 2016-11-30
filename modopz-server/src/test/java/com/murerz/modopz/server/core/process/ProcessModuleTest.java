package com.murerz.modopz.server.core.process;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.murerz.modopz.core.module.ProcessModule;
import com.murerz.modopz.core.process.MOProcessStatus;
import com.murerz.modopz.core.process.ProcessCommand;
import com.murerz.modopz.server.AbstractTestCase;

public class ProcessModuleTest extends AbstractTestCase {

	@Test
	public void testProcess() {
		ProcessModule module = service.module(ProcessModule.class);
		Long id = module.create(new ProcessCommand().setCmds(Arrays.asList("/bin/bash")));
		assertTrue(id.longValue() > 0);
		
		MOProcessStatus status = module.status(10L);
		assertNull(status.getStderr());
		assertNull(status.getCode());

	}

}
