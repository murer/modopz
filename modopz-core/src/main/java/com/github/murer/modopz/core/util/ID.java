package com.github.murer.modopz.core.util;

public class ID {

	private static final ID ME = new ID();

	private long last = 0;

	public static long next() {
		synchronized (ME) {
			return ++ME.last;
		}
	}

	public static void reset() {
		set(0L);
	}

	public static void set(long n) {
		synchronized (ME) {
			ME.last = n;
		}
	}

}
