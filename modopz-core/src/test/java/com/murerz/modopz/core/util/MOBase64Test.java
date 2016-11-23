package com.murerz.modopz.core.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class MOBase64Test {

	@Test
	public void testEncode() {
		assertEquals("dGVzdA==", MOB64.encode(MOUtil.toBytes("test", "UTF-8")));
		assertEquals("YWJj", MOB64.encode(MOUtil.toBytes("abc", "UTF-8")));
	}

	@Test
	public void testDecode() {
		assertEquals("test", MOUtil.toString(MOB64.decode("dGVzdA=="), "UTF-8"));
		assertEquals("abc", MOUtil.toString(MOB64.decode("YWJj"), "UTF-8"));
	}

	@Test
	public void testURLSafe() {
		assertEquals("-_s=", MOB64.encode(new byte[] { -5, -5 }));
		assertEquals("[-5, -5]", Arrays.toString(MOB64.decode("-_s=")));
	}

}
