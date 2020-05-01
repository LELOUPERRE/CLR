package fr.christophelouer.dao.common.dao;

import java.util.List;

import fr.christophelouer.dao.common.dao.exceptions.DaoException;

/**
 * Interface CRUD générique
 * @author Christophe Louër
 *
 * @param <T>
 * Type de classe à persister
 * 
 */
public interface Dao<T,K> {
	/**
	 * Méthode proposant la création de l'objet passé en paramètre dans le contexte de persistance.
	 * @param t
	 * Objet à persister.
	 * @exception DaoException
	 * Exception levée si l'operation n'est pas réalisée.
	 * 
	 */
	public void create(T t) throws DaoException;
	/**
	 * Méthode proposant la lecture d'un objet persisté à partir de la clé primaire.
	 * @param k
	 * clé primaire
	 * @return
	 * Objet lu
	 */
	public T read(K k) throws DaoException;
	/**
	 * Méthode proposant la suppression de l'objet passé en paramètre dans le contexte de persistance.
	 * @param t
	 * Objet à supprimer
	 * Objet présent dans le contexte de persistance à supprimer.
	 * @exception DaoException
	 * Exception levée si l'operation n'est pas réalisée.
	 */
	public void delete(T t) throws DaoException;
	/**
	 * Méthode proposant la modification d'un objet existant dans le contexte de persistance.
	 * @param t
	 * Objet à modifier.
	 * Objet présent dans le contexte de persistance à modifier.
	 * @exception DaoException
	 * Exception levée si l'operation n'est pas réalisée.
	 */
	public void update(T t) throws DaoException;
	/**
	 * Méthode proposant la lecture de l'ensemble des objets présents dans le contexte de persistance.
	 * @return
	 * Retourne une liste contenant l'ensemble des objets présents dans le contexte de persistance.
	 */
	public List<T> readAll() throws DaoException;
	/**
	 * Méthode testant l'existance de l'objet passé en paramètre dans le contexte de persistance.
	 * @param t
	 * Objet recherché.
	 * @return
	 * Retourne 'true' si l'objet existe dans le contexte de persistance, 'false' sinon.
	 * @exception DaoException
	 * Exception levée si l'operation n'est pas réalisée.
	 */
	public boolean exist(T t) throws DaoException;
	/**
	 * méthode permettant l'initialisation d'une persistance.
	 */
	public default void init() {
		throw new UnsupportedOperationException();
	}

	
}
