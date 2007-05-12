package br.com.safeHibernate.certificate.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for Safe Hibernate");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestCertificateFactory.class);
		//$JUnit-END$
		return suite;
	}

}
