package com.murerz.modopz.core.log;

public abstract class MOLogFactory {

	private static final Object MUTEX = new Object();

	private static MOLogFactory me;

	public static void set(MOLogFactory factory) {
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

	public static MOLogFactory me() {
		if (me == null) {
			synchronized (MUTEX) {
				if (me == null) {
					me = new MOJdkLogFactory();
				}
			}
		}
		return me;
	}

	public abstract MOLog create(Class<?> clazz);

}
