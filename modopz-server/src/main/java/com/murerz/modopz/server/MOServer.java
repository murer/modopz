package com.murerz.modopz.server;

import java.io.Closeable;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

import com.murerz.modopz.core.log.Log;
import com.murerz.modopz.core.log.LogFactory;

public class MOServer implements Closeable {

	private static final Log LOG = LogFactory.me().create(MOServer.class);

	private Server server;

	public void boot() {
		try {
			server = new Server();
			ServletHandler handler = new ServletHandler();
			handler.addFilterWithMapping(MOFilter.class, "/s/modopz", EnumSet.of(DispatcherType.REQUEST));
			server.setHandler(handler);
			server.start();
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

	public void close() {
		if (server != null) {
			try {
				server.stop();
			} catch (Exception e) {
				LOG.error("error stopping server", e);
			}
		}
	}

}
