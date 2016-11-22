package com.murerz.modopz.core.json;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JSONTest {

	public static class J1 {
		private Long id;
		private String name;

		public Long getId() {
			return id;
		}

		public J1 setId(Long id) {
			this.id = id;
			return this;
		}

		public String getName() {
			return name;
		}

		public J1 setName(String name) {
			this.name = name;
			return this;
		}

		@Override
		public String toString() {
			return "[J1 id=" + id + ", name=" + name + "]";
		}

	}

	@Test
	public void testJson() {
		assertEquals("{\"id\":1,\"name\":\"n1\"}", JSON.stringify(new J1().setId(1L).setName("n1")));
		assertEquals("[J1 id=1, name=n1]", JSON.parse("{\"id\":1,\"name\":\"n1\"}", J1.class).toString());
	}

}
