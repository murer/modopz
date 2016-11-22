package com.murerz.dsopz.core;

import org.junit.Test;

public class MOLogFactoryTest {

	@Test
	public void testLoggerFactory() {
		MOLog log = MOLogFactory.me().create(MOLogFactoryTest.class);
		log.info("test");
	}

}
