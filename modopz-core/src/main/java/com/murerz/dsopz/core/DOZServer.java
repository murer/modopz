package com.murerz.dsopz.core;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

public class DOZServer implements Closeable {

	private List<DOZHandler<?>> handlers = new ArrayList<DOZHandler<?>>();

	public DOZServer handler(DOZHandler<?> handler) {
		handlers.add(handler);
		return this;
	}

	public DOZServer start() {
		for (DOZHandler<?> handler : handlers) {
			handler.start();
		}
		return this;
	}

	@Override
	public void close() {
		for (int i = handlers.size() - 1; i >= 0; i--) {
			DOZUtil.close(handlers.get(i));
		}
	}

	public DOZServer command(DOZCommand command) {
		return null;
	}

}
