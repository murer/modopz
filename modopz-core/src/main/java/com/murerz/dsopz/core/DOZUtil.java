package com.murerz.dsopz.core;

public class DOZUtil {

	public static void close(AutoCloseable o) {
		if(o != null) {
			try {
				o.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}
