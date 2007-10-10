package br.com.safehibernate.interceptor;

import org.hibernate.cfg.Environment;

import br.com.safehibernate.provider.ICertificateProvider;
import br.com.safehibernate.provider.SignatureProvider;
import br.com.safehibernate.util.CustomProperties;

public class HibernateCertificateLookup implements ICertificateLookup {
	
	private static ICertificateProvider encryptionInstance;
	private static SignatureProvider signatureInstance;
	
	@Override
	public ICertificateProvider getEncryptionProvider() {
		String className = Environment.getProperties().getProperty(CustomProperties.ENCRYPTION_PROVIDER);
		if (encryptionInstance == null) {
			try {
				Class<?> clazz = Class.forName(className);
				encryptionInstance = (ICertificateProvider) clazz.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return encryptionInstance;
	}

	@Override
	public SignatureProvider getSignatureProvider() {
		String className = Environment.getProperties().getProperty(CustomProperties.SIGNATURE_PROVIDER);
		if (signatureInstance == null) {
			try {
				Class<?> clazz = Class.forName(className);
				signatureInstance = (SignatureProvider) clazz.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return signatureInstance;
	}
	
}
