package br.com.safehibernate.cfg;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.safehibernate.interceptor.PostLoadInterceptor;
import br.com.safehibernate.provider.ICertificateProvider;
import br.com.safehibernate.util.CustomProperties;

public class SafeConfiguration extends Configuration {

	private static final long serialVersionUID = 1L;

	private Class<? extends ICertificateProvider> provider;

	public SafeConfiguration(Class<? extends ICertificateProvider> provider) {
		setProvider(provider);
	}

	@Override
	public SessionFactory buildSessionFactory() throws HibernateException {
		this.setProperty(CustomProperties.WRAPPED_DIALECT, "org.hibernate.dialect.MySQLDialect");
		this.setProperty(CustomProperties.CERTIFICATE_PROVIDER, getProvider().getClass().getName());
		this.setListener("post-load", new PostLoadInterceptor());
		SessionFactory sf = super.buildSessionFactory();
		return new SafeSessionFactory(sf);
	}

	public Class<? extends ICertificateProvider> getProvider() {
		return this.provider;
	}

	public void setProvider(Class<? extends ICertificateProvider> provider) {
		if (provider == null) {
			throw new IllegalArgumentException("provider cannot be null");
		}
		this.provider = provider;
	}

}
