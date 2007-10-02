package br.com.safehibernate.cfg;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.Reference;

import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.classic.Session;
import org.hibernate.engine.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;

public class SafeSessionFactory implements SessionFactory {

	private static final long serialVersionUID = -2957177350950014428L;
	
	private static final Interceptor INTERCEPTOR = new br.com.safehibernate.interceptor.CommonInterceptor();
	
	private SessionFactory wrapped;

	public SafeSessionFactory(SessionFactory wrapped) {
		if (wrapped == null) {
			throw new IllegalArgumentException("wrapped session factory cannot be null");
		}
		this.wrapped = wrapped;
	}

	public void close() throws HibernateException {
		this.wrapped.close();
	}

	@SuppressWarnings("unchecked")
	public void evict(Class persistentClass, Serializable id)
			throws HibernateException {
		this.wrapped.evict(persistentClass, id);
	}

	@SuppressWarnings("unchecked")
	public void evict(Class persistentClass) throws HibernateException {
		this.wrapped.evict(persistentClass);
	}

	public void evictCollection(String roleName, Serializable id)
			throws HibernateException {
		this.wrapped.evictCollection(roleName, id);
	}

	public void evictCollection(String roleName) throws HibernateException {
		this.wrapped.evictCollection(roleName);
	}

	public void evictEntity(String entityName, Serializable id)
			throws HibernateException {
		this.wrapped.evictEntity(entityName, id);
	}

	public void evictEntity(String entityName) throws HibernateException {
		this.wrapped.evictEntity(entityName);
	}

	public void evictQueries() throws HibernateException {
		this.wrapped.evictQueries();
	}

	public void evictQueries(String cacheRegion) throws HibernateException {
		this.wrapped.evictQueries(cacheRegion);
	}

	public Map<?,?> getAllClassMetadata() throws HibernateException {
		return this.wrapped.getAllClassMetadata();
	}

	public Map<?, ?> getAllCollectionMetadata() throws HibernateException {
		return this.wrapped.getAllCollectionMetadata();
	}

	@SuppressWarnings("unchecked")
	public ClassMetadata getClassMetadata(Class persistentClass)
			throws HibernateException {
		return this.wrapped.getClassMetadata(persistentClass);
	}

	public ClassMetadata getClassMetadata(String entityName)
			throws HibernateException {
		return this.wrapped.getClassMetadata(entityName);
	}

	public CollectionMetadata getCollectionMetadata(String roleName)
			throws HibernateException {
		return this.wrapped.getCollectionMetadata(roleName);
	}

	public Session getCurrentSession() throws HibernateException {
		return this.wrapped.getCurrentSession();
	}

	public Set<?> getDefinedFilterNames() {
		return this.wrapped.getDefinedFilterNames();
	}

	public FilterDefinition getFilterDefinition(String filterName)
			throws HibernateException {
		return this.wrapped.getFilterDefinition(filterName);
	}

	public Reference getReference() throws NamingException {
		return this.wrapped.getReference();
	}

	public Statistics getStatistics() {
		return this.wrapped.getStatistics();
	}

	public boolean isClosed() {
		return this.wrapped.isClosed();
	}

	public Session openSession() throws HibernateException {
		return this.openSession(INTERCEPTOR);
	}

	public Session openSession(Connection connection, Interceptor interceptor) {
		return this.wrapped.openSession(connection, interceptor);
	}

	public Session openSession(Connection connection) {
		return this.wrapped.openSession(connection);
	}

	public Session openSession(Interceptor interceptor)
			throws HibernateException {
		return this.wrapped.openSession(interceptor);
	}

	public StatelessSession openStatelessSession() {
		return this.wrapped.openStatelessSession();
	}

	public StatelessSession openStatelessSession(Connection connection) {
		return this.wrapped.openStatelessSession(connection);
	}

}
