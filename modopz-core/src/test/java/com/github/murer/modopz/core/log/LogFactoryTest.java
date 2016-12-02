package com.github.murer.modopz.core.log;

import org.junit.Test;

import com.github.murer.modopz.core.log.Log;
import com.github.murer.modopz.core.log.LogFactory;

public class LogFactoryTest {

	@Test
	public void testLoggerFactory() {
		Log log = LogFactory.me().create(LogFactoryTest.class);
		log.info("test");
	}

}
