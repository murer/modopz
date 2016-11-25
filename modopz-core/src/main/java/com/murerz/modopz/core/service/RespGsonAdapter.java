package com.murerz.modopz.core.service;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.murerz.modopz.core.util.Reflect;

public class RespGsonAdapter implements JsonDeserializer<Resp<?>>, JsonSerializer<Resp<?>> {

	public Resp<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		if (json.isJsonNull()) {
			return null;
		}
		JsonObject obj = json.getAsJsonObject();
		Resp<Object> ret = new Resp<Object>();
		ret.setCode(obj.get("code").getAsInt());
		String type = obj.get("type").getAsString();
		Class<?> clazz = Reflect.clazz(type);
		Object result = context.deserialize(obj.get("result"), clazz);
		ret.setResult(result);
		return ret;
	}

	public JsonElement serialize(Resp<?> src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject ret = new JsonObject();
		ret.addProperty("code", src.getCode());
		ret.addProperty("type", src.type());
		ret.add("result", context.serialize(src.getResult()));
		return ret;
	}

}
