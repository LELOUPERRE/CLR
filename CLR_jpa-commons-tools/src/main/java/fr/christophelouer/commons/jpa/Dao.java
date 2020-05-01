package fr.christophelouer.commons.jpa;

import java.util.List;
import java.util.Map;

/**
 * interface DAO pour les entités, paramétrée par "generics".
 * T est le type paramétré des entités gérées par la DAO.
 *
 * 
 * @author CDT RBN
 * @version 2.0
 *
 */
public interface Dao<T>
{
	/**
	 * définitions des formats de message de logs.
	 * 
	 * @author CDT RBN
	 *
	 */
	public enum Message
	{
		MESSAGE_CREATE("Ecriture en base de %s."),
		MESSAGE_UPDATE("Mise à jour en base de %s."),
		MESSAGE_DELETE("Suppression en base de %s."),
		MESSAGE_CANNOT_CREATE_INSTANCE("Erreur pendant l'instanciation de la classe %s : %s.");
			
		private final String format;

		private Message(String format)
		{
			this.format = format;
		}
		
		public String getFormat()
		{
			return format;
		}
		
		@Override
		public String toString()
		{
			return format;
		}		
	}

	/**
	 * renvoie la classe du type paramétré de la classe &lt;T&gt;.
	 *
	 * @return la définition d'une classe (objet Class)
	 */
	Class<T> getBusinessClass();

	/**
	 * ajoute une nouvelle entity au contexte JPA (persist).
	 *
	 * @param t
	 *            instance d'une entité à ajouter.
	 */
	void create(T t);

	/**
	 * met à jour les données portées par l'instance de T (merge).
	 *
	 * @param t
	 */
	T update(T t);

	/**
	 * supprime l'instance de T.
	 *
	 * @param t
	 */
	void delete(T t);

	/**
	 * récupère toutes les instances de l'entité T.
	 *
	 * @return liste des instances, éventuellement triées.
	 */
	List<T> readAll();

	/**
	 * récupère toutes les instances de l'entité T, éventuellement triées par
	 * ordre descendant (DESC) sur une liste d'attributs.
	 *
	 * @return liste des instances, éventuellement triées.
	 */
	List<T> readAll(String... orderBy);

	/**
	 * lit une instance de T dont l'ID est passé en paramètre (find).
	 *
	 * @param id
	 * @return instance d'une entité
	 */
	T read(Object id);

	/**
	 * effectue une recherche simple sur la valeur d'un attribut avec
	 * l'opérateur d'égalité. Le résultat est trié (ou non) selon une liste
	 * d'attributs par ordre "ASC".
	 *
	 * @param parameterName
	 *            attribut testé.
	 * @param parameterValue
	 *            valeur à tester.
	 * @param orderBy
	 *            liste d'attributs pour le tri ascendant.
	 * @return liste triée des entités correpondant à la recherche.
	 */
	List<T> search(String parameterName, Object parameterValue, String... orderBy);

	/**
	 * cherche la première occurrence de la classe T où l'attribut passé en
	 * paramètre est égal à l'objet passé en paramètre.
	 *
	 * @param parameterName
	 *            attribut à tester
	 * @param parameterValue
	 *            valeur à trouver
	 * @return instance de T, ou alors retourne null si aucune occurence n'a été
	 *         trouvée..
	 */
	T searchFirstResult(String parameterName, Object parameterValue);

	/**
	 * retourne le nombre d'occurences (tuples) en base de données de la classe
	 * T.
	 *
	 * @return nombre d'occurences.
	 */
	Long count();

	/**
	 * permet de savoir si la table associée à cette classe ne contient aucun
	 * tuple en base de données.
	 *
	 * @return true si la table est vide, false dans le cas contraire.
	 */
	boolean isEmpty();

	/**
	 * retourne "true" si une entité contient un attribut dont la valeur est
	 * spécifiée.
	 *
	 * @param parameterName
	 *            attribut à tester
	 * @param parameterValue
	 *            valeur à tester
	 * @return vrai si une entité a été trouvé, false dans le cas contraire.
	 */
	boolean exists(String parameterName, Object parameterValue);

	/**
	 * Rafraichit l'instance en mémoire par rapport à la base de données.
	 *
	 * @param t
	 *            instance à relire depuis la base de données.
	 *
	 */
	void refresh(T t);

	/**
	 * Méthode qui effectue une recherche "paginée".
	 *
	 * @author SCH FRANQUIN
	 * @author CDT ROBIN
	 *
	 * @param filters
	 *            les filtres sur le champs de type String.
	 * @param sortColumn
	 *            colonne de tri.
	 * @param sortOrder
	 *            l'ordre de tri (ASC ou DESC).
	 * @param first
	 *            l'index de la première instance à retourner parmi celles qui
	 *            répondent à la requête.
	 * @param pageSize
	 *            le nombre maximum d'éléments à retourner.
	 * @return List<T> liste des entités qui répondent à l'ensemble des
	 *         critères, de manière paginée.
	 */
	List<T> advancedSearch(Map<String, String> filters, String sortColumn, SortOrder sortOrder, int first, int pageSize);

	Long count(Map<String, String> filters);

	/**
	 * retourne une liste d'objets corresponds au critère de restriction
	 * fournis: attribut = objet.
	 *
	 * @author CDT ROBIN
	 *
	 * @param restrictions
	 *            map de nom d'attributs d'entités JPA et de valeurs (instance
	 *            d'objets).
	 * @return liste des tuples correspondant aux critères.
	 */
	List<T> filteredSearch(Map<String, Object> restrictions);

}