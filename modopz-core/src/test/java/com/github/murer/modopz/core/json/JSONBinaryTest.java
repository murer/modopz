package com.github.murer.modopz.core.json;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.murer.modopz.core.json.JSON;
import com.github.murer.modopz.core.util.Util;

public class JSONBinaryTest {

	public static class J1 {
		private Long id;
		private byte[] data;

		public Long getId() {
			return id;
		}

		public J1 setId(Long id) {
			this.id = id;
			return this;
		}

		public byte[] getData() {
			return data;
		}

		public J1 setData(byte[] data) {
			this.data = data;
			return this;
		}

		@Override
		public String toString() {
			return "[1 id=" + id + ", data=" + Util.toString(data, "UTF-8") + "]";
		}

	}

	@Test
	public void testJsonBinary() {
		String json = JSON.stringify(new J1().setId(1L).setData(Util.toBytes("abc", "UTF-8")));
		assertEquals("{\"id\":1,\"data\":\"YWJj\"}", json);
		assertEquals("[1 id=1, data=abc]", JSON.parse(json, J1.class).toString());
	}

}
