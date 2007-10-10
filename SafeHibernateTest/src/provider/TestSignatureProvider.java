package provider;

import static org.junit.Assert.assertEquals;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import br.com.safeHibernate.certificate.CertificateFactory;
import br.com.safehibernate.provider.AESProvider;
import br.com.safehibernate.provider.DESProvider;
import br.com.safehibernate.provider.ICertificateProvider;
import br.com.safehibernate.provider.SignatureProvider;

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
		final Inner inner = new Inner();
		inner.setInner(new AESProvider() {

			@Override
			public SecretKey getSecretKey() {
				return inner.key;
			}
			
		});
		
		String value = "Testando um texto secreto";
		
		byte[] desArray = inner.encrypt(value.getBytes());
		
		assertEquals(value, new String(inner.decrypt(desArray)));
	}
	
	@Test public void testSighAndEncryptWithPBEAndDES() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		final Inner inner = new Inner();
		//String algorithm = "PBEWithMD5And128BitAES-CBC-OpenSSL";
		String algorithm = "PBEWithMD5AndDES";
		SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm, "BC");  
        KeySpec ks = new PBEKeySpec ("senha".toCharArray(), new byte[]{1,2,3,4,5,6,7,8,9,10}, 1000);   
        inner.key = skf.generateSecret(ks);   
		
		inner.setInner(new DESProvider() {
			@Override
			public SecretKey getSecretKey() {
				return inner.key;
			}
		});
		String value = "Testando um texto secreto";
		
		byte[] desArray = inner.encrypt(value.getBytes());
		
		assertEquals(value, new String(inner.decrypt(desArray)));
	}

	public static class Inner extends SignatureProvider {

		private SecretKey key;
		private KeyPair keyPair;

		Inner() throws NoSuchAlgorithmException, NoSuchProviderException {
			super("SHA1withRSA", "BC", 90);
			try {
				this.key = CertificateFactory.createSecretKey("AES", 128);
				this.keyPair = CertificateFactory.createKeyPair("RSA", 512);
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
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
