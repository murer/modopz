package com.murerz.modopz.core.kernel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MOParserTest extends MOAbstractKernelTest {

	@Test
	public void testParser() {
		String json = kernel.parser().stringify(new MOEchoCommand().setMsg("t1"));
		assertEquals("{\"msg\":\"t1\",\"command\":\"MOEchoCommand\"}", json);

		assertEquals("[MOEchoCommand msg=t1]", kernel.parser().parse(json).toString());
	}

}
