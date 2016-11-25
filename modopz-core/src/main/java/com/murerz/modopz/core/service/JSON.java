package com.murerz.modopz.core.service;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.murerz.modopz.core.json.ByteArrayBase64GsonAdapter;

public class JSON {

	public static GsonBuilder basic() {
		GsonBuilder ret = new GsonBuilder();
		ret.registerTypeHierarchyAdapter(byte[].class, new ByteArrayBase64GsonAdapter());
		ret.registerTypeHierarchyAdapter(Command.class, new CommandGsonAdapter());
		return ret;
	}

	public static String stringify(Object obj) {
		return basic().create().toJson(obj);
	}

	@SuppressWarnings("unchecked")
	public static <T> Resp<T> parseResp(Class<T> type, String json) {
		Gson gson = basic().create();
		JsonObject tree = new JsonParser().parse(json).getAsJsonObject();
		Integer code = gson.fromJson(tree.get("code"), Integer.class);
		JsonElement result = tree.get("result");
		result = result == null ? JsonNull.INSTANCE : result;
		Object parsed = gson.fromJson(result, type);
		return (Resp<T>) Resp.create(parsed).setCode(code);
	}

	public static <T> T parse(String json, Class<T> clazz) {
		return basic().create().fromJson(json, clazz);
	}

	public static Set<String> keys(JsonObject obj) {
		Set<String> ret = new HashSet<String>();
		for (Entry<String, JsonElement> entry : obj.entrySet()) {
			ret.add(entry.getKey());
		}
		return ret;
	}

}
