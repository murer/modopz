package com.github.murer.modopz.core.json;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import com.github.murer.modopz.core.service.Command;
import com.github.murer.modopz.core.util.MOUtil;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CommandGsonAdapter implements JsonDeserializer<Command>, JsonSerializer<Command> {

	public Command deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		if (json.isJsonNull()) {
			return null;
		}
		JsonObject obj = json.getAsJsonObject();
		Command ret = new Command();
		ret.setCmd(context.<String>deserialize(obj.get("cmd"), String.class));
		JsonObject params = obj.get("params") == null || obj.get("params").isJsonNull() ? null
				: obj.get("params").getAsJsonObject();
		if (params != null) {
			Set<String> keys = JSON.keys(params);
			Class<?> clazz = MOUtil.module(ret.module());
			Method method = MOUtil.moduleMethod(clazz, ret.action(), keys);
			List<String> paramNames = MOUtil.parseParamNames(method);
			for (int i = 0; i < paramNames.size(); i++) {
				String paramName = paramNames.get(i);
				Class<?> paramType = method.getParameterTypes()[i];
				Object paramParsed = context.deserialize(params.get(paramName), paramType);
				ret.getParams().put(paramName, paramParsed);
			}
		}
		return ret;
	}

	public JsonElement serialize(Command src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject ret = new JsonObject();
		ret.addProperty("cmd", src.getCmd());
		ret.add("params", context.serialize(src.getParams()));
		return ret;
	}

}
