package fr.christophelouer.commons.uuid;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.christophelouer.commons.introspection.IntrospectionUtils;

/**
 * classe utilitaire pour l'injection d'un UUID dans un champ d'un objet. Ce
 * champ doit être de type String ou byte[].
 *
 *
 * @author CDT ROBIN
 *
 */

public final class UUIDInjector
{
	// Logger
	protected static Log log = LogFactory.getLog(UUIDInjector.class);

	// Erreurs
	private static final String ERROR_MESSAGE = "Erreur pendant l'injection de l'UUID.";
	private static final String ERROR_DETAILS = "Le champ annoté avec @GeneratedUUID %s n'est pas de type String ou byte[].";

	// Map d'association des différents injecteurs
	private static final Map<Class<?>, BiConsumer<Object, Field>> injectors = new HashMap<>();

	// Initialisation de la map pour assosier à chaque type cible, une méthode
	// d'injection de UUID
	static 
        {
		injectors.put(UUID.class, UUIDInjector::injectUUIDField);
		injectors.put(String.class, UUIDInjector::injectStringField);
	}

	private UUIDInjector()
	{
		// Protection du constructeur.
	}

	/**
	 * parcours les champs de type String ou byte[] et vérifie que l'annotation
	 * GeneratedUUID est présente. Si cela est le cas, elle est injectée.
	 *
	 * @param t
	 *            objet à inspecter pour injection
	 * @param <T> classe générique.           
	 */
	public static final <T> void inject(final T t)
	{
		final Set<Field> fields = IntrospectionUtils.getAllFields(t.getClass());
		fields.forEach(f -> UUIDInjector.visitField(t, f));
	}

	/**
	 * retourne l'injecteur sous forme de BiConsumer associé au type du champs passé
	 * en paramètre.
	 * 
	 * @param f
	 * 		champs à injecter
	 * @return
	 * 		instance du BiConsumer capable d'injecter un UUID dans ce type de champs.
	 * 		Si aucun type n'est trouvé, alors l'injecteur pour un tableau de byte[]
	 * 		est retourné par défaut.
	 */
	private static final BiConsumer<Object, Field> getInjector(final Field f)
	{
		final Class<?> fieldClass = f.getType();
		return injectors.containsKey(fieldClass) ? injectors.get(fieldClass) : UUIDInjector::injectBinaryField;
	}

	/**
	 * visite un champ d'un objet à la recherce de l'annotation GeneratedUUID pour l'injecter.
	 * L'injection est déléguée à un BiConsumer déclaré dans la map "injectors".
	 * 
	 * @param t
	 * 		instance visitée.
	 * @param f
	 * 		champ à analyser et à injecter le cas échéans.
	 */
	private static final <T> void visitField(T t, final Field f)
	{
		if (f.isAnnotationPresent(GeneratedUUID.class))
		{
			try
			{
				final boolean accessible = f.isAccessible();
				f.setAccessible(true);
				getInjector(f).accept(t, f);
				f.setAccessible(accessible);
			}
			catch (SecurityException e)
			{
				if (UUIDInjector.log.isErrorEnabled())
				{
					UUIDInjector.log.error(UUIDInjector.ERROR_MESSAGE, e);
				}
			}
		}
	}

	// **** INJECTEURS **** //
	
	/*
	 * Injecteur pour type UUID.
	 */
	private static final <T> void injectUUIDField(final T t, final Field f)
	{
		final UUID uuid = UUID.randomUUID();
		try
		{
			f.set(t, uuid);
		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			if (UUIDInjector.log.isErrorEnabled())
			{
				UUIDInjector.log.error(UUIDInjector.ERROR_MESSAGE, e);
			}
		}
	}

	/*
	 * Injecteur pour type String suportant 2 formats : base64 ou hexadecimal.
	 */
	private static final <T> void injectStringField(final T t, final Field f)
	{
		final GeneratedUUID annotation = f.getAnnotation(GeneratedUUID.class);
		final UUID uuid = UUID.randomUUID();
		String value;

		if (UUIDRepresentation.BASE64_STRING.equals(annotation.representation()))
		{
			// utilisation de Commons Codec car les caractères "+" et "/" et "=="
			// peuvent être "dangereux" dans les clés qui servent en WEB.
			// si besoin décoder plus tard il faut utiler l'une des méthodes 
			// de commons codec.
			value = Base64.encodeBase64URLSafeString(UUIDUtils.convertToBytes(uuid));
		}
		else
		{
			// mode hexadécimal pur
			value = uuid.toString();
		}

		// injection de la valeur
		try
		{
			f.set(t, value);
		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			if (UUIDInjector.log.isErrorEnabled())
			{
				UUIDInjector.log.error(UUIDInjector.ERROR_MESSAGE, e);
			}
		}
	}

	/*
	 * injecteur pour tableau d'octets.
	 */
	private static final <T> void injectBinaryField(final T t, final Field f) //NOSONAR
	{
		final Class<?> fieldClass = f.getType();

		if (fieldClass.isArray() && fieldClass.getComponentType().equals(byte.class))
		{
			final UUID uuid = UUID.randomUUID();
			final byte[] buffer = UUIDUtils.convertToBytes(uuid);
			// injection de la valeur
			try
			{
				f.set(t, buffer);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				if (UUIDInjector.log.isErrorEnabled())
				{
					UUIDInjector.log.error(UUIDInjector.ERROR_MESSAGE, e);
				}
			}
		}
		else
		{
			if (UUIDInjector.log.isInfoEnabled())
			{
				UUIDInjector.log.info(UUIDInjector.ERROR_MESSAGE);
				UUIDInjector.log.info(String.format(UUIDInjector.ERROR_DETAILS, f.getName()));
			}
		}
	}
}
