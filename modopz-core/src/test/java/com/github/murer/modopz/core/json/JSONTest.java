package com.github.murer.modopz.core.json;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.github.murer.modopz.core.json.JSON;

public class JSONTest {

	public static class J3 {
		private Long num;

		public Long getNum() {
			return num;
		}

		public J3 setNum(Long num) {
			this.num = num;
			return this;
		}

		@Override
		public String toString() {
			return "[J3 num=" + num + "]";
		}

	}

	public static class J2 {
		private List<J3> j3s = new ArrayList<JSONTest.J3>();

		public List<J3> getJ3s() {
			return j3s;
		}

		public J2 setJ3s(List<J3> j3s) {
			this.j3s = j3s;
			return this;
		}

		@Override
		public String toString() {
			return "[J2 j3s=" + j3s + "]";
		}

	}

	public static class J1 {
		private Long id;
		private String name;
		private List<String> tags = new ArrayList<String>();
		private J2 j2;

		public J2 getJ2() {
			return j2;
		}

		public J1 setJ2(J2 j2) {
			this.j2 = j2;
			return this;
		}

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

		public List<String> getTags() {
			return tags;
		}

		public J1 setTags(List<String> tags) {
			this.tags = tags;
			return this;
		}

		@Override
		public String toString() {
			return "[J1 id=" + id + ", name=" + name + ", tags=" + tags + ", j2=" + j2 + "]";
		}

	}

	@Test
	public void testJsonBasic() {
		String json = JSON.stringify(new J1().setId(1L).setName("n1"));
		assertEquals("[J1 id=1, name=n1, tags=[], j2=null]", JSON.parse(json, J1.class).toString());
		assertEquals("[J1 id=1, name=n1, tags=[], j2=null]",
				JSON.parse("{\"id\":1,\"name\":\"n1\"}", J1.class).toString());
	}

	@Test
	public void testJsonTags() {
		String json = JSON.stringify(new J1().setId(1L).setName("n1").setTags(Arrays.asList("tag1", "tag2")));
		assertEquals("[J1 id=1, name=n1, tags=[tag1, tag2], j2=null]", JSON.parse(json, J1.class).toString());
		assertEquals("[J1 id=1, name=n1, tags=[t1, t2], j2=null]",
				JSON.parse("{\"id\":1,\"name\":\"n1\",\"tags\":[\"t1\",\"t2\"]}", J1.class).toString());
	}

	@Test
	public void testJsonList() {
		J1 j1 = new J1().setId(1L).setName("n1");
		j1.setJ2(new J2());
		j1.getJ2().getJ3s().add(new J3().setNum(100L));
		j1.getJ2().getJ3s().add(new J3().setNum(101L));

		String json = JSON.stringify(j1);
		assertEquals("[J1 id=1, name=n1, tags=[], j2=[J2 j3s=[[J3 num=100], [J3 num=101]]]]", JSON.parse(json, J1.class).toString());
	}

}
