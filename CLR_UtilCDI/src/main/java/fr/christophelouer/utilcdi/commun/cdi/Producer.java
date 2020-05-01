package fr.christophelouer.utilcdi.commun.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import fr.christophelouer.utilcdi.commun.cdi.annotations.jpa.PersistenceContextJavaSE;

@ApplicationScoped
public class Producer {
	
	@Produces
	@PersistenceContextJavaSE
	public EntityManager fabriquerEntityManager(InjectionPoint ip) {
		// Récupération de l'annotation sur le point d'injection
		PersistenceContextJavaSE a = ip.getAnnotated().getAnnotation(PersistenceContextJavaSE.class);
		PersistenceContextJavaSE annotationParam = (PersistenceContextJavaSE) a;
		String persistenceUnitName = annotationParam.persistenceUnitName();

		return Persistence.createEntityManagerFactory(persistenceUnitName).createEntityManager();
	}
}
