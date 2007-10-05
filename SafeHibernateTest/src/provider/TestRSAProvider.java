package provider;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import br.com.safeHibernate.certificate.CertificateFactory;
import br.com.safehibernate.provider.RSAProvider;
import static org.junit.Assert.*;

public class TestRSAProvider {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	@Test public void testEncrypt() {
		InnerRSA innerRSA = new InnerRSA();
		
		String value = "Testando!!!123 abc adasd asd asd asd asd  dd asdas  s";
		
		byte[] data =  innerRSA.encrypt(value.getBytes());
		
		String aa = new String(data);
		
		data = aa.getBytes();
		
		assertEquals(value, new String(innerRSA.decrypt(data)));
		
	}
	
	public static class InnerRSA extends RSAProvider {

		private KeyPair keyPair;

		InnerRSA() {
			this.keyPair = CertificateFactory.createKeyPair();
		}
		
		@Override
		public PrivateKey getPrivateKey() {
			return this.keyPair.getPrivate();
		}

		@Override
		public PublicKey getPublicKey() {
			return this.keyPair.getPublic();
		}
		
	}
	
}
