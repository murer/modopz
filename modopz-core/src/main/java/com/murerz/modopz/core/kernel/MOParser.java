package com.murerz.modopz.core.kernel;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.murerz.modopz.core.json.MOJson;

public class MOParser {

	private Map<String, Class<?>> commands;

	public MOParser(Map<String, Class<?>> commands) {
		this.commands = commands;
	}

	private Gson gson() {
		return MOJson.basic().create();
	}

	public MOCommand parse(String json) {
		return parse(json, MOCommand.class);
	}

	@SuppressWarnings("unchecked")
	private <T> T parse(String json, Class<T> clazz) {
		if (!MOCommand.class.isAssignableFrom(clazz)) {
			throw new RuntimeException("unsupported: " + clazz);
		}
		JsonObject tree = new JsonParser().parse(json).getAsJsonObject();
		String cmdName = tree.get("command").getAsString();
		Class<?> cmdClass = commands.get(cmdName);
		Gson gson = gson();
		Object ret = gson.fromJson(tree, cmdClass);
		return (T) ret;
	}

}
