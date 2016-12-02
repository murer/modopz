package com.github.murer.modopz.core.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Set;

public class KPCrypt {

	private static final String ALG_SIGNATURE = "SHA256withRSA";

	private static final String STRING_ENCODE = "UTF-8";

	private static final String ALG_KEY = "RSA";

	private PrivateKey privateKey;

	private PublicKey publicKey;

	public static KPCrypt create() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALG_KEY);
			keyGen.initialize(512);
			KeyPair keypair = keyGen.genKeyPair();
			KPCrypt ret = new KPCrypt();
			ret.privateKey = keypair.getPrivate();
			ret.publicKey = keypair.getPublic();
			return ret;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public String signB64(String data) {
		byte[] sign = sign(data);
		return B64.encode(sign);
	}

	public byte[] sign(String data) {
		try {
			return sign(data.getBytes(STRING_ENCODE));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private byte[] sign(byte[] bytes) {
		try {
			Signature sig = getSignature();
			sig.initSign(privateKey);
			sig.update(bytes, 0, bytes.length);
			return sig.sign();
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (SignatureException e) {
			throw new RuntimeException(e);
		}
	}

	private Signature getSignature() throws NoSuchAlgorithmException {
		return Signature.getInstance(ALG_SIGNATURE);
	}

	public boolean verify(String text, byte[] sign) {
		try {
			return verify(text.getBytes(STRING_ENCODE), sign);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean verify(byte[] data, byte[] sign) {
		try {
			Signature sig = Signature.getInstance(ALG_SIGNATURE);
			sig.initVerify(publicKey);
			sig.update(data, 0, data.length);
			return sig.verify(sign);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (SignatureException e) {
			throw new RuntimeException(e);
		}
	}

	public static KPCrypt create(PrivateKey privateKey, PublicKey publicKey) {
		KPCrypt ret = new KPCrypt();
		ret.privateKey = privateKey;
		ret.publicKey = publicKey;
		return ret;
	}

	public static KPCrypt create(byte[] privateKey, byte[] publicKey) {
		try {
			PrivateKey privateKeyObj = null;
			PublicKey publicKeyObj = null;
			KeyFactory factory = KeyFactory.getInstance(ALG_KEY);
			if (privateKey != null) {
				EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKey);
				privateKeyObj = factory.generatePrivate(privateKeySpec);
			}
			if (publicKey != null) {
				EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKey);
				publicKeyObj = factory.generatePublic(publicKeySpec);
			}
			return create(privateKeyObj, publicKeyObj);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	public String getPrivateKeyB64() {
		return B64.encode(getPrivateKey().getEncoded());
	}

	public String getPublicKeyB64() {
		return B64.encode(getPublicKey().getEncoded());
	}

	public static KPCrypt create(String privateKey, String publicKey) {
		byte[] privateKeyData = null;
		byte[] publicKeyData = null;
		if (privateKey != null) {
			privateKeyData = B64.decode(privateKey);
		}
		if (publicKey != null) {
			publicKeyData = B64.decode(publicKey);
		}
		return create(privateKeyData, publicKeyData);
	}

	public static void main(String[] args) {
		Provider[] providers = Security.getProviders();
		for (Provider provider : providers) {
			System.out.println("Provider: " + provider);
			Set<Service> services = provider.getServices();
			for (Service service : services) {
				String alg = service.getAlgorithm();
				System.out.println("alg: " + alg);
			}
		}
	}

}