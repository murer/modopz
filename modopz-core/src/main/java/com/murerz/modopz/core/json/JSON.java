package com.murerz.modopz.core.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSON {

	public static String stringify(Object obj) {
		return gson().toJson(obj);
	}

	private static Gson gson() {
		return new GsonBuilder().registerTypeHierarchyAdapter(byte[].class, new MOByteArrayBase64GsonAdapter()).create();
	}

	public static <T> T parse(String json, Class<T> clazz) {
		return gson().fromJson(json, clazz);
	}

}
