package com.murerz.modopz.core.log;

import org.junit.Test;

import com.murerz.modopz.core.log.MOLog;
import com.murerz.modopz.core.log.MOLogFactory;

public class MOLogFactoryTest {

	@Test
	public void testLoggerFactory() {
		MOLog log = MOLogFactory.me().create(MOLogFactoryTest.class);
		log.info("test");
	}

}
