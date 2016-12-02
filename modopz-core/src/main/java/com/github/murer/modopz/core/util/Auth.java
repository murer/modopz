package com.github.murer.modopz.core.util;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class Auth {

	private Map<String, PublicKey> pubs = new HashMap<String, PublicKey>();

	public Auth add(String pub) {
		byte[] bytes = B64.decode(pub);
		return add(bytes);
	}

	private Auth add(byte[] pub) {
		String name = Hash.md5B64(pub);
		KPCrypt kp = KPCrypt.create(null, pub);
		pubs.put(name, kp.getPublicKey());
		return this;
	}

	public Map<String, PublicKey> getPubs() {
		return pubs;
	}

}
