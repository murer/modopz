package com.github.murer.modopz.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.github.murer.modopz.core.exp.MOException;
import com.github.murer.modopz.core.json.JSON;
import com.github.murer.modopz.core.module.Module;
import com.github.murer.modopz.core.service.Command;
import com.github.murer.modopz.core.service.Param;
import com.github.murer.modopz.core.service.Resp;

public class MOUtil {

	@SuppressWarnings("unchecked")
	public static Resp<?> invoke(Command cmd, Module module) {
		try {
			Class<?> spec = MOUtil.module(cmd.module());
			Method method = MOUtil.moduleMethod(spec, cmd.action(), cmd.keys());
			Object[] args = formatArgs(method, cmd.getParams());
			Object result = method.invoke(module, args);
			Class<Object> returnType = (Class<Object>) method.getReturnType();
			Resp<Object> ret = Resp.create(returnType);
			return ret.setResult(result);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			return handleException(e);
		}
	}

	private static Resp<?> handleException(InvocationTargetException exp) {
		String stack = Util.stack(exp);
		MOException mo = Util.cause(exp, MOException.class);
		if (mo == null) {
			return Resp.create(String.class).setCode(500).setResult(stack);
		}
		return Resp.create(String.class).setCode(mo.getCode()).setResult(stack);
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
		if (methods.isEmpty()) {
			throw new RuntimeException("method not found: " + clazz + "." + methodName + " " + keys);
		}
		if (methods.size() > 1) {
			throw new RuntimeException("method overload not supported: " + clazz + "." + methodName + " " + keys);
		}
		return methods.get(0);
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
