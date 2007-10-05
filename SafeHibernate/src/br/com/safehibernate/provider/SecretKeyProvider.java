package br.com.safehibernate.provider;

import javax.crypto.SecretKey;

public interface SecretKeyProvider extends ICertificateProvider {

	public abstract SecretKey getSecreteKey();

}
