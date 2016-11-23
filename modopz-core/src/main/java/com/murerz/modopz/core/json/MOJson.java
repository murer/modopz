package com.murerz.modopz.core.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MOJson {

	public static String stringify(Object obj) {
		return gson().toJson(obj);
	}

	public static Gson gson() {
		return builder().create();
	}

	public static GsonBuilder builder() {
		return new GsonBuilder().registerTypeHierarchyAdapter(byte[].class, new MOByteArrayBase64GsonAdapter());
	}

	public static <T> T parse(String json, Class<T> clazz) {
		return gson().fromJson(json, clazz);
	}

}
