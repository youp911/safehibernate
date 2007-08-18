package br.com.safehibernate;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface ICertificateProvider {

	PrivateKey getPrivateKey();
	PublicKey getPublicKey();
	String getAlgorithm();
}
