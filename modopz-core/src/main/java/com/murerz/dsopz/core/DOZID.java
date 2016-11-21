package com.murerz.dsopz.core;

public class DOZID {

	private static final DOZID ME = new DOZID();

	private long last = 0;

	public static long next() {
		synchronized (ME) {
			return ME.last++;
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
