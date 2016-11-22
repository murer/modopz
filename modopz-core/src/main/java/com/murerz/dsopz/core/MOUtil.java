package com.murerz.dsopz.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import com.murerz.dsopz.core.MOLogFactory.DOZLog;

public class MOUtil {

	private static final DOZLog LOG = MOLogFactory.me().create(MOUtil.class);

	public static void close(AutoCloseable o) {
		if (o != null) {
			try {
				o.close();
			} catch (Exception e) {
				LOG.error("error closing", e);
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

	public static int copyAvailable(InputStream in, OutputStream out, int limit) {
		try {
			limit = Math.min(available(in), limit);
			if (limit <= 0) {
				return 0;
			}
			byte[] buffer = new byte[limit];
			int read = in.read(buffer);
			out.write(buffer, 0, read);
			return read;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static int available(InputStream in) {
		try {
			return in.available();
		} catch (IOException e) {
			return -1;
		}
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toString(ByteArrayOutputStream out, String charset) {
		return toString(out.toByteArray(), charset);
	}

	public static String toString(byte[] array, String charset) {
		try {
			return new String(array, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
