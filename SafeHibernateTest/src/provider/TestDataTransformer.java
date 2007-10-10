package provider;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
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
import br.com.safehibernate.annotation.EncryptedField;
import br.com.safehibernate.annotation.SignedField;
import br.com.safehibernate.interceptor.DataTransformer;
import br.com.safehibernate.interceptor.ICertificateLookup;
import br.com.safehibernate.provider.DESProvider;
import br.com.safehibernate.provider.ICertificateProvider;
import br.com.safehibernate.provider.SignatureProvider;

public class TestDataTransformer {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	class MySignatureProvider extends SignatureProvider {

		public MySignatureProvider() throws NoSuchAlgorithmException, NoSuchProviderException {
			super("SHA1withRSA", "BC", 90);
		}
		
		KeyPair keyPair = CertificateFactory.createKeyPair();
		
		@Override
		public PrivateKey getPrivateKey() {
			return this.keyPair.getPrivate();
		}

		@Override
		public PublicKey getPublicKey() {
			return this.keyPair.getPublic();
		}
		
	}
	
	@Test
	public void testStringEncryption() throws SecurityException, NoSuchFieldException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		
		class MyClass {
			
			@SignedField
			@EncryptedField
			private String text;

			@SuppressWarnings("unused")
			public String getText() {
				return this.text;
			}

			public void setText(String text) {
				this.text = text;
			}
		}

		MyClass myInstance = new MyClass();
		myInstance.setText("Testing");
	
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBEWithMD5AndDES", "BC");  
        KeySpec ks = new PBEKeySpec ("senha".toCharArray(), new byte[]{1,2,3,4,5,6,7,8,9,10}, 1000);   
        final SecretKey secretKey = skf.generateSecret(ks);
		//final SecretKey secretKey = CertificateFactory.createSecretKey("DES", 128);
		
		class MyEncryptionProvider extends DESProvider {
			@Override
			public SecretKey getSecretKey() {
				return secretKey;
			}
		}
		
		Field field = MyClass.class.getDeclaredField("text");
		
		DataTransformer dataTransformer = new DataTransformer(new ICertificateLookup() {

			@Override
			public ICertificateProvider getEncryptionProvider() {
				return new MyEncryptionProvider();
			}

			@Override
			public SignatureProvider getSignatureProvider() {
				try {
					return new MySignatureProvider();
				} catch (NoSuchAlgorithmException e) {
					throw new RuntimeException(e);
				} catch (NoSuchProviderException e) {
					throw new RuntimeException(e);
				}
			}
			
		});
		
		dataTransformer.encrypt(field, myInstance);
		dataTransformer.decrypt(field, myInstance);
		
	}
	
	@Test
	public void testNumericEncryption() throws SecurityException, NoSuchFieldException, NoSuchAlgorithmException {
		
		class MyClass {
			
			@SignedField(storeOnField="assinaturaCodigo")
			@EncryptedField(storeOnField="criptografiaCodigo")
			private long codigo;
			
			private String assinaturaCodigo;
			private String criptografiaCodigo;
			
			@SuppressWarnings("unused")
			public long getCodigo() {
				return this.codigo;
			}
			
			public void setCodigo(long value) {
				this.codigo = value;
			}

			public String getAssinaturaCodigo() {
				return this.assinaturaCodigo;
			}

			@SuppressWarnings("unused")
			public void setAssinaturaCodigo(String assinaturaCodigo) {
				this.assinaturaCodigo = assinaturaCodigo;
			}

			public String getCriptografiaCodigo() {
				return this.criptografiaCodigo;
			}

			@SuppressWarnings("unused")
			public void setCriptografiaCodigo(String criptografiaCodigo) {
				this.criptografiaCodigo = criptografiaCodigo;
			}
		}
		
		MyClass myInstance = new MyClass();
		myInstance.setCodigo(10);
		
		final SecretKey secretKey = CertificateFactory.createSecretKey("DES", 128);
		
		class MyEncryptionProvider extends DESProvider {
			@Override
			public SecretKey getSecretKey() {
				return secretKey;
			}
		}
		
		Field field = MyClass.class.getDeclaredField("codigo");
		
		DataTransformer dataTransformer = new DataTransformer(new ICertificateLookup() {
			
			@Override
			public ICertificateProvider getEncryptionProvider() {
				return new MyEncryptionProvider();
			}
			
			@Override
			public SignatureProvider getSignatureProvider() {
				try {
					return new MySignatureProvider();
				} catch (NoSuchAlgorithmException e) {
					throw new RuntimeException(e);
				} catch (NoSuchProviderException e) {
					throw new RuntimeException(e);
				}
			}
			
		});
		
		dataTransformer.encrypt(field, myInstance);
		
		assertNotNull(myInstance.getAssinaturaCodigo());
		
		assertNotNull(myInstance.getCriptografiaCodigo());
		
		dataTransformer.decrypt(field, myInstance);
	}

}
