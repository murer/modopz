package com.murerz.modopz.core.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

import com.murerz.modopz.core.service.ACommand;
import com.murerz.modopz.core.service.AJSON;
import com.murerz.modopz.core.service.AResp;
import com.murerz.modopz.core.service.AService;
import com.murerz.modopz.core.util.MOUtil;

public class AHttpClient extends AClient {

	private String url;

	public AService prepare(String url) {
		this.url = url;
		return this;
	}

	@Override
	protected Object proxy(Invoker invoker, Object proxy, Class<?> spec, Method method, Object[] args) {
		args = args == null ? new Object[0] : args;
		if (args.length > 1) {
			throw new RuntimeException("unsupported: " + args.length);
		}
		if (method.getParameterTypes().length > 1) {
			throw new RuntimeException("unsupported: " + method.getParameterTypes().length);
		}
		ACommand<Object> cmd = new ACommand<Object>();
		cmd.setModule(spec.getSimpleName());
		cmd.setAction(method.getName());
		cmd.setParams(args.length > 0 ? args[0] : null);
		String json = post(cmd);
		AResp<?> ret = AJSON.parseResp(method.getReturnType(), json);
		if (ret.getCode().intValue() != 200) {
			throw new RuntimeException("not implemented: " + ret.getCode());
		}
		return ret.getResult();
	}

	private String post(ACommand<Object> cmd) {
		HttpURLConnection conn = null;
		try {
			String send = AJSON.stringify(cmd);
			URL u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
			conn.setRequestMethod("POST");
			conn.addRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream out = conn.getOutputStream();
			MOUtil.writeFlush(out, send, "UTF-8");
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
			String text = MOUtil.readAll(in, charset);
			return text;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			MOUtil.close(conn);
		}
	}

}
