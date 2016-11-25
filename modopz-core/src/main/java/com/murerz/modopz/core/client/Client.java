package com.murerz.modopz.core.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.murerz.modopz.core.service.Service;

public abstract class Client implements Service {

	protected class Invoker implements InvocationHandler {

		private final Class<?> spec;

		public Invoker(Class<?> spec) {
			this.spec = spec;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			return handleInvoke(this, proxy, spec, method, args);
		}

	}

	@SuppressWarnings("unchecked")
	public <T> T module(final Class<T> spec) {
		return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[] { spec }, new Invoker(spec));
	}

	protected Object handleInvoke(Invoker invoker, Object proxy, Class<?> spec, Method method, Object[] args) {
		if ("toString".equals(method.getName()) && method.getParameterTypes().length == 0) {
			return handleToString(invoker, proxy, spec, method);
		}
		if ("equals".equals(method.getName()) && method.getParameterTypes().length == 1) {
			return handleEquals(invoker, proxy, spec, method, args[0]);
		}
		if ("hashCode".equals(method.getName()) && method.getParameterTypes().length == 0) {
			return handleHashCode(invoker, proxy, spec, method);
		}
		return proxy(invoker, proxy, spec, method, args);
	}

	protected abstract Object proxy(Invoker invoker, Object proxy, Class<?> spec, Method method, Object[] args);

	protected Object handleHashCode(Invoker invoker, Object proxy, Class<?> spec, Method method) {
		return invoker.hashCode();
	}

	protected boolean handleEquals(Invoker invoker, Object proxy, Class<?> spec, Method method, Object object) {
		return (proxy == object);
	}

	protected String handleToString(Invoker invoker, Object proxy, Class<?> spec, Method method) {
		return "[proxy:" + spec.getName() + "]";
	}

}
