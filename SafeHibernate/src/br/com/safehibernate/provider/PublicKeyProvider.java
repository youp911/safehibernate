package br.com.safehibernate.provider;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface PublicKeyProvider extends ICertificateProvider {

	PrivateKey getPrivateKey();

	PublicKey getPublicKey();
}
