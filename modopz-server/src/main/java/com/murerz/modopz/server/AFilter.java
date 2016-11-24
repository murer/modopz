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

import com.murerz.modopz.core.service.ABasicModuleImpl;
import com.murerz.modopz.core.service.ACommand;
import com.murerz.modopz.core.service.AJSON;
import com.murerz.modopz.core.service.AKernel;
import com.murerz.modopz.core.service.AModule;
import com.murerz.modopz.core.service.AResp;
import com.murerz.modopz.core.util.AReflect;
import com.murerz.modopz.core.util.MOUtil;
import com.murerz.modopz.servlet.MOServletUtil;

public class AFilter implements Filter {

	private AKernel kernel;

	public void init(FilterConfig filterConfig) throws ServletException {
		kernel = new AKernel();
		kernel.load(new ABasicModuleImpl());
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
		String json = MOServletUtil.readText(req);
		ACommand<?> cmd = AJSON.parseCommand(kernel, json);
		AModule module = kernel.module(cmd.getModule());
		Object result = AReflect.invoke(module, cmd.getAction(), cmd.getParams());
		AResp<Object> response = AResp.create(result);
		String ret = AJSON.stringify(response);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(ret);
	}

	public void destroy() {
		MOUtil.close(kernel);
	}

}
