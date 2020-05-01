package fr.christophelouer.commons.jpa;

/**
 * exception technique levée quand une fonctionnalité CRUD n'a pas été
 * autorisée.
 *
 * @author CDT RBN
 *
 */
@SuppressWarnings("serial")
public class UnimplementedOperation extends RuntimeException
{
	private static final String MSG = "Fonctionnalité non implémentée : %s";

	public UnimplementedOperation(final String message)
	{
		super(String.format(UnimplementedOperation.MSG, message));
	}
}
