package com.murerz.modopz.core.util;

import java.security.PrivateKey;
import java.security.PublicKey;

import com.murerz.modopz.core.json.JSON;

public class JWT {

	public static class Header {
		private String alg = "RS256";
		private String typ = "JWT";

		@Override
		public String toString() {
			return "[Header alg=" + alg + ", typ=" + typ + "]";
		}

	}

	public static class Payload {
		private String user;
		private String service;
		private long iat;
		private long exp;

		public String getUser() {
			return user;
		}

		public Payload setUser(String user) {
			this.user = user;
			return this;
		}

		public String getService() {
			return service;
		}

		public Payload setService(String service) {
			this.service = service;
			return this;
		}

		public long getIat() {
			return iat;
		}

		public Payload setIat(long iat) {
			this.iat = iat;
			return this;
		}

		public long getExp() {
			return exp;
		}

		public Payload setExp(long exp) {
			this.exp = exp;
			return this;
		}

		public void exp(long iat, long time) {
			this.iat = iat;
			this.exp = iat + time;
		}

		@Override
		public String toString() {
			return "Payload user=" + user + ", service=" + service + ", iat=" + iat + ", exp=" + exp + "]";
		}

	}

	private String header;
	private String payload;
	private byte[] sign;

	public Payload parsePayload() {
		String json = Util.toString(B64.decode(payload), "UTF-8");
		return JSON.parse(json, Payload.class);
	}

	public Header parseHeader() {
		String json = Util.toString(B64.decode(header), "UTF-8");
		return JSON.parse(json, Header.class);
	}

	public static void main(String[] args) {
		JWT jwt = new JWT();
		Header header = new Header();
		Payload payload = new Payload();
		payload.setUser("test").setService("modopz").exp(System.currentTimeMillis() / 1000, 3600L);

		jwt.formatHeader(header);
		jwt.formatPayload(payload);

		KPCrypt kp = KPCrypt.create();
		jwt.sign(kp.getPrivateKey());

		String token = jwt.formatToken();
		System.out.println("Token: " + token.length() + " " + token);

		jwt = JWT.parse(token);
		header = jwt.parseHeader();
		payload = jwt.parsePayload();

		System.out.println("header: " + header);
		System.out.println("payload: " + payload);

		System.out.println(jwt.verify(kp.getPublicKey()));
	}

	public boolean verify(PublicKey key) {
		if (!verifyExp()) {
			return false;
		}
		return verifySign(key);
	}

	private boolean verifySign(PublicKey key) {
		String tk = Util.format("%s.%s", header, payload);
		KPCrypt kp = KPCrypt.create(null, key);
		return kp.verify(tk, sign);
	}

	private boolean verifyExp() {
		long now = System.currentTimeMillis();
		long exp = parsePayload().getExp() * 1000;
		return exp >= now;
	}

	public static JWT parse(String token) {
		String[] array = token.split("\\.");
		JWT ret = new JWT();
		ret.header = array[0];
		ret.payload = array[1];
		ret.sign = B64.decode(array[2]);
		return ret;
	}

	public String formatToken() {
		return Util.format("%s.%s.%s", header, payload, B64.encode(sign));
	}

	public JWT sign(PrivateKey key) {
		String tk = Util.format("%s.%s", header, payload);
		KPCrypt kp = KPCrypt.create(key, null);
		this.sign = kp.sign(tk);
		return this;
	}

	public JWT formatPayload(Payload payload) {
		String json = JSON.stringify(payload);
		this.payload = B64.encode(Util.toBytes(json, "UTF-8"));
		return this;
	}

	public JWT formatHeader(Header header) {
		String json = JSON.stringify(header);
		this.header = B64.encode(Util.toBytes(json, "UTF-8"));
		return this;
	}

}
