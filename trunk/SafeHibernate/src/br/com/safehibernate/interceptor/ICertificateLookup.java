package br.com.safehibernate.interceptor;

import br.com.safehibernate.provider.ICertificateProvider;
import br.com.safehibernate.provider.SignatureProvider;

public interface ICertificateLookup {

	ICertificateProvider getEncryptionProvider();

	SignatureProvider getSignatureProvider();

}