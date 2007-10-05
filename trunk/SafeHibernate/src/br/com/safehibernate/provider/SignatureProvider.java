package br.com.safehibernate.provider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.SignatureException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public abstract class SignatureProvider implements SecretKeyProvider,
		PublicKeyProvider {

	private Signature signature;

	int signatureSize;

	private ICertificateProvider inner;

	public SignatureProvider(String algorithm,
			int signatureSize,
			ICertificateProvider proxyProvider) throws NoSuchAlgorithmException {
		this(algorithm, signatureSize);
		this.inner = proxyProvider;
	}

	public SignatureProvider(String algorithm, int signatureSize)
			throws NoSuchAlgorithmException {
		this.signature = Signature.getInstance(algorithm);
		this.signatureSize = signatureSize;
	}

	public SignatureProvider(String algorithm,
			String provider,
			int signatureSize) throws NoSuchAlgorithmException,
			NoSuchProviderException {
		this.signature = Signature.getInstance(algorithm, provider);
		this.signatureSize = signatureSize;
	}

	@Override
	public byte[] decrypt(byte[] source) {
		byte[] signature = new byte[this.signatureSize];
		byte[] orignalData = new byte[source.length - this.signatureSize];
		System.arraycopy(source, 0, signature, 0, this.signatureSize);
		System.arraycopy(source, this.signatureSize, orignalData, 0,
				orignalData.length);

		BASE64Decoder b64dec = new BASE64Decoder();
		byte[] decodedSignature;
		try {
			decodedSignature = b64dec.decodeBuffer(new String(signature));
			this.signature.initVerify(getPublicKey());
			this.signature.update(decodedSignature);

			if (this.signature.verify(orignalData)) {
				throw new IllegalStateException("Invalid signature");
			}
			if (this.inner != null) {
				return this.inner.decrypt(orignalData);
			}
			return orignalData;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (SignatureException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] encrypt(byte[] source) {
		byte[] data = source;
		if (this.inner != null) {
			data = this.inner.encrypt(source);
		}
		byte[] assinatura;
		try {
			this.signature.initSign(getPrivateKey());
			this.signature.update(data);
			assinatura = this.signature.sign();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// encode signature in base 64
			BASE64Encoder b64enc = new BASE64Encoder();
			b64enc.encode(assinatura, baos);
			if (baos.size() != this.signatureSize) {
				throw new IllegalStateException(
						"Invalid signature size, expected "
								+ this.signatureSize + " but was "
								+ baos.size());
			}
			baos.write(data);
			return baos.toByteArray();

		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (SignatureException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected ICertificateProvider getInner() {
		return this.inner;
	}

	protected void setInner(ICertificateProvider inner) {
		this.inner = inner;
	}
}