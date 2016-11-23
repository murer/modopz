package com.murerz.modopz.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.murerz.modopz.core.exec.MOProcessModule;
import com.murerz.modopz.core.kernel.MOCommand;
import com.murerz.modopz.core.kernel.MOEchoModule;
import com.murerz.modopz.core.kernel.MOKernel;
import com.murerz.modopz.core.kernel.MOResult;
import com.murerz.modopz.core.socket.MOSocketModule;
import com.murerz.modopz.core.util.MOUtil;

public class MOFilter implements Filter {

	private MOKernel kernel;

	public MOKernel getKernel() {
		return kernel;
	}

	public final void init(FilterConfig filterConfig) throws ServletException {
		kernel = createKernel();
	}

	protected MOKernel createKernel() {
		MOKernel ret = new MOKernel();
		ret.load(new MOEchoModule());
		ret.load(new MOProcessModule());
		ret.load(new MOSocketModule());
		ret.start();
		return ret;
	}

	public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		filter((HttpServletRequest) request, (HttpServletResponse) response);
	}

	protected void filter(HttpServletRequest req, HttpServletResponse resp) {
		try {
			if (!"POST".equals(req.getMethod())) {
				resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				return;
			}
			post(req, resp);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected void post(HttpServletRequest req, HttpServletResponse resp) {
		String json = MOServletUtil.readText(req);
		MOCommand command = kernel.parser().parse(json);
		MOResult result = command.execute(kernel);
		MOServletUtil.writeJson(resp, result);
	}

	public final void destroy() {
		closeKernel();
	}

	protected void closeKernel() {
		MOUtil.close(kernel);
	}

}
