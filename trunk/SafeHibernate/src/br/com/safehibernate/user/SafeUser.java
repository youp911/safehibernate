package br.com.safehibernate.user;

import java.security.KeyPair;

public class SafeUser {

	private String name;
	private byte[] privateKey;
	private byte[] publicKey;

	public SafeUser(String name, KeyPair pair) {
		setName(name);
		setPair(pair);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		assert name != null;
		this.name = name;
	}

	private void setPair(KeyPair pair) {
		assert pair != null;
	}

	public byte[] getPrivateKey() {
		return this.privateKey;
	}

	public void setPrivateKey(byte[] privateKey) {
		this.privateKey = privateKey;
	}

	public byte[] getPublicKey() {
		return this.publicKey;
	}

	public void setPublicKey(byte[] publicKey) {
		this.publicKey = publicKey;
	}
}
