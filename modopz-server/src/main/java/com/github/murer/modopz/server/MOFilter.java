package com.github.murer.modopz.server;

import java.io.IOException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.murer.modopz.core.json.JSON;
import com.github.murer.modopz.core.log.Log;
import com.github.murer.modopz.core.log.LogFactory;
import com.github.murer.modopz.core.module.BasicModuleImpl;
import com.github.murer.modopz.core.module.Module;
import com.github.murer.modopz.core.process.ProcessModuleImpl;
import com.github.murer.modopz.core.service.Command;
import com.github.murer.modopz.core.service.Kernel;
import com.github.murer.modopz.core.service.Resp;
import com.github.murer.modopz.core.socket.SocketModuleImpl;
import com.github.murer.modopz.core.util.JWT;
import com.github.murer.modopz.core.util.KPCrypt;
import com.github.murer.modopz.core.util.MOUtil;
import com.github.murer.modopz.core.util.Util;
import com.github.murer.modopz.core.util.JWT.Payload;

public class MOFilter implements Filter {

	private static final Log LOG = LogFactory.me().create(MOFilter.class);

	private Kernel kernel;

	private Map<String, PublicKey> pubs = new HashMap<String, PublicKey>();

	public void init(FilterConfig filterConfig) throws ServletException {
		kernel = new Kernel();
		kernel.load(new BasicModuleImpl());
		kernel.load(new ProcessModuleImpl());
		kernel.load(new SocketModuleImpl());
		kernel.start();

		String publicKey = System.getProperty("modopz.server.pub",
				"MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALF65WfU7KVi4RLLmW7JvBHCqDJoS2UFUeJag6q0qPQEiT3ZK_3LTdwr8-Kxb537Qn4ozOFkSXSnskiqwae9fdMCAwEAAQ");
		pubs.put("test", KPCrypt.create(null, publicKey).getPublicKey());
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		filter((HttpServletRequest) request, (HttpServletResponse) response);
	}

	private void filter(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (!"POST".equals(req.getMethod())) {
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return;
		}
		if (!auth(req, resp)) {
			return;
		}
		post(req, resp);
	}

	private boolean auth(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String header = ServletUtil.header(req, "Authorization");
		if (header == null) {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization: Bearer token is required");
			return false;
		}
		if (!header.startsWith("Bearer ")) {
			throw new RuntimeException("unsupported: " + header);
		}
		String token = header.replaceAll("Bearer ", "");
		JWT jwt = JWT.parse(token);
		if (jwt == null) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN, "JWT parse error");
			return false;
		}
		Payload payload = jwt.parsePayload();
		String user = payload.getUser();
		PublicKey key = pubs.get(user);
		if (key == null) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN, "PublicKey not found: " + user);
			return false;
		}
		if (!jwt.verify(key)) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Wrong sign");
			return false;
		}
		return true;
	}

	private void post(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String json = ServletUtil.readText(req);
		Command cmd = JSON.parse(json, Command.class);

		Module module = kernel.module(cmd.module());
		Resp<?> response = MOUtil.invoke(cmd, module);
		if (response.getCode() != 200) {
			LOG.error("Error on module: " + response.getCode() + ": " + response.getResult());
		}
		String ret = JSON.stringify(response);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(ret);
	}

	public void destroy() {
		Util.close(kernel);
	}

}
