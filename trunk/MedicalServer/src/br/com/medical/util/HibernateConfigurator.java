package br.com.medical.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.safehibernate.cfg.SafeConfiguration;

public final class HibernateConfigurator {

	private static HibernateConfigurator configurator;
	private SessionFactory sf;

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
		// SessionFactory deve ser criado uma Única vez durante a execução
		// da aplicação
		Configuration cf = new SafeConfiguration(MedicalCertificateProvider.class).configure();
		this.sf = cf.buildSessionFactory();

	}

	public Session getSession() {
		return this.sf.openSession();
	}
}
