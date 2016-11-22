package com.murerz.modopz.core.kernel;

import org.junit.After;
import org.junit.Before;

import com.murerz.modopz.core.exec.MOProcessModule;
import com.murerz.modopz.core.socket.MOSocketModule;
import com.murerz.modopz.core.util.MOUtil;

public class MOAbstractKernelTest {

	protected MOKernel kernel;

	@Before
	public void setUp() {
		kernel = MOKernel.create();
		kernel.load(new MOEchoModule());
		kernel.load(new MOProcessModule());
		kernel.load(new MOSocketModule());

		kernel.start();
	}

	@After
	public void tearDown() {
		MOUtil.close(kernel);
	}
}
