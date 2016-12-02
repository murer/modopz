package com.github.murer.modopz.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.murer.modopz.core.json.JSON;
import com.github.murer.modopz.core.util.Util;

public class ServletUtil {

	public static final int REQUEST_MAX_SIZE = 32 * 1024 * 1024;

	public static String readText(HttpServletRequest req) {
		StringWriter writer = new StringWriter();
		InputStreamReader in = null;
		try {
			String charset = req.getCharacterEncoding();
			if (charset == null) {
				throw new RuntimeException("charset is required");
			}
			in = new InputStreamReader(new BufferedInputStream(req.getInputStream()), charset);
			int max = REQUEST_MAX_SIZE;
			for (int i = 0; i < max; i++) {
				int c = in.read();
				if (c < 0) {
					return writer.toString();
				}
				writer.append((char) c);
			}
			if (in.read() >= 0) {
				throw new EntityTooLargeHttpException(REQUEST_MAX_SIZE);
			}
			return writer.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void writeJson(HttpServletResponse resp, Object obj) {
		try {
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			String json = JSON.stringify(obj);
			resp.getWriter().write(json);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public static String header(HttpServletRequest req, String name) {
		return Util.str(req.getHeader(name));
	}

}
