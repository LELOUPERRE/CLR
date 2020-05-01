package fr.christophelouer.commons.jpa;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * Classe abstraite générique CRUD pour les entités, paramétrée par "generics"
 * version Java EE. ATTENTION, NE PAS UTILISER EN JAVA STANDARD EDITION !
 *
 * @author CDT RBN
 * @version 2.0
 *
 */
public abstract class AbstractDaoEntrepriseEdition<T> extends AbstractDaoCommon<T>
{

	/**
	 * ajoute une nouvelle entity au contexte JPA (persist).
	 *
	 * @param t
	 *            instance d'une entité à ajouter.
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void create(final T t)
	{
		super.create(t);
	}

	/**
	 * met à jour les données portées par l'instance de T (merge).
	 *
	 * @param t
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public T update(final T t)
	{
		return super.update(t);
	}

	/**
	 * supprime l'instance de T.
	 *
	 * @param t
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(final T t)
	{
		super.delete(t);
	}

}
