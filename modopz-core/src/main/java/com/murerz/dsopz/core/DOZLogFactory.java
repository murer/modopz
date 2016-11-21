package com.murerz.dsopz.core;

public abstract class DOZLogFactory {

	private static final Object MUTEX = new Object();

	private static DOZLogFactory me;

	public static interface DOZLog {

		void error(String msg, Exception e);

		void info(String msg);

	}

	public static DOZLogFactory me() {
		if (me == null) {
			synchronized (MUTEX) {
				if (me == null) {
					me = new JdkDOZLogFactory();
				}
			}
		}
		return me;
	}

	public abstract DOZLog create(Class<?> clazz);

}
