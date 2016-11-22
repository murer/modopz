package com.murerz.dsopz.core;

import org.junit.Test;

import com.murerz.dsopz.core.MOLogFactory.DOZLog;

public class MOLogFactoryTest {

	@Test
	public void testLoggerFactory() {
		DOZLog log = MOLogFactory.me().create(MOLogFactoryTest.class);
		log.info("test");
	}

}
