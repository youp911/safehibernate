 package br.com.safehibernate.interceptor;

import java.lang.reflect.Field;

import org.hibernate.event.PostLoadEvent;
import org.hibernate.event.def.DefaultPostLoadEventListener;

public class PostLoadInterceptor extends DefaultPostLoadEventListener {

	private static final long serialVersionUID = 6395300952543384839L;

	@Override
	public void onPostLoad(PostLoadEvent event) {
		super.onPostLoad(event);
		Object o = event.getEntity();

		Field[] fields = o.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			EncryptedField annotation = field.getAnnotation(EncryptedField.class);
			if (annotation != null) {
				try {
					field.setAccessible(true);
					Object value = field.get(o);
					field.set(o, new String(DataTransformer.decrypt(value)));
				} catch (IllegalArgumentException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}