package provider;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import br.com.safehibernate.provider.DESProvider;

public class TestDESProvider {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	@Test public void testEncrypt() {
		InnerDES inner = new InnerDES();

		String value = "Testing!!!!";

		byte[] data = inner.encrypt(value.getBytes());
		
		String testValue = new String(data);

		data = testValue.getBytes();
		
		assertEquals(value, new String(inner.decrypt(data)));

	}

	public static class InnerDES extends DESProvider {

		private SecretKey key;

		InnerDES() {
			try {
				//this.key = CertificateFactory.createSecretKey("DES", 128);
				
				SecretKeyFactory skf = SecretKeyFactory.getInstance("PBEWithMD5AndDES", "BC");  
		        KeySpec ks = new PBEKeySpec ("delphi".toCharArray(), new byte[]{1,2,3,4,5,6,7,8,9,10}, 1000);   
		        this.key = skf.generateSecret(ks);
				
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			} catch (NoSuchProviderException e) {
				throw new RuntimeException(e);
			} catch (InvalidKeySpecException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public SecretKey getSecretKey() {
			return this.key;
		}

	}

}
