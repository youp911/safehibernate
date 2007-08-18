package br.com.safehibernate.util;

import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.Date;

import br.com.safeHibernate.certificate.CertificateFactory;
import br.com.safehibernate.user.SafeUser;

/**
 * This class provides de certificate management, encryption and decryption of
 * data
 * @author jean
 */
public class CertificateManager {

	private static CertificateManager instance = null;

	private static X509Certificate cARoot;
	private static KeyPair rootPair;

	private static final String PRINCIPAL = "CN=Safe Hibernate Certification Autority";

	public static CertificateManager getInstance() {
		if (instance == null) {
			instance = new CertificateManager();
		}
		return instance;
	}

	private static KeyPair getRootPair() {
		if (rootPair == null) {
			rootPair = CertificateFactory.createKeyPair();
		}
		return rootPair;
	}

	private static X509Certificate getCARoot() {
		if (cARoot == null) {
			Date startDate = new Date();
			Date expiryDate = new Date();

			try {
				cARoot = CertificateFactory.generateSelfSignedCertificate(
						startDate, //
						expiryDate, //
						getRootPair().getPublic(), // 
						getRootPair().getPrivate(), //
						PRINCIPAL);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return cARoot;
	}

	private SafeUser createUser(String name, KeyPair pair) {

		SafeUser user = new SafeUser(name, pair);
		Date startDate = new Date();
		Date expiryDate = new Date();

		/*String principal = "CN=" + name;

		try {
			CertificateFactory.generateCASignedX509Certificate(rootPair
					.getPrivate(), //
					getCARoot(), // 
					startDate, // 
					expiryDate, // 
					pair.getPublic(), principal);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}*/
		return user;
	}

}
