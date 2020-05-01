package fr.christophelouer.commons.tests.dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import fr.christophelouer.commons.jpa.AbstractDaoStandardEdition;

public abstract class AbstractDao<T> extends AbstractDaoStandardEdition<T>
{

	private static final EntityManager EM = Persistence.createEntityManagerFactory("AbstractDaoTests").createEntityManager();

	@Override
	protected EntityManager getEntityManager()
	{
		return EM;
	}

}

