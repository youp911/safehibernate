package provider;

import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.SecretKey;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import br.com.safeHibernate.certificate.CertificateFactory;
import br.com.safehibernate.provider.AESProvider;
import static org.junit.Assert.*;

public class TestAESProvider {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	@Test
	public void testEncrypt() {
		InnerAES inner = new InnerAES();

		String value = "Testando um texto secreto";

		byte[] data = inner.encrypt(value.getBytes());

		String testValue = new String(data);

		data = testValue.getBytes();
		
		assertEquals(value, new String(inner.decrypt(data)));

	}

	public static class InnerAES extends AESProvider {

		private SecretKey key;

		InnerAES() {
			try {
				this.key = CertificateFactory.createSecretKey("AES", 128);
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public SecretKey getSecretKey() {
			return this.key;
		}

	}

}
