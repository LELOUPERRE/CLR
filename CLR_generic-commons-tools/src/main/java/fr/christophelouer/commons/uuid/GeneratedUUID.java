package fr.christophelouer.commons.uuid;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;


/**
 * permet de faire générer un UUID sur un champs de type :
 *
 * <pre>
 *  - UUID
 *  - String (hexa ou base64) ;
 *  - byte[16] (16 octets pour 128 bits d'un UUID).
 * </pre>
 *
 * @author francois.robin
 *
 */

@Qualifier
@Target({ TYPE, METHOD, PARAMETER, FIELD })
@Retention(RUNTIME)
@Documented
public @interface GeneratedUUID
{	
	/**
	 * prend les valeurs des l'enum UUIDRepresentation.
	 * 
	 * @return une représentation de UUID
	 */
	UUIDRepresentation representation() default UUIDRepresentation.HEXA_STRING;

}
