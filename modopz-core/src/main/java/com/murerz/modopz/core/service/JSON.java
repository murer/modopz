package com.murerz.modopz.core.service;

import java.lang.reflect.Method;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.murerz.modopz.core.json.ByteArrayBase64GsonAdapter;
import com.murerz.modopz.core.util.Reflect;

public class JSON {

	public static GsonBuilder basic() {
		GsonBuilder ret = new GsonBuilder();
		ret.registerTypeHierarchyAdapter(byte[].class, new ByteArrayBase64GsonAdapter());
		return ret;
	}

	public static Command<?> parseCommand(Kernel kernel, String json) {
		JsonObject tree = new JsonParser().parse(json).getAsJsonObject();
		String mod = tree.get("module").getAsString();
		String act = tree.get("action").getAsString();
		JsonElement param = tree.get("params");
		param = param == null ? JsonNull.INSTANCE : param;
		Object parsed = null;
		if (!param.isJsonNull()) {
			Module module = kernel.module(mod);
			Method method = Reflect.method(module.getClass(), act);
			Class<?> type = method.getReturnType();
			parsed = basic().create().fromJson(param, type);
		}
		return new Command<Object>().setModule(mod).setAction(act).setParams(parsed);
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

}
