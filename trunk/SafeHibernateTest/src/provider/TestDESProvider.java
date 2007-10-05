package provider;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.SecretKey;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import br.com.safeHibernate.certificate.CertificateFactory;
import br.com.safehibernate.provider.DESProvider;

public class TestDESProvider {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	@Test public void testEncrypt() {
		InnerDES inner = new InnerDES();

		String value = "Testando um texto secreto";

		byte[] data = inner.encrypt(value.getBytes());

		assertEquals(value, new String(inner.decrypt(data)));

	}

	public static class InnerDES extends DESProvider {

		private SecretKey key;

		InnerDES() {
			try {
				this.key = CertificateFactory.createSecreteKey("DES", 128);
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public SecretKey getSecreteKey() {
			return this.key;
		}

	}

}
