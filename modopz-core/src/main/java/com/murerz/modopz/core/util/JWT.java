package com.murerz.modopz.core.util;

import java.security.PrivateKey;
import java.security.PublicKey;

import com.google.gson.JsonObject;
import com.murerz.modopz.core.json.JSON;

public class JWT {

	private String pub;

	private String service;

	private long iat;

	private long exp;

	public String getPub() {
		return pub;
	}

	public JWT setPub(String pub) {
		this.pub = pub;
		return this;
	}

	public String getService() {
		return service;
	}

	public JWT setService(String service) {
		this.service = service;
		return this;
	}

	public long getIat() {
		return iat;
	}

	public JWT setIat(long iat) {
		this.iat = iat;
		return this;
	}

	public long getExp() {
		return exp;
	}

	public JWT setExp(long exp) {
		this.exp = exp;
		return this;
	}

	public void exp(long time) {
		exp = iat + time;
	}

	public String stringify(PrivateKey key) {
		String payload = encodePayload();
		KPCrypt crypt = KPCrypt.create(key, null);
		String sign = crypt.signB64(payload);
		return Util.format("%s.%s", payload, sign);
	}

	private String encodePayload() {
		String header = B64.encode(Util.toBytes("{\"alg\":\"RS256\",\"typ\":\"JWT\"}", "UTF-8"));
		String payload = JSON.stringify(this);
		payload = B64.encode(Util.toBytes(payload, "UTF-8"));
		return Util.format("%s.%s", header, payload);
	}

	public static JWT parse(String token, PublicKey key) {
		String[] array = token.split("\\.");
		if (array.length != 3) {
			throw new RuntimeException("wrong: " + token);
		}
		String encHeader = Util.toString(B64.decode(array[0]), "UTF-8");
		String encPayload = Util.toString(B64.decode(array[1]), "UTF-8");
		byte[] sign = B64.decode(array[2]);

		checkHeader(encHeader);
		if (!checkSign(array[0], array[1], sign, key)) {
			return null;
		}
		JWT jwt = JSON.parse(encPayload, JWT.class);
		long time = jwt.getExp() - System.currentTimeMillis();
		System.out.println(time);
		if (time < 0) {
			return null;
		}
		String pub = Hash.md5B64(key.getEncoded());
		if (!pub.equals(jwt.getPub())) {
			return null;
		}

		return jwt;
	}

	private static boolean checkSign(String header, String payload, byte[] sign, PublicKey key) {
		String tk = Util.format("%s.%s", header, payload);
		KPCrypt kp = KPCrypt.create(null, key);
		return kp.verify(tk, sign);
	}

	private static void checkHeader(String header) {
		JsonObject obj = JSON.parse(header, JsonObject.class);
		if (!"RS256".equals(obj.get("alg").getAsString())) {
			throw new RuntimeException("wrong: " + header);
		}
		if (!"JWT".equals(obj.get("typ").getAsString())) {
			throw new RuntimeException("wrong: " + header);
		}
	}

	@Override
	public String toString() {
		return "[JWT user=" + pub + ", service=" + service + ", iat=" + iat + ", exp=" + exp + "]";
	}

	public void pub(PublicKey key) {
		byte[] encoded = key.getEncoded();
		String user = Hash.md5B64(encoded);
		this.pub = user;
	}

}
