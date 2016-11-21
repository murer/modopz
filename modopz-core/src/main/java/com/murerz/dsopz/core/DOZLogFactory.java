package com.murerz.dsopz.core;

public abstract class DOZLogFactory {

	private static final Object MUTEX = new Object();

	private static DOZLogFactory me;

	public static interface DOZLog {

		void error(String msg, Exception e);

		void info(String msg);

	}

	public static void set(DOZLogFactory factory) {
		if(me != null) {
			throw new RuntimeException("you cannot reinit log: " + me);
		}
		synchronized (MUTEX) {
			if(me != null) {
				throw new RuntimeException("you cannot reinit log: " + me);
			}
			me = factory;	
		}
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
