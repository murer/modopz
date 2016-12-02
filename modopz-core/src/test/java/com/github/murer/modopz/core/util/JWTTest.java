package com.github.murer.modopz.core.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.github.murer.modopz.core.util.JWT;
import com.github.murer.modopz.core.util.KPCrypt;
import com.github.murer.modopz.core.util.JWT.Header;
import com.github.murer.modopz.core.util.JWT.Payload;

public class JWTTest {

	@Test
	public void testJWT() {
		KPCrypt kp = KPCrypt.create();

		JWT jwt = new JWT();
		Header header = new Header();
		Payload payload = new Payload();
		payload.setUser("test");
		payload.setService("modopz");
		long iat = System.currentTimeMillis() / 1000;
		payload.exp(iat, 3600);
		jwt.formatHeader(header).formatPayload(payload).sign(kp.getPrivateKey());

		String token = jwt.formatToken();
		assertNotNull(token);

		jwt = JWT.parse(token);
		payload = jwt.parsePayload();
		assertEquals("test", payload.getUser());
		assertEquals("modopz", payload.getService());
		assertEquals(iat, payload.getIat());
		assertEquals(iat + 3600L, payload.getExp());
	}

	@Test
	public void testExpired() {
		KPCrypt kp = KPCrypt.create();

		JWT jwt = new JWT();
		Header header = new Header();
		Payload payload = new Payload();
		payload.setUser("test");
		payload.setService("modopz");
		long iat = (System.currentTimeMillis() / 1000) - 10;
		payload.exp(iat, 5);
		jwt.formatHeader(header).formatPayload(payload).sign(kp.getPrivateKey());

		String token = jwt.formatToken();
		assertNotNull(token);

		assertFalse(JWT.parse(token).verify(kp.getPublicKey()));
	}

	@Test
	public void testSign() {
		KPCrypt kp = KPCrypt.create();
		KPCrypt wrong = KPCrypt.create();

		JWT jwt = new JWT();
		Header header = new Header();
		Payload payload = new Payload();
		payload.setUser("test");
		payload.setService("modopz");
		long iat = System.currentTimeMillis() / 1000;
		payload.exp(iat, 3600);
		jwt.formatHeader(header).formatPayload(payload).sign(kp.getPrivateKey());

		String token = jwt.formatToken();
		assertNotNull(token);

		assertFalse(JWT.parse(token).verify(wrong.getPublicKey()));
	}

}
