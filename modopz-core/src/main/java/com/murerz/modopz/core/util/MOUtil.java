package com.murerz.modopz.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.murerz.modopz.core.module.Module;
import com.murerz.modopz.core.service.Command;
import com.murerz.modopz.core.service.JSON;
import com.murerz.modopz.core.service.Param;

public class MOUtil {

	public static Object invoke(Command cmd, Module module) {
		try {
			Class<?> spec = MOUtil.module(cmd.module());
			Method method = MOUtil.moduleMethod(spec, cmd.action(), cmd.keys());
			Object[] args = formatArgs(method, cmd.getParams());
			return method.invoke(module, args);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private static Object[] formatArgs(Method method, Map<String, Object> params) {
		Object[] ret = new Object[params.size()];
		List<String> names = parseParamNames(method);
		for (int i = 0; i < names.size(); i++) {
			String name = names.get(i);
			ret[i] = params.get(name);
		}
		return ret;
	}

	public static Class<?> module(String name) {
		try {
			String pack = Module.class.getPackage().getName();
			name = Util.format("%s.%s", pack, name);
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static Method moduleMethod(Class<?> clazz, String methodName, Collection<String> keys) {
		keys = new HashSet<String>(keys);
		List<Method> methods = Reflect.methods(clazz, methodName);
		for (Method method : methods) {
			List<String> names = parseParamNames(method);
			if (names.size() == keys.size() && keys.containsAll(names)) {
				return method;
			}
		}
		throw new RuntimeException("method not found: " + clazz + "." + methodName + " " + keys);
	}

	public static Map<String, Object> parseParams(Method method, Object[] args) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<String> paramNames = parseParamNames(method);
		for (int i = 0; i < args.length; i++) {
			Object arg = args[i];
			String name = paramNames.get(i);
			String json = JSON.stringify(arg);
			Class<?> type = method.getParameterTypes()[i];
			Object parsed = JSON.parse(json, type);
			params.put(name, parsed);
		}
		return params;
	}

	public static List<String> parseParamNames(Method method) {
		List<String> ret = new ArrayList<String>();
		Annotation[][] annons = method.getParameterAnnotations();
		for (int i = 0; i < annons.length; i++) {
			Param annon = Reflect.annon(annons[i], Param.class);
			if (annon == null) {
				throw new RuntimeException("Param annotation not found on: " + method);
			}
			if (!ret.add(annon.value())) {
				throw new RuntimeException("Param name duplicated: " + method + ", name: " + annon.value());
			}
		}
		return ret;
	}

}
