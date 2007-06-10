package br.com.safehibernate.interceptor;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class Interceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 2810394194063695562L;

	public Interceptor() {
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
				Field field = clazz.getField(propertyName);
				SafeField annotation = field.getAnnotation(SafeField.class);
				if (annotation != null) {
					field.set(entity, "BLABLABLA");
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
		
		return super.onSave(entity, id, state, propertyNames, types);
	}
	
	
}
