package br.com.safehibernate.provider;

import javax.crypto.SecretKey;

public abstract class SecreteKeyProvider implements ICertificateProvider {

	public abstract SecretKey getSecreteKey();

}
