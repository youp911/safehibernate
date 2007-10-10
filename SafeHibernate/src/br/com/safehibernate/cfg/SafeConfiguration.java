package br.com.safehibernate.cfg;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.safehibernate.interceptor.PostLoadInterceptor;
import br.com.safehibernate.provider.ICertificateProvider;
import br.com.safehibernate.provider.SignatureProvider;
import br.com.safehibernate.util.CustomProperties;

public class SafeConfiguration extends Configuration {

	private static final long serialVersionUID = 1L;

	private Class<? extends ICertificateProvider> encryptionProvider;
	private Class<? extends SignatureProvider> signatureProvider;

	public SafeConfiguration(Class<? extends ICertificateProvider> encryptionProvider, Class<? extends SignatureProvider> signatureProvider) {
		setEncryptionProvider(encryptionProvider);
		setSignatureProvider(signatureProvider);
	}

	@Override
	public SessionFactory buildSessionFactory() throws HibernateException {
		this.setProperty(CustomProperties.WRAPPED_DIALECT, "org.hibernate.dialect.MySQLDialect");
		this.setProperty(CustomProperties.ENCRYPTION_PROVIDER, getEncryptionProvider().getClass().getName());
		if (getSignatureProvider() != null) {
			this.setProperty(CustomProperties.SIGNATURE_PROVIDER, getSignatureProvider().getClass().getName());
		}
		this.setListener("post-load", new PostLoadInterceptor());
		SessionFactory sf = super.buildSessionFactory();
		return new SafeSessionFactory(sf);
	}

	public Class<? extends ICertificateProvider> getEncryptionProvider() {
		return this.encryptionProvider;
	}

	public void setEncryptionProvider(Class<? extends ICertificateProvider> provider) {
		if (provider == null) {
			throw new IllegalArgumentException("encryption provider cannot be null");
		}
		this.encryptionProvider = provider;
	}
	
	public Class<? extends SignatureProvider> getSignatureProvider() {
		return this.signatureProvider;
	}
	
	public void setSignatureProvider(Class<? extends SignatureProvider> provider) {
		this.signatureProvider = provider;
	}

}
