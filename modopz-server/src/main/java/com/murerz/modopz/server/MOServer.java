package com.murerz.modopz.server;

import java.io.Closeable;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHandler;

import com.murerz.modopz.core.kernel.MOKernel;
import com.murerz.modopz.core.log.MOLog;
import com.murerz.modopz.core.log.MOLogFactory;
import com.murerz.modopz.servlet.MOFilter;

public class MOServer implements Closeable {

	private static final MOLog LOG = MOLogFactory.me().create(MOServer.class);

	private Server server;

	private MOKernel kernel;

	public void close() {
		if (server != null) {
			try {
				server.stop();
			} catch (Exception e) {
				LOG.error("error stopping server", e);
			}
		}
	}

	public void boot() {
		try {
			server = new Server();
			ServletHandler handler = new ServletHandler();
			MOFilter filter = new MOFilter();
			handler.addFilterWithMapping(new FilterHolder(filter), "/s/modopz", EnumSet.of(DispatcherType.REQUEST));
			server.setHandler(handler);
			server.start();
			kernel = filter.getKernel();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int bind(String host, int port) {
		try {
			ServerConnector connector = new ServerConnector(server);
			connector.setHost(host);
			connector.setPort(port);
			server.addConnector(connector);
			connector.start();
			return connector.getLocalPort();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public MOKernel getKernel() {
		return kernel;
	}

}
