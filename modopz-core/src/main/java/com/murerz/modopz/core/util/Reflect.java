package com.murerz.modopz.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflect {

	public static Object invoke(Object obj, String methodName, Object param) {
		try {
			Method method = method(obj.getClass(), methodName);
			if (method.getParameterTypes().length == 0) {
				return method.invoke(obj);
			}
			return method.invoke(obj, new Object[] { param });
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static Method method(Class<?> clazz, String methodName) {
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			boolean equals = method.getName().equals(methodName);
			if (equals) {
				return method;
			}
		}
		Class<Object> superclass = (Class<Object>) clazz.getSuperclass();
		if (superclass == null) {
			return null;
		}
		return method(superclass, methodName);
	}

}
