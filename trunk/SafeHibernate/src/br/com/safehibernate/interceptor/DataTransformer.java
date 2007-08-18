package br.com.safehibernate.interceptor;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import org.hibernate.cfg.Environment;

import br.com.safehibernate.CustomProperties;
import br.com.safehibernate.ICertificateProvider;

public final class DataTransformer {

	private static ICertificateProvider getCertificateProvider() {
		String className = Environment.getProperties().getProperty(
				CustomProperties.CERTIFICATE_PROVIDER);
		try {
			Class<?> clazz = Class.forName(className);
			ICertificateProvider o = (ICertificateProvider) clazz.newInstance();
			return o;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] encrypt(Object value) {
		if (value == null) {
			return null;
		}
		assert value instanceof String;
		byte[] data = ((String) value).getBytes();

		PublicKey privateKey = getCertificateProvider().getPublicKey();
		String algorithm = getCertificateProvider().getAlgorithm();

		try {
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] descrypt(Object value) {
		if (value == null) {
			return null;
		}
		assert value instanceof String;
		byte[] data = ((String) value).getBytes();

		PrivateKey privateKey = getCertificateProvider().getPrivateKey();
		String algorithm = getCertificateProvider().getAlgorithm();

		try {
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}

}
