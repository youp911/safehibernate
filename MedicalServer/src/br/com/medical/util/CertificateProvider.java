package br.com.medical.util;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import br.com.medical.to.UserTO;
import br.com.safehibernate.provider.RSAProvider;

public class CertificateProvider extends RSAProvider {

	public static ThreadLocal<UserTO> threadLocal = new ThreadLocal<UserTO>();

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	@Override
	protected PrivateKey getPrivateKey() {
		return threadLocal.get().keyPair.getPrivate();
	}

	@Override
	protected PublicKey getPublicKey() {
		return threadLocal.get().keyPair.getPublic();
	}

}