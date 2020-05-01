package fr.christophelouer.commons.jpa;

import java.util.function.Supplier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.entetrs.commons.logs.LogUtils;
import net.entetrs.commons.logs.LogUtils.LogLevel;
import net.entetrs.commons.uuid.GeneratedUUID;
import net.entetrs.commons.uuid.UUIDInjector;

/**
 * EntityFactory générique qui permet d'instancier la classe paramétrée T et
 * injecte un UUID sur un attribut annoté avec <code>@GenerateUUID</code>.
 *
 * @author CDT ROBIN
 * @see GeneratedUUID
 * @param <T>
 */

public class EntityFactory<T>
{
	public static final String MESSAGE_CANNOT_INSTANCIATE = "Instanciation impossible : %s (%s)";

	/**
	 * référence vers le log de cette classe.
	 */
	private final Log log = LogFactory.getLog(this.getClass());

	/**
	 * référence vers une classe concrète
	 */
	private Class<T> concreteClass;
	
	/**
	 * référence vers un supplier de T (lambda)
	 */
	private Supplier <T> supplier;

	/**
	 * instancie la factory de T.
	 *
	 * @param concreteClass
	 */
	public EntityFactory(final Class<T> concreteClass)
	{
		super();
		this.concreteClass = concreteClass;
	}
	
	/**
	 * instancie la factory de T.
	 *
	 * @param supplier
	 */
	public EntityFactory(final Supplier<T> supplier)
	{
		super();
		this.supplier = supplier;
	}

	/**
	 * construit un instance de la classe T et injecte des UUID si des champs sont annotés
	 * avec l'annotation GeneratedUUID.
	 *
	 * @return instance de T
	 * @see GeneratedUUID
	 */
	public T newInstance()
	{
		T t = null;
		try
		{			
			t =  (supplier != null) ? supplier.get() : this.concreteClass.newInstance();
			UUIDInjector.inject(t);			
		}
		catch (final RuntimeException | InstantiationException | IllegalAccessException ex)
		{
			LogUtils.logFormat(this.log, LogLevel.ERROR, EntityFactory.MESSAGE_CANNOT_INSTANCIATE, this.concreteClass.toString(), ex);		
		}
		return t;
	}

	public Class<T> getConcreteClass()
	{
		return this.concreteClass;
	}

}
