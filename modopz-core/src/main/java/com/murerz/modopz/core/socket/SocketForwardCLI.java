package com.murerz.modopz.core.socket;

import java.util.ArrayList;
import java.util.List;

import com.murerz.modopz.core.client.CLI;
import com.murerz.modopz.core.client.ClientConfig;
import com.murerz.modopz.core.log.Log;
import com.murerz.modopz.core.log.LogFactory;
import com.murerz.modopz.core.util.Util;

public class SocketForwardCLI extends CLI {

	private static final Log LOG = LogFactory.me().create(Util.class);

	private List<SocketForward> local = new ArrayList<SocketForward>();

	public static void main(String[] args) {
		ClientConfig.me().config("modopz.client", SocketForwardCLI.class.getName());
		ClientConfig.me().config("modopz.socketforward", "5000:irc.freenode.net:6667,5001:irc.freenode.net:6667");
		CLI.cli();
	}

	public void exec() {
		SocketFowardModuleImpl mod = new SocketFowardModuleImpl();
		try {
			mod.setService(service());
			mod.start();
			for (SocketForward forward : this.local) {
				LOG.info("Binding: " + forward);
				mod.bind(forward);
			}
			hold();
		} finally {
			Util.close(mod);
		}
	}

	public void config() {
		super.config();
		String[] split = ClientConfig.me().require("modopz.socketforward").split(",");
		for (String str : split) {
			String[] array = str.split(":");
			if (array.length == 3) {
				String[] n = new String[4];
				n[0] = "127.0.0.1";
				System.arraycopy(array, 0, n, 1, array.length);
				array = n;
			}
			SocketForward forward = new SocketForward();
			forward.setSourceHost(array[0]);
			forward.setSourcePort(Integer.parseInt(array[1]));
			forward.setDestHost(array[2]);
			forward.setDestPort(Integer.parseInt(array[3]));
			local.add(forward);
		}
	}

}
