package br.com.safehibernate.provider;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import br.com.safeHibernate.certificate.CertificateFactory;

public class TestRSAProvider {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	public static void main(String[] args) {
		InnerRSA innerRSA = new InnerRSA();
		
		String value = "Testando!!!123 abc adasd asd asd asd asd  dd asdas  s";
		
		byte[] data =  innerRSA.encrypt(value.getBytes());
		
		String aa = new String(data);
		
		data = aa.getBytes();
		
		System.out.println(new String(innerRSA.decrypt(data)));
		
	}
	
	public static class InnerRSA extends RSAProvider {

		private KeyPair keyPair;

		InnerRSA() {
			this.keyPair = CertificateFactory.createKeyPair();
		}
		
		@Override
		protected PrivateKey getPrivateKey() {
			return this.keyPair.getPrivate();
		}

		@Override
		protected PublicKey getPublicKey() {
			return this.keyPair.getPublic();
		}
		
	}
	
}
