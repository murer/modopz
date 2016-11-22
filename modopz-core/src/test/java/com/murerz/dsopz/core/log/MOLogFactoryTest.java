package com.murerz.dsopz.core.log;

import org.junit.Test;

import com.murerz.dsopz.core.log.MOLog;
import com.murerz.dsopz.core.log.MOLogFactory;

public class MOLogFactoryTest {

	@Test
	public void testLoggerFactory() {
		MOLog log = MOLogFactory.me().create(MOLogFactoryTest.class);
		log.info("test");
	}

}
