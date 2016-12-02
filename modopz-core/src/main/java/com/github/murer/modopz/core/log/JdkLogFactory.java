package com.github.murer.modopz.core.log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JdkLogFactory extends LogFactory {

	public static class JdkLog implements Log {

		private final Logger logger;

		public JdkLog(Class<?> clazz) {
			logger = Logger.getLogger(clazz.getName());
		}

		public void error(String msg, Exception e) {
			logger.log(Level.SEVERE, msg, e);
		}

		public void info(String msg) {
			logger.log(Level.INFO, msg);
		}

		public void error(String msg) {
			logger.log(Level.SEVERE, msg);
		}

	}

	@Override
	public Log create(Class<?> clazz) {
		return new JdkLog(clazz);
	}

}
