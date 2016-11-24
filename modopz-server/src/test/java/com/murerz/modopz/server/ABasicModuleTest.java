package com.murerz.modopz.server;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.murerz.modopz.core.service.ABasicModule;

public class ABasicModuleTest extends ATestCase {

	@Test
	public void testPing() {
		ABasicModule module = service.module(ABasicModule.class);
		assertEquals("OK", module.ping());
	}

}
