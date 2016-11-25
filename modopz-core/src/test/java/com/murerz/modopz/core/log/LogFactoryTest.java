package com.murerz.modopz.core.log;

import org.junit.Test;

import com.murerz.modopz.core.log.Log;
import com.murerz.modopz.core.log.LogFactory;

public class LogFactoryTest {

	@Test
	public void testLoggerFactory() {
		Log log = LogFactory.me().create(LogFactoryTest.class);
		log.info("test");
	}

}
