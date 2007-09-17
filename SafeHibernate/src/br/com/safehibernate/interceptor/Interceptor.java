package br.com.safehibernate.interceptor;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class Interceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 2810394194063695562L;

	public Interceptor() {
	}

	@Override
	public boolean onFlushDirty(Object entity,
		Serializable id,
		Object[] currentState,
		Object[] previousState,
		String[] propertyNames,
		Type[] types) {
		Class<?> clazz = entity.getClass();
		for (int i = 0; i < propertyNames.length; i++) {
			String propertyName = propertyNames[i];
			try {
				Field field = clazz.getDeclaredField(propertyName);
				field.setAccessible(true);
				SafeField annotation = field.getAnnotation(SafeField.class);
				if (annotation != null) {
					Object value = field.get(entity);
					
					String newValue = new String(DataTransformer.encrypt(value)); 
					
					currentState[i] = newValue;
				}
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			} catch (NoSuchFieldException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		
		return true;
	}
	
	@Override
	public boolean onSave(Object entity,
		Serializable id,
		Object[] state,
		String[] propertyNames,
		Type[] types) {
		
		Class<?> clazz = entity.getClass();
		for (int i = 0; i < propertyNames.length; i++) {
			String propertyName = propertyNames[i];
			try {
				Field field = clazz.getDeclaredField(propertyName);
				field.setAccessible(true);
				SafeField annotation = field.getAnnotation(SafeField.class);
				if (annotation != null) {
					Object value = field.get(entity);
					
					String newValue = new String(DataTransformer.encrypt(value)); 
					
					field.set(entity, newValue);
					state[i] = newValue;
				}
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			} catch (NoSuchFieldException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		
		return true;
	}
	
	@Override
	public boolean onLoad(Object entity,
		Serializable id,
		Object[] state,
		String[] propertyNames,
		Type[] types) {
		boolean load = super.onLoad(entity, id, state, propertyNames, types);
		
		Class<?> clazz = entity.getClass();
		for (int i = 0; i < propertyNames.length; i++) {
			String propertyName = propertyNames[i];
			try {
				Field field = clazz.getDeclaredField(propertyName);
				field.setAccessible(true);
				SafeField annotation = field.getAnnotation(SafeField.class);
				if (annotation != null) {
					String methodName = "get" + propertyName.substring(0,1).toUpperCase() + propertyName.substring(1);
					Method m = clazz.getMethod(methodName);
					Object value = m.invoke(entity);
					field.set(entity, DataTransformer.decrypt(value));
				}
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			} catch (NoSuchFieldException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			} 
		}
		
		return load;
	}
}