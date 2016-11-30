package com.murerz.modopz.core.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class JWTTest {

	@Test
	public void testJWT() {
		KPCrypt kp = KPCrypt.create();

		JWT jwt = new JWT();
		jwt.setUser("test");
		jwt.setService("modopz");
		long iat = System.currentTimeMillis();
		jwt.setIat(iat);
		jwt.exp(3600L);

		String token = jwt.stringify(kp.getPrivateKey());
		assertNotNull(token);

		jwt = JWT.parse(token, kp.getPublicKey());
		assertEquals("test", jwt.getUser());
		assertEquals("modopz", jwt.getService());
		assertEquals(iat, jwt.getIat());
		assertEquals(iat + 3600L, jwt.getExp());
	}

	@Test
	public void testExpired() {
		KPCrypt kp = KPCrypt.create();

		JWT jwt = new JWT();
		jwt.setUser("test");
		jwt.setService("modopz");
		long iat = System.currentTimeMillis() - 3000;
		jwt.setIat(iat);
		jwt.exp(1000L);

		String token = jwt.stringify(kp.getPrivateKey());
		assertNotNull(token);

		assertNull(JWT.parse(token, kp.getPublicKey()));
	}

	@Test
	public void testSign() {
		KPCrypt kp = KPCrypt.create();
		KPCrypt wrong = KPCrypt.create();

		JWT jwt = new JWT();
		jwt.setUser("test");
		jwt.setService("modopz");
		long iat = System.currentTimeMillis();
		jwt.setIat(iat);
		jwt.exp(3600L);

		String token = jwt.stringify(kp.getPrivateKey());
		assertNotNull(token);

		assertNull(JWT.parse(token, wrong.getPublicKey()));
	}

}
