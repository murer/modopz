package com.github.murer.modopz.core.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PrivateKey;
import java.util.Map;

import com.github.murer.modopz.core.client.ClientConfig;
import com.github.murer.modopz.core.exp.MOException;
import com.github.murer.modopz.core.json.JSON;
import com.github.murer.modopz.core.util.JWT;
import com.github.murer.modopz.core.util.KPCrypt;
import com.github.murer.modopz.core.util.MOUtil;
import com.github.murer.modopz.core.util.Util;
import com.github.murer.modopz.core.util.JWT.Header;
import com.github.murer.modopz.core.util.JWT.Payload;

public class HttpProxyService extends ProxyService implements CLIConfigurable {

	private String url;
	private String user;
	private PrivateKey key;
	private String service = "modopz";

	public String getUser() {
		return user;
	}

	public HttpProxyService setUser(String user) {
		this.user = user;
		return this;
	}

	public PrivateKey getKey() {
		return key;
	}

	public HttpProxyService setKey(PrivateKey key) {
		this.key = key;
		return this;
	}

	public String getService() {
		return service;
	}

	public HttpProxyService setService(String service) {
		this.service = service;
		return this;
	}

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

			JWT jwt = new JWT();
			Header header = new Header();
			Payload payload = new Payload();
			payload.setUser(user).setService(service).exp(System.currentTimeMillis() / 1000, 3600);
			jwt.formatHeader(header).formatPayload(payload).sign(key);
			String token = jwt.formatToken();

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
		String priv = ClientConfig.me().prop("modopz.http.key",
				"MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAsXrlZ9TspWLhEsuZbsm8EcKoMmhLZQVR4lqDqrSo9ASJPdkr_ctN3Cvz4rFvnftCfijM4WRJdKeySKrBp7190wIDAQABAkAxpm3S9FAXnGfWuDp-MdV5KnmfUGn3ItvbdPLsqImzabIqMZu8fEB99LCot83Pxk7WSZKO4xwjyJvv1Hvjw4BxAiEA2WKvBwdRG1BG2MG8aywESlGifybL_LUeEYqw1toF1U8CIQDRAZD_bbj9bYXI-ltGouPNsJUJcVlW9OwSZVUZsDU2PQIgV79j0zx62sGet2QMgF42JSGqrBSnBoy9ZGtNUoyTCjUCIB4prbVPLm1UiwQwLVAKXfnnS_rq4svL2O3mtdtZNLS5AiEAr1qLlgnus_6o1DIZh_XqbWLJdGdtOEpMQ8OiyJjYtBI");
		this.key = KPCrypt.create(priv, null).getPrivateKey();
		this.service = ClientConfig.me().prop("modopz.http.service", "modopz");
		this.user = ClientConfig.me().prop("modopz.http.user", "user");
	}

	public static HttpProxyService create(String url) {
		HttpProxyService ret = new HttpProxyService();
		ret.prepare(url);
		return ret;
	}

}
