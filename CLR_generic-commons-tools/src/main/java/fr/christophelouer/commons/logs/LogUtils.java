package fr.christophelouer.commons.logs;

import java.util.Formatter;

import org.apache.commons.logging.Log;

/**
 * classe utilitaire pour la production de logs.
 *
 * @author CDT ROBIN
 *
 */

public final class LogUtils
{

	/**
	 * niveaux le logs supportés.
	 *
	 */
	public enum LogLevel
	{
		ERROR, WARN, INFO, DEBUG
	}

	private LogUtils()
	{
		// protection du constructeur pour cette classe utilitaire.
	}

	/**
	 * formate le message avec les paramètres et génère le log.
	 *
	 * @param log
	 *            instance du log d'écriture
	 * @param level
	 *            niveau de log du message
	 * @param format
	 *            chaine de formatage {@link Formatter}
	 * @param objects
	 *            liste des paramètres à injecter dans la chaine de formatage.
	 */
	public static final void logFormat(final Log log, final LogLevel level, final String format, final Object... objects) //NOSONAR
	{
		// NOTE : les messages sont construits volontairement après chaque
		// "if is...Enabled"
		// pour ne pas construire les chaines si jamais le log n'est pas actif.
		// NE PAS TENTER DE FACTORISER EN SORTANT CETTE INSTRUCTION, sinon cela
		// serait contre-
		// productif.
		
		switch (level)
		{
			case INFO: // NOSONAR
				if (log.isInfoEnabled())
				{
					final String message = String.format(format, objects);
					log.info(message);
				}
				break;
			case WARN: // NOSONAR
				if (log.isWarnEnabled())
				{
					final String message = String.format(format, objects);
					log.warn(message);
				}
				break;
			case ERROR: // NOSONAR
				if (log.isErrorEnabled())
				{
					final String message = String.format(format, objects);
					log.error(message);
				}
				break;
			case DEBUG: // NOSONAR
				if (log.isDebugEnabled())
				{
					final String message = String.format(format, objects);
					log.debug(message);
				}
				break;
			default: // NOSONAR
				if (log.isInfoEnabled())
				{
					final String message = String.format(format, objects);
					log.info(message);
				}
				break;
		}
	}

	/**
	 * formate le message avec les paramètres et génère le log en niveau INFO
	 * automatiquement.
	 *
	 * @param log
	 *            instance du log d'écriture
	 * @param format
	 *            chaine de formatage {@link Formatter}
	 * @param objects
	 *            liste des paramètres à injecter dans la chaine de formatage.
	 */
	public static final void logFormat(final Log log, final String format, final Object... objects)
	{
		LogUtils.logFormat(log, LogLevel.INFO, format, objects);
	}

	public static final void logException(final Log log, final LogLevel level, final Throwable pException, final String format, final Object... objects)
	{
		switch (level) // NOSONAR
		{
			case INFO: // NOSONAR
				if (log.isInfoEnabled())
				{
					final String message = String.format(format, objects);
					log.info(message, pException);
				}
				break;
			case WARN: // NOSONAR
				if (log.isWarnEnabled())
				{
					final String message = String.format(format, objects);
					log.warn(message, pException);
				}
				break;
			case ERROR: // NOSONAR
				if (log.isErrorEnabled())
				{
					final String message = String.format(format, objects);
					log.error(message, pException);
				}
				break;
			case DEBUG: // NOSONAR
				if (log.isDebugEnabled())
				{
					final String message = String.format(format, objects);
					log.debug(message, pException);
				}
				break;
		}
	}
}
