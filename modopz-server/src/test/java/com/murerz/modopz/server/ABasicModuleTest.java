package com.murerz.modopz.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.murerz.modopz.core.service.ABasicModule;
import com.murerz.modopz.core.service.AEcho;

public class ABasicModuleTest extends ATestCase {

	@Test
	public void testPing() {
		ABasicModule module = service.module(ABasicModule.class);
		assertEquals("OK", module.ping());
	}

	@Test
	public void testEcho() {
		ABasicModule module = service.module(ABasicModule.class);
		assertEquals("[AEcho n1]", module.echo(new AEcho().setName("n1")).toString());
	}

	@Test
	public void testObjectMethods() {
		ABasicModule module = service.module(ABasicModule.class);
		assertEquals("[proxy:com.murerz.modopz.core.service.ABasicModule]", module.toString());

		assertEquals(true, module.equals(module));
		assertEquals(false, module.equals("any"));

		int hashCode = module.hashCode();
		assertTrue(hashCode > 0);
		assertEquals(hashCode, module.hashCode());

	}

}
