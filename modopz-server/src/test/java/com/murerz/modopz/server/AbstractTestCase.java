package com.murerz.modopz.server;

import org.junit.After;
import org.junit.Before;

import com.murerz.modopz.core.service.HttpProxyService;
import com.murerz.modopz.core.service.Kernel;
import com.murerz.modopz.core.service.Service;
import com.murerz.modopz.core.socket.SocketFowardModuleImpl;
import com.murerz.modopz.core.util.Hash;
import com.murerz.modopz.core.util.KPCrypt;
import com.murerz.modopz.core.util.Util;

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
				"MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALF65WfU7KVi4RLLmW7JvBHCqDJoS2UFUeJag6q0qPQEiT3ZK_3LTdwr8-Kxb537Qn4ozOFkSXSnskiqwae9fdMCAwEAAQ");
		String pub = Hash.md5B64(kp.getPublicKey().getEncoded());

		service = HttpProxyService.create("http://localhost:" + port + "/s/modopz", pub, kp.getPrivateKeyB64());

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
