package com.murerz.modopz.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.murerz.modopz.core.service.BasicModule;
import com.murerz.modopz.core.service.Echo;

public class BasicModuleTest extends AbstractTestCase {

	@Test
	public void testPing() {
		BasicModule module = service.module(BasicModule.class);
		assertEquals("OK", module.ping());
	}

	@Test
	public void testEcho() {
		BasicModule module = service.module(BasicModule.class);
		assertEquals("[AEcho n1]", module.echo(new Echo().setName("n1")).toString());
	}

	@Test
	public void testObjectMethods() {
		BasicModule module = service.module(BasicModule.class);
		assertEquals("[proxy:com.murerz.modopz.core.service.BasicModule]", module.toString());

		assertEquals(true, module.equals(module));
		assertEquals(false, module.equals("any"));

		int hashCode = module.hashCode();
		assertTrue(hashCode > 0);
		assertEquals(hashCode, module.hashCode());

	}

}
