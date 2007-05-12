/**
 * 
 */
package br.com.safeHibernate.certificate.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Date;

import junit.framework.TestCase;
import br.com.safeHibernate.certificate.CertificateFactory;

/**
 * @author jean
 */
public class TestCertificateFactory extends TestCase {

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for
	 * {@link br.com.safeHibernate.certificate.CertificateFactory#generateSelfSignedCertificate(java.util.Date, java.util.Date, java.math.BigInteger, java.security.PublicKey, java.security.PrivateKey, java.lang.String)}.
	 * @throws SignatureException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws IllegalStateException
	 * @throws InvalidKeyException
	 * @throws CertificateEncodingException
	 */
	public void testGenerateSelfSignedCertificate()
			throws CertificateEncodingException,
				InvalidKeyException,
				IllegalStateException,
				NoSuchProviderException,
				NoSuchAlgorithmException,
				SignatureException {
		BigInteger serialNumber = new BigInteger("1111111111111111");
		String dName = "CN=Test Generate Self Signed Certificate";
		X509Certificate certificate = generateTestCertificate(null);
		assertNotNull(certificate);
		assertEquals(certificate.getSerialNumber(), serialNumber);
		assertEquals(dName, certificate.getIssuerDN().toString());
	}

	/**
	 * Test method for
	 * {@link br.com.safeHibernate.certificate.CertificateFactory#exportCertificate(java.security.cert.X509Certificate, java.io.File)}.
	 * @throws SignatureException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws IllegalStateException
	 * @throws InvalidKeyException
	 * @throws CertificateEncodingException
	 * @throws IOException 
	 */
	public void testExportCertificate()
			throws CertificateEncodingException,
				InvalidKeyException,
				IllegalStateException,
				NoSuchProviderException,
				NoSuchAlgorithmException,
				SignatureException, IOException {
		X509Certificate certificate = generateTestCertificate(null);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		CertificateFactory.exportCertificate(certificate, baos);
		assertTrue(baos.toByteArray().length != 0);
	}

	/**
	 * Test method for
	 * {@link br.com.safeHibernate.certificate.CertificateFactory#generateCASignedX509Certificate(java.security.PrivateKey, java.security.cert.X509Certificate, java.util.Date, java.util.Date, java.math.BigInteger, java.security.PublicKey, java.lang.String)}.
	 * @throws Exception
	 */
	public void testGenerateCASignedX509Certificate() throws Exception {
		KeyPair keyPair = CertificateFactory.createKeyPair();
		X509Certificate certificate = generateTestCertificate(keyPair);
		KeyPair otherKey = CertificateFactory.createKeyPair();
		Date startDate = new Date();
		Date expiryDate = new Date();
		BigInteger serialNumber = new BigInteger("222222");
		String principal = "CN=Other subject";
		X509Certificate otherCertificate = CertificateFactory
				.generateCASignedX509Certificate(keyPair.getPrivate(),
						certificate, startDate, expiryDate, serialNumber,
						otherKey.getPublic(), principal);
		assertNotNull(otherCertificate);
		assertEquals(otherCertificate.getIssuerDN(), certificate.getIssuerDN());
		assertEquals(otherCertificate.getSubjectDN().toString(), principal);
	}

	/**
	 * Test method for
	 * {@link br.com.safeHibernate.certificate.CertificateFactory#createKeyPair()}.
	 */
	public void testCreateKeyPair() {
		KeyPair keyPair = CertificateFactory.createKeyPair();
		assertNotNull(keyPair);
		assertNotNull(keyPair.getPublic());
		assertNotNull(keyPair.getPrivate());
	}

	/**
	 * Test method for
	 * {@link br.com.safeHibernate.certificate.CertificateFactory#createKeyPair(java.lang.String, int)}.
	 */
	public void testCreateKeyPairStringInt() {
		try {
			CertificateFactory.createKeyPair("Blablabla", 1024);
			fail("Can't generate certificate for a unknowed algorithm");
		} catch (RuntimeException e) {
			// don't throw
		}
	}

	/**
	 * Test method for
	 * {@link br.com.safeHibernate.certificate.CertificateFactory#saveKeyToCsr(java.security.PublicKey, java.io.File)}.
	 * @throws IOException
	 */
	public void testSaveKeyToCsrFile() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PublicKey key = CertificateFactory.createKeyPair().getPublic();
		CertificateFactory.saveKeyToCsr(key, baos);
		String certificateValue = new String(baos.toByteArray());
		assertTrue(certificateValue.startsWith("-----BEGIN CERTIFICATE-----"));
		assertTrue(certificateValue.endsWith("-----END CERTIFICATE-----\r\n"));
	}

	private X509Certificate generateTestCertificate(KeyPair keyPair)
			throws CertificateEncodingException,
				InvalidKeyException,
				IllegalStateException,
				NoSuchProviderException,
				NoSuchAlgorithmException,
				SignatureException {
		Date startDate = new Date();
		Date expiryDate = new Date();
		BigInteger serialNumber = new BigInteger("1111111111111111");
		String dName = "CN=Test Generate Self Signed Certificate";
		KeyPair key = keyPair != null ? keyPair : CertificateFactory
				.createKeyPair();
		return CertificateFactory.generateSelfSignedCertificate(startDate,
				expiryDate, serialNumber, key.getPublic(), key.getPrivate(),
				dName);
	}
}
