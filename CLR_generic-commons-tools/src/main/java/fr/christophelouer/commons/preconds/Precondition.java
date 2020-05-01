package fr.christophelouer.commons.preconds;

import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * offre des méthodes statics pour les tests de préconditions (en entrée de
 * méthode par exemple).
 * 
 * @author CDT ROBIN
 *
 */
public final class Precondition
{

	@SuppressWarnings("serial")
	public static class PreconditionException extends RuntimeException
	{
		public PreconditionException(String message)
		{
			super("Precondition failure : " + message);
		}
	}

	private Precondition()
	{
		// protection
	}

	/**
	 * vérifie que le BooleanSupplier fourni en argument retourne bien "true". 
	 * Si ce n'est pas le cas le supplier de toute exception héritant de RuntimeException
	 * est invoqué et l'exception est levée.
	 * 
	 * @param checkFunction
	 * 		supplier qui doit retouner "true" ou "false"
	 * @param exceptionCreator
	 * 		supplier d'exception de type Runtime à invoquer le cas échéant.
	 */
	public static void check(BooleanSupplier checkFunction, Supplier<? extends RuntimeException> exceptionCreator)
	{
		if (!checkFunction.getAsBoolean())
		{
			throw exceptionCreator.get();
		}
	}
	
	/**
	 * vérifie qu'un objet n 'est pas null.
	 * Si ce n'est pas le cas le supplier de toute exception héritant de RuntimeException
	 * est invoqué et l'exception est levée avec le message passé en paramètre.
	 * 
	 * @param o
	 * 		référence à tester.
	 * @param exceptionCreator
	 * 		supplier d'exception de type Runtime à invoquer le cas échéant
	 * @param message
	 * 		message de l'exception.
	 */
	
	public static void checkNotNull(Object o, Function<String, ? extends RuntimeException> exceptionCreator, String message)
	{
		if (o == null)
		{
			throw exceptionCreator.apply(message);
		}
	}
	
	/**
	 * vérifie qu'un objet n 'est pas null.
	 * Si ce n'est pas le cas une exception "PreconditionException" est levée
	 * avec le message passé en paramètre.
	 * 
	 * 
	 * @param o
	 * 		référence à tester.
	 * @param message
	 * 		message de l'exception.
	 */
	public static void checkNotNull(Object o, String message)
	{
		if (o == null)
		{
			throw new PreconditionException(message);
		}
	}
	
	/**
	 * vérifie que le BooleanSupplier fourni en argument retourne bien "true". 
	 * Si ce n'est pas le cas une exception "PreconditionException" est levée
	 * avec le message passé en paramètre.
	 * 
	 * @param checkFunction
	 * 		supplier qui doit retouner "true" ou "false"
	 * @param message
	 * 		message de l'exception
	 */

	public static void check(BooleanSupplier checkFunction, String message)
	{
		if (!checkFunction.getAsBoolean())
		{
			throw new PreconditionException(message);
		}
	}

	/**
	 * vérifie que le BooleanSupplier fourni en argument retourne bien "true".
	 * Si ce n'est pas le cas une exception "PreconditionException" est levée
	 * avec le format de message StringFormat, passé en paramètre, construit avec
	 * les instances fournies en varargs.
	 * 
	 * @param checkFunction
	 * 		supplier qui doit retouner "true" ou "false"
	 * @param messageFormat
	 * 		format de message
	 * @param values
	 * 		instances à injecter dans le message.
	 * 
	 */
	public static void check(BooleanSupplier checkFunction, String messageFormat, Object... values)
	{
		if (!checkFunction.getAsBoolean())
		{
			throw new PreconditionException(String.format(messageFormat, values));
		}
	}

}
