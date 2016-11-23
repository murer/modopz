package com.murerz.modopz.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.murerz.modopz.core.json.MOJson;
import com.murerz.modopz.core.kernel.MOCommand;
import com.murerz.modopz.core.util.MOUtil;

public class MOHttpClient {

	private String url;

	public String getUrl() {
		return url;
	}

	public MOHttpClient setUrl(String url) {
		this.url = url;
		return this;
	}

	public <T> T json(MOCommand command, Class<T> type) {
		String send = MOJson.stringify(command);
		HttpURLConnection conn = null;
		try {
			URL u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
			conn.setRequestMethod("POST");
			conn.addRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream out = conn.getOutputStream();
			out.write(MOUtil.toBytes(send, "UTF-8"));
			out.flush();
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
			return MOJson.parse(text, type);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (ProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			MOUtil.close(conn);
		}
	}

}
