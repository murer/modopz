package com.murerz.dsopz.core.log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MOJdkLogFactory extends MOLogFactory {

	public static class MOJdkLog implements MOLog {

		private final Logger logger;

		public MOJdkLog(Class<?> clazz) {
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
	public MOLog create(Class<?> clazz) {
		return new MOJdkLog(clazz);
	}

}
