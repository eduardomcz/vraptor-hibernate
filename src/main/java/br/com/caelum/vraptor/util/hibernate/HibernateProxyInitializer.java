package br.com.caelum.vraptor.util.hibernate;

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.serialization.ProxyInitializer;

/**
 * Initializer for Hibernate proxy objects
 * @author Tomaz Lavieri
 * @since 3.1.2
 */
@Component
@ApplicationScoped
public class HibernateProxyInitializer implements ProxyInitializer {

	public boolean isProxy(Class<?> clazz) {
		return HibernateProxy.class.isAssignableFrom(clazz);
	}

	public void initialize(Object obj) {
		if (obj != null && isProxy(obj.getClass())) {
			getInitializer(obj).initialize();
		}
	}

	private LazyInitializer getInitializer(Object obj) {
		return ((HibernateProxy) obj).getHibernateLazyInitializer();
	}

	public Class<?> getActualClass(Object obj) {
		return isProxy(obj.getClass()) ? getInitializer(obj).getPersistentClass() : obj.getClass();
	}
}