package com.murerz.modopz.core.json;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.murerz.modopz.core.service.Command;
import com.murerz.modopz.core.service.Resp;

public class JSON {

	public static GsonBuilder basic() {
		GsonBuilder ret = new GsonBuilder();
		ret.registerTypeHierarchyAdapter(byte[].class, new ByteArrayBase64GsonAdapter());
		ret.registerTypeHierarchyAdapter(Command.class, new CommandGsonAdapter());
		ret.registerTypeHierarchyAdapter(Resp.class, new RespGsonAdapter());
		return ret;
	}

	public static String stringify(Object obj) {
		return basic().create().toJson(obj);
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
