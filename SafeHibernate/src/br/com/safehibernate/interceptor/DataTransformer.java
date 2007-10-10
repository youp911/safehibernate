package br.com.safehibernate.interceptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import br.com.safehibernate.annotation.EncryptedField;
import br.com.safehibernate.annotation.SignedField;

public final class DataTransformer {

	public DataTransformer() {
		this(new HibernateCertificateLookup());
	}
	
	public DataTransformer(ICertificateLookup certificateLookup) {
		this.certificateLookup = certificateLookup;
	}
	
	private ICertificateLookup certificateLookup;
	
	private static Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<Class<?>, Class<?>>();
	
    static {
         primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
         primitiveWrapperMap.put(Byte.TYPE, Byte.class);
         primitiveWrapperMap.put(Character.TYPE, Character.class);
         primitiveWrapperMap.put(Short.TYPE, Short.class);
         primitiveWrapperMap.put(Integer.TYPE, Integer.class);
         primitiveWrapperMap.put(Long.TYPE, Long.class);
         primitiveWrapperMap.put(Double.TYPE, Double.class);
         primitiveWrapperMap.put(Float.TYPE, Float.class);
    }

	public Object encrypt(Field field, Object object) {
		try {
			EncryptedField encryptionAnnotation = field.getAnnotation(EncryptedField.class);
			if (encryptionAnnotation != null) {
				Field encryptedField = field;
				String encryptFieldName = encryptionAnnotation.storeOnField();
				boolean sameField = "this".equals(encryptFieldName);
				if (!sameField) {
					encryptedField = object.getClass().getDeclaredField(encryptFieldName);
				}
				encryptedField.setAccessible(true);
				byte[] decryptedData =  String.valueOf(encryptedField.get(object)).getBytes();
				byte[] tempData = this.certificateLookup.getEncryptionProvider().encrypt(decryptedData);
				store(encryptedField, object, tempData);
			}
			
			//Assina o campo, se for necessário.
			SignedField signatureAnnotation = field.getAnnotation(SignedField.class);
			if (signatureAnnotation != null) {
				Field signatureField = field;
				String signatureFieldName = signatureAnnotation.storeOnField();
				boolean sameField = "this".equals(signatureFieldName);
				if (!sameField) {
					signatureField = object.getClass().getDeclaredField(signatureFieldName);
				}
				field.setAccessible(true);
				
				Object o = field.get(object);
				if (o != null) {
					byte[] signedData = String.valueOf(o).getBytes();
					byte[] tempData = this.certificateLookup.getSignatureProvider().encrypt(signedData);
					store(signatureField, object, tempData);
				}
			}
			field.setAccessible(true);
			return field.get(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Object decrypt(Field field, Object object) {
		try {
			//Primeiro verifica a assinatura, se houver.
			SignedField signatureAnnotation = field.getAnnotation(SignedField.class);
			if (signatureAnnotation != null) {
				Field signatureField = field;
				String signatureFieldName = signatureAnnotation.storeOnField();
				boolean sameField = "this".equals(signatureFieldName);
				if (!sameField) {
					signatureField = object.getClass().getDeclaredField(signatureFieldName);
				}
				signatureField.setAccessible(true);
				Object o = signatureField.get(object);
				if (o != null) {
					byte[] signedData = String.valueOf(signatureField.get(object)).getBytes();
					byte[] tempData = this.certificateLookup.getSignatureProvider().decrypt(signedData);
					store(signatureField, object, tempData);
				}
			}
			
			EncryptedField encryptionAnnotation = field.getAnnotation(EncryptedField.class);
			if (encryptionAnnotation != null) {
				Field encryptedField = field;
				String encryptFieldName = encryptionAnnotation.storeOnField();
				boolean sameField = "this".equals(encryptFieldName);
				if (!sameField) {
					encryptedField = object.getClass().getDeclaredField(encryptFieldName);
				}
				encryptedField.setAccessible(true);
				Object o = encryptedField.get(object);
				if (o == null) {
					return null;
				}
				byte[] encryptedData =  String.valueOf(o).getBytes();
				byte[] tempData = this.certificateLookup.getEncryptionProvider().decrypt(encryptedData);
				store(encryptedField, object, tempData);
			}
			return field.get(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void store(Field storeField, Object object, byte[] data) {
		Class<?> type = storeField.getType(); 
		if (type.isPrimitive()) {
			type = primitiveWrapperMap.get(storeField.getType());
		}
		try {
			Constructor<?> constructor = type.getConstructor(String.class);
			Object newInstance = constructor.newInstance(new String(data));
			storeField.setAccessible(true);
			storeField.set(object, newInstance);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}
