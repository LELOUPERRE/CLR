package fr.christophelouer.dao.model.entities;

public final class EntitiesFactory {

	private EntitiesFactory() {
	}

	public static Personne fabriquerPersonne(String nom,String prenom) {
		return new Personne(nom, prenom);
	}
}
