package fr.christophelouer.utilcdi.commun.cdi.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * Annotation à ajouter devant la méthode à éxécuter dans le context Weld.
 * @author Christophe Louër
 *
 */
@Qualifier
@Target({ METHOD })
@Retention(RUNTIME)
@Documented
public @interface MethodWeldExecute {
	@Nonbinding String persistenceUnitName() default "";

}
