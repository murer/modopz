package com.murerz.modopz.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

	public static byte[] md5(byte[] data) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			return digest.digest(data);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static String md5B64(byte[] data) {
		byte[] hash = md5(data);
		return B64.encode(hash);
	}

	public static byte[] md5(String id, String charset) {
		byte[] data = Util.toBytes(id, "UTF-8");
		return md5(data);
	}

}
