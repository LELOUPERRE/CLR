package fr.christophelouer.commons.introspection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * classe utilitaire permettant de bénéficier de fonctionnalités d'introspection
 * complémentaires.
 *
 * @author CDT ROBIN
 *
 */

public final class IntrospectionUtils
{

	private IntrospectionUtils()
	{
		// protection du constructeur pour cette classe utilitaire.
	}

	/**
	 * retourne un set des champs présents dans la classe, même hérités, quelle
	 * que soit leur visibilité.
	 *
	 * @param classe
	 *            classe à introspecter.
	 * @param <T> classe générique.           
	 * @return set des champs.
	 */
	public static <T> Set<Field> getAllFields(final Class<T> classe)
	{
		final Set<Field> fields = new LinkedHashSet<>();
		fields.addAll(Arrays.asList(classe.getDeclaredFields()));

		if (classe.getSuperclass() != null)
		{
			// récursivité tant qu'on a pas atteind la classe mère "Object".
			fields.addAll(IntrospectionUtils.getAllFields(classe.getSuperclass()));
		}

		return fields;
	}

	/**
	 * renvoie "true" si la classe (ou la première de ses classes mères en
	 * remontant l'arbre d'héritage) possède l'annotation passée en paramètre.
	 *
	 * @param annotation
	 *            annotation à trouver.
	 * @param testedClass
	 *            classe à vérifier.
	 * @return true si l'annotation a été trouvée, false dans le cas contraire.
	 */
	public static boolean isClassAnnotatedWith(final Class<? extends Annotation> annotation, final Class<?> testedClass)
	{
		if (testedClass == null)
		{
			return false;
		}
		else
		{
			boolean result;

			if ((annotation != null) && testedClass.isAnnotationPresent(annotation))
			{
				result = true;
			}
			else
			{
				// récursif si on a pas atteint la classe "Object" qui est sans
				// classe mère.
				result = (testedClass.getSuperclass() == null) ? false : IntrospectionUtils.isClassAnnotatedWith(annotation, testedClass.getSuperclass());
			}
			return result;
		}
	}

	/**
	 * retourne le contenu de l'attribut d'une annotation de classe.
	 *
	 * @param inspectedClass
	 *            classe inspectée
	 * @param annotationClass
	 *            annotation (class) à analyser
	 * @param attributeName
	 *            attribut à retourner
	 * @return le contenu de l'attribut s'il existe, null dans tous les autres
	 *         cas.
	 */
	public static Object getAnnotationAttribute(final Class<?> inspectedClass, final Class<? extends Annotation> annotationClass, final String attributeName)
	{
		final Annotation annotation = inspectedClass.getAnnotation(annotationClass);
		if (annotation == null)
		{
			return null;
		}
		else
		{
			try
			{
				final Method m = annotation.annotationType().getMethod(attributeName);
				return (m != null) ? m.invoke(annotation) : null;
			}
			catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) // NOSONAR
			{
				return null;
			}
		}
	}
	
	/**
	 * met à jour la référence d'un champs, qu'il soit accessible ou pas.
	 * 
	 * 
	 * @param o
	 * 		instance manipulée
	 * 
	 * @param f
	 * 		champ modifié
	 *
	 * @param v
	 * 		nouvelle valeur affectée
	 */
	public static void changeFieldValue(Object o, Field f, Object v)
	{
		boolean accessible = f.isAccessible();
		f.setAccessible(true);
		try {
			f.set(o, v);
		} catch (IllegalArgumentException | IllegalAccessException e) {
		    // TODO : modifie avec un log erreur propre
			e.printStackTrace();
		}
		f.setAccessible(accessible);
	}
}
