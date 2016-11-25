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

import com.murerz.modopz.core.module.BasicModuleImpl;
import com.murerz.modopz.core.module.Module;
import com.murerz.modopz.core.service.Command;
import com.murerz.modopz.core.service.JSON;
import com.murerz.modopz.core.service.Kernel;
import com.murerz.modopz.core.service.Resp;
import com.murerz.modopz.core.util.MOUtil;
import com.murerz.modopz.core.util.Reflect;
import com.murerz.modopz.core.util.Util;

public class MOFilter implements Filter {

	private Kernel kernel;

	public void init(FilterConfig filterConfig) throws ServletException {
		kernel = new Kernel();
		kernel.load(new BasicModuleImpl());
		kernel.start();
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
		post(req, resp);
	}

	private void post(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String json = ServletUtil.readText(req);
		Command cmd = JSON.parse(json, Command.class);
		Module module = kernel.module(cmd.module());
		Object result = MOUtil.invoke(cmd, module);
		Resp<Object> response = Resp.create(result);
		String ret = JSON.stringify(response);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(ret);
	}

	public void destroy() {
		Util.close(kernel);
	}

}
