package fr.christophelouer.dao.common.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import fr.christophelouer.dao.common.dao.exceptions.DaoException;

/**
 * Interface CRUD générique
 * @author Christophe Louër
 *
 * @param <T>
 * Type de classe à persister
 * 
 */
public interface DaoV2<T,K> {
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
	public Optional<T> read(K k) throws DaoException;
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
	 * Retourne l'ensemble des objets présents dans le contexte de persistance.
	 * @return
	 * Retourne une liste contenant l'ensemble des objets présents dans le contexte de persistance.
	 */
	public List<T> readAll() throws DaoException;
	/**
	 * Retourne dans un stream l'ensemble des objets présents dans le contexte de persistance.
	 * @return flux d'objets (Stream)
	 * @throws DaoException
	 * Exception levée si l'operation n'est pas réalisée.
	 */
	public Stream<T> readAllStream() throws DaoException;
	/**
	 * Identique l'existance ou non d'un l'objet dans le contexte de persistance.
	 * @param t
	 * Objet recherché.
	 * @return
	 * Retourne 'true' si l'objet existe dans le contexte de persistance, 'false' sinon.
	 * @exception DaoException
	 * Exception levée si l'operation n'est pas réalisée.
	 */
	public boolean exist(T t) throws DaoException;
	/**
	 * Initialise une entité dans le contexte de persistance.
	 */
	public default void init() {
		throw new UnsupportedOperationException();
	}
	public default void serialiser() {
		throw new UnsupportedOperationException();
	}
	public default void deserialiser() {
		throw new UnsupportedOperationException();
	}
	
	
}
