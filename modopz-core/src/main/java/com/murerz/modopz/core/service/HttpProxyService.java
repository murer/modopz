package com.murerz.modopz.core.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.murerz.modopz.core.client.ClientConfig;
import com.murerz.modopz.core.exp.MOException;
import com.murerz.modopz.core.json.JSON;
import com.murerz.modopz.core.util.MOUtil;
import com.murerz.modopz.core.util.Util;

public class HttpProxyService extends ProxyService implements CLIConfigurable {

	private String url;

	public Service prepare(String url) {
		this.url = url;
		return this;
	}

	@Override
	protected Object proxy(Invoker invoker, Object proxy, Class<?> spec, Method method, Object[] args) {
		args = args == null ? new Object[0] : args;
		Command command = new Command();
		command.module(spec.getSimpleName()).action(method.getName());

		Map<String, Object> params = MOUtil.parseParams(method, args);
		command.setParams(params);
		Resp<?> resp = post(command);
		if (resp.getCode().intValue() != 200) {
			throw new MOException("server error " + resp.getCode() + ": " + resp.getResult()).setCode(resp.getCode());
		}
		return resp.getResult();
	}

	private Resp<?> post(Command cmd) {
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
			Resp<?> ret = JSON.parse(text, Resp.class);
			return ret;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			Util.close(conn);
		}
	}

	public void start() {

	}

	public void close() {

	}

	public void config() {
		prepare(ClientConfig.me().prop("modopz.url", "http://localhost:8765/s/modopz"));
	}

}
