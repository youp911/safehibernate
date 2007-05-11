package br.com.safeHibernate.certificate;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
		//adds the Bouncy castle provider to java security
		Security.addProvider(new BouncyCastleProvider());
	}

	/**
	 * @param startDate time from which certificate is valid
	 * @param expiryDate time after which certificate is not valid
	 * @throws SignatureException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws IllegalStateException
	 * @throws InvalidKeyException
	 * @throws CertificateEncodingException
	 */
	public static void generateSelfSignedCertificate(Date startDate,
		Date expiryDate,
		BigInteger serialNumber,
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

		certGen.setSerialNumber(serialNumber);
		certGen.setIssuerDN(dnName);
		certGen.setNotBefore(startDate);
		certGen.setNotAfter(expiryDate);
		// note: same as issuer
		certGen.setSubjectDN(dnName);
		certGen.setPublicKey(publicKey);
		certGen.setSignatureAlgorithm(DEFAULT_SIGNATURE_ALGORITHM);

		X509Certificate certJean = certGen.generate(privateKey,
				SECURITY_PROVIDER);
		System.out.println(certJean);
	}

	/**
	 * @param certificate
	 * @param file
	 * @throws IOException
	 * @throws CertificateEncodingException
	 * @throws java.security.cert.CertificateEncodingException
	 */
	public static void exportCertificate(X509Certificate certificate, File file)
			throws IOException,
				CertificateEncodingException,
				java.security.cert.CertificateEncodingException {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(certificate.getEncoded());
			bos.flush();
		} finally {
			if (fos != null) fos.close();
			if (bos != null) bos.close();
		}
	}

	public static X509Certificate generateCASignedX509Certificate(PrivateKey caPrivateKey,
		X509Certificate caCert,
		Date startDate,
		Date expiryDate,
		BigInteger serialNumber,
		PublicKey publicKey,
		String principal) throws Exception {

		X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
		X500Principal subjectName = new X500Principal(principal);

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

	/**
	 * Saves public key in csr file
	 * @param publicKey public key to save
	 * @param file destination file
	 * @throws IOException any exception during the save process
	 */
	public static final void saveKeyToCsrFile(PublicKey publicKey, File file)
			throws IOException {
		BASE64Encoder myB64 = new BASE64Encoder();
		String b64 = myB64.encode(publicKey.getEncoded());

		FileOutputStream fos = new FileOutputStream(file);
		fos.write("-----BEGIN CERTIFICATE-----\r\n".getBytes());
		fos.write(b64.getBytes());
		fos.write("-----END CERTIFICATE-----\r\n".getBytes());
		fos.close();
	}
}
