package com.murerz.modopz.core.log;

public abstract class LogFactory {

	private static final Object MUTEX = new Object();

	private static LogFactory me;

	public static void set(LogFactory factory) {
		if (me != null) {
			throw new RuntimeException("you cannot reinit log: " + me);
		}
		synchronized (MUTEX) {
			if (me != null) {
				throw new RuntimeException("you cannot reinit log: " + me);
			}
			me = factory;
		}
	}

	public static LogFactory me() {
		if (me == null) {
			synchronized (MUTEX) {
				if (me == null) {
					me = new JdkLogFactory();
				}
			}
		}
		return me;
	}

	public abstract Log create(Class<?> clazz);

}
