package br.com.safehibernate.provider;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public abstract class DESProvider extends SecreteKeyProvider {

	private static final String DES = "DES/ECB/PKCS5Padding";
	
	@Override
	public byte[] decrypt(byte[] source) {
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(DES, "BC");
			cipher.init(Cipher.DECRYPT_MODE, getSecreteKey());
			byte[] encrypted = cipher.doFinal(source);
			return encrypted;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (NoSuchProviderException e) {
			throw new RuntimeException(e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		} catch (BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] encrypt(byte[] source) {
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(DES, "BC");
			cipher.init(Cipher.ENCRYPT_MODE, getSecreteKey());
			byte[] encrypted = cipher.doFinal(source);
			return encrypted;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (NoSuchProviderException e) {
			throw new RuntimeException(e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		} catch (BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

}
