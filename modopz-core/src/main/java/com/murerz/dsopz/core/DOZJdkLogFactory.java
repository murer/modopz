package com.murerz.dsopz.core;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DOZJdkLogFactory extends DOZLogFactory {

	public static class DOZJdkLog implements DOZLog {

		private final Logger logger;

		public DOZJdkLog(Class<?> clazz) {
			logger = Logger.getLogger(clazz.getName());
		}

		public void error(String msg, Exception e) {
			logger.log(Level.SEVERE, msg, e);
		}

		public void info(String msg) {
			logger.log(Level.INFO, msg);
		}

	}

	@Override
	public DOZLog create(Class<?> clazz) {
		return new DOZJdkLog(clazz);
	}

}
