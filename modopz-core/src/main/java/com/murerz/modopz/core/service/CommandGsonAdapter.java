package com.murerz.modopz.core.service;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class CommandGsonAdapter implements JsonDeserializer<Command> {

	public Command deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		if (json.isJsonNull()) {
			return null;
		}
		JsonObject obj = json.getAsJsonObject();
		String cmd = context.deserialize(obj.get("cmd"), String.class);
		return new Command().setCmd(cmd);
	}

}
