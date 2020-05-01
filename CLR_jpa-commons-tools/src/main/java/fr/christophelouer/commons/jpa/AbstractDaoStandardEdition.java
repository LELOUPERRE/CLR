package fr.christophelouer.commons.jpa;

import javax.persistence.EntityTransaction;

/**
 * Classe abstraite générique CRUD pour les entités, paramétrée par "generics"
 * version Java SE. ATTENTION, NE PAS UTILISER CETTE FACADE EN JAVA ENTERPRISE
 * EDITION !
 *
 * @author CDT RBN
 * @version 1.0
 *
 */
public abstract class AbstractDaoStandardEdition<T> extends AbstractDaoCommon<T>
{

	public AbstractDaoStandardEdition()
	{
		// appel du constructeur de la classe mère.
		super(); 
		// appel de init() car non automatique en Java SE
		this.init();
	}

	/*
	 * cette classe enrobe les méthodes transactionnelles de la classe mère pour
	 * gérer manuellement la transaction en mode Java Standard Edition.
	 */

	/**
	 * enregistre l'entité JPA passée en paramètre.
	 */
	@Override
	public void create(final T t)
	{
		final boolean openedHere = this.openTransactionIfNotActive();
		super.create(t);
		if (openedHere)
		{
			this.getEntityManager().getTransaction().commit();
		}
	}

	/**
	 * sauvegarde l'entité JPA passé en paramètre.
	 */
	@Override
	public T update(final T t)
	{
		final boolean openedHere = this.openTransactionIfNotActive();
		final T managed = super.update(t);
		if (openedHere)
		{
			this.getEntityManager().getTransaction().commit();
		}
		return managed;
	}

	/**
	 * supprime l'entité JPA passée en paramètre.
	 */
	@Override
	public void delete(final T t)
	{
		final boolean openedHere = this.openTransactionIfNotActive();
		super.delete(t);
		if (openedHere)
		{
			this.getEntityManager().getTransaction().commit();
		}
	}

	// méthodes privée pour la gestion des transactions de manière manuelle.

	/**
	 * ouvre une transaction si elle n'est pas déjà ouverte.
	 *
	 * @return true si la transaction a dû être commencée, false si elle
	 *         existait déjà.
	 */
	private boolean openTransactionIfNotActive()
	{
		final EntityTransaction transaction = this.getEntityManager().getTransaction();
		if (!this.getEntityManager().getTransaction().isActive())
		{
			// la transaction n'est pas ouverte, on ouvre !
			transaction.begin();
			return true;
		}
		else
		{
			// la transaction était active, donc ce n'est pas cet appel qui
			// l'a ouverte, donc on ne l'ouvre pas, et on ne commitera pas non
			// plus.
			return false;
		}
	}

	/**
	 * commit la transaction si elle est active.
	 *
	 */
	private void commitTransactionIfActive()
	{
		final EntityTransaction transaction = this.getEntityManager().getTransaction();
		if (this.getEntityManager().getTransaction().isActive())
		{
			transaction.commit();
		}
	}

	/**
	 * ouvre une transaction si aucune transaction n'est en cours.
	 */
	public void beginTransaction()
	{
		this.openTransactionIfNotActive();
	}

	/**
	 * termine la transaction si une transction est en cours.
	 */
	public void commitTransaction()
	{
		this.commitTransactionIfActive();
	}
}
