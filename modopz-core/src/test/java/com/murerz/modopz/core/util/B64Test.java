package com.murerz.modopz.core.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class B64Test {

	@Test
	public void testEncode() {
		assertEquals("dGVzdA", B64.encode(Util.toBytes("test", "UTF-8")));
		assertEquals("YWJj", B64.encode(Util.toBytes("abc", "UTF-8")));
	}

	@Test
	public void testDecode() {
		assertEquals("test", Util.toString(B64.decode("dGVzdA=="), "UTF-8"));
		assertEquals("test", Util.toString(B64.decode("dGVzdA="), "UTF-8"));
		assertEquals("test", Util.toString(B64.decode("dGVzdA"), "UTF-8"));
		assertEquals("abc", Util.toString(B64.decode("YWJj"), "UTF-8"));
	}

	@Test
	public void testURLSafe() {
		assertEquals("-_s", B64.encode(new byte[] { -5, -5 }));
		assertEquals("[-5, -5]", Arrays.toString(B64.decode("-_s=")));
		assertEquals("[-5, -5]", Arrays.toString(B64.decode("-_s")));
	}

}
