package com.murerz.modopz.core.json;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.murerz.modopz.core.kernel.MOCommand;

public class MOCommandGsonAdapter implements JsonSerializer<MOCommand> {

	public JsonElement serialize(MOCommand src, Type typeOfSrc, JsonSerializationContext context) {
		JsonElement tree = MOJson.basic().create().toJsonTree(src);
		tree.getAsJsonObject().addProperty("command", src.getClass().getSimpleName());
		return tree;
	}

}
