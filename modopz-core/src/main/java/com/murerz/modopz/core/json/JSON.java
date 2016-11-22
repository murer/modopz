package com.murerz.modopz.core.json;

import com.google.gson.Gson;

public class JSON {

	public static String stringify(Object obj) {
		return new Gson().toJson(obj);
	}

	public static <T> T parse(String json, Class<T> clazz) {
		return new Gson().fromJson(json, clazz);
	}

}
