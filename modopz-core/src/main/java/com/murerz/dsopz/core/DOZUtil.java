package com.murerz.dsopz.core;

public class DOZUtil {

	public static void close(DOZLog log, AutoCloseable o) {
		if (o != null) {
			try {
				o.close();
			} catch (Exception e) {
				log.error("error closing", e);
			}
		}
	}

}
