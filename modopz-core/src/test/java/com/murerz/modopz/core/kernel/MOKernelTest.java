package com.murerz.modopz.core.kernel;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import com.murerz.modopz.core.util.MOUtil;

public class MOKernelTest {

	private MOKernel kernel;

	@After
	public void tearDown() {
		MOUtil.close(kernel);
	}

	@Test
	public void testKernel() {
		kernel = MOKernel.create();
		kernel.load(new MOEchoModule());

		kernel.start();
		assertEquals("t1", new MOEchoCommand().setMsg("t1").execute(kernel).getMsg());
		assertEquals("t2", new MOEchoCommand().setMsg("t2").execute(kernel).getMsg());
	}

}
