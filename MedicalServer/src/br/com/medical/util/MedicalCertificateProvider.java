package br.com.medical.util;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import javax.crypto.SecretKey;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import br.com.medical.to.UserTO;
import br.com.safehibernate.provider.AESProvider;
import br.com.safehibernate.provider.SignatureProvider;

public class MedicalCertificateProvider extends SignatureProvider {

	public MedicalCertificateProvider() throws NoSuchAlgorithmException {
		super("SHA1withRSA", 90, new AESProvider() {
			@Override
			public SecretKey getSecreteKey() {
				return threadLocal.get().secretKey;
			}
		});
	}

	public static ThreadLocal<UserTO> threadLocal = new ThreadLocal<UserTO>();

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	@Override
	public PrivateKey getPrivateKey() {
		return threadLocal.get().keyPair.getPrivate();
	}

	@Override
	public PublicKey getPublicKey() {
		return threadLocal.get().keyPair.getPublic();
	}
}