package provider;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import javax.crypto.SecretKey;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import br.com.safeHibernate.certificate.CertificateFactory;
import br.com.safehibernate.provider.ICertificateProvider;
import br.com.safehibernate.provider.RSAProvider;
import br.com.safehibernate.provider.SignatureProvider;
import static org.junit.Assert.*;

public class TestSignatureProvider {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	@Test public void testSignRawData() throws NoSuchAlgorithmException, NoSuchProviderException {
		Inner inner = new Inner();

		String value = "Testando um texto secreto";

		byte[] data = inner.encrypt(value.getBytes());

		String aa = new String(data);

		data = aa.getBytes();
		assertEquals(value, new String(inner.decrypt(data)));
	}
	
	@Test public void testSignAndEcrypt() throws NoSuchAlgorithmException, NoSuchProviderException {
		final Inner desInner = new Inner();
		desInner.setInner(new RSAProvider() {

			@Override
			public PrivateKey getPrivateKey() {
				return desInner.getPrivateKey();
			}

			@Override
			public PublicKey getPublicKey() {
				return desInner.getPublicKey();
			}
			
		});
		
		String value = "Testando um texto secreto";
		
		byte[] desArray = desInner.encrypt(value.getBytes());
		
		assertEquals(value, new String(desInner.decrypt(desArray)));
	}

	public static class Inner extends SignatureProvider {

		private SecretKey key;
		private KeyPair keyPair;

		Inner() throws NoSuchAlgorithmException, NoSuchProviderException {
			super("SHA1withRSA", "BC", 90);
			try {
				this.key = CertificateFactory.createSecreteKey("DES", 128);
				this.keyPair = CertificateFactory.createKeyPair("RSA", 512);
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public SecretKey getSecreteKey() {
			return this.key;
		}

		@Override
		public PrivateKey getPrivateKey() {
			return this.keyPair.getPrivate();
		}

		@Override
		public PublicKey getPublicKey() {
			return this.keyPair.getPublic();
		}

		@Override
		protected void setInner(ICertificateProvider inner) {
			super.setInner(inner);
		}
	}

}
