package br.com.safeHibernate.certificate;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
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

public class CertificateFactory {

	public static void main(String[] args) throws Exception {

		Security.addProvider(new BouncyCastleProvider());

		Date startDate = new Date(); // time from which certificate is valid
		Date expiryDate = new Date(); // time after which certificate is not
		// valid
		BigInteger serialNumber = new BigInteger("1111"); // serial number for
		// certificate
		KeyPair keyPair = createKeyPair(); // EC public/private key pair

		X509V1CertificateGenerator certGen = new X509V1CertificateGenerator();
		X500Principal dnName = new X500Principal("CN=Jean Carlos Pereira");

		certGen.setSerialNumber(serialNumber);
		certGen.setIssuerDN(dnName);
		certGen.setNotBefore(startDate);
		certGen.setNotAfter(expiryDate);
		certGen.setSubjectDN(dnName); // note: same as
		// issuer
		certGen.setPublicKey(keyPair.getPublic());
		certGen.setSignatureAlgorithm("MD5withRSA");

		X509Certificate certJean = certGen.generate(keyPair.getPrivate(), "BC");
		System.out.println(certJean);

		System.out.println("\n\n\n\n\n");

		PrivateKey privateKey = keyPair.getPrivate();

		X509Certificate certDerlei = generateSignedCertificate(privateKey,
				certJean);
		System.out.println(certDerlei);

		exportCertificate(certJean, new File("/tmp/jean.crt"));
		exportCertificate(certDerlei, new File("/tmp/derlei.crt"));

	}

	private static void exportCertificate(X509Certificate certificate, File file)
			throws IOException, CertificateEncodingException,
			java.security.cert.CertificateEncodingException {
		FileOutputStream fos = new FileOutputStream(file);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		try {
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(certificate.getEncoded());
			bos.flush();
		} finally {
			if (fos != null)
				fos.close();
			if (bos != null)
				bos.close();
		}
	}

	private static X509Certificate generateSignedCertificate(
			PrivateKey privateKey, X509Certificate caCert) throws Exception {
		Date startDate = new Date();
		Date expiryDate = new Date();
		BigInteger serialNumber = new BigInteger("2222");
		KeyPair keyPair = createKeyPair();

		X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
		X500Principal subjectName = new X500Principal(
				"CN=Derlei Alvaro Mathias");

		certGen.setSerialNumber(serialNumber);
		certGen.setIssuerDN(caCert.getSubjectX500Principal());
		certGen.setNotBefore(startDate);
		certGen.setNotAfter(expiryDate);
		certGen.setSubjectDN(subjectName);
		certGen.setPublicKey(keyPair.getPublic());
		certGen.setSignatureAlgorithm("MD5withRSA");

		certGen.addExtension(X509Extensions.AuthorityKeyIdentifier, false,
				new AuthorityKeyIdentifierStructure(caCert));
		certGen.addExtension(X509Extensions.SubjectKeyIdentifier, false,
				new SubjectKeyIdentifierStructure(keyPair.getPublic()));

		return certGen.generate(privateKey, "BC"); // note: private key of CA
	}

	private static KeyPair createKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom secRan = new SecureRandom();
			keyGen.initialize(512, secRan);
			return keyGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private static final void extractCsrKey(PublicKey publicKey, File file)
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
