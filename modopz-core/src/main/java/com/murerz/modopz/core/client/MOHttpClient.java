package com.murerz.modopz.core.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.murerz.modopz.core.service.Command;
import com.murerz.modopz.core.service.JSON;
import com.murerz.modopz.core.service.Param;
import com.murerz.modopz.core.service.Service;
import com.murerz.modopz.core.util.Util;

public class MOHttpClient extends MOClient {

	private String url;

	public Service prepare(String url) {
		this.url = url;
		return this;
	}

	@Override
	protected Object proxy(Invoker invoker, Object proxy, Class<?> spec, Method method, Object[] args) {
		List<String> paramNames = parseParamNames(method);
		// args = args == null ? new Object[0] : args;
		// if (args.length > 1) {
		// throw new RuntimeException("unsupported: " + args.length);
		// }
		// if (method.getParameterTypes().length > 1) {
		// throw new RuntimeException("unsupported: " +
		// method.getParameterTypes().length);
		// }
		// Command<Object> cmd = new Command<Object>();
		// cmd.setModule(spec.getSimpleName());
		// cmd.setAction(method.getName());
		// cmd.setParams(args.length > 0 ? args[0] : null);
		// String json = post(cmd);
		// Resp<?> ret = JSON.parseResp(method.getReturnType(), json);
		// if (ret.getCode().intValue() != 200) {
		// throw new RuntimeException("not implemented: " + ret.getCode());
		// }
		// return ret.getResult();
		throw new RuntimeException("implement");
	}

	private List<String> parseParamNames(Method method) {
		List<String> ret = new ArrayList<String>();
		Annotation[][] annons = method.getParameterAnnotations();
		for (int i = 0; i < annons.length; i++) {
			Param annon = (Param) annons[i][0];
			ret.add(annon.value());
		}
		return ret;
	}

	private String post(Command cmd) {
		HttpURLConnection conn = null;
		try {
			String send = JSON.stringify(cmd);
			URL u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
			conn.setRequestMethod("POST");
			conn.addRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream out = conn.getOutputStream();
			Util.writeFlush(out, send, "UTF-8");
			int code = conn.getResponseCode();
			if (code != 200) {
				throw new RuntimeException("wrong: " + code);
			}
			String contentType = conn.getHeaderField("content-type");
			String charset = contentType.replaceAll("^.*charset=", "");
			if (charset == null) {
				throw new RuntimeException("charset is required: " + contentType);
			}
			InputStream in = conn.getInputStream();
			String text = Util.readAll(in, charset);
			return text;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			Util.close(conn);
		}
	}

}
