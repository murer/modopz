package com.github.murer.modopz.server.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.github.murer.modopz.core.exp.MOException;
import com.github.murer.modopz.core.module.BasicModule;
import com.github.murer.modopz.core.service.Echo;
import com.github.murer.modopz.core.util.Util;
import com.github.murer.modopz.server.AbstractTestCase;

public class BasicModuleTest extends AbstractTestCase {

	@Test
	public void testPing() {
		BasicModule module = service.module(BasicModule.class);
		assertEquals("OK", module.ping());
	}

	@Test
	public void testEcho() {
		BasicModule module = service.module(BasicModule.class);
		assertEquals("[Echo n1]", module.echo(new Echo().setName("n1")).toString());
		assertNull(module.echo(null));
	}

	@Test
	public void testNull() {
		BasicModule module = service.module(BasicModule.class);
		assertNull(module.none());
	}

	@Test
	public void testException() {
		BasicModule module = service.module(BasicModule.class);
		try {
			module.exp(404);
			fail("MOException expected");
		} catch (MOException e) {
			assertEquals(new Integer(404), e.getCode());
			assertNotNull(e.getMessage());
		}
		try {
			module.exp(null);
			fail("MOException expected");
		} catch (MOException e) {
			assertEquals(new Integer(500), e.getCode());
			assertNotNull(e.getMessage());
		}
	}

	@Test
	public void testObjectMethods() {
		BasicModule module = service.module(BasicModule.class);
		assertEquals(Util.format("[proxy:%s]", BasicModule.class.getName()), module.toString());

		assertEquals(true, module.equals(module));
		assertEquals(false, module.equals("any"));

		int hashCode = module.hashCode();
		assertTrue(hashCode > 0);
		assertEquals(hashCode, module.hashCode());

	}

}
