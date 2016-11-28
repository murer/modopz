package com.murerz.modopz.core.socket;

import java.util.ArrayList;
import java.util.List;

import com.murerz.modopz.core.log.Log;
import com.murerz.modopz.core.log.LogFactory;
import com.murerz.modopz.core.module.SocketFowardModule;
import com.murerz.modopz.core.service.HttpProxyService;
import com.murerz.modopz.core.service.Kernel;
import com.murerz.modopz.core.service.Service;
import com.murerz.modopz.core.util.Util;

public class SocketForwardClient {

	private static final Log LOG = LogFactory.me().create(Util.class);

	private List<SocketForward> local = new ArrayList<SocketForward>();

	public List<SocketForward> getLocal() {
		return local;
	}

	public SocketForwardClient setLocal(List<SocketForward> local) {
		this.local = local;
		return this;
	}

	public static void main(String[] args) {
		// SocketForwardClient client =
		// ClientConfig.me().json("modsopz.socketforward",
		// SocketForwardClient.class);
		// System.setProperty("modopz.url", "http://localhost:8765/s/modopz");

		SocketForwardClient client = new SocketForwardClient();
		client.getLocal()
				.add(new SocketForward().setSourcePort(5000).setDestHost("irc.freenode.net").setDestPort(6667));
		client.getLocal()
				.add(new SocketForward().setSourcePort(5001).setDestHost("irc.freenode.net").setDestPort(6667));
		client.exec();
	}

	private void exec() {
		Service remote = new HttpProxyService().prepare();
		Kernel local = new Kernel();
		try {
			local.load(SocketFowardModuleImpl.create().setService(remote));
			local.start();

			SocketFowardModule module = local.module(SocketFowardModule.class);
			for (SocketForward forward : this.local) {
				LOG.info("Binding: " + forward);
				module.bind(forward);
			}
			Util.hold();
		} finally {
			LOG.info("Closing");
			Util.close(local);
		}
		LOG.info("Done");
	}

}
