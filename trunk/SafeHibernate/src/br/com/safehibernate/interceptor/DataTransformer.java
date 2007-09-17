package br.com.safehibernate.interceptor;

import org.hibernate.cfg.Environment;

import br.com.safehibernate.CustomProperties;
import br.com.safehibernate.provider.ICertificateProvider;

public final class DataTransformer {

	private static ICertificateProvider getCertificateProvider() {
		Environment.getProperties().getProperty(
				CustomProperties.WRAPPED_DIALECT);
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

		return getCertificateProvider().encrypt(data);
	}

	public static byte[] decrypt(Object value) {
		if (value == null) {
			return null;
		}
		assert value instanceof String;
		byte[] data = ((String) value).getBytes();

		return getCertificateProvider().decrypt(data);
	}

}
