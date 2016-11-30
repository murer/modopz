package com.murerz.modopz.server.core.process;

import org.junit.Test;

import com.murerz.modopz.core.module.ProcessModule;
import com.murerz.modopz.server.AbstractTestCase;

public class ProcessModuleTest extends AbstractTestCase {

	@Test
	public void testProcess() {
		ProcessModule module = service.module(ProcessModule.class);
	}

}
