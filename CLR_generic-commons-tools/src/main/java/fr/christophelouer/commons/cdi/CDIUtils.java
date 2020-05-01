package fr.christophelouer.commons.cdi;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.inject.Inject;

import fr.christophelouer.commons.introspection.IntrospectionUtils;

/**
 * classe utilitaire CDI.
 * 
 * @author CDT RBN
 *
 */
public final class CDIUtils
{
	private CDIUtils()
	{
		// protection
	}
	
	/**
	 * retourne un bean géré par CDI, celui marqué par Default ou Any.
	 * 
	 * @param clazz
	 * 		type de classe à demander à CDI.
	 * @param <T> type générique.
	 * @return instance du BEAN.
	 */
	public static final <T> T getBean(Class<T> clazz)
	{
		return CDI.current().select(clazz).get();
	}
	
	/**
	 * résoud les injections CDI sur une instance non gérée par CDI.
	 * 
	 * @param o
	 * 		instance à traiter.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void inject(Object o)
	{
	    BeanManager beanManager = CDI.current().getBeanManager();   
	    AnnotatedType type = beanManager.createAnnotatedType(o.getClass());
	    InjectionTarget target = beanManager.createInjectionTarget(type);
	    CreationalContext creationalContext = beanManager.createCreationalContext(null);
	    target.inject(o, creationalContext);
	}
	
	/**
	 * ré-initialise les références à null des attributs (champs) annotés avec @Inject.
	 * 
	 * <p>Cette méthode parcourt tous les champs, même les champs hérités
	 *    et affecte "null" à ceux annotés avec @Inject.</p>
	 * 
	 * @param o
	 * 		instance à traiter.
	 */
	public static void reinitInjected(Object o, String... fieldNames)
	{
		List <String> lstFieldsNames = Arrays.asList(fieldNames);
		Set<Field> fields = IntrospectionUtils.getAllFields(o.getClass());
		for (Field f : fields)
		{
			// si l'annotation Inject est présente
			// et que le champ figure dans la liste ou que la liste est vide
			if (f.isAnnotationPresent(Inject.class)  
				&&  (lstFieldsNames.contains(f.getName()) || lstFieldsNames.isEmpty()))
			{
				// on modifie ans l'objet, la valeur du champ à null car il est annoté avec @Inject
				IntrospectionUtils.changeFieldValue(o, f, null);
			}	
		}
	}
	
	/**
	 * ré-initialise les références à null des attributs (champs) annotés avec @Inject
	 * et ré-injecte des dépendences dans la foulée.
	 * 
	 * <p>Cette méthode parcourt tous les champs, même les champs hérités
	 *    et affecte "null" à ceux annotés avec @Inject.</p>
	 * 
	 * @param o
	 * 		instance à traiter.
	 */
	public static void reinitAndInject(Object o, String... fieldNames)
	{
		// trivial :-)
		reinitInjected(o, fieldNames);
		inject(o);
	}
}
