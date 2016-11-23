package com.murerz.modopz.core.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.murerz.modopz.core.util.MOB64;

public class MOByteArrayBase64GsonAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {

	public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		String encoded = json.getAsString();
		return MOB64.decode(encoded);
	}

	public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(MOB64.encode(src));
	}

}
