package fr.christophelouer.commons.jpa;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.entetrs.commons.logs.LogUtils;
import net.entetrs.commons.logs.LogUtils.LogLevel;

/**
 * classe abstraite générique CRUD (DAO) pour les entités, paramétrée par
 * "generics" Cette classe abstraite ne sert qu'à factoriser du code entre les
 * DAO abstraites Java SE et Java EE.
 *
 * @author CDT RBN
 * @version 2.0
 *
 */
public abstract class AbstractDaoCommon<T> implements Dao<T>
{
	/**
	 * référence vers le log de cette classe.
	 */
	private final Log log = LogFactory.getLog(this.getClass());

	/**
	 * référence vers le PersistenceUnitUtil de l'entityManager courant. Cette
	 * référence est récupérée en PostConstruct.
	 */
	protected PersistenceUnitUtil puu;

	private EntityFactory<T> entityFactory;

	/**
	 * retourne l'instance de l'entity manager en cours. cette méthode est
	 * abstraite car seul un EJB du module EJB-JAR pourra obtenir l'injection
	 * correcte d'un entity manager.
	 *
	 * @return instance de l'entity manager.
	 *
	 */
	protected abstract EntityManager getEntityManager();

	/**
	 * méthode déclenchée après construction pour récupérer un
	 * PersistenceUnitUtil.
	 */
	@PostConstruct
	public void init()
	{
		/* TODO:coder ici un testeur JAVA SE / JAVA EE sur la présence de HttpServlet, par exemple pour
		 * afficher un gros warning d'usage de la mauvaise classe.  
		 */
		
		final Class<?> thisClass = this.getClass();
		final ParameterizedType paramType = (ParameterizedType) thisClass.getGenericSuperclass();
		@SuppressWarnings("unchecked")
		final Class<T> parameterizedType = (Class<T>) paramType.getActualTypeArguments()[0];
		this.entityFactory = new EntityFactory<>(parameterizedType);

		// si l'entity manager n'est pas null, alors on récupère une instance de
		// PersistenceUnitUtil.
		this.puu = (this.getEntityManager() == null) ? null : this.getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil();
	}

	/**
	 * crée une nouvelle instance du type paramétrée &lt;T&gt; de la classe.
	 *
	 * @return nouvelle instance
	 */
	public T newInstance()
	{
		return this.entityFactory.newInstance();
	}

	/* (non-Javadoc)
	 * @see net.entetrs.commons.jpa.Dao#getBusinessClass()
	 */
	@Override
	public Class<T> getBusinessClass()
	{
		return this.entityFactory.getConcreteClass();
	}

	/* (non-Javadoc)
	 * @see net.entetrs.commons.jpa.Dao#create(T)
	 */
	@Override
	public void create(final T t)
	{
		this.getEntityManager().persist(t);
		LogUtils.logFormat(this.log, LogLevel.INFO, Dao.Message.MESSAGE_CREATE.getFormat(), t);
	}

	/* (non-Javadoc)
	 * @see net.entetrs.commons.jpa.Dao#update(T)
	 */
	@Override
	public T update(final T t)
	{
		final T managedInstance = this.getEntityManager().merge(t);
		LogUtils.logFormat(this.log, LogLevel.INFO, Dao.Message.MESSAGE_UPDATE.getFormat(), managedInstance);
		return managedInstance;
	}

	/* (non-Javadoc)
	 * @see net.entetrs.commons.jpa.Dao#delete(T)
	 */
	@Override
	public void delete(final T t)
	{
		final T attachedEntity = this.getEntityManager().getReference(this.getBusinessClass(), this.puu.getIdentifier(t));
		this.getEntityManager().remove(attachedEntity);
		LogUtils.logFormat(this.log, LogLevel.INFO, Dao.Message.MESSAGE_DELETE.getFormat(), t);
	}

	/* (non-Javadoc)
	 * @see net.entetrs.commons.jpa.Dao#readAll()
	 */
	@Override
	public List<T> readAll()
	{
		return this.readAll((String[]) null);
	}

	/* (non-Javadoc)
	 * @see net.entetrs.commons.jpa.Dao#readAll(java.lang.String)
	 */
	@Override
	public List<T> readAll(final String... orderBy)
	{
		final CriteriaBuilder qb = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<T> c = qb.createQuery(this.getBusinessClass());
		final Root<T> from = c.from(this.getBusinessClass());
		this.addSort(qb, c, from, orderBy);
		final TypedQuery<T> query = this.getEntityManager().createQuery(c);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see net.entetrs.commons.jpa.Dao#read(java.lang.Object)
	 */
	@Override
	public T read(final Object id)
	{
		return this.getEntityManager().find(this.getBusinessClass(), id);
	}

	/* (non-Javadoc)
	 * @see net.entetrs.commons.jpa.Dao#search(java.lang.String, java.lang.Object, java.lang.String)
	 */
	@Override
	public List<T> search(final String parameterName, final Object parameterValue, final String... orderBy)
	{
		final CriteriaBuilder qb = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<T> c = qb.createQuery(this.getBusinessClass());
		final Root<T> from = c.from(this.getBusinessClass());
		final Predicate restriction = qb.equal(from.get(parameterName), parameterValue);
		c.where(restriction);
		this.addSort(qb, c, from, orderBy);
		final TypedQuery<T> query = this.getEntityManager().createQuery(c);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see net.entetrs.commons.jpa.Dao#searchFirstResult(java.lang.String, java.lang.Object)
	 */
	@Override
	public T searchFirstResult(final String parameterName, final Object parameterValue)
	{
		final CriteriaBuilder qb = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<T> c = qb.createQuery(this.getBusinessClass());
		final Root<T> from = c.from(this.getBusinessClass());
		final Predicate restriction = qb.equal(from.get(parameterName), parameterValue);
		c.where(restriction);
		final TypedQuery<T> query = this.getEntityManager().createQuery(c);
		query.setMaxResults(1);
		try
		{
			return query.getSingleResult();
		}
		catch (final javax.persistence.NoResultException ex) // NOSONAR
		{
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see net.entetrs.commons.jpa.Dao#count()
	 */
	@Override
	public Long count()
	{
		final CriteriaBuilder qb = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Long> c = qb.createQuery(Long.class);
		c.select(qb.count(c.from(this.getBusinessClass())));
		return this.getEntityManager().createQuery(c).getSingleResult();
	}

	/* (non-Javadoc)
	 * @see net.entetrs.commons.jpa.Dao#isEmpty()
	 */
	@Override
	public boolean isEmpty()
	{
		return this.count() == 0;
	}

	/* (non-Javadoc)
	 * @see net.entetrs.commons.jpa.Dao#exists(java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean exists(final String parameterName, final Object parameterValue)
	{
		final CriteriaBuilder qb = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Long> c = qb.createQuery(Long.class);
		c.select(qb.count(c.from(this.getBusinessClass())));
		final Root<T> from = c.from(this.getBusinessClass());
		final Predicate restriction = qb.equal(from.get(parameterName), parameterValue);
		c.where(restriction);
		final Long found = this.getEntityManager().createQuery(c).getSingleResult();
		return found != 0;
	}

	/* (non-Javadoc)
	 * @see net.entetrs.commons.jpa.Dao#refresh(T)
	 */
	@Override
	public void refresh(final T t)
	{
		final T managedInstance = this.getEntityManager().getReference(this.getBusinessClass(), this.puu.getIdentifier(t));
		this.getEntityManager().refresh(managedInstance);
	}

	/* (non-Javadoc)
	 * @see net.entetrs.commons.jpa.Dao#advancedSearch(java.util.Map, java.lang.String, net.entetrs.commons.jpa.SortOrder, int, int)
	 */
	@Override
	public List<T> advancedSearch(final Map<String, String> filters, final String sortColumn, final SortOrder sortOrder, final int first, final int pageSize)
	{
		final CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<T> c = cb.createQuery(this.getBusinessClass());
		final Root<T> root = c.from(this.getBusinessClass());
		final List<Predicate> predicates = new ArrayList<>();
		for (final Entry<String, String> entry : filters.entrySet())
		{
			// vérification : le % ?
			final Predicate p = cb.like(root.<String> get(entry.getKey()), entry.getValue() + "%");
			predicates.add(p);
		}
		c.where(predicates.toArray(new Predicate[0]));

		if (sortColumn != null)
		{
			final Order order  = (sortOrder == SortOrder.ASC) ? cb.asc(root.get(sortColumn)) : cb.desc(root.get(sortColumn));
			c.orderBy(order);
		}

		final TypedQuery<T> query = this.getEntityManager().createQuery(c);
		// pagination.
		query.setFirstResult(first);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	
	/* (non-Javadoc)
	 * @see net.entetrs.commons.jpa.Dao#count(java.util.Map)
	 */
	@Override
	public Long count(final Map<String, String> filters)
	{
		// criteriaBuilder.count(empCount)
		
		final CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Long> c = cb.createQuery(Long.class);	
		final Root<T> root = c.from(this.getBusinessClass());
		
		final List<Predicate> predicates = new ArrayList<>();
		for (final Entry<String, String> entry : filters.entrySet())
		{
			// vérification : le % ?
			final Predicate p = cb.like(root.<String> get(entry.getKey()), entry.getValue() + "%");
			predicates.add(p);
		}
		
		c.select(cb.count(root)).where(predicates.toArray(new Predicate[0]));
		
		
		final TypedQuery<Long> query = this.getEntityManager().createQuery(c);
		// pagination.
		
		return query.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see net.entetrs.commons.jpa.Dao#filteredSearch(java.util.Map)
	 */
	@Override
	public List<T> filteredSearch(final Map<String, Object> restrictions)
	{
		throw new UnimplementedOperation("filteredSeach");
	}

	/**
	 * retourne le log courant.
	 *
	 * @return instance du log.
	 */
	public Log getLog()
	{
		return this.log;
	}

	/**
	 * Méthode privée qui ajouter un critère de tri à une requête Criteria.
	 * Cette méthode est utilisée en interne par readAll() et search().
	 *
	 * @param criteriaBuilder
	 * @param query
	 * @param from
	 * @param orderBy
	 */
	private void addSort(final CriteriaBuilder criteriaBuilder, final CriteriaQuery<T> query, final Root<T> from, final String... orderBy)
	{
		if ((orderBy != null) && (orderBy.length > 0))
		{
			final List<Order> orders = new ArrayList<>();
			for (final String orderParameter : orderBy)
			{
				final Order order = criteriaBuilder.asc(from.get(orderParameter));
				orders.add(order);
			}
			query.orderBy(orders);
		}
	}

}
