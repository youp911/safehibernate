package br.com.medical.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.safehibernate.CustomProperties;
import br.com.safehibernate.interceptor.Interceptor;
import br.com.safehibernate.interceptor.PostLoadEventListener;

public final class HibernateConfigurator {

	private static HibernateConfigurator configurator;
	private SessionFactory sf;
	private static final Interceptor INTERCEPTOR = new br.com.safehibernate.interceptor.Interceptor();

	private HibernateConfigurator() {
		configure();
	}

	public static HibernateConfigurator getInstance() {
		if (configurator == null) {
			configurator = new HibernateConfigurator();
		}
		return configurator;
	}

	private void configure() {
		// SessionFactory deve ser criado uma única vez durante a execução
		// da aplicação
		Configuration cf = new org.hibernate.cfg.Configuration().configure();
		// .configure("hibernate.cfg.xml");
		cf.setProperty(CustomProperties.WRAPPED_DIALECT,
				"org.hibernate.dialect.MySQLDialect");
		cf.setProperty(CustomProperties.CERTIFICATE_PROVIDER, 
				CertificateProvider.class.getName());
		
		cf.setListener("post-load", new PostLoadEventListener());

		this.sf = cf.buildSessionFactory();

	}

	public Session getSession() {
		Session session = this.sf.openSession(INTERCEPTOR);
		return session;
	}
}
