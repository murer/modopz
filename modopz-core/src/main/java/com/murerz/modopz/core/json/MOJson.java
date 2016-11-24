package com.murerz.modopz.core.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.murerz.modopz.core.kernel.MOCommand;

public class MOJson {

	public static String stringify(Object obj) {
		return gson().toJson(obj);
	}

	public static Gson gson() {
		return builder().create();
	}

	public static GsonBuilder builder() {
		return basic().registerTypeHierarchyAdapter(MOCommand.class, new MOCommandGsonAdapter());
	}

	public static GsonBuilder basic() {
		return new GsonBuilder().registerTypeHierarchyAdapter(byte[].class, new AByteArrayBase64GsonAdapter());
	}

	public static <T> T parse(String json, Class<T> clazz) {
		return gson().fromJson(json, clazz);
	}

	public static Object parse(String text) {
		return parse(text, null);
	}

}
