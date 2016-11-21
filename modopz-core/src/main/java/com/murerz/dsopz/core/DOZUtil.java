package com.murerz.dsopz.core;

import java.io.IOException;
import java.io.InputStream;

import com.murerz.dsopz.core.DOZLogFactory.DOZLog;

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

	public static byte[] readAvailable(InputStream in, int max) {
		try {
			max = Math.min(in.available(), max);
			byte[] ret = new byte[max];
			if (max > 0) {
				in.read(ret, 0, max);
			}
			return ret;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Long exitCode(Process process) {
		try {
			int ret = process.exitValue();
			return new Long(ret);
		} catch (IllegalThreadStateException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
