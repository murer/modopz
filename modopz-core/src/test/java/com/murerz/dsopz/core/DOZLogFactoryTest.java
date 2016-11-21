package com.murerz.dsopz.core;

import org.junit.Test;

import com.murerz.dsopz.core.DOZLogFactory.DOZLog;

public class DOZLogFactoryTest {

	@Test
	public void testLoggerFactory() {
		DOZLog log = DOZLogFactory.me().create(DOZLogFactoryTest.class);
		log.info("test");
	}

}
