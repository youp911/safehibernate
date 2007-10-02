package br.com.safehibernate.provider;

import java.security.PrivateKey;
import java.security.PublicKey;

public abstract class PublicKeyProvider implements ICertificateProvider {

	protected abstract PrivateKey getPrivateKey();

	protected abstract PublicKey getPublicKey();
}
