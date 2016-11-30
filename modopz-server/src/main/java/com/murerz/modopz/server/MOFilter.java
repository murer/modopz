package com.murerz.modopz.server;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.murerz.modopz.core.json.JSON;
import com.murerz.modopz.core.log.Log;
import com.murerz.modopz.core.log.LogFactory;
import com.murerz.modopz.core.module.BasicModuleImpl;
import com.murerz.modopz.core.module.Module;
import com.murerz.modopz.core.process.ProcessModuleImpl;
import com.murerz.modopz.core.service.Command;
import com.murerz.modopz.core.service.Kernel;
import com.murerz.modopz.core.service.Resp;
import com.murerz.modopz.core.socket.SocketModuleImpl;
import com.murerz.modopz.core.util.Auth;
import com.murerz.modopz.core.util.JWT;
import com.murerz.modopz.core.util.MOUtil;
import com.murerz.modopz.core.util.Util;

public class MOFilter implements Filter {

	private static final Log LOG = LogFactory.me().create(MOFilter.class);

	private Kernel kernel;

	private Auth auth;

	public void init(FilterConfig filterConfig) throws ServletException {
		kernel = new Kernel();
		kernel.load(new BasicModuleImpl());
		kernel.load(new ProcessModuleImpl());
		kernel.load(new SocketModuleImpl());
		kernel.start();

		auth = new Auth();
		auth.add(System.getProperty("modopz.server.pub",
				"MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALF65WfU7KVi4RLLmW7JvBHCqDJoS2UFUeJag6q0qPQEiT3ZK_3LTdwr8-Kxb537Qn4ozOFkSXSnskiqwae9fdMCAwEAAQ"));
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
		auth(req, resp);
		post(req, resp);
	}

	private void auth(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String header = ServletUtil.header(req, "Authorization");
		if (header == null) {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		if (!header.startsWith("Bearer ")) {
			throw new RuntimeException("unsupported: " + header);
		}
		String token = header.replaceAll("Bearer ", "");
		JWT jwt = JWT.parse(token, auth.getPubs());
		if (jwt == null) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		if (!"modopz".equals(jwt.getService())) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
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
