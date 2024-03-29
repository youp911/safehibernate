package br.com.safehibernate.provider;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.util.encoders.Base64;

public abstract class AESProvider implements SecretKeyProvider {

private static final String AES = "AES/ECB/PKCS5Padding";
	
	@Override
	public byte[] decrypt(byte[] source) {
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(AES, "BC");
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
			byte[] encrypted = cipher.doFinal(Base64.decode(source));
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
			cipher = Cipher.getInstance(AES, "BC");
			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
			byte[] encrypted = cipher.doFinal(source);
			return Base64.encode(encrypted);
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
