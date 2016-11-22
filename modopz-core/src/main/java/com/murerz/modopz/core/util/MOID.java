package com.murerz.modopz.core.util;

public class MOID {

	private static final MOID ME = new MOID();

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