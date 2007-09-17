package br.com.safeHibernate.certificate;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Random;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V1CertificateGenerator;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;

import sun.misc.BASE64Encoder;

/**
 * Certificate Generator
 * @author jean
 */
public class CertificateFactory {

	private static final int RSA_KEY_SIZE = 512;
	private static final String DEFAULT_KEY_ALGORITHM = "RSA";
	private static final String DEFAULT_SIGNATURE_ALGORITHM = "MD5withRSA";
	private static final String SECURITY_PROVIDER = "BC";

	static {
		// adds the Bouncy castle provider to java security
		Security.addProvider(new BouncyCastleProvider());
	}

	/**
	 * @param startDate time from which certificate is valid
	 * @param expiryDate time after which certificate is not valid
	 * @return
	 * @throws SignatureException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws IllegalStateException
	 * @throws InvalidKeyException
	 * @throws CertificateEncodingException
	 */
	public static X509Certificate generateSelfSignedCertificate(Date startDate,
		Date expiryDate,
		PublicKey publicKey,
		PrivateKey privateKey,
		String dName)
			throws CertificateEncodingException,
				InvalidKeyException,
				IllegalStateException,
				NoSuchProviderException,
				NoSuchAlgorithmException,
				SignatureException {

		X509V1CertificateGenerator certGen = new X509V1CertificateGenerator();
		X500Principal dnName = new X500Principal(dName);

		// Generates a random serial number
		Random random = new Random(System.currentTimeMillis());
		BigInteger serialNumber = BigInteger.probablePrime(20, random);

		certGen.setSerialNumber(serialNumber);
		certGen.setIssuerDN(dnName);
		certGen.setNotBefore(startDate);
		certGen.setNotAfter(expiryDate);
		// note: same as issuer
		certGen.setSubjectDN(dnName);
		certGen.setPublicKey(publicKey);
		certGen.setSignatureAlgorithm(DEFAULT_SIGNATURE_ALGORITHM);

		return certGen.generate(privateKey, SECURITY_PROVIDER);
	}

	/**
	 * @param certificate
	 * @param out
	 * @throws IOException
	 * @throws CertificateEncodingException
	 * @throws java.security.cert.CertificateEncodingException
	 */
	public static void exportCertificate(X509Certificate certificate,
		OutputStream out)
			throws IOException,
				CertificateEncodingException,
				java.security.cert.CertificateEncodingException {
		out.write(certificate.getEncoded());
		out.flush();
		out.close();
	}

	public static X509Certificate generateCASignedX509Certificate(PrivateKey caPrivateKey,
		X509Certificate caCert,
		Date startDate,
		Date expiryDate,
		PublicKey publicKey,
		String principal) throws Exception {

		X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
		X500Principal subjectName = new X500Principal(principal);

		Random random = new Random(System.currentTimeMillis());
		BigInteger serialNumber = BigInteger.probablePrime(20, random);

		certGen.setSerialNumber(serialNumber);
		certGen.setIssuerDN(caCert.getSubjectX500Principal());
		certGen.setNotBefore(startDate);
		certGen.setNotAfter(expiryDate);
		certGen.setSubjectDN(subjectName);
		certGen.setPublicKey(publicKey);
		certGen.setSignatureAlgorithm(DEFAULT_SIGNATURE_ALGORITHM);

		certGen.addExtension(X509Extensions.AuthorityKeyIdentifier, false,
				new AuthorityKeyIdentifierStructure(caCert));
		certGen.addExtension(X509Extensions.SubjectKeyIdentifier, false,
				new SubjectKeyIdentifierStructure(publicKey));

		// note: private key of CA
		return certGen.generate(caPrivateKey, SECURITY_PROVIDER);

	}

	/**
	 * creates a random RSA key pair
	 * @see CertificateFactory#createKeyPair(String, int)
	 * @return RSA key pair
	 */
	public static KeyPair createKeyPair() {
		return createKeyPair(DEFAULT_KEY_ALGORITHM, RSA_KEY_SIZE);
	}

	/**
	 * creates a random generated key pair defined by algorithm and keysize
	 * @param algorithm
	 * @param keysize
	 * @return random generated key pair
	 */
	public static KeyPair createKeyPair(String algorithm, int keysize) {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
			SecureRandom secRan = new SecureRandom();
			keyGen.initialize(keysize, secRan);
			return keyGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static final void saveKeyToCsr(PublicKey publicKey, OutputStream out)
			throws IOException {
		BASE64Encoder myB64 = new BASE64Encoder();
		String b64 = myB64.encode(publicKey.getEncoded());
		out.write("-----BEGIN CERTIFICATE-----\r\n".getBytes());
		out.write(b64.getBytes());
		out.write("-----END CERTIFICATE-----\r\n".getBytes());
		out.close();

	}

	public static void main(String[] args) throws Exception {

		KeyPair pair = createKeyPair();
		/*
		 * Date date = new Date(); Date other = new
		 * SimpleDateFormat("dd/MM/yyyy").parse("31/12/2007"); X509Certificate
		 * generateSelfSignedCertificate = generateSelfSignedCertificate(date,
		 * other, pair.getPublic(), pair.getPrivate(), "CN=Root");
		 */

		BASE64Encoder b64 = new BASE64Encoder();
		String privateEncoded = b64.encode(pair.getPrivate().getEncoded());
		String publicEncoded = b64.encode(pair.getPublic().getEncoded());

		System.out.println("private: " + privateEncoded);
		System.out.println("public: " + publicEncoded);

	}
}
