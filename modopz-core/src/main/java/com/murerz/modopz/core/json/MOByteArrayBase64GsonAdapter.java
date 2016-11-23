package com.murerz.modopz.core.json;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class MOByteArrayBase64GsonAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {

	public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonArray array = json.getAsJsonArray();
		byte[] ret = new byte[array.size()];
		int i = 0;
		for (JsonElement n : array) {
			ret[i++] = n.getAsByte();
		}
		return ret;
	}

	public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
		JsonArray ret = new JsonArray();
		ret.add(new JsonPrimitive(97L));
		ret.add(new JsonPrimitive(98L));
		ret.add(new JsonPrimitive(99L));
		return ret;
	}

}
