package com.murerz.modopz.core.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PrivateKey;
import java.util.Map;

import com.murerz.modopz.core.client.ClientConfig;
import com.murerz.modopz.core.exp.MOException;
import com.murerz.modopz.core.json.JSON;
import com.murerz.modopz.core.util.JWT;
import com.murerz.modopz.core.util.KPCrypt;
import com.murerz.modopz.core.util.MOUtil;
import com.murerz.modopz.core.util.Util;

public class HttpProxyService extends ProxyService implements CLIConfigurable {

	private String url;
	private String pub;
	private String priv;

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

			JWT jwt = new JWT().setPub(pub).setService("modopz").setIat(System.currentTimeMillis()).exp(3600);
			PrivateKey key = KPCrypt.create(priv, null).getPrivateKey();
			String token = jwt.stringify(key);

			String send = JSON.stringify(cmd);
			URL u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
			conn.setRequestMethod("POST");
			conn.addRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.addRequestProperty("Authorization", Util.format("Bearer %s", token));
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
		pub = ClientConfig.me().prop("modopz.pub",
				"MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALF65WfU7KVi4RLLmW7JvBHCqDJoS2UFUeJag6q0qPQEiT3ZK_3LTdwr8-Kxb537Qn4ozOFkSXSnskiqwae9fdMCAwEAAQ");
		priv = ClientConfig.me().prop("modopz.priv",
				"MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAsXrlZ9TspWLhEsuZbsm8EcKoMmhLZQVR4lqDqrSo9ASJPdkr_ctN3Cvz4rFvnftCfijM4WRJdKeySKrBp7190wIDAQABAkAxpm3S9FAXnGfWuDp-MdV5KnmfUGn3ItvbdPLsqImzabIqMZu8fEB99LCot83Pxk7WSZKO4xwjyJvv1Hvjw4BxAiEA2WKvBwdRG1BG2MG8aywESlGifybL_LUeEYqw1toF1U8CIQDRAZD_bbj9bYXI-ltGouPNsJUJcVlW9OwSZVUZsDU2PQIgV79j0zx62sGet2QMgF42JSGqrBSnBoy9ZGtNUoyTCjUCIB4prbVPLm1UiwQwLVAKXfnnS_rq4svL2O3mtdtZNLS5AiEAr1qLlgnus_6o1DIZh_XqbWLJdGdtOEpMQ8OiyJjYtBI");
	}

	public static Service create(String url, String pub, String priv) {
		HttpProxyService ret = new HttpProxyService();
		ret.prepare(url);
		ret.pub = pub;
		ret.priv = priv;
		return ret;
	}

}
