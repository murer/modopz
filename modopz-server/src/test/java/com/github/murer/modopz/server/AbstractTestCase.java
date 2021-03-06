package com.github.murer.modopz.server;

import org.junit.After;
import org.junit.Before;

import com.github.murer.modopz.core.service.HttpProxyService;
import com.github.murer.modopz.core.service.Kernel;
import com.github.murer.modopz.core.service.Service;
import com.github.murer.modopz.core.socket.SocketFowardModuleImpl;
import com.github.murer.modopz.core.util.KPCrypt;
import com.github.murer.modopz.core.util.Util;
import com.github.murer.modopz.server.MOServer;

public class AbstractTestCase {

	protected MOServer server;
	protected int port;
	protected Service service;
	protected Kernel local;

	@Before
	public void setUp() {
		server = new MOServer();
		server.boot();
		port = server.bind("127.0.0.1", 0);

		KPCrypt kp = KPCrypt.create(
				"MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAsXrlZ9TspWLhEsuZbsm8EcKoMmhLZQVR4lqDqrSo9ASJPdkr_ctN3Cvz4rFvnftCfijM4WRJdKeySKrBp7190wIDAQABAkAxpm3S9FAXnGfWuDp-MdV5KnmfUGn3ItvbdPLsqImzabIqMZu8fEB99LCot83Pxk7WSZKO4xwjyJvv1Hvjw4BxAiEA2WKvBwdRG1BG2MG8aywESlGifybL_LUeEYqw1toF1U8CIQDRAZD_bbj9bYXI-ltGouPNsJUJcVlW9OwSZVUZsDU2PQIgV79j0zx62sGet2QMgF42JSGqrBSnBoy9ZGtNUoyTCjUCIB4prbVPLm1UiwQwLVAKXfnnS_rq4svL2O3mtdtZNLS5AiEAr1qLlgnus_6o1DIZh_XqbWLJdGdtOEpMQ8OiyJjYtBI",
				null);
		HttpProxyService service = HttpProxyService.create("http://localhost:" + port + "/s/modopz");
		this.service = service;
		service.setKey(kp.getPrivateKey()).setUser("test").setService("modopz");

		local = new Kernel();
		local.load(SocketFowardModuleImpl.create().setService(service));
		local.start();
	}

	@After
	public void tearDown() {
		Util.close(local);
		Util.close(server);
	}
}
